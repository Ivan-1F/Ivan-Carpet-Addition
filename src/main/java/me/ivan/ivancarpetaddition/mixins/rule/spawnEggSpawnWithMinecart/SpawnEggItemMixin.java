package me.ivan.ivancarpetaddition.mixins.rule.spawnEggSpawnWithMinecart;

import com.llamalad7.mixinextras.sugar.Local;
import me.ivan.ivancarpetaddition.IvanCarpetAdditionSettings;
import net.minecraft.block.AbstractRailBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.enums.RailShape;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnType;
import net.minecraft.entity.vehicle.MinecartEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Objects;

//#if MC >= 11600
//$$ import net.minecraft.server.world.ServerWorld;
//#endif

@Mixin(SpawnEggItem.class)
public abstract class SpawnEggItemMixin {
    @Inject(
            method = "useOnBlock",
            at = @At(
                    value = "INVOKE",
                    //#if MC >= 11600
                    //$$ target = "Lnet/minecraft/block/BlockState;getCollisionShape(Lnet/minecraft/world/BlockView;Lnet/minecraft/util/math/BlockPos;)Lnet/minecraft/util/shape/VoxelShape;"
                    //#else
                    target = "Lnet/minecraft/block/BlockState;getBlock()Lnet/minecraft/block/Block;"
                    //#endif
            ),
            cancellable = true
    )
    private void spawnWithMinecart(
            ItemUsageContext context,
            CallbackInfoReturnable<ActionResult> cir,
            @Local World world,
            @Local BlockPos blockPos,
            @Local Direction direction,
            @Local ItemStack itemStack,
            @Local BlockState blockState
    ) {
        if (!IvanCarpetAdditionSettings.spawnEggSpawnWithMinecart) {
            return;
        }
        if (
                //#if MC >= 11600
                //$$ blockState.isIn(BlockTags.RAILS)
                //#else
                blockState.matches(BlockTags.RAILS)
                //#endif
        ) {
            RailShape railShape = blockState.getBlock() instanceof AbstractRailBlock ? blockState.get(((AbstractRailBlock) blockState.getBlock()).getShapeProperty()) : RailShape.NORTH_SOUTH;
            double d = railShape.isAscending() ? 0.5 : 0.0;
            MinecartEntity minecartEntity = new MinecartEntity(world, blockPos.getX() + 0.5, blockPos.getY() + 0.0625 + d, blockPos.getZ() + 0.5);
            world.spawnEntity(minecartEntity);
            SpawnEggItem self = (SpawnEggItem) (Object) this;

            BlockPos entityBlockPos = blockState.getCollisionShape(world, blockPos).isEmpty() ? blockPos : blockPos.offset(direction);
            EntityType<?> entityType = self.getEntityType(
                    //#if MC >= 12005
                    //$$ itemStack
                    //#else
                    itemStack.getTag()
                    //#endif
            );
            Entity entity = entityType.spawnFromItemStack(
                    //#if MC >= 11600
                    //$$ (ServerWorld) world,
                    //#else
                    world,
                    //#endif
                    itemStack,
                    context.getPlayer(),
                    entityBlockPos,
                    SpawnType.SPAWN_EGG,
                    true,
                    !Objects.equals(blockPos, entityBlockPos) && direction == Direction.UP
            );
            if (entity != null) {
                entity.startRiding(minecartEntity);
                itemStack.decrement(1);
            }

            cir.setReturnValue(ActionResult.SUCCESS);
        }
    }
}
