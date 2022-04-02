package com.mocretion.randombar.command;

import com.mocretion.randombar.Randombar;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.context.CommandContext;
import net.fabricmc.fabric.api.client.command.v1.FabricClientCommandSource;
import net.minecraft.text.LiteralText;

public class RandomCommand implements Command<FabricClientCommandSource> {

    private final Randombar bar;
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
        if(bar.rndStart > 0)
            context.getSource().sendFeedback(new LiteralText("Randombar enabled."));
        else
            context.getSource().sendFeedback(new LiteralText("Randombar disabled."));
        return 1;
    }
}
