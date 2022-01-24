package me.ivan.ivancarpetaddition.mixins.rule.functionalSpongeItem;

import com.google.common.collect.Lists;
import me.ivan.ivancarpetaddition.IvanCarpetAdditionSettings;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.tag.FluidTags;
import net.minecraft.util.Pair;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Queue;

@Mixin(ItemEntity.class)
public class ItemEntityMixin {
    ItemEntity itemEntity = (ItemEntity)(Object) this;
    @Inject(method = "tick", at = @At("HEAD"))
    private void tick(CallbackInfo ci) {
        if (!IvanCarpetAdditionSettings.functionalSpongeItem) return;
        if (itemEntity.getStack().getItem() == Items.SPONGE) {
            if (absorbWater(itemEntity.world, new BlockPos(itemEntity.getPos()))) {
                ItemEntity wetSponge = new ItemEntity(itemEntity.world, itemEntity.getX(), itemEntity.getY(), itemEntity.getZ(), new ItemStack(Items.WET_SPONGE, itemEntity.getStack().getCount()));
                wetSponge.setVelocity(itemEntity.getVelocity());
                itemEntity.world.spawnEntity(wetSponge);
                itemEntity.discard();
            }
        }
        if (itemEntity.getStack().getItem() == Items.WET_SPONGE && itemEntity.world.getDimension().isUltrawarm() && itemEntity.getItemAge() > 60) {
            ItemEntity sponge = new ItemEntity(itemEntity.world, itemEntity.getX(), itemEntity.getY(), itemEntity.getZ(), new ItemStack(Items.SPONGE, itemEntity.getStack().getCount()));
            sponge.setVelocity(itemEntity.getVelocity());
            itemEntity.world.spawnEntity(sponge);
            itemEntity.world.playSound(null, new BlockPos(itemEntity.getPos()), SoundEvents.BLOCK_FIRE_EXTINGUISH, SoundCategory.BLOCKS, 1.0F, (1.0F + itemEntity.world.getRandom().nextFloat() * 0.2F) * 0.7F);
            itemEntity.discard();
        }
    }

    // stolen from SpongeBlock#absorbWateer
    private boolean absorbWater(World world, BlockPos pos) {
        Queue<Pair<BlockPos, Integer>> queue = Lists.newLinkedList();
        queue.add(new Pair(pos, 0));
        int i = 0;

        while(!queue.isEmpty()) {
            Pair<BlockPos, Integer> pair = queue.poll();
            BlockPos blockPos = pair.getLeft();
            int j = pair.getRight();
            Direction[] var8 = Direction.values();
            int var9 = var8.length;

            for(int var10 = 0; var10 < var9; ++var10) {
                Direction direction = var8[var10];
                BlockPos blockPos2 = blockPos.offset(direction);
                BlockState blockState = world.getBlockState(blockPos2);
                FluidState fluidState = world.getFluidState(blockPos2);
                Material material = blockState.getMaterial();
                if (fluidState.isIn(FluidTags.WATER)) {
                    if (blockState.getBlock() instanceof FluidDrainable && !((FluidDrainable)blockState.getBlock()).tryDrainFluid(world, blockPos2, blockState).isEmpty()) {
                        ++i;
                        if (j < 6) {
                            queue.add(new Pair(blockPos2, j + 1));
                        }
                    } else if (blockState.getBlock() instanceof FluidBlock) {
                        world.setBlockState(blockPos2, Blocks.AIR.getDefaultState(), 3);
                        ++i;
                        if (j < 6) {
                            queue.add(new Pair(blockPos2, j + 1));
                        }
                    } else if (material == Material.UNDERWATER_PLANT || material == Material.REPLACEABLE_UNDERWATER_PLANT) {
                        BlockEntity blockEntity = blockState.hasBlockEntity() ? world.getBlockEntity(blockPos2) : null;
                        SpongeBlock.dropStacks(blockState, world, blockPos2, blockEntity);
                        world.setBlockState(blockPos2, Blocks.AIR.getDefaultState(), 3);
                        ++i;
                        if (j < 6) {
                            queue.add(new Pair(blockPos2, j + 1));
                        }
                    }
                }
            }

            if (i > 64) {
                break;
            }
        }

        return i > 0;
    }
}
