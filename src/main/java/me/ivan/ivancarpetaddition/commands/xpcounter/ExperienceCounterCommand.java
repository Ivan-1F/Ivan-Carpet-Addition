package me.ivan.ivancarpetaddition.commands.xpcounter;

import carpet.CarpetSettings;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import me.ivan.ivancarpetaddition.IvanCarpetAdditionSettings;
import me.ivan.ivancarpetaddition.helpers.xpcounter.ExperienceCounter;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.BaseText;

public class ExperienceCounterCommand {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        LiteralArgumentBuilder<ServerCommandSource> literalArgumentBuilder = CommandManager.literal("xpcounter").executes((context)
                -> listAllCounters(context.getSource(), false)).requires((player) ->
                IvanCarpetAdditionSettings.experienceCounter);

        dispatcher.register(literalArgumentBuilder);
    }

    private static int listAllCounters(ServerCommandSource source, boolean realtime) {
        try {
            ExperienceCounter counter = ExperienceCounter.getCounter(source.getPlayer());
            for (BaseText message: counter.format(realtime)) {
                source.sendFeedback(message, false);
            }
        } catch (CommandSyntaxException e) {
            e.printStackTrace();
        }
        return 1;
    }
}
