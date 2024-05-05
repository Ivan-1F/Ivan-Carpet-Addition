package me.ivan.ivancarpetaddition.mixins.translations;

import carpet.logging.HUDController;
import me.ivan.ivancarpetaddition.translations.ICATranslations;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

//#if MC < 11600
import net.minecraft.entity.player.PlayerEntity;
//#endif

//#if MC >= 11900
//$$ import net.minecraft.text.MutableText;
//$$ import net.minecraft.text.Text;
//#else
import net.minecraft.text.BaseText;
//#endif

@Mixin(HUDController.class)
public abstract class HUDControllerMixin {
    @ModifyVariable(method = "addMessage", at = @At("HEAD"), argsOnly = true, remap = false)
    private static
    //#if MC >= 11900
    //$$ Text
    //#else
    BaseText
    //#endif
    applyICATranslationToHudLoggerMessage(
            //#if MC >= 11900
            //$$ Text hudMessage,
            //#else
            BaseText hudMessage,
            //#endif

            /* ----- parent method parameters vvv ----- */

            //#if MC >= 11600
            //$$ ServerPlayerEntity player,
            //#else
            PlayerEntity player,
            //#endif

            //#if MC >= 11900
            //$$ Text hudMessage_
            //#else
            BaseText hudMessage_
            //#endif
    ) {
        return ICATranslations.translate(
                //#if MC >= 11900
                //$$ (MutableText) hudMessage,
                //#else
                hudMessage,
                //#endif
                (ServerPlayerEntity) player
        );
    }
}
