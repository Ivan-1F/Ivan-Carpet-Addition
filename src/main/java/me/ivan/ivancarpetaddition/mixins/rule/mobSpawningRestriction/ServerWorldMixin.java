package me.ivan.ivancarpetaddition.mixins.rule.mobSpawningRestriction;

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
        String entityName = entity.getType().getTranslationKey().split("\\.")[2];
        if (IvanCarpetAdditionSettings.mobSpawningRestrictionMode.equals("blacklist")) {
            Set<String> blackList = Sets.newLinkedHashSet(Arrays.asList(IvanCarpetAdditionSettings.mobBlackList.split(",")));
            blackList.forEach(name -> {
                if (entityName.equals(name)) {
                    cir.setReturnValue(false);
                }
            });
        }
        if (IvanCarpetAdditionSettings.mobSpawningRestrictionMode.equals("whitelist")) {
            Set<String> whitelist = Sets.newLinkedHashSet(Arrays.asList(IvanCarpetAdditionSettings.mobWhiteList.split(",")));
            whitelist.forEach(name -> {
                if (!entityName.equals(name)) {
                    cir.setReturnValue(false);
                }
            });
        }
    }
}
