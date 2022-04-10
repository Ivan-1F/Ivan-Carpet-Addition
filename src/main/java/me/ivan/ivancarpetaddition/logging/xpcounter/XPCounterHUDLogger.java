package me.ivan.ivancarpetaddition.logging.xpcounter;

import me.ivan.ivancarpetaddition.commands.xpcounter.XPCounter;
import me.ivan.ivancarpetaddition.logging.loggers.AbstractHUDLogger;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.BaseText;

import java.util.Arrays;
import java.util.Objects;

public class XPCounterHUDLogger extends AbstractHUDLogger {
    public static final String NAME = "xpcounter";
    public static final XPCounterHUDLogger INSTANCE = new XPCounterHUDLogger();

    private XPCounterHUDLogger() {
        super(NAME);
    }

    public static XPCounterHUDLogger getInstance() {
        return INSTANCE;
    }

    @Override
    public BaseText[] onHudUpdate(String option, PlayerEntity playerEntity) {
        if (option == null) {
            return new BaseText[]{};
        }
        return Arrays.stream(option.split(MULTI_OPTION_SEP_REG))
                .map(counterName -> XPCounter.getCounter(getPlayerFromName(counterName)))
                .filter(Objects::nonNull)
                .map(counter -> counter.format(false, true))
                .map(text -> text.get(0))   // brief results should be singleton lists
                .toArray(BaseText[]::new);
    }

    private static ServerPlayerEntity getPlayerFromName(String name) {
        return XPCounter.getAttachedServer().getPlayerManager().getPlayer(name);
    }
}
