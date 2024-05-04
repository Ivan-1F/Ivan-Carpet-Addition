package me.ivan.ivancarpetaddition.mixins.rule.undeadImmuneToSunlight;

import me.ivan.ivancarpetaddition.IvanCarpetAdditionSettings;
import net.minecraft.entity.mob.MobEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(MobEntity.class)
public abstract class MobEntityMixin {
    @Inject(
            //#if MC >= 11600
            //$$ method = "isAffectedByDaylight",
            //#else
            method = "isInDaylight",
            //#endif
            at = @At("RETURN"),
            cancellable = true
    )
    public void hookDaylight(CallbackInfoReturnable<Boolean> cir) {
        if (IvanCarpetAdditionSettings.undeadImmuneToSunlight) cir.setReturnValue(false);
    }
}