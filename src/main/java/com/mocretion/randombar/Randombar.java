package com.mocretion.randombar;

import com.mocretion.randombar.command.RandomCommand;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.command.v1.ClientCommandManager;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.text.LiteralText;
import net.minecraft.util.ActionResult;
import org.lwjgl.glfw.GLFW;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Random;

public class Randombar implements ModInitializer {

	private static KeyBinding toggleRndKey;
	private boolean rndEnabled;
	private int rndStart = 7;
	private int rndEnd = 9;

	public static final Logger LOGGER = LoggerFactory.getLogger("modid");

	@Override
	public void onInitialize() {
		rndEnabled = false;

		ClientCommandManager.DISPATCHER.register(
				ClientCommandManager.literal("randombar")
						.then(ClientCommandManager.argument("StartSlot", IntegerArgumentType.integer(1, 9))
								.then(ClientCommandManager.argument("EndSlot", IntegerArgumentType.integer(1, 9))
										.executes(new RandomCommand(this)))));

		// Register hotkey
		toggleRndKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
				"Toggle Random Hotbar Selection",
				InputUtil.Type.KEYSYM,
				GLFW.GLFW_KEY_R,
				"Randombar"
		));

		// Check for toggle hotkey
		ClientTickEvents.END_CLIENT_TICK.register(client -> {
			if(client.player == null)
				return;

			while (toggleRndKey.wasPressed()) {
				rndEnabled = !rndEnabled;
				if(rndEnabled)
					client.player.sendMessage(new LiteralText("Randombar enabled. Selected Slots: " +
							rndStart + " - " + rndEnd), false);
				else
					client.player.sendMessage(new LiteralText("Randombar disabled."), false);
			}
		});

		// On placing a block
		UseBlockCallback.EVENT.register((player, world, hand, hitResult) ->
		{
			if(!rndEnabled)  // If rndhotbar is disabled
				return ActionResult.PASS;

			Random rnd = new Random();
			player.getInventory().selectedSlot = rnd.nextInt(rndStart - 1, rndEnd);

			return ActionResult.PASS;
		});
	}

	public void changeRndSlots(int start, int end){
		rndStart = start;
		rndEnd = end;
	}

	public void enableRnd(boolean enable){
		rndEnabled = enable;
	}

	public boolean rndIsEnabled(){
		return rndEnabled;
	}
}
