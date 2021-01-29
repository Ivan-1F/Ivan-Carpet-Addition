package me.ivan.ivancarpetaddition.mixins.rule.zeroTickFarm;

import me.ivan.ivancarpetaddition.IvanCarpetAdditionSettings;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.CactusBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import java.util.Random;

@Mixin(CactusBlock.class)
public class CactusBlockMixin extends Block {

    public CactusBlockMixin(Settings settings) {
        super(settings);
    }

    @Overwrite
    public void scheduledTick(final BlockState state, final ServerWorld world, final BlockPos pos, final Random random) {
        if (!state.canPlaceAt(world, pos)) {
            world.breakBlock(pos, true);
        } else if (IvanCarpetAdditionSettings.zeroTickFarm) {
            BlockPos blockPos = pos.up();
            if (world.isAir(blockPos)) {
                int i;
                for(i = 1; world.getBlockState(pos.down(i)).getBlock() == this; ++i) {
                }

                if (i < 3) {
                    int j = (Integer)state.get(CactusBlock.AGE);
                    if (j == 15) {
                        world.setBlockState(blockPos, this.getDefaultState());
                        BlockState blockState = (BlockState)state.with(CactusBlock.AGE, 0);
                        world.setBlockState(pos, blockState, 4);
                        blockState.neighborUpdate(world, blockPos, this, pos, false);
                    } else {
                        world.setBlockState(pos, (BlockState)state.with(CactusBlock.AGE, j + 1), 4);
                    }

                }
            }
        }
    }
}
