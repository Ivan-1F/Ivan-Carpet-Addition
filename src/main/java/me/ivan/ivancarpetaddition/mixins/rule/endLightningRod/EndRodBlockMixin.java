package me.ivan.ivancarpetaddition.mixins.rule.endLightningRod;

import me.ivan.ivancarpetaddition.IvanCarpetAdditionSettings;
import net.minecraft.block.BlockState;
import net.minecraft.block.EndRodBlock;
import net.minecraft.block.FacingBlock;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LightningEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.entity.projectile.TridentEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(EndRodBlock.class)
public class EndRodBlockMixin extends FacingBlock {
    protected EndRodBlockMixin(Settings settings) {
        super(settings);
    }

    @Override
    public void onProjectileHit(World world, BlockState state, BlockHitResult hitResult, Entity entity) {
        if (!IvanCarpetAdditionSettings.endLightningRod) return;
        if (entity instanceof ProjectileEntity) {
            ProjectileEntity projectile = (ProjectileEntity) entity;
            boolean hasChanneling = EnchantmentHelper.hasChanneling(((TridentEntityAccessor) projectile).getTridentStack());
            if (world.isThundering() && projectile instanceof TridentEntity && hasChanneling) {
                BlockPos blockPos = hitResult.getBlockPos();
                if (world.isSkyVisible(blockPos)) {
                    Vec3d vec3d = ofBottomCenter(blockPos);
                    LightningEntity lightningEntity = new LightningEntity(world, vec3d.x, vec3d.y, vec3d.z, false);
                    ((ServerWorld) world).addLightning(lightningEntity);
                    world.playSound(null, blockPos, SoundEvents.ITEM_TRIDENT_THUNDER, SoundCategory.WEATHER, 5.0F, 1.0F);
                }
            }
        }

    }

    private Vec3d ofBottomCenter(Vec3i vec) {
        return new Vec3d((double)vec.getX() + 0.5D, vec.getY(), (double)vec.getZ() + 0.5D);
    }
}
