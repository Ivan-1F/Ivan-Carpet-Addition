package me.ivan.ivancarpetaddition.mixins.rule.creeperDropCompletely;

import me.ivan.ivancarpetaddition.IvanCarpetAdditionSettings;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.CreeperEntity;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import net.minecraft.world.explosion.Explosion;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(CreeperEntity.class)
public abstract class CreeperEntityMixin extends HostileEntity {
    protected CreeperEntityMixin(EntityType<? extends HostileEntity> type, World world) {
        super(type, world);
    }

    @ModifyArg(
            method = "explode",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/World;createExplosion(Lnet/minecraft/entity/Entity;DDDFLnet/minecraft/world/explosion/Explosion$DestructionType;)Lnet/minecraft/world/explosion/Explosion;"
            )
    )
    private Explosion.DestructionType setDestructionType(Explosion.DestructionType destructionType) {
        if (
                !this.world.getGameRules().getBoolean(
                        //#if MC >= 11600
                        //$$ GameRules.DO_MOB_GRIEFING
                        //#else
                        GameRules.MOB_GRIEFING
                        //#endif
                )
        ) {
            return Explosion.DestructionType.NONE;
        }
        return IvanCarpetAdditionSettings.creeperDropCompletely ? Explosion.DestructionType.BREAK : Explosion.DestructionType.DESTROY;
    }
}
