package me.ivan.ivancarpetaddition.mixins.rule.unbreakableHelmetInSunlight;

import me.ivan.ivancarpetaddition.IvanCarpetAdditionSettings;
import net.minecraft.entity.mob.ZombieEntity;
import net.minecraft.util.math.random.AbstractRandom;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(ZombieEntity.class)
public class ZombieEntityMixin {
    @Redirect(
            method = "tickMovement",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/util/math/random/AbstractRandom;nextInt(I)I"
            )
    )
    private int stopDamagingHelmet(AbstractRandom instance, int bound) {
        return IvanCarpetAdditionSettings.unbreakableHelmetInSunlight ? 0 : instance.nextInt(bound);
    }
}
