package me.ivan.ivancarpetaddition.mixins.translations;

import me.ivan.ivancarpetaddition.translations.ICATranslations;
import me.ivan.ivancarpetaddition.translations.TranslationConstants;
import net.minecraft.text.TranslatableText;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

/**
 * From Carpet TIS Addition
 */
@Mixin(TranslatableText.class)
public abstract class TranslatableTextMixin {
    @Shadow
    @Final
    private String key;

    @ModifyArg(
            method = "updateTranslations",
            at = @At(
                    value = "INVOKE",
                    //#if MC >= 11900
                    //$$ target = "Lnet/minecraft/text/TranslatableTextContent;forEachPart(Ljava/lang/String;Ljava/util/function/Consumer;)V"
                    //#elseif MC >= 11800
                    //$$ target = "Lnet/minecraft/text/TranslatableText;forEachPart(Ljava/lang/String;Ljava/util/function/Consumer;)V"
                    //#else
                    target = "Lnet/minecraft/text/TranslatableText;setTranslation(Ljava/lang/String;)V"
                    //#endif
            )
    )
    private String applyICATranslation(String vanillaTranslatedFormattingString) {
        if (this.key.startsWith(TranslationConstants.TRANSLATION_KEY_PREFIX) && vanillaTranslatedFormattingString.equals(this.key)) {
            String icaTranslated = ICATranslations.translateKeyToFormattedString(ICATranslations.getServerLanguage(), this.key);
            if (icaTranslated != null) {
                return icaTranslated;
            }
        }
        return vanillaTranslatedFormattingString;
    }
}
