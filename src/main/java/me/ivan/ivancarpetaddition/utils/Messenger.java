package me.ivan.ivancarpetaddition.utils;

import me.ivan.ivancarpetaddition.mixins.translations.StyleAccessor;
import me.ivan.ivancarpetaddition.translations.ICATranslations;
import net.minecraft.entity.Entity;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.BaseText;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Style;
import net.minecraft.text.TranslatableText;

public class Messenger {
    // Compound Text
    public static BaseText c(Object... fields) {
        return carpet.utils.Messenger.c(fields);
    }

    // Simple Text
    public static BaseText s(String text) {
        return new LiteralText(text);
    }

    // Simple Text with carpet style
    public static BaseText s(String text, String carpetStyle) {
        return formatting(s(text), carpetStyle);
    }

    public static BaseText tr(String key, Object... args) {
        return new TranslatableText(key, args);
    }

    public static void tell(ServerCommandSource source, BaseText text) {
        Entity entity = source.getEntity();
        text = entity instanceof ServerPlayerEntity ?
                ICATranslations.translate(text, (ServerPlayerEntity) entity) :
                ICATranslations.translate(text);
        source.sendFeedback(text, false);
    }

    public static BaseText formatting(BaseText text, String carpetStyle) {
        Style textStyle = text.getStyle();
        StyleAccessor parsedStyle = (StyleAccessor) parseCarpetStyle(carpetStyle);
        textStyle.setColor(parsedStyle.getColorField());
        textStyle.setBold(parsedStyle.getBoldField());
        textStyle.setItalic(parsedStyle.getItalicField());
        textStyle.setUnderline(parsedStyle.getUnderlineField());
        textStyle.setStrikethrough(parsedStyle.getStrikethroughField());
        textStyle.setObfuscated(parsedStyle.getObfuscatedField());
        return style(text, textStyle);
    }

    public static BaseText style(BaseText text, Style style) {
        text.setStyle(style);
        return text;
    }

    public static Style parseCarpetStyle(String style) {
        return carpet.utils.Messenger.parseStyle(style);
    }
}
