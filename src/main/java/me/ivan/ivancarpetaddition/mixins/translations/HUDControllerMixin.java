package me.ivan.ivancarpetaddition.mixins.translations;

import carpet.logging.HUDController;
import me.ivan.ivancarpetaddition.translations.ICATranslations;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.BaseText;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(HUDController.class)
public abstract class HUDControllerMixin {
    @ModifyVariable(method = "addMessage", at = @At("HEAD"), argsOnly = true, remap = false)
    private static BaseText applyTISCarpetTranslationToHudLoggerMessage(BaseText hudMessage, /* parent method parameters -> */ ServerPlayerEntity player, BaseText hudMessage_) {
        if (player != null) {
            hudMessage = ICATranslations.translate(hudMessage, player);
        }
        return hudMessage;
    }
}
