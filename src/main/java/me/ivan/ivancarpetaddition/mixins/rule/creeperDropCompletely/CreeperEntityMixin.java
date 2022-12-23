package me.ivan.ivancarpetaddition.mixins.rule.creeperDropCompletely;

import me.ivan.ivancarpetaddition.IvanCarpetAdditionSettings;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.CreeperEntity;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import net.minecraft.world.explosion.Explosion;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(CreeperEntity.class)
public abstract class CreeperEntityMixin extends HostileEntity {
    protected CreeperEntityMixin(EntityType<? extends HostileEntity> type, World world) {
        super(type, world);
    }

    @ModifyArg(method = "explode", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;createExplosion(Lnet/minecraft/entity/Entity;DDDFLnet/minecraft/world/World$ExplosionSourceType;)Lnet/minecraft/world/explosion/Explosion;"))
    private World.ExplosionSourceType setDestructionType(@Nullable Entity entity) {
        return this.world.getGameRules().getBoolean(GameRules.DO_MOB_GRIEFING) ? IvanCarpetAdditionSettings.creeperDropCompletely ? World.ExplosionSourceType.MOB : World.ExplosionSourceType.TNT : World.ExplosionSourceType.NONE;
    }
}
