package me.ivan.ivancarpetaddition.logging;

import carpet.logging.HUDLogger;
import carpet.logging.Logger;
//#if MC >= 11500
import carpet.logging.LoggerRegistry;
//#else
//$$ import me.ivan.ivancarpetaddition.logging.compat.ExtensionHUDLogger;
//$$ import me.ivan.ivancarpetaddition.logging.compat.ExtensionLogger;
//$$ import me.ivan.ivancarpetaddition.logging.compat.LoggerRegistry;
//#endif
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

    public static Logger standardLogger(
            String logName, String def, String[] options
            //#if MC >= 11700
            //$$ , boolean strictOptions
            //#endif
    ) {
        return new
                //#if MC >= 11500
                Logger
                //#else
                //$$ ExtensionLogger
                //#endif
                (
                        getLoggerField(logName), logName, def, options
                        //#if MC >= 11700
                        //$$ , strictOptions
                        //#endif
                );
    }

    public static HUDLogger standardHUDLogger(
            String logName, String def, String[] options
            //#if MC >= 11700
            //$$ , boolean strictOptions
            //#endif
    ) {
        return new
                //#if MC >= 11500
                HUDLogger
                //#else
                //$$ ExtensionHUDLogger
                //#endif
                (
                        getLoggerField(logName), logName, def, options
                        //#if MC >= 11700
                        //$$ , strictOptions
                        //#endif
                );
    }
}
