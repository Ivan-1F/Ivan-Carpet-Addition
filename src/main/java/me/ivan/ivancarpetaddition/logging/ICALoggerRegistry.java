package me.ivan.ivancarpetaddition.logging;

import carpet.logging.HUDLogger;
import carpet.logging.Logger;
import carpet.logging.LoggerRegistry;
import me.ivan.ivancarpetaddition.IvanCarpetAdditionServer;
import me.ivan.ivancarpetaddition.commands.xpcounter.XPCounter;
import me.ivan.ivancarpetaddition.logging.loggers.xpcounter.XPCounterHUDLogger;

import java.lang.reflect.Field;

public class ICALoggerRegistry {
    public static boolean __xpcounter;

    public static void registerLoggers() {
        LoggerRegistry.registerLogger(XPCounterHUDLogger.NAME, standardHUDLogger(XPCounterHUDLogger.NAME, null, XPCounter.getPlayers().toArray(String[]::new)));
    }

    public static Field getLoggerField(String logName) {
        try {
            return ICALoggerRegistry.class.getField("__" + logName);
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(String.format("Failed to get logger field \"%s\" @ %s", logName, IvanCarpetAdditionServer.fancyName));
        }
    }

    public static Logger standardLogger(String logName, String def, String[] options) {
        return new Logger(getLoggerField(logName), logName, def, options);
    }

    public static HUDLogger standardHUDLogger(String logName, String def, String[] options) {
        return new HUDLogger(getLoggerField(logName), logName, def, options);
    }
}
