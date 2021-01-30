package me.ivan.ivancarpetaddition.translations;

public class TranslatableBase implements Translatable{

    private final Translator translator;

    public TranslatableBase(Translator translator) {
        this.translator = translator;
    }

    @Override
    public String tr(String key, String text, boolean autoFormat) {
        return null;
    }

    @Override
    public String tr(String key, String text) {
        return null;
    }

    @Override
    public String tr(String key) {
        return null;
    }
}
