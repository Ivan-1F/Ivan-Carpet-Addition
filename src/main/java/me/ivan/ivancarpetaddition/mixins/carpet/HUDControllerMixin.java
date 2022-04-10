package me.ivan.ivancarpetaddition.mixins.carpet;

import carpet.logging.HUDController;
import me.ivan.ivancarpetaddition.logging.ICAHUDController;
import net.minecraft.server.MinecraftServer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import java.util.List;
import java.util.function.Consumer;

@Mixin(HUDController.class)
public class HUDControllerMixin {
    @Shadow(remap = false)
    private static List<Consumer<MinecraftServer>> HUDListeners;

    static {
        HUDListeners.add(ICAHUDController::updateHUD);
    }
}
