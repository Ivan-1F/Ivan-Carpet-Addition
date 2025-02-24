package me.ivan.ivancarpetaddition.mixins.rule.dispensersNotAffectPlayers;

import me.fallenbreath.conditionalmixin.api.annotation.Condition;
import me.fallenbreath.conditionalmixin.api.annotation.Restriction;
import me.ivan.ivancarpetaddition.IvanCarpetAdditionSettings;
import me.ivan.ivancarpetaddition.utils.ModIds;
import net.minecraft.block.dispenser.EquippableDispenserBehavior;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Box;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.List;
import java.util.function.Predicate;

@Restriction(require = @Condition(value = ModIds.minecraft, versionPredicates = ">=1.21.3"))
@Mixin(EquippableDispenserBehavior.class)
public class EquippableDispenserBehaviorMixin {
    @Redirect(
            method = "dispense",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/server/world/ServerWorld;getEntitiesByClass(Ljava/lang/Class;Lnet/minecraft/util/math/Box;Ljava/util/function/Predicate;)Ljava/util/List;")
    )
    private static <T extends Entity> List<T> removePlayer(ServerWorld instance, Class<T> entityClass, Box box, Predicate<? super T> predicate) {
        List<T> entities = instance.getEntitiesByClass(entityClass, box, predicate);
        if (IvanCarpetAdditionSettings.dispensersNotAffectPlayers) {
            entities.removeIf(entity -> entity instanceof PlayerEntity);
        }
        return entities;
    }
}
