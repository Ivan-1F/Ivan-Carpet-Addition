package me.ivan.ivancarpetaddition.mixins.rule.mendableSnowGolem;

import me.ivan.ivancarpetaddition.IvanCarpetAdditionSettings;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.SnowGolemEntity;
import net.minecraft.entity.thrown.SnowballEntity;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Random;

@Mixin(SnowballEntity.class)
public class SnowballEntityMixin {
    @Inject(method = "onCollision", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/Entity;damage(Lnet/minecraft/entity/damage/DamageSource;F)Z"), cancellable = true)
    private void onCollision(HitResult hitResult, CallbackInfo ci) {
        Entity entity = ((EntityHitResult)hitResult).getEntity();
        if (entity instanceof SnowGolemEntity && IvanCarpetAdditionSettings.mendableSnowGolem) {
            SnowGolemEntity snowGolem = (SnowGolemEntity) entity;
            SnowballEntity snowball = (SnowballEntity)(Object) this;
            float f = snowGolem.getHealth();
            snowGolem.heal(1.0F);
            if (snowGolem.getHealth() != f) {
                float g = 1.0F + (new Random().nextFloat() - new Random().nextFloat()) * 0.2F;
                snowGolem.playSound(SoundEvents.BLOCK_SNOW_PLACE, 1.0F, g);
            }
            if (!snowball.world.isClient) {
                snowball.world.sendEntityStatus(snowball, (byte)3);
                snowball.remove();
            }
            ci.cancel();
        }
    }
}
