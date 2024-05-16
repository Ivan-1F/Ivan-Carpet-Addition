package me.ivan.ivancarpetaddition.mixins.rule.dispensersNotAffectPlayers;

import me.ivan.ivancarpetaddition.IvanCarpetAdditionSettings;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ArmorItem;
import net.minecraft.util.math.Box;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

//#if MC >= 11600
//$$ import net.minecraft.server.world.ServerWorld;
//#else
import net.minecraft.world.World;
//#endif

import java.util.List;
import java.util.function.Predicate;

@Mixin(ArmorItem.class)
public class ArmorItemMixin {
    @Redirect(
            method = "dispenseArmor",
            at = @At(
                    value = "INVOKE",
                    //#if MC >= 11600
                    //$$ target = "Lnet/minecraft/server/world/ServerWorld;getEntitiesByClass(Ljava/lang/Class;Lnet/minecraft/util/math/Box;Ljava/util/function/Predicate;)Ljava/util/List;"
                    //#else
                    target = "Lnet/minecraft/world/World;getEntities(Ljava/lang/Class;Lnet/minecraft/util/math/Box;Ljava/util/function/Predicate;)Ljava/util/List;"
                    //#endif
            )
    )
    //#if MC >= 11700
    //$$ @SuppressWarnings("unchecked")
    //#endif
    private static <T extends Entity> List<T> removePlayer(
            //#if MC >= 11600
            //$$ ServerWorld instance,
            //#else
            World instance,
            //#endif
            Class<? extends T> entityClass,
            Box box,
            @Nullable Predicate<? super T> predicate
    ) {
        // Vanilla
        //#if MC >= 11700
        //$$ List<T> entities = (List<T>) instance.getEntitiesByClass(entityClass, box, predicate);
        //#elseif MC >= 11600
        //$$ List<T> entities = instance.getEntitiesByClass(entityClass, box, predicate);
        //#else
        List<T> entities = instance.getEntities(entityClass, box, predicate);
        //#endif
        if (IvanCarpetAdditionSettings.dispensersNotAffectPlayers) {
            entities.removeIf(entity -> entity instanceof PlayerEntity);
        }
        return entities;
    }
}
