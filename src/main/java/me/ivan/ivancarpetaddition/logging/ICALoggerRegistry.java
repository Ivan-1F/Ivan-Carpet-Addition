package me.ivan.ivancarpetaddition.logging;

import carpet.logging.HUDLogger;
import carpet.logging.Logger;
import carpet.logging.LoggerRegistry;
import me.ivan.ivancarpetaddition.IvanCarpetAdditionServer;
import me.ivan.ivancarpetaddition.logging.loggers.AbstractLogger;
import me.ivan.ivancarpetaddition.logging.loggers.shulker.ShulkerLogger;
import me.ivan.ivancarpetaddition.logging.loggers.xpcounter.XPCounterHUDLogger;

import java.lang.reflect.Field;

public class ICALoggerRegistry {
    public static boolean __xpcounter;
    public static boolean __shulker;

    public static void registerLoggers() {
        register(XPCounterHUDLogger.getInstance());
        register(ShulkerLogger.getInstance());
    }

    private static void register(AbstractLogger logger) {
        register(logger.createCarpetLogger());
    }

    private static void register(Logger logger) {
        LoggerRegistry.registerLogger(logger.getLogName(), logger);
    }

    public static Field getLoggerField(String logName) {
        try {
            return ICALoggerRegistry.class.getField("__" + logName);
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(String.format("Failed to get logger field \"%s\" @ %s", logName, IvanCarpetAdditionServer.fancyName));
        }
    }

    public static Logger standardLogger(String logName, String def, String[] options, boolean strictOptions) {
        return new Logger(getLoggerField(logName), logName, def, options, strictOptions);
    }

    public static HUDLogger standardHUDLogger(String logName, String def, String[] options, boolean strictOptions) {
        return new HUDLogger(getLoggerField(logName), logName, def, options, strictOptions);
    }
}
