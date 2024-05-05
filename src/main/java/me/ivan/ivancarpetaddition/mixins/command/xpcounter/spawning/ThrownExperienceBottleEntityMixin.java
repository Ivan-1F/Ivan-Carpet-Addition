package me.ivan.ivancarpetaddition.mixins.command.xpcounter.spawning;

import me.fallenbreath.conditionalmixin.api.annotation.Condition;
import me.fallenbreath.conditionalmixin.api.annotation.Restriction;
import me.ivan.ivancarpetaddition.commands.xpcounter.SpawnReason;
import me.ivan.ivancarpetaddition.commands.xpcounter.interfaces.IExperienceOrbEntity;
import me.ivan.ivancarpetaddition.utils.ModIds;
import net.minecraft.entity.Entity;
import net.minecraft.entity.thrown.ThrownExperienceBottleEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Restriction(require = @Condition(value = ModIds.minecraft, versionPredicates = "<1.17"))
@Mixin(ThrownExperienceBottleEntity.class)
public class ThrownExperienceBottleEntityMixin {
    @ModifyArg(method = "onCollision", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;spawnEntity(Lnet/minecraft/entity/Entity;)Z"))
    private Entity onSpawningExperienceOrbEntity(Entity experienceOrbEntity) {
        ((IExperienceOrbEntity) experienceOrbEntity).setSpawnReason(SpawnReason.EXPERIENCE_BOTTLE);
        return experienceOrbEntity;
    }
}
