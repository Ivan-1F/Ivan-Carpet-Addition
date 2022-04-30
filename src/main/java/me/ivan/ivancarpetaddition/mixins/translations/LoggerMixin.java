package me.ivan.ivancarpetaddition.mixins.translations;

import carpet.logging.Logger;
import me.ivan.ivancarpetaddition.translations.ICATranslations;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(Logger.class)
public abstract class LoggerMixin {
    @ModifyVariable(method = "sendPlayerMessage", at = @At("HEAD"), argsOnly = true, remap = false)
    private Text[] applyICATranslationToLoggerMessage(Text[] messages, /* parent method parameters -> */ ServerPlayerEntity player, Text... messages_) {
        for (int i = 0; i < messages.length; i++) {
            messages[i] = ICATranslations.translate((MutableText) messages[i], player);
        }
        return messages;
    }
}
