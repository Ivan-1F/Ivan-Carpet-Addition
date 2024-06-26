package me.ivan.ivancarpetaddition.mixins.rule.pistonBedrockBreakingFix;

import me.ivan.ivancarpetaddition.IvanCarpetAdditionSettings;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.PistonBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PistonBlock.class)
public class PistonBlockMixin {
    @Inject(
            //#if MC >= 11600
            //$$ method = "onSyncedBlockEvent",
            //#else
            method = "onBlockAction",
            //#endif
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/World;removeBlock(Lnet/minecraft/util/math/BlockPos;Z)Z"
            ),
            cancellable = true
    )
    private void stopRemovingBlocks(BlockState state, World world, BlockPos pos, int type, int data, CallbackInfoReturnable<Boolean> cir) {
        if (IvanCarpetAdditionSettings.pistonBedrockBreakingFix) {
            Direction direction = state.get(PistonBlock.FACING);
            if (world.getBlockState(pos.offset(direction)).getBlock() != Blocks.PISTON_HEAD) {
                cir.cancel();
            }
        }
    }
}
