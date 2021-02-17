package me.ivan.ivancarpetaddition.mixins.rule.creeperDropCompletely;

import me.ivan.ivancarpetaddition.IvanCarpetAdditionSettings;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.CreeperEntity;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import net.minecraft.world.explosion.Explosion;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(CreeperEntity.class)
public abstract class CreeperEntityMixin extends HostileEntity{
    @Shadow public abstract boolean shouldRenderOverlay();

    @Shadow protected abstract void spawnEffectsCloud();

    @Shadow private int explosionRadius;

    public CreeperEntityMixin(EntityType<? extends CreeperEntity> entityType, World world) {
        super(entityType, world);
    }

    @Overwrite
    private void explode() {
        if (!this.world.isClient()) {
            Explosion.DestructionType destructionType = this.world.getGameRules().getBoolean(GameRules.MOB_GRIEFING) ? IvanCarpetAdditionSettings.creeperDropCompletely ? Explosion.DestructionType.BREAK : Explosion.DestructionType.DESTROY : Explosion.DestructionType.NONE;
            float f = shouldRenderOverlay() ? 2.0F : 1.0F;
            this.dead = true;
            this.world.createExplosion(this, this.getX(), this.getY(), this.getZ(), (float)explosionRadius * f, destructionType);
            this.remove();
            spawnEffectsCloud();
        }

    }
}
