package me.ivan.ivancarpetaddition.utils;

import me.ivan.ivancarpetaddition.mixins.translations.StyleAccessor;
import me.ivan.ivancarpetaddition.translations.ICATranslations;
import net.minecraft.entity.Entity;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.*;

import java.util.Arrays;

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

    // Fancy Text
    public static BaseText fancy(String carpetStyle, BaseText displayText, BaseText hoverText, ClickEvent clickEvent) {
        BaseText text = copy(displayText);
        if (carpetStyle != null) {
            text.setStyle(parseCarpetStyle(carpetStyle));
        }
        if (hoverText != null) {
            hover(text, hoverText);
        }
        if (clickEvent != null) {
            click(text, clickEvent);
        }
        return text;
    }

    public static BaseText fancy(BaseText displayText, BaseText hoverText, ClickEvent clickEvent) {
        return fancy(null, displayText, hoverText, clickEvent);
    }

    // Translation Text
    public static BaseText tr(String key, Object... args) {
        return new TranslatableText(key, args);
    }

    public static BaseText copy(BaseText text) {
        return (BaseText) text.deepCopy();
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

    public static BaseText click(BaseText text, ClickEvent clickEvent) {
        text.getStyle().setClickEvent(clickEvent);
        return text;
    }

    public static BaseText hover(BaseText text, HoverEvent hoverEvent) {
        text.getStyle().setHoverEvent(hoverEvent);
        return text;
    }

    public static BaseText hover(BaseText text, BaseText hoverText) {
        return hover(text, new HoverEvent(HoverEvent.Action.SHOW_TEXT, hoverText));
    }

    public static Style parseCarpetStyle(String style) {
        return carpet.utils.Messenger.parseStyle(style);
    }
}
