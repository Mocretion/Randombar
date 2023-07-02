package com.mocretion.randombar.command;

import com.mocretion.randombar.Randombar;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.context.CommandContext;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.minecraft.text.Text;

public class RandomCommand implements Command<FabricClientCommandSource> {

    private final Randombar bar;
    public RandomCommand(Randombar _bar){
        bar = _bar;
    }

    @Override
    public int run(CommandContext<FabricClientCommandSource> context) {
        if(context.getArgument("StartSlot", Integer.class) >= context.getArgument("EndSlot", Integer.class)) {
            context.getSource().sendError(Text.of("The second argument must be bigger than the first one!"));
            return -1;
        }

        final int start = context.getArgument("StartSlot", Integer.class);
        final int end = context.getArgument("EndSlot", Integer.class);
        bar.changeRndSlots(start, end);

        if(bar.rndIsEnabled()){
            context.getSource().sendFeedback(Text.of("Slots changed to " + start + " - " + end + "."));
        }else{
            bar.enableRnd(true);
            context.getSource().sendFeedback(Text.of("Randombar enabled. Selected Slots: " +
                    start + " - " + end));
        }
        return 1;
    }
}
