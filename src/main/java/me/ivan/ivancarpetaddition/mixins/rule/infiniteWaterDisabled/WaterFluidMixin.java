package me.ivan.ivancarpetaddition.mixins.rule.infiniteWaterDisabled;

import me.ivan.ivancarpetaddition.IvanCarpetAdditionSettings;
import net.minecraft.fluid.BaseFluid;
import net.minecraft.fluid.WaterFluid;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(WaterFluid.class)
public abstract class WaterFluidMixin extends BaseFluid {
    @Override
    protected boolean isInfinite() {
        return !IvanCarpetAdditionSettings.infiniteWaterDisabled;
    }
}
