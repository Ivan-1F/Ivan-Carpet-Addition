package me.ivan.ivancarpetaddition;

import carpet.settings.Rule;

import static carpet.settings.RuleCategory.CREATIVE;

public class IvanCarpetAdditionSettings {
    public static final String ICA = "ICA";

    @Rule(
            desc = "Set a custom version on client trying to connect to the server",
            extra = "use '_' to disable",
            options = "_",
            strict = false,
            category = {ICA, CREATIVE}
    )
    public static String customVersion = "_";
}
