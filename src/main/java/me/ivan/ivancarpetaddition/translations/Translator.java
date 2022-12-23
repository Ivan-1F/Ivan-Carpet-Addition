package me.ivan.ivancarpetaddition.translations;

import me.ivan.ivancarpetaddition.utils.Messenger;
import net.minecraft.text.BaseText;

public class Translator {
    private final String translationPath;

    public Translator getDerivedTranslator(String derivedName) {
        return new Translator(this.translationPath + "." + derivedName);
    }

    public Translator(String translationPath) {
        this.translationPath = translationPath;
    }

    public BaseText tr(String key, Object... args) {
        String translationKey = TranslationConstants.TRANSLATION_KEY_PREFIX + this.translationPath + "." + key;
        return Messenger.tr(translationKey, args);
    }
}
