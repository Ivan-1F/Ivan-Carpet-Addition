package me.ivan.ivancarpetaddition.mixins.rule.renewableSoulSand;

import me.ivan.ivancarpetaddition.IvanCarpetAdditionSettings;
import me.ivan.ivancarpetaddition.utils.EntityUtil;
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
        if (
                source ==
                        //#if MC >= 11904
                        //$$ EntityUtil.getEntityWorld(livingEntity).getDamageSources().inWall()
                        //#else
                        DamageSource.IN_WALL
                        //#endif
        ) {
            if (EntityUtil.getEntityWorld(livingEntity).getBlockState(livingEntity.getBlockPos()).getBlock() == Blocks.SAND) {
                EntityUtil.getEntityWorld(livingEntity).setBlockState(livingEntity.getBlockPos(), Blocks.SOUL_SAND.getDefaultState());
            }
        }
    }
}
