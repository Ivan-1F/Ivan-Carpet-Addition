package me.ivan.ivancarpetaddition.mixins.rule.endLightningRod;

import me.ivan.ivancarpetaddition.IvanCarpetAdditionSettings;
import net.minecraft.block.BlockState;
import net.minecraft.block.EndRodBlock;
import net.minecraft.block.FacingBlock;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.LightningEntity;
import net.minecraft.entity.projectile.TridentEntity;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;

//#if MC >= 11600
//$$ import net.minecraft.entity.EntityType;
//#else
import net.minecraft.entity.Entity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.Vec3i;
import org.spongepowered.asm.mixin.Unique;
//#endif

// prevent ProjectileEntity(1.15) from being remapped into PersistentProjectileEntity(1.16)
//#if MC >= 11600
//$$ import net.minecraft.entity.projectile.ProjectileEntity;
//#else
import net.minecraft.entity.projectile.ProjectileEntity;
//#endif

@Mixin(EndRodBlock.class)
public abstract class EndRodBlockMixin extends FacingBlock {
    protected EndRodBlockMixin(Settings settings) {
        super(settings);
    }

    @Override
    public void onProjectileHit(
            World world,
            BlockState state,
            BlockHitResult hitResult,
            //#if MC >= 11600
            //$$ ProjectileEntity projectile
            //#else
            Entity entity
            //#endif
    ) {
        if (!IvanCarpetAdditionSettings.endLightningRod) return;
        //#if MC >= 11600
        //#if MC >= 12004
        //$$ if (world.isThundering() && projectile instanceof TridentEntity && ((TridentEntity) projectile).hasChanneling()) {
        //#else
        //$$ boolean hasChanneling = EnchantmentHelper.hasChanneling(((TridentEntityAccessor) projectile).getTridentStack());
        //$$ if (world.isThundering() && projectile instanceof TridentEntity && hasChanneling) {
        //#endif
        //$$     BlockPos blockPos = hitResult.getBlockPos();
        //$$     if (world.isSkyVisible(blockPos)) {
        //$$         LightningEntity lightningEntity = EntityType.LIGHTNING_BOLT.create(world);
        //$$         assert lightningEntity != null;
        //$$         lightningEntity.refreshPositionAfterTeleport(Vec3d.ofBottomCenter(blockPos));
        //$$         world.spawnEntity(lightningEntity);
        //$$     }
        //$$ }
        //#else
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
        //#endif
    }

    //#if MC < 11600
    @Unique
    private Vec3d ofBottomCenter(Vec3i vec) {
        return new Vec3d((double)vec.getX() + 0.5D, vec.getY(), (double)vec.getZ() + 0.5D);
    }
    //#endif
}
