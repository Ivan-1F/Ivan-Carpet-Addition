package me.ivan.ivancarpetaddition.mixins.command.xpcounter.spawning.trading;

import me.ivan.ivancarpetaddition.commands.xpcounter.SpawnReason;
import me.ivan.ivancarpetaddition.commands.xpcounter.interfaces.IExperienceOrbEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.entity.passive.WanderingTraderEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(WanderingTraderEntity.class)
public class WanderingTraderEntityMixin {
    @ModifyArg(method = "afterUsing", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;spawnEntity(Lnet/minecraft/entity/Entity;)Z"))
    private Entity onWanderingTraderTradeDropExperience(Entity experienceOrbEntity) {
        ((IExperienceOrbEntity) experienceOrbEntity).setSpawnReason(SpawnReason.TRADING);
        return experienceOrbEntity;
    }
}
