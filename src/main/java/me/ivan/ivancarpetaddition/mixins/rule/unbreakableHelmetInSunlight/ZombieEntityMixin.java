package me.ivan.ivancarpetaddition.mixins.rule.unbreakableHelmetInSunlight;

import me.ivan.ivancarpetaddition.IvanCarpetAdditionSettings;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.mob.ZombieEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ZombieEntity.class)
public class ZombieEntityMixin {
    @Inject(method = "tickMovement", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;setDamage(I)V", shift = At.Shift.AFTER))
    private void repairHelmet(CallbackInfo ci) {
        if (IvanCarpetAdditionSettings.unbreakableHelmetInSunlight) {
            ((ZombieEntity) (Object) this).getEquippedStack(EquipmentSlot.HEAD).setDamage(0);
        }
    }
}
