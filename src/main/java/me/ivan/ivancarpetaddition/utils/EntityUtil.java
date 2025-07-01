package me.ivan.ivancarpetaddition.utils;

import net.minecraft.entity.Entity;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;

public class EntityUtil {
    public static World getEntityWorld(@NotNull Entity entity) {
        //#if MC >= 12106
        //$$ return entity.getWorld();
        //#else
        return entity.getEntityWorld();
        //#endif
    }
}
