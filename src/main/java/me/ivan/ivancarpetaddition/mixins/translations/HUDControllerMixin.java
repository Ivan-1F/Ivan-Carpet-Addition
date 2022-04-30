package me.ivan.ivancarpetaddition.mixins.translations;

import carpet.logging.HUDController;
import me.ivan.ivancarpetaddition.translations.ICATranslations;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(HUDController.class)
public abstract class HUDControllerMixin {
    @ModifyVariable(method = "addMessage", at = @At("HEAD"), argsOnly = true, remap = false)
    private static Text applyICATranslationToHudLoggerMessage(Text hudMessage, /* parent method parameters -> */ ServerPlayerEntity player, Text hudMessage_) {
        if (player != null) {
            hudMessage = ICATranslations.translate((MutableText) hudMessage, player);
        }
        return hudMessage;
    }
}
