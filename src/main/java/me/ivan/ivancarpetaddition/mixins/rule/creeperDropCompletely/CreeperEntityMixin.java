package me.ivan.ivancarpetaddition.mixins.rule.creeperDropCompletely;

import me.ivan.ivancarpetaddition.IvanCarpetAdditionSettings;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.CreeperEntity;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

//#if MC < 11903
import net.minecraft.world.explosion.Explosion;
//#endif

@Mixin(CreeperEntity.class)
public abstract class CreeperEntityMixin extends HostileEntity {
    protected CreeperEntityMixin(EntityType<? extends HostileEntity> type, World world) {
        super(type, world);
    }

    @ModifyArg(
            method = "explode",
            at = @At(
                    value = "INVOKE",
                    //#if MC >= 12103
                    //$$ target = "Lnet/minecraft/server/world/ServerWorld;createExplosion(Lnet/minecraft/entity/Entity;DDDFLnet/minecraft/world/World$ExplosionSourceType;)V"
                    //#elseif MC >= 11903
                    //$$ target = "Lnet/minecraft/world/World;createExplosion(Lnet/minecraft/entity/Entity;DDDFLnet/minecraft/world/World$ExplosionSourceType;)Lnet/minecraft/world/explosion/Explosion;"
                    //#else
                    target = "Lnet/minecraft/world/World;createExplosion(Lnet/minecraft/entity/Entity;DDDFLnet/minecraft/world/explosion/Explosion$DestructionType;)Lnet/minecraft/world/explosion/Explosion;"
                    //#endif
            )
    )
    private
    //#if MC >= 11903
    //$$ World.ExplosionSourceType
    //#else
    Explosion.DestructionType
    //#endif
    setDestructionType(
            //#if MC >= 11903
            //$$ World.ExplosionSourceType explosionSourceType
            //#else
            Explosion.DestructionType destructionType
            //#endif
    ) {
//        if (
//                !this.getEntityWorld().getGameRules().getBoolean(
//                        //#if MC >= 11600
//                        //$$ GameRules.DO_MOB_GRIEFING
//                        //#else
//                        GameRules.MOB_GRIEFING
//                        //#endif
//                )
//        ) {
//            //#if MC >= 11903
//            //$$ return World.ExplosionSourceType.NONE;
//            //#else
//            return Explosion.DestructionType.NONE;
//            //#endif
//        }
        return IvanCarpetAdditionSettings.creeperDropCompletely
                //#if MC >= 11903
                //$$ ? World.ExplosionSourceType.TNT
                //#else
                ? Explosion.DestructionType.BREAK
                //#endif

                //#if MC >= 11903
                //$$ : World.ExplosionSourceType.MOB;
                //#else
                : Explosion.DestructionType.DESTROY;
                //#endif
    }
}
