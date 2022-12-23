package me.ivan.ivancarpetaddition.translations;

import me.ivan.ivancarpetaddition.IvanCarpetAdditionServer;

public class TranslationConstants {
    public static final String DEFAULT_LANGUAGE = "en_us";
    public static final String TRANSLATION_NAMESPACE = IvanCarpetAdditionServer.compactName;  // "ivancarpetaddition"
    public static final String TRANSLATION_KEY_PREFIX = TRANSLATION_NAMESPACE + ".";  // "ivancarpetaddition."
    public static final String CARPET_TRANSLATIONS_KEY_PREFIX = TRANSLATION_KEY_PREFIX + "carpet_translations.";  // "ivancarpetaddition.carpet_translations."
}
