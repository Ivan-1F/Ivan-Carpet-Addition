package me.ivan.ivancarpetaddition.mixins.rule.infiniteWaterDisabled;

import me.ivan.ivancarpetaddition.IvanCarpetAdditionSettings;
import net.minecraft.fluid.WaterFluid;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(WaterFluid.class)
public abstract class WaterFluidMixin {
    @Inject(method = "isInfinite", at = @At("HEAD"), cancellable = true)
    private void isInfinite(CallbackInfoReturnable<Boolean> cir) {
        if (IvanCarpetAdditionSettings.infiniteWaterDisabled) cir.setReturnValue(false);
    }
}
