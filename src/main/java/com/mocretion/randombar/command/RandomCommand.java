package com.mocretion.randombar.command;

import com.mocretion.randombar.Randombar;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.fabricmc.fabric.api.client.command.v1.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v1.FabricClientCommandSource;
import net.minecraft.client.network.ClientCommandSource;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.LiteralText;

public class RandomCommand implements Command<FabricClientCommandSource> {

    private Randombar bar;
    public RandomCommand(Randombar _bar){
        bar = _bar;
    }

    @Override
    public int run(CommandContext<FabricClientCommandSource> context) {
        if(context.getArgument("StartSlot", Integer.class) >= context.getArgument("EndSlot", Integer.class)) {
            context.getSource().sendError(new LiteralText("The second argument must be bigger than the first one!"));
            return -1;
        }

        bar.rndStart = context.getArgument("StartSlot", Integer.class);
        bar.rndEnd = context.getArgument("EndSlot", Integer.class);
        return 1;
    }
}
