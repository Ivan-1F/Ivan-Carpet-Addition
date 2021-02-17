package me.ivan.ivancarpetaddition.mixins.rule.bannedMobs;

import com.google.common.collect.Sets;
import me.ivan.ivancarpetaddition.IvanCarpetAdditionSettings;
import net.minecraft.entity.Entity;
import net.minecraft.server.world.ServerWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Arrays;
import java.util.Set;

@Mixin(ServerWorld.class)
public class ServerWorldMixin {
    @Inject(method = "spawnEntity", at = @At("HEAD"), cancellable = true)
    private void spawnEntity(Entity entity, CallbackInfoReturnable<Boolean> cir) {
        if (IvanCarpetAdditionSettings.bannedMobs.equals("_")) return;
        Set<String> bannedMobs = Sets.newLinkedHashSet(Arrays.asList(IvanCarpetAdditionSettings.bannedMobs.split(",")));
        if (bannedMobs.contains(entity.getType().getName().getString().toLowerCase())) {
            cir.cancel();
        }
    }
}
