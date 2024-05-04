package me.ivan.ivancarpetaddition.mixins.rule.stopFreezing;

import me.ivan.ivancarpetaddition.IvanCarpetAdditionSettings;
import net.minecraft.util.math.BlockPos;
//#if MC >= 11500
import net.minecraft.world.WorldView;
//#else
//$$ import net.minecraft.world.CollisionView;
//#endif
import net.minecraft.world.biome.Biome;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Biome.class)
public class BiomeMixin {
    @Inject(
            //#if MC >= 11500
            method = "canSetIce(Lnet/minecraft/world/WorldView;Lnet/minecraft/util/math/BlockPos;Z)Z",
            //#else
            //$$ method = "canSetIce",
            //#endif
            at = @At("HEAD"),
            cancellable = true
    )
    private void stopFreezing(
            //#if MC >= 11500
            WorldView world,
            //#else
            //$$ CollisionView world,
            //#endif
            BlockPos pos,
            //#if MC >= 11500
            boolean doWaterCheck,
            //#endif
            CallbackInfoReturnable<Boolean> cir
    ) {
        if (IvanCarpetAdditionSettings.stopFreezing) cir.setReturnValue(false);
    }
}
