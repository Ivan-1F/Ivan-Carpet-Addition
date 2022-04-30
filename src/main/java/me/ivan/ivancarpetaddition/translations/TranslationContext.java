package me.ivan.ivancarpetaddition.translations;

import net.minecraft.text.MutableText;

public class TranslationContext {
    private final Translator translator;

    protected TranslationContext(Translator translator) {
        this.translator = translator;
    }

    protected TranslationContext(String translationPath) {
        this(new Translator(translationPath));
    }

    public Translator getTranslator() {
        return translator;
    }

    protected MutableText tr(String key, Object... args) {
        return this.translator.tr(key, args);
    }
}
