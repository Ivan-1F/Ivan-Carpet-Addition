package me.ivan.ivancarpetaddition.utils;

import com.google.common.collect.ImmutableMap;
import me.ivan.ivancarpetaddition.mixins.translations.StyleAccessor;
import me.ivan.ivancarpetaddition.translations.ICATranslations;
import me.ivan.ivancarpetaddition.translations.Translator;
import me.ivan.ivancarpetaddition.utils.compat.DimensionWrapper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.*;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3i;
import org.jetbrains.annotations.Nullable;


/**
 * Reference: Carpet TIS Addition
 */
public class Messenger {
    private static final Translator translator = new Translator("util");

    // Compound Text
    public static MutableText c(Object... fields) {
        return (MutableText) carpet.utils.Messenger.c(fields);
    }

    // Simple Text
    public static MutableText s(Object text) {
        return Text.literal(text.toString());
    }

    // Simple Text with carpet style
    public static MutableText s(Object text, String carpetStyle) {
        return formatting(s(text), carpetStyle);
    }

    // Simple Text with formatting
    public static MutableText s(Object text, Formatting textFormatting) {
        return formatting(s(text), textFormatting);
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

    public static MutableText copy(MutableText text) {
        return text.copy();
    }

    public static void tell(ServerCommandSource source, MutableText text) {
        Entity entity = source.getEntity();
        text = entity instanceof ServerPlayerEntity ?
                ICATranslations.translate(text, (ServerPlayerEntity) entity) :
                ICATranslations.translate(text);
        MutableText finalText = text;
        source.sendFeedback(() -> finalText, false);
    }

    public static MutableText formatting(MutableText text, Formatting... formattings) {
        text.formatted(formattings);
        return text;
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

    public static MutableText entity(String style, Entity entity) {
        MutableText entityBaseName = (MutableText) entity.getType().getName();
        MutableText entityDisplayName = (MutableText) entity.getName();
        MutableText hoverText = Messenger.c(
                translator.tr("entity_type", entityBaseName, s(EntityType.getId(entity.getType()).toString())), newLine(),
                getTeleportHint(entityDisplayName)
        );
        return fancy(style, entityDisplayName, hoverText, new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, TextUtil.tp(entity)));
    }

    private static MutableText getTeleportHint(MutableText dest) {
        return translator.tr("teleport_hint", dest);
    }

    public static MutableText newLine() {
        return s("\n");
    }

    private static final ImmutableMap<DimensionWrapper, MutableText> DIMENSION_NAME = ImmutableMap.of(
            DimensionWrapper.OVERWORLD, tr("createWorld.customize.preset.overworld"),
            DimensionWrapper.THE_NETHER, tr("advancements.nether.root.title"),
            DimensionWrapper.THE_END, tr("advancements.end.root.title")
    );

    public static MutableText dimension(DimensionWrapper dim) {
        MutableText dimText = DIMENSION_NAME.get(dim);
        return dimText != null ? copy(dimText) : Messenger.s(dim.getIdentifierString());
    }

    private static MutableText __coord(String style, @Nullable DimensionWrapper dim, String posStr, String command) {
        MutableText hoverText = Messenger.s("");
        hoverText.append(getTeleportHint(Messenger.s(posStr)));
        if (dim != null) {
            hoverText.append("\n");
            hoverText.append(translator.tr("teleport_hint.dimension"));
            hoverText.append(": ");
            hoverText.append(dimension(dim));
        }
        return fancy(style, Messenger.s(posStr), hoverText, new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, command));
    }

    public static MutableText coord(String style, Vec3d pos, DimensionWrapper dim) {
        return __coord(style, dim, TextUtil.coord(pos), TextUtil.tp(pos, dim));
    }

    public static MutableText coord(String style, Vec3i pos, DimensionWrapper dim) {
        return __coord(style, dim, TextUtil.coord(pos), TextUtil.tp(pos, dim));
    }

    public static MutableText coord(String style, ChunkPos pos, DimensionWrapper dim) {
        return __coord(style, dim, TextUtil.coord(pos), TextUtil.tp(pos, dim));
    }

    public static MutableText coord(String style, Vec3d pos) {
        return __coord(style, null, TextUtil.coord(pos), TextUtil.tp(pos));
    }

    public static MutableText coord(String style, Vec3i pos) {
        return __coord(style, null, TextUtil.coord(pos), TextUtil.tp(pos));
    }

    public static MutableText coord(String style, ChunkPos pos) {
        return __coord(style, null, TextUtil.coord(pos), TextUtil.tp(pos));
    }

    public static MutableText coord(Vec3d pos, DimensionWrapper dim) {
        return coord(null, pos, dim);
    }

    public static MutableText coord(Vec3i pos, DimensionWrapper dim) {
        return coord(null, pos, dim);
    }

    public static MutableText coord(ChunkPos pos, DimensionWrapper dim) {
        return coord(null, pos, dim);
    }

    public static MutableText coord(Vec3d pos) {
        return coord(null, pos);
    }

    public static MutableText coord(Vec3i pos) {
        return coord(null, pos);
    }

    public static MutableText coord(ChunkPos pos) {
        return coord(null, pos);
    }

    public static Style parseCarpetStyle(String style) {
        return carpet.utils.Messenger.parseStyle(style);
    }
}
