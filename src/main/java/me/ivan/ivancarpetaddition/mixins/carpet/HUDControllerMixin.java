package me.ivan.ivancarpetaddition.mixins.carpet;

import carpet.logging.HUDController;
import me.ivan.ivancarpetaddition.logging.ICAHUDController;
import net.minecraft.server.MinecraftServer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(HUDController.class)
public class HUDControllerMixin {
    @Inject(
            method = "update_hud",
            at = @At(
                    value = "INVOKE",
                    target = "Ljava/util/Map;keySet()Ljava/util/Set;"
            ),
            remap = false
    )
    private static void updateTISAdditionHUDLoggers(MinecraftServer server, CallbackInfo ci) {
        ICAHUDController.updateHUD(server);
    }
}
