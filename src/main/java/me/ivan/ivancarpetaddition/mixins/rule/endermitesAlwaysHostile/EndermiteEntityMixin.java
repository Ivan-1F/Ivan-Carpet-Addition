package me.ivan.ivancarpetaddition.mixins.rule.endermitesAlwaysHostile;

import me.ivan.ivancarpetaddition.IvanCarpetAdditionSettings;
import net.minecraft.entity.mob.EndermiteEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EndermiteEntity.class)
public class EndermiteEntityMixin {
    @Inject(method = "isPlayerSpawned", at = @At("HEAD"), cancellable = true)
    private void playerSpawnedAlwaysToBeTrue(CallbackInfoReturnable<Boolean> cir) {
        if (IvanCarpetAdditionSettings.endermitesAlwaysHostile) {
            cir.setReturnValue(true);
        }
    }
}
