package me.ivan.ivancarpetaddition.mixins.rule.mobAlwaysPickUpLoot;

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
            method = "initialize",
            at = @At(
                    value = "INVOKE",
                    //#if MC >= 11900
                    //$$ target = "Lnet/minecraft/util/math/random/Random;nextFloat()F",
                    //#else
                    target = "Ljava/util/Random;nextFloat()F",
                    //#endif
                    ordinal = 0
            )
    )
    //#disable-remap
    private float alwaysReturnsNegative(Random instance) {
    //#enable-remap
        if (IvanCarpetAdditionSettings.mobAlwaysPickUpLoot) {
            return -1;
        }
        // Vanilla
        return instance.nextFloat();
    }
}
