package me.ivan.ivancarpetaddition.logging.compat;

import carpet.logging.Logger;

//#if MC < 11500
//$$ import me.ivan.ivancarpetaddition.mixins.carpet.LoggerRegistryInvoker;
//#endif

/**
 * Used in mc 1.14.4 where carpet doesn't provide logging support for carpet extensions
 */
public class LoggerRegistry {
    // a wrapped method to reduce merge conflicts
    public static void registerLogger(String name, Logger logger) {
        //#if MC < 11500
        //$$ LoggerRegistryInvoker.callRegisterLogger(name, logger);
        //#endif
    }
}
