package me.ivan.ivancarpetaddition.mixins.rule.unbreakableHelmetInSunlight;

import me.ivan.ivancarpetaddition.IvanCarpetAdditionSettings;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.mob.AbstractSkeletonEntity;
import net.minecraft.entity.mob.ZombieEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AbstractSkeletonEntity.class)
public class AbstractSkeletonEntityMixin {
    private int damage;
    @Inject(method = "tickMovement", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;setDamage(I)V", shift = At.Shift.BEFORE))
    private void beforeDamagingHelmet(CallbackInfo ci) {
        if (IvanCarpetAdditionSettings.unbreakableHelmetInSunlight) {
            damage = ((AbstractSkeletonEntity) (Object) this).getEquippedStack(EquipmentSlot.HEAD).getDamage();
        }
    }
    @Inject(method = "tickMovement", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;setDamage(I)V", shift = At.Shift.AFTER))
    private void afterDamagingHelmet(CallbackInfo ci) {
        if (IvanCarpetAdditionSettings.unbreakableHelmetInSunlight) {
            ((AbstractSkeletonEntity) (Object) this).getEquippedStack(EquipmentSlot.HEAD).setDamage(damage);
        }
    }
}
