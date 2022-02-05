package me.ivan.ivancarpetaddition.mixins.rule.mobAlwaysPickUpLoot;

import me.ivan.ivancarpetaddition.IvanCarpetAdditionSettings;
import net.minecraft.entity.mob.AbstractSkeletonEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.Random;

@Mixin(AbstractSkeletonEntity.class)
public class AbstractSkeletonEntityMixin {
    @Redirect(method = "initialize", at = @At(value = "INVOKE", target = "Ljava/util/Random;nextFloat()F"))
    private float alwaysReturnNegative(Random instance) {
        if (IvanCarpetAdditionSettings.mobAlwaysPickUpLoot) {
            return -1;
        }
        // Vanilla
        return instance.nextFloat();
    }
}
