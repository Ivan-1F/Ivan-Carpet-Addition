package me.ivan.ivancarpetaddition;

import me.ivan.ivancarpetaddition.translations.ICATranslations;

public class TranslationTest {
    public static void main(String[] args) {
        ICATranslations.loadTranslations();
        System.out.println(ICATranslations.getTranslation("zh_cn"));
    }
}
