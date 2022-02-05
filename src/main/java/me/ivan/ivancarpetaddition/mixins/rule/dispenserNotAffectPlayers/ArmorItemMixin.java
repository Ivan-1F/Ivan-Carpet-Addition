package me.ivan.ivancarpetaddition.mixins.rule.dispenserNotAffectPlayers;

import me.ivan.ivancarpetaddition.IvanCarpetAdditionSettings;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ArmorItem;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.List;
import java.util.function.Predicate;

@Mixin(ArmorItem.class)
public class ArmorItemMixin {
    @Redirect(method = "dispenseArmor", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;getEntities(Ljava/lang/Class;Lnet/minecraft/util/math/Box;Ljava/util/function/Predicate;)Ljava/util/List;"))
    private static <T extends Entity> List<T> removePlayerFromList(World instance, Class<? extends T> entityClass, Box box, @Nullable Predicate<? super T> predicate) {
        List<T> entities = instance.getEntities(entityClass, box, predicate);
        if (IvanCarpetAdditionSettings.dispenserNotAffectPlayers) {
            entities.removeIf(item -> item instanceof PlayerEntity);
        }
        return entities;
    }
}
