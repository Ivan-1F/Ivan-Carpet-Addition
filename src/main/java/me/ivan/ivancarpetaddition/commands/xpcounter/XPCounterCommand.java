package me.ivan.ivancarpetaddition.commands.xpcounter;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import me.ivan.ivancarpetaddition.IvanCarpetAdditionSettings;
import me.ivan.ivancarpetaddition.commands.AbstractCommand;
import me.ivan.ivancarpetaddition.utils.CarpetModUtil;
import me.ivan.ivancarpetaddition.utils.Messenger;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.BaseText;

import static com.mojang.brigadier.arguments.StringArgumentType.getString;
import static com.mojang.brigadier.arguments.StringArgumentType.string;
import static net.minecraft.server.command.CommandManager.*;
import static net.minecraft.command.CommandSource.suggestMatching;

public class XPCounterCommand extends AbstractCommand {
    private static final String NAME = "xpcounter";
    private static final XPCounterCommand INSTANCE = new XPCounterCommand();

    private XPCounterCommand() {
        super(NAME);
    }

    public static XPCounterCommand getInstance() {
        return INSTANCE;
    }

    public void registerCommand(CommandDispatcher<ServerCommandSource> dispatcher) {
        LiteralArgumentBuilder<ServerCommandSource> root = literal(NAME).requires((player) -> CarpetModUtil.canUseCommand(player, IvanCarpetAdditionSettings.xpCounter));

        root.executes((context) -> listAllCounters(context.getSource(), false));

        root.then(
                literal("reset")
                        .executes((ctx) -> resetCounter(ctx.getSource(), (ServerPlayerEntity) null))
        );

        root.then(
                argument("player", string())
                        .suggests((c, b) -> suggestMatching(XPCounter.getPlayers(), b))
                        .executes((ctx) -> displayCounter(ctx.getSource(), getString(ctx, "player"), false))
        );

        root.then(
                argument("player", string())
                        .suggests((c, b) -> suggestMatching(XPCounter.getPlayers(), b))
                        .then(
                                literal("reset")
                                        .executes((ctx) -> resetCounter(ctx.getSource(), getString(ctx, "player")))
                        )
        );

        root.then(
                argument("player", string())
                        .suggests((c, b) -> suggestMatching(XPCounter.getPlayers(), b))
                        .then(
                                literal("realtime")
                                        .executes((ctx) -> displayCounter(ctx.getSource(), getString(ctx, "player"), true))
                        )
        );

        dispatcher.register(root);
    }

    private static int listAllCounters(ServerCommandSource source, boolean realtime) {
        for (BaseText message : XPCounter.formatAll(realtime)) {
            Messenger.tell(source, message);
        }
        return 1;
    }

    private ServerPlayerEntity getPlayerFromName(String name) {
        return XPCounter.getAttachedServer().getPlayerManager().getPlayer(name);
    }

    private int resetCounter(ServerCommandSource source, String player) {
        return resetCounter(source, getPlayerFromName(player));
    }

    private int resetCounter(ServerCommandSource source, ServerPlayerEntity player) {
        if (player == null) {
            XPCounter.resetAll();
        } else {
            XPCounter counter = XPCounter.getCounter(player);
            counter.reset();
        }
        XPCounter.sendRestarted(source, player);
        return 1;
    }

    private int displayCounter(ServerCommandSource source, String player, boolean realtime) {
        return displayCounter(source, getPlayerFromName(player), realtime);
    }

    private int displayCounter(ServerCommandSource source, ServerPlayerEntity player, boolean realtime) {
        XPCounter counter = XPCounter.getCounter(player);

        for (BaseText message : counter.format(realtime, false)) {
            Messenger.tell(source, message);
        }
        return 1;
    }
}
