package me.ivan.ivancarpetaddition.logging.loggers;

import me.ivan.ivancarpetaddition.translations.TranslationContext;

public abstract class AbstractLogger extends TranslationContext {
    protected final static String MULTI_OPTION_SEP_REG = "[,. ]";

    public AbstractLogger(String name) {
        super("logger." + name);
    }
}
