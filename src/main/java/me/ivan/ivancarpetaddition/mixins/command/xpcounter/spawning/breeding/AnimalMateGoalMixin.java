package me.ivan.ivancarpetaddition.mixins.command.xpcounter.spawning.breeding;

import me.ivan.ivancarpetaddition.commands.xpcounter.SpawnReason;
import me.ivan.ivancarpetaddition.commands.xpcounter.interfaces.IExperienceOrbEntity;
import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

//#if MC >= 11600
//$$ import net.minecraft.entity.passive.AnimalEntity;
//#else
import net.minecraft.entity.ai.goal.AnimalMateGoal;
//#endif

@Mixin(
        //#if MC >= 11600
        //$$ AnimalEntity.class
        //#else
        AnimalMateGoal.class
        //#endif
)
public class AnimalMateGoalMixin {
    @ModifyArg(
            method = "breed",
            at = @At(
                    value = "INVOKE",
                    //#if MC >= 11600
                    //$$ target = "Lnet/minecraft/server/world/ServerWorld;spawnEntity(Lnet/minecraft/entity/Entity;)Z"
                    //#else
                    target = "Lnet/minecraft/world/World;spawnEntity(Lnet/minecraft/entity/Entity;)Z",
                    ordinal = 1
                    //#endif
            )
    )
    private Entity onAnimalBreedingDropExperience(Entity experienceOrbEntity) {
        ((IExperienceOrbEntity) experienceOrbEntity).setSpawnReason(SpawnReason.BREEDING);
        return experienceOrbEntity;
    }
}
