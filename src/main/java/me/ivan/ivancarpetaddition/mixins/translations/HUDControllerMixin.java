package me.ivan.ivancarpetaddition.mixins.translations;

import carpet.logging.HUDController;
import me.ivan.ivancarpetaddition.translations.ICATranslations;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.BaseText;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

//#if MC < 11600
import net.minecraft.entity.player.PlayerEntity;
//#endif


@Mixin(HUDController.class)
public abstract class HUDControllerMixin {
    @ModifyVariable(method = "addMessage", at = @At("HEAD"), argsOnly = true, remap = false)
    private static BaseText applyICATranslationToHudLoggerMessage(
            BaseText hudMessage,
            /* ----- parent method parameters vvv ----- */

            //#if MC >= 11600
            //$$ ServerPlayerEntity player,
            //#else
            PlayerEntity player,
            //#endif

            BaseText hudMessage_
    ) {
        return ICATranslations.translate(hudMessage, (ServerPlayerEntity) player);
    }
}
