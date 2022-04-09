package me.ivan.ivancarpetaddition.mixins.command.xpcounter;

import me.ivan.ivancarpetaddition.IvanCarpetAdditionSettings;
import me.ivan.ivancarpetaddition.commands.xpcounter.SpawnReason;
import me.ivan.ivancarpetaddition.commands.xpcounter.interfaces.IExperienceOrbEntity;
import me.ivan.ivancarpetaddition.commands.xpcounter.XPCounter;
import net.minecraft.entity.ExperienceOrbEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ExperienceOrbEntity.class)
public class ExperienceOrbEntityMixin implements IExperienceOrbEntity {
    private SpawnReason spawnReason;

    @Inject(method = "onPlayerCollision", at = @At("TAIL"))
    private void onPlayerCollision(PlayerEntity player, CallbackInfo ci) {
        if (!IvanCarpetAdditionSettings.xpCounter) return;
        ExperienceOrbEntity experienceOrbEntity = (ExperienceOrbEntity) (Object) this;
        XPCounter.getCounter((ServerPlayerEntity) player).add(experienceOrbEntity);
    }

    @Override
    public void setSpawnReason(SpawnReason reason) {
        this.spawnReason = reason;
    }

    @Override
    public SpawnReason getSpawnReason() {
        return this.spawnReason;
    }
}
