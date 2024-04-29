package me.ivan.ivancarpetaddition.mixins.rule.endLightningRod;

import me.ivan.ivancarpetaddition.IvanCarpetAdditionSettings;
import net.minecraft.block.BlockState;
import net.minecraft.block.EndRodBlock;
import net.minecraft.block.FacingBlock;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LightningEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.entity.projectile.TridentEntity;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(EndRodBlock.class)
public abstract class EndRodBlockMixin extends FacingBlock {
    protected EndRodBlockMixin(Settings settings) {
        super(settings);
    }

    @Override
    public void onProjectileHit(World world, BlockState state, BlockHitResult hit, ProjectileEntity projectile) {
        if (!IvanCarpetAdditionSettings.endLightningRod) return;
        if (world.isThundering() && projectile instanceof TridentEntity && ((TridentEntity) projectile).hasChanneling()) {
            BlockPos blockPos = hit.getBlockPos();
            if (world.isSkyVisible(blockPos)) {
                LightningEntity lightningEntity = EntityType.LIGHTNING_BOLT.create(world);
                assert lightningEntity != null;
                lightningEntity.refreshPositionAfterTeleport(Vec3d.ofBottomCenter(blockPos));
                world.spawnEntity(lightningEntity);
            }
        }
    }
}
