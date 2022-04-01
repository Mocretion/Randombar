package com.mocretion.randombar;

import com.mocretion.randombar.command.RandomCommand;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.command.v1.ClientCommandManager;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.command.v1.CommandRegistrationCallback;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.fabricmc.fabric.api.event.player.UseItemCallback;
import net.fabricmc.fabric.mixin.client.keybinding.KeyCodeAccessor;
import net.minecraft.client.Keyboard;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.MinecraftClientGame;
import net.minecraft.client.WindowSettings;
import net.minecraft.client.gui.screen.option.OptionsScreen;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.option.GameOptions;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.command.argument.ArgumentTypes;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.TypedActionResult;
import org.lwjgl.glfw.GLFW;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Random;

public class Randombar implements ModInitializer {

	public int rndStart = 0;
	public int rndEnd = 0;

	public static final Logger LOGGER = LoggerFactory.getLogger("modid");

	@Override
	public void onInitialize() {
		ClientCommandManager.DISPATCHER.register(
				ClientCommandManager.literal("randombar")
						.then(ClientCommandManager.argument("StartSlot", IntegerArgumentType.integer(0, 9))
								.then(ClientCommandManager.argument("EndSlot", IntegerArgumentType.integer(1, 9))
										.executes(new RandomCommand(this)))));

		UseBlockCallback.EVENT.register((player, world, hand, hitResult) ->
		{
			if(rndStart < 1)  // If rndhotbar is disabled
				return ActionResult.PASS;

			//if( KeyBinding.onKeyPressed(Key););)

			//InputUtil.fromTranslationKey("key.use").getCode();
			Random rnd = new Random();
			int slotToSelect = rnd.nextInt(rndStart - 1, rndEnd);

			while(player.getInventory().selectedSlot != slotToSelect){
				player.getInventory().scrollInHotbar(1);
			}
			return ActionResult.PASS;
		});
	}
}
