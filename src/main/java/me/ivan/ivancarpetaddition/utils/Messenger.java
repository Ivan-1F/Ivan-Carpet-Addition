package me.ivan.ivancarpetaddition.utils;

import net.minecraft.text.BaseText;
import net.minecraft.text.TranslatableText;

public class Messenger {
    public static BaseText tr(String key, Object... args) {
        return new TranslatableText(key, args);
    }
}
