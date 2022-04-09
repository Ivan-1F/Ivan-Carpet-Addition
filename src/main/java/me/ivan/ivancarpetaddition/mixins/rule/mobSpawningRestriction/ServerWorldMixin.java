package me.ivan.ivancarpetaddition.mixins.rule.mobSpawningRestriction;

import me.ivan.ivancarpetaddition.helpers.rule.mobSpawningRestriction.MobSpawningRestrictionHelper;
import net.minecraft.entity.Entity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.world.ServerWorld;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ServerWorld.class)
public class ServerWorldMixin {
    @Inject(method = "spawnEntity", at = @At("HEAD"), cancellable = true)
    private void restrictionMobSpawning(Entity entity, CallbackInfoReturnable<Boolean> cir) {
        if (!MobSpawningRestrictionHelper.canSpawn(entity)) {
            cir.setReturnValue(false);
        }
    }
}
