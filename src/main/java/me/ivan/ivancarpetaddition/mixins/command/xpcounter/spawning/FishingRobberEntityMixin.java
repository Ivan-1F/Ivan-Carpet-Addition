package me.ivan.ivancarpetaddition.mixins.command.xpcounter.spawning;

import me.ivan.ivancarpetaddition.commands.xpcounter.SpawnReason;
import me.ivan.ivancarpetaddition.commands.xpcounter.interfaces.IExperienceOrbEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.projectile.FishingBobberEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(FishingBobberEntity.class)
public class FishingRobberEntityMixin {
    @ModifyArg(method = "use", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;spawnEntity(Lnet/minecraft/entity/Entity;)Z", ordinal = 1))
    private Entity onFoxBreedingDropExperience(Entity experienceOrbEntity) {
        ((IExperienceOrbEntity) experienceOrbEntity).setSpawnReason(SpawnReason.FISHING);
        return experienceOrbEntity;
    }
}
