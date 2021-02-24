package me.ivan.ivancarpetaddition.mixins.rule.mobSpawningControl;

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
        if (IvanCarpetAdditionSettings.mobSpawningControlMode.equals("blacklist")) {
            Set<String> blackList = Sets.newLinkedHashSet(Arrays.asList(IvanCarpetAdditionSettings.mobBlackList.split(",")));
            if (blackList.contains(entity.getName().getString().toLowerCase())) {
                cir.setReturnValue(false);
            }
        }
        if (IvanCarpetAdditionSettings.mobSpawningControlMode.equals("whitelist")) {
            Set<String> whitelist = Sets.newLinkedHashSet(Arrays.asList(IvanCarpetAdditionSettings.mobWhiteList.split(",")));
            if (!whitelist.contains(entity.getName().getString().toLowerCase())) {
                cir.setReturnValue(false);
            }
        }
    }
}
