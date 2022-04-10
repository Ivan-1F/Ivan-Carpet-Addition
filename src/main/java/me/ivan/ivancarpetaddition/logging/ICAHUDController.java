package me.ivan.ivancarpetaddition.logging;

import carpet.logging.LoggerRegistry;
import me.ivan.ivancarpetaddition.logging.loggers.AbstractHUDLogger;
import me.ivan.ivancarpetaddition.logging.loggers.xpcounter.XPCounterHUDLogger;
import net.minecraft.server.MinecraftServer;

public class ICAHUDController {
    public static void updateHUD(MinecraftServer server) {
        doHudLogging(ICALoggerRegistry.__xpcounter, XPCounterHUDLogger.NAME, XPCounterHUDLogger.getInstance());
    }

    private static void doHudLogging(boolean condition, String loggerName, AbstractHUDLogger logger) {
        if (condition) {
            LoggerRegistry.getLogger(loggerName).log(logger::onHudUpdate);
        }
    }
}
