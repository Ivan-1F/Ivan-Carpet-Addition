package me.ivan.ivancarpetaddition.mixins.rule.renewableSoulSand;

import me.ivan.ivancarpetaddition.IvanCarpetAdditionSettings;
import net.minecraft.block.Blocks;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public class LivingEntityMixin {
    @Inject(method = "onDeath", at = @At("HEAD"))
    private void convertSandToSoulSand(DamageSource source, CallbackInfo ci) {
        if (!IvanCarpetAdditionSettings.renewableSoulSand) return;
        LivingEntity livingEntity = (LivingEntity)(Object) this;
        if (source == DamageSource.IN_WALL) {
            if (livingEntity.world.getBlockState(livingEntity.getBlockPos()).getBlock() == Blocks.SAND) {
                livingEntity.world.setBlockState(livingEntity.getBlockPos(), Blocks.SOUL_SAND.getDefaultState());
            }
        }
    }
}
