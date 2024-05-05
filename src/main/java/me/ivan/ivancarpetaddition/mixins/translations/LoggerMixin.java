package me.ivan.ivancarpetaddition.mixins.translations;

import carpet.logging.Logger;
import me.ivan.ivancarpetaddition.translations.ICATranslations;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

//#if MC >= 11900
//$$ import net.minecraft.text.MutableText;
//$$ import net.minecraft.text.Text;
//#else
import net.minecraft.text.BaseText;
//#endif

@Mixin(Logger.class)
public abstract class LoggerMixin {
    @ModifyVariable(method = "sendPlayerMessage", at = @At("HEAD"), argsOnly = true, remap = false)
    private
    //#if MC >= 11900
    //$$ Text[]
    //#else
    BaseText[]
    //#endif
    applyICATranslationToLoggerMessage(
            //#if MC >= 11900
            //$$ Text[] messages,
            //#else
            BaseText[] messages,
            //#endif
            /* ----- parent method parameters vvv ----- */
            ServerPlayerEntity player,
            //#if MC >= 11900
            //$$ Text... message_
            //#else
            BaseText... messages_
            //#endif
    ) {
        for (int i = 0; i < messages.length; i++) {
            messages[i] = ICATranslations.translate(
                    //#if MC >= 11900
                    //$$ (MutableText) messages[i],
                    //#else
                    messages[i],
                    //#endif
                    player
            );
        }
        return messages;
    }
}
