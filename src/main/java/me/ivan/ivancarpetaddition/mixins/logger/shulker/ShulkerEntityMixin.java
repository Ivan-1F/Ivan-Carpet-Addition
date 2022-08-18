package me.ivan.ivancarpetaddition.mixins.logger.shulker;

import me.ivan.ivancarpetaddition.logging.ICALoggerRegistry;
import me.ivan.ivancarpetaddition.logging.loggers.shulker.ShulkerLogger;
import net.minecraft.entity.mob.ShulkerEntity;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(ShulkerEntity.class)
public class ShulkerEntityMixin {
    @Inject(
            method = "method_7127",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/entity/mob/ShulkerEntity;playSound(Lnet/minecraft/sound/SoundEvent;FF)V"
            ),
            locals = LocalCapture.CAPTURE_FAILHARD
    )
    private void onTryTeleport(CallbackInfoReturnable<Boolean> cir, BlockPos blockPos, int i, BlockPos blockPos2, boolean bl) {
        if (!ICALoggerRegistry.__shulker) return;
        ShulkerLogger.getInstance().onShulkerTeleport(
                (ShulkerEntity) (Object) this,
                blockPos,
                blockPos2
        );
    }
}
