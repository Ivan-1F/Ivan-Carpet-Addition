package me.ivan.ivancarpetaddition.mixins.rule.unbreakableHelmetInSunlight;

import me.ivan.ivancarpetaddition.IvanCarpetAdditionSettings;
import net.minecraft.entity.mob.AbstractSkeletonEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

//#if MC >= 11900
//$$ import net.minecraft.util.math.random.Random;
//#else
import java.util.Random;
//#endif

@Mixin(AbstractSkeletonEntity.class)
public class AbstractSkeletonEntityMixin {
    @Redirect(
            method = "tickMovement",
            at = @At(
                    value = "INVOKE",
                    //#if MC >= 11900
                    //$$ target = "Lnet/minecraft/util/math/random/Random;nextInt(I)I"
                    //#else
                    target = "Ljava/util/Random;nextInt(I)I"
                    //#endif
            )
    )
    //#disable-remap
    private int stopDamagingHelmet(Random instance, int bound) {
    //#enable-remap
        return IvanCarpetAdditionSettings.unbreakableHelmetInSunlight ? 0 : instance.nextInt(bound);
    }
}
