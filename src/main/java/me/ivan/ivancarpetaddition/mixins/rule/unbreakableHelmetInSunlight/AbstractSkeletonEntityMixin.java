package me.ivan.ivancarpetaddition.mixins.rule.unbreakableHelmetInSunlight;

import me.ivan.ivancarpetaddition.IvanCarpetAdditionSettings;
import net.minecraft.entity.mob.AbstractSkeletonEntity;
import net.minecraft.util.math.random.Random;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(AbstractSkeletonEntity.class)
public class AbstractSkeletonEntityMixin {
    @Redirect(
            method = "tickMovement",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/util/math/random/Random;nextInt(I)I"
            )
    )
    private int stopDamagingHelmet(Random instance, int bound) {
        return IvanCarpetAdditionSettings.unbreakableHelmetInSunlight ? 0 : instance.nextInt(bound);
    }
}
