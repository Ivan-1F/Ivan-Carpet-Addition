package me.ivan.ivancarpetaddition.commands;

import me.ivan.ivancarpetaddition.translations.TranslationContext;

public abstract class AbstractCommand extends TranslationContext implements CommandRegister {
    public AbstractCommand(String name) {
        super("command." + name);
    }
}
