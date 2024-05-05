package me.ivan.ivancarpetaddition.mixins.rule.mendableSnowGolem;

import me.ivan.ivancarpetaddition.IvanCarpetAdditionSettings;
import me.ivan.ivancarpetaddition.helpers.rule.mendableGolem.MendableGolemHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.SnowGolemEntity;
import net.minecraft.entity.thrown.SnowballEntity;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.hit.EntityHitResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

//#if MC < 11600
import net.minecraft.util.hit.HitResult;
//#endif

@Mixin(SnowballEntity.class)
public class SnowballEntityMixin {
    @Inject(
            //#if MC >= 11600
            //$$ method = "onEntityHit",
            //#else
            method = "onCollision",
            //#endif
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/entity/Entity;damage(Lnet/minecraft/entity/damage/DamageSource;F)Z"
            ),
            cancellable = true
    )
    private void onCollision(
            //#if MC >= 11600
            //$$ EntityHitResult hitResult,
            //#else
            HitResult hitResult,
            //#endif
            CallbackInfo ci
    ) {
        //#if MC >= 11600
        //$$ Entity entity = hitResult.getEntity();
        //#else
        Entity entity = ((EntityHitResult) hitResult).getEntity();
        //#endif
        if (entity instanceof SnowGolemEntity && IvanCarpetAdditionSettings.mendableSnowGolem) {
            SnowGolemEntity snowGolem = (SnowGolemEntity) entity;
            SnowballEntity snowball = (SnowballEntity) (Object) this;
            MendableGolemHelper.mendGolem(
                    snowGolem,
                    1.0F,
                    SoundEvents.BLOCK_SNOW_PLACE
            );
            if (!snowball.world.isClient) {
                snowball.world.sendEntityStatus(snowball, (byte) 3);
                //#if MC >= 11700
                //$$ snowball.discard();
                //#else
                snowball.remove();
                //#endif
            }
            ci.cancel();
        }
    }
}
