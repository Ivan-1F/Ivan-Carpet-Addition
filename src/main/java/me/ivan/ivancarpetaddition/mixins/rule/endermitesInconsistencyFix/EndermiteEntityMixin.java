package me.ivan.ivancarpetaddition.mixins.rule.endermitesInconsistencyFix;

import me.fallenbreath.conditionalmixin.api.annotation.Condition;
import me.fallenbreath.conditionalmixin.api.annotation.Restriction;
import me.ivan.ivancarpetaddition.IvanCarpetAdditionSettings;
import me.ivan.ivancarpetaddition.utils.ModIds;
import net.minecraft.entity.mob.EndermiteEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Restriction(require = @Condition(value = ModIds.minecraft, versionPredicates = "<1.17"))
@Mixin(EndermiteEntity.class)
public class EndermiteEntityMixin {
    @Inject(method = "isPlayerSpawned", at = @At("HEAD"), cancellable = true)
    private void playerSpawnedAlwaysToBeTrue(CallbackInfoReturnable<Boolean> cir) {
        if (IvanCarpetAdditionSettings.endermitesInconsistencyFix) {
            cir.setReturnValue(true);
        }
    }
}
