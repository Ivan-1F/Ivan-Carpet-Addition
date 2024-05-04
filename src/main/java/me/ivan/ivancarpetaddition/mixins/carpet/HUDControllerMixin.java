package me.ivan.ivancarpetaddition.mixins.carpet;

import carpet.logging.HUDController;
import me.ivan.ivancarpetaddition.logging.ICAHUDController;
import net.minecraft.server.MinecraftServer;
import org.spongepowered.asm.mixin.Mixin;

//#if MC >= 11600
//$$ import org.spongepowered.asm.mixin.Shadow;
//$$ import java.util.List;
//$$ import java.util.function.Consumer;
//#else
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
//#endif

@Mixin(HUDController.class)
public class HUDControllerMixin {
    //#if MC >= 11600
    //$$ @Shadow(remap = false)
    //$$ private static List<Consumer<MinecraftServer>> HUDListeners;
    //$$
    //$$ static
    //$$ {
    //$$ 	HUDListeners.add(ICAHUDController::updateHUD);
    //$$ }
    //#else
    @Inject(
            method = "update_hud",
            at = @At(
                    value = "INVOKE",
                    target = "Ljava/util/Map;keySet()Ljava/util/Set;"
            ),
            remap = false
    )
    private static void updateICAHUDLoggers(MinecraftServer server, CallbackInfo ci) {
        ICAHUDController.updateHUD(server);
    }
    //#endif
}
