package me.ivan.ivancarpetaddition.mixins.rule.magmaBlockDamageItem;

import me.ivan.ivancarpetaddition.IvanCarpetAdditionSettings;
import net.minecraft.block.MagmaBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MagmaBlock.class)
public class MagmaBlockMixin {
    @Inject(method = "onSteppedOn", at = @At("HEAD"))
    public void onSteppedOn(World world, BlockPos pos, Entity entity, CallbackInfo ci) {
        if (entity instanceof ItemEntity && IvanCarpetAdditionSettings.magmaBlockDamageItem) {
            entity.damage(DamageSource.HOT_FLOOR, 1.0F);
        }
    }
}
