package me.ivan.ivancarpetaddition.helpers.xpcounter.rule.mobSpawningRestriction;

import me.ivan.ivancarpetaddition.IvanCarpetAdditionSettings;
import net.minecraft.entity.Entity;

import java.util.Arrays;

public class MobSpawningRestrictionHelper {
    public static boolean canSpawn(Entity entity) {
        String name = entity.getType().getTranslationKey().split("\\.")[2];
        if (IvanCarpetAdditionSettings.mobSpawningRestrictionMode.equals("blacklist")) {
            return !Arrays.asList(IvanCarpetAdditionSettings.mobBlackList.split(",")).contains(name);
        }
        if (IvanCarpetAdditionSettings.mobSpawningRestrictionMode.equals("whitelist")) {
            return Arrays.asList(IvanCarpetAdditionSettings.mobWhiteList.split(",")).contains(name);
        }
        return true;
    }
}
