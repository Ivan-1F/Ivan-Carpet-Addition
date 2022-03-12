package me.ivan.ivancarpetaddition.mixins.rule.stopFreezing;

import me.ivan.ivancarpetaddition.IvanCarpetAdditionSettings;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldView;
import net.minecraft.world.biome.Biome;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Biome.class)
public class BiomeMixin {
    @Inject(method = "canSetIce(Lnet/minecraft/world/WorldView;Lnet/minecraft/util/math/BlockPos;Z)Z", at = @At("HEAD"), cancellable = true)
    private void stopFreezing(WorldView world, BlockPos pos, boolean doWaterCheck, CallbackInfoReturnable<Boolean> cir) {
        if (IvanCarpetAdditionSettings.stopFreezing) cir.setReturnValue(false);
    }
}
