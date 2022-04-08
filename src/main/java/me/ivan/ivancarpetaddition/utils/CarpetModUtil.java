package me.ivan.ivancarpetaddition.utils;

import net.minecraft.server.command.ServerCommandSource;

public class CarpetModUtil {
    /**
     * Copied from {@link carpet.settings.SettingsManager#canUseCommand} in fabric-carpet 1.4.19
     * Also prevents merge conflicts
     */
    public static boolean canUseCommand(ServerCommandSource source, Object commandLevel) {
        if (commandLevel instanceof Boolean) return (Boolean) commandLevel;
        String commandLevelString = commandLevel.toString();
        switch (commandLevelString) {
            case "true":
                return true;
            case "false":
                return false;
            case "ops":
                return source.hasPermissionLevel(2); // typical for other cheaty commands
            case "0":
            case "1":
            case "2":
            case "3":
            case "4":
                return source.hasPermissionLevel(Integer.parseInt(commandLevelString));
        }
        return false;
    }
}
