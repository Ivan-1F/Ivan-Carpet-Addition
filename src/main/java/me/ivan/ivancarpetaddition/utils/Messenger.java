package me.ivan.ivancarpetaddition.utils;

import me.ivan.ivancarpetaddition.mixins.translations.StyleAccessor;
import me.ivan.ivancarpetaddition.translations.ICATranslations;
import net.minecraft.entity.Entity;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.*;

public class Messenger {
    // Compound Text
    public static MutableText c(Object... fields) {
        return (MutableText) carpet.utils.Messenger.c(fields);
    }

    // Simple Text
    public static MutableText s(String text) {
        return Text.literal(text);
    }

    // Simple Text with carpet style
    public static MutableText s(String text, String carpetStyle) {
        return formatting(s(text), carpetStyle);
    }

    // Fancy Text
    public static MutableText fancy(String carpetStyle, MutableText displayText, MutableText hoverText, ClickEvent clickEvent) {
        MutableText text = copy(displayText);
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

    public static MutableText fancy(MutableText displayText, MutableText hoverText, ClickEvent clickEvent) {
        return fancy(null, displayText, hoverText, clickEvent);
    }

    // Translation Text
    public static MutableText tr(String key, Object... args) {
        return Text.translatable(key, args);
    }

    public static MutableText copy(Text text) {
        return text.shallowCopy();
    }

    public static void tell(ServerCommandSource source, MutableText text) {
        Entity entity = source.getEntity();
        text = entity instanceof ServerPlayerEntity ?
                ICATranslations.translate(text, (ServerPlayerEntity) entity) :
                ICATranslations.translate(text);
        source.sendFeedback(text, false);
    }

    public static MutableText formatting(MutableText text, String carpetStyle) {
        Style textStyle = text.getStyle();
        StyleAccessor parsedStyle = (StyleAccessor) parseCarpetStyle(carpetStyle);
        textStyle =  textStyle.withColor(parsedStyle.getColorField());
        textStyle = textStyle.withBold(parsedStyle.getBoldField());
        textStyle = textStyle.withItalic(parsedStyle.getItalicField());
        ((StyleAccessor) textStyle).setUnderlinedField(parsedStyle.getUnderlineField());
        ((StyleAccessor) textStyle).setStrikethroughField(parsedStyle.getStrikethroughField());
        ((StyleAccessor) textStyle).setObfuscatedField(parsedStyle.getObfuscatedField());
        return style(text, textStyle);
    }

    public static MutableText style(MutableText text, Style style) {
        text.setStyle(style);
        return text;
    }

    public static MutableText click(MutableText text, ClickEvent clickEvent) {
        style(text, text.getStyle().withClickEvent(clickEvent));
        return text;
    }

    public static MutableText hover(MutableText text, HoverEvent hoverEvent) {
        style(text, text.getStyle().withHoverEvent(hoverEvent));
        return text;
    }

    public static MutableText hover(MutableText text, MutableText hoverText) {
        return hover(text, new HoverEvent(HoverEvent.Action.SHOW_TEXT, hoverText));
    }

    public static Style parseCarpetStyle(String style) {
        return carpet.utils.Messenger.parseStyle(style);
    }
}
