package me.ivan.ivancarpetaddition.mixins.rule.magmaBlockDamageItem;

import me.fallenbreath.conditionalmixin.api.annotation.Condition;
import me.fallenbreath.conditionalmixin.api.annotation.Restriction;
import me.ivan.ivancarpetaddition.IvanCarpetAdditionSettings;
import me.ivan.ivancarpetaddition.utils.ModIds;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.FenceGateBlock;
import net.minecraft.block.MagmaBlock;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Restriction(require = @Condition(value = ModIds.minecraft, versionPredicates = "<1.15"))
@Mixin(ItemEntity.class)
public class ItemEntityMixin {
    ItemEntity entity = (ItemEntity)(Object) this;

    @Inject(method = "tick", at = @At("HEAD"))
    private void tick(CallbackInfo ci) {
        if (!IvanCarpetAdditionSettings.magmaBlockDamageItem) return;
        int i = MathHelper.floor(entity.x);
        int j = MathHelper.floor(entity.y - 0.20000000298023224D);
        int k = MathHelper.floor(entity.z);
        BlockPos blockPos = new BlockPos(i, j, k);
        BlockState blockState = entity.world.getBlockState(blockPos);
        if (blockState.isAir()) {
            BlockPos blockPos2 = blockPos.down();
            BlockState blockState2 = entity.world.getBlockState(blockPos2);
            Block block = blockState2.getBlock();
            if (block.matches(BlockTags.FENCES) || block.matches(BlockTags.WALLS) || block instanceof FenceGateBlock) {
                blockState = blockState2;
            }
        }
        Block block = blockState.getBlock();
        if (block instanceof MagmaBlock) {
            entity.damage(DamageSource.HOT_FLOOR, 1);
        }
    }
}
