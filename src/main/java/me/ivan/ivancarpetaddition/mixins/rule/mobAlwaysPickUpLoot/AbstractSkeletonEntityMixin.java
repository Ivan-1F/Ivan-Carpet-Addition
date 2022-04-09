package me.ivan.ivancarpetaddition.mixins.rule.mobAlwaysPickUpLoot;

import me.ivan.ivancarpetaddition.IvanCarpetAdditionSettings;
import net.minecraft.entity.mob.AbstractSkeletonEntity;
import net.minecraft.util.math.random.AbstractRandom;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(AbstractSkeletonEntity.class)
public class AbstractSkeletonEntityMixin {
    @Redirect(
            method = "initialize",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/util/math/random/AbstractRandom;nextFloat()F",
                    ordinal = 0
            )
    )
    private float alwaysReturnsNegative(AbstractRandom instance) {
        if (IvanCarpetAdditionSettings.mobAlwaysPickUpLoot) {
            return -1;
        }
        // Vanilla
        return instance.nextFloat();
    }
}
