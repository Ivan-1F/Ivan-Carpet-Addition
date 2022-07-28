package me.ivan.ivancarpetaddition.logging.loggers;

import carpet.logging.Logger;
import me.ivan.ivancarpetaddition.logging.ICALoggerRegistry;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.BaseText;

public abstract class AbstractHUDLogger extends AbstractLogger {
    public AbstractHUDLogger(String name, boolean strictOption) {
        super(name, strictOption);
    }

    public abstract BaseText[] onHudUpdate(String option, PlayerEntity playerEntity);

    @Override
    public Logger createCarpetLogger() {
        return ICALoggerRegistry.standardHUDLogger(this.getName(), this.getDefaultLoggingOption(), this.getSuggestedLoggingOption());
    }
}
