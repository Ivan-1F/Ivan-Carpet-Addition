package me.ivan.ivancarpetaddition.mixins.command.xpcounter.spawning;

import me.fallenbreath.conditionalmixin.api.annotation.Condition;
import me.fallenbreath.conditionalmixin.api.annotation.Restriction;
import me.ivan.ivancarpetaddition.commands.xpcounter.SpawnReason;
import me.ivan.ivancarpetaddition.commands.xpcounter.interfaces.IExperienceOrbEntity;
import me.ivan.ivancarpetaddition.utils.ModIds;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ExperienceOrbEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

import java.util.Arrays;

@Restriction(require = @Condition(value = ModIds.minecraft, versionPredicates = ">=1.17"))
@Mixin(ExperienceOrbEntity.class)
public class ExperienceOrbEntityMixin {
    @ModifyArg(
            //#if MC >= 12106
            //$$ method = "spawn(Lnet/minecraft/server/world/ServerWorld;Lnet/minecraft/util/math/Vec3d;Lnet/minecraft/util/math/Vec3d;I)V",
            //#else
            method = "spawn",
            //#endif
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/server/world/ServerWorld;spawnEntity(Lnet/minecraft/entity/Entity;)Z"
            )
    )
    private static Entity onCommonExperienceOrbSpawn(Entity experienceOrbEntity) {
        // <3 mojang
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        System.out.println(Arrays.toString(stackTrace));
        if (stackTrace.length < 4) return experienceOrbEntity;
        //#if MC >= 12106
        //$$ String className = stackTrace[4].getClassName();
        //#else
        String className = stackTrace[3].getClassName();
        //#endif
        IExperienceOrbEntity iExperienceOrbEntity = (IExperienceOrbEntity) experienceOrbEntity;
        if (className.contains("ExperienceBottleEntity")) {
            iExperienceOrbEntity.setSpawnReason(SpawnReason.EXPERIENCE_BOTTLE);
        } else if (className.contains("LivingEntity")) {
            iExperienceOrbEntity.setSpawnReason(SpawnReason.ENTITY_LOOT);
        } else if (className.contains("AbstractFurnaceBlockEntity")) {
            iExperienceOrbEntity.setSpawnReason(SpawnReason.FURNACE);
        } else if (className.contains("Block")) {
            iExperienceOrbEntity.setSpawnReason(SpawnReason.BLOCK);
        } else if (className.contains("EnderDragonEntity")) {
            iExperienceOrbEntity.setSpawnReason(SpawnReason.ENDER_DRAGON);
        }
        return experienceOrbEntity;
    }
}
