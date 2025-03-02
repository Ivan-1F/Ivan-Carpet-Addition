package me.ivan.ivancarpetaddition.mixins.command.xpcounter.spawning.breeding;

import me.ivan.ivancarpetaddition.commands.xpcounter.SpawnReason;
import me.ivan.ivancarpetaddition.commands.xpcounter.interfaces.IExperienceOrbEntity;
import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(targets = "net.minecraft.entity.passive.FoxEntity$MateGoal")
public class FoxEntityMateGoalMixin {
    @ModifyArg(
            method = "breed",
            at = @At(
                    value = "INVOKE",
                    //#if MC >= 12103
                    //$$ target = "Lnet/minecraft/server/world/ServerWorld;spawnEntity(Lnet/minecraft/entity/Entity;)Z"
                    //#else
                    target = "Lnet/minecraft/world/World;spawnEntity(Lnet/minecraft/entity/Entity;)Z"
                    //#endif
                    //#if MC < 11600
                    , ordinal = 1
                    //#endif
            )
    )
    private Entity onFoxBreedingDropExperience(Entity experienceOrbEntity) {
        ((IExperienceOrbEntity) experienceOrbEntity).setSpawnReason(SpawnReason.BREEDING);
        return experienceOrbEntity;
    }
}
