package me.ivan.ivancarpetaddition.utils;

import com.google.common.collect.ImmutableMap;
import me.ivan.ivancarpetaddition.mixins.translations.StyleAccessor;
import me.ivan.ivancarpetaddition.mixins.translations.TranslatableTextAccessor;
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

//#if MC < 11500
//$$ import me.ivan.ivancarpetaddition.mixins.translations.MessengerInvoker;
//#endif

//#if MC >= 11600
//$$ import me.ivan.ivancarpetaddition.mixins.translations.TranslatableTextAccessor;
//#endif

//#if MC >= 11800
//$$ import com.google.common.collect.Lists;
//$$ import java.util.List;
//#endif


/**
 * Reference: Carpet TIS Addition
 */
public class Messenger {
    private static final Translator translator = new Translator("util");

    /**
     * MC 1.19 +- compatibility
     * Get the object of the text object that indicates variable kind of text type
     */
    public static
    //#if MC >= 11900
    //$$ TextContent
    //#else
    BaseText
    //#endif
    getTextContent(BaseText text)
    {
        //#if MC >= 11900
        //$$ return text.getContent();
        //#else
        return text;
        //#endif
    }

    // Compound Text in carpet style
    public static BaseText c(Object... fields) {
        return
                //#if MC >= 11900
                //$$ (MutableText)
                //#endif
                carpet.utils.Messenger.c(fields);
    }

    // Simple Text
    public static BaseText s(Object text) {
        return
                //#if MC >= 11900
                //$$ Text.literal
                //#else
                new LiteralText
                //#endif
                        (text.toString());
    }

    // Simple Text with carpet style
    public static BaseText s(Object text, String carpetStyle) {
        return formatting(s(text), carpetStyle);
    }

    // Simple Text with formatting
    public static BaseText s(Object text, Formatting textFormatting) {
        return formatting(s(text), textFormatting);
    }

    // Fancy Text
    public static BaseText fancy(String carpetStyle, BaseText displayText, BaseText hoverText, ClickEvent clickEvent) {
        // copy the text to make sure the original text will not be modified
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

    public static BaseText format(String formatter, Object... args) {
        TranslatableTextAccessor dummy = (TranslatableTextAccessor) getTextContent(tr(formatter, args));

        try {
            //#if MC >= 11800
            //$$ List<StringVisitable> segments = Lists.newArrayList();
            //$$ dummy.invokeForEachPart(formatter, segments::add);
            //#else
            dummy.getTranslations().clear();
            dummy.invokeSetTranslation(formatter);
            //#endif

            return Messenger.c(
                    //#if MC >= 11900
                    //$$ segments.stream().map(stringVisitable -> {
                    //$$     if (stringVisitable instanceof Text) {
                    //$$         return (Text) stringVisitable;
                    //$$     }
                    //$$     return Messenger.s(stringVisitable.getString());
                    //$$ }).toArray()
                    //#elseif MC > 11800
                    //$$ segments.stream().map(stringVisitable -> {
                    //$$     if (stringVisitable instanceof BaseText) {
                    //$$         return (BaseText) stringVisitable;
                    //$$     }
                    //$$     return Messenger.s(stringVisitable.getString());
                    //$$ }).toArray()
                    //#elseif MC > 11600
                    //$$ dummy.getTranslations().stream().map(stringVisitable -> {
                    //$$     if (stringVisitable instanceof BaseText) {
                    //$$         return (BaseText) stringVisitable;
                    //$$     }
                    //$$     return Messenger.s(stringVisitable.getString());
                    //$$ }).toArray()
                    //#else
                    dummy.getTranslations().toArray(new Object[0])
                    //#endif
            );
        }
        catch (TranslationException e) {
            throw new IllegalArgumentException(formatter);
        }
    }

    // Translation Text
    public static BaseText tr(String key, Object... args) {
        return
                //#if MC >= 11900
                //$$ Text.translatable
                //#else
                new TranslatableText
                //#endif
                    (key, args);
    }

    public static BaseText copy(BaseText text) {
        BaseText copied;

        //#if MC >= 11900
        //$$ copied = text.copy();
        //#elseif MC >= 11600
        //$$ copied = (BaseText) text.shallowCopy();
        //#else
        copied = (BaseText) text.deepCopy();
        //#endif

        // mc1.16+ doesn't make a copy of args of a TranslatableText,
        // so we need to copy that by ourselves
        //#if MC >= 11600
        //$$ if (getTextContent(copied) instanceof TranslatableText) {
        //$$     TranslatableText translatableText = (TranslatableText) getTextContent(copied);
        //$$     Object[] args = translatableText.getArgs().clone();
        //$$     for (int i = 0; i < args.length; i++) {
        //$$         if (args[i] instanceof BaseText) {
        //$$             args[i] = copy((BaseText) args[i]);
        //$$         }
        //$$     }
        //$$     ((TranslatableTextAccessor) translatableText).setArgs(args);
        //$$ }
        //#endif

        return copied;
    }

    public static void tell(ServerCommandSource source, BaseText text) {
        Entity entity = source.getEntity();
        text = entity instanceof ServerPlayerEntity ?
                ICATranslations.translate(text, (ServerPlayerEntity) entity) :
                ICATranslations.translate(text);
        BaseText finalText = text;
        source.sendFeedback(
                //#if MC >= 12000
                //$$ () ->
                //#endif
                finalText,
                false
        );
    }

    public static BaseText formatting(BaseText text, Formatting... formattings) {
        text.formatted(formattings);
        return text;
    }

    public static BaseText formatting(BaseText text, String carpetStyle) {
        Style textStyle = text.getStyle();
        StyleAccessor parsedStyle = (StyleAccessor) parseCarpetStyle(carpetStyle);

        //#if MC >= 11600
        //$$ textStyle = textStyle.withColor(parsedStyle.getColorField());
        //$$ textStyle = textStyle.withBold(parsedStyle.getBoldField());
        //$$ textStyle = textStyle.withItalic(parsedStyle.getItalicField());
        //$$ ((StyleAccessor)textStyle).setUnderlinedField(parsedStyle.getUnderlineField());
        //$$ ((StyleAccessor)textStyle).setStrikethroughField(parsedStyle.getStrikethroughField());
        //$$ ((StyleAccessor)textStyle).setObfuscatedField(parsedStyle.getObfuscatedField());
        //#else
        textStyle.setColor(parsedStyle.getColorField());
        textStyle.setBold(parsedStyle.getBoldField());
        textStyle.setItalic(parsedStyle.getItalicField());
        textStyle.setUnderline(parsedStyle.getUnderlineField());
        textStyle.setStrikethrough(parsedStyle.getStrikethroughField());
        textStyle.setObfuscated(parsedStyle.getObfuscatedField());
        //#endif

        return style(text, textStyle);
    }

    public static BaseText style(BaseText text, Style style) {
        text.setStyle(style);
        return text;
    }

    public static BaseText click(BaseText text, ClickEvent clickEvent) {
        //#if MC >= 11600
        //$$ style(text, text.getStyle().withClickEvent(clickEvent));
        //#else
        text.getStyle().setClickEvent(clickEvent);
        //#endif
        return text;
    }

    public static BaseText hover(BaseText text, HoverEvent hoverEvent) {
        //#if MC >= 11600
        //$$ style(text, text.getStyle().withHoverEvent(hoverEvent));
        //#else
        text.getStyle().setHoverEvent(hoverEvent);
        //#endif
        return text;
    }

    public static BaseText hover(BaseText text, BaseText hoverText) {
        return hover(text, new HoverEvent(HoverEvent.Action.SHOW_TEXT, hoverText));
    }

    public static BaseText entity(String style, Entity entity) {
        BaseText entityBaseName = (BaseText) entity.getType().getName();
        BaseText entityDisplayName = (BaseText) entity.getName();
        BaseText hoverText = Messenger.c(
                translator.tr("entity_type", entityBaseName, s(EntityType.getId(entity.getType()).toString())), newLine(),
                getTeleportHint(entityDisplayName)
        );
        return fancy(style, entityDisplayName, hoverText, new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, TextUtil.tp(entity)));
    }

    private static BaseText getTeleportHint(BaseText dest) {
        return translator.tr("teleport_hint", dest);
    }

    public static BaseText newLine() {
        return s("\n");
    }

    private static final ImmutableMap<DimensionWrapper, BaseText> DIMENSION_NAME = ImmutableMap.of(
            DimensionWrapper.OVERWORLD, tr("createWorld.customize.preset.overworld"),
            DimensionWrapper.THE_NETHER, tr("advancements.nether.root.title"),
            DimensionWrapper.THE_END, tr("advancements.end.root.title")
    );

    public static BaseText dimension(DimensionWrapper dim) {
        BaseText dimText = DIMENSION_NAME.get(dim);
        return dimText != null ? copy(dimText) : Messenger.s(dim.getIdentifierString());
    }

    private static BaseText __coord(String style, @Nullable DimensionWrapper dim, String posStr, String command) {
        BaseText hoverText = Messenger.s("");
        hoverText.append(getTeleportHint(Messenger.s(posStr)));
        if (dim != null) {
            hoverText.append("\n");
            hoverText.append(translator.tr("teleport_hint.dimension"));
            hoverText.append(": ");
            hoverText.append(dimension(dim));
        }
        return fancy(style, Messenger.s(posStr), hoverText, new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, command));
    }

    public static BaseText coord(String style, Vec3d pos, DimensionWrapper dim) {
        return __coord(style, dim, TextUtil.coord(pos), TextUtil.tp(pos, dim));
    }

    public static BaseText coord(String style, Vec3i pos, DimensionWrapper dim) {
        return __coord(style, dim, TextUtil.coord(pos), TextUtil.tp(pos, dim));
    }

    public static BaseText coord(String style, ChunkPos pos, DimensionWrapper dim) {
        return __coord(style, dim, TextUtil.coord(pos), TextUtil.tp(pos, dim));
    }

    public static BaseText coord(String style, Vec3d pos) {
        return __coord(style, null, TextUtil.coord(pos), TextUtil.tp(pos));
    }

    public static BaseText coord(String style, Vec3i pos) {
        return __coord(style, null, TextUtil.coord(pos), TextUtil.tp(pos));
    }

    public static BaseText coord(String style, ChunkPos pos) {
        return __coord(style, null, TextUtil.coord(pos), TextUtil.tp(pos));
    }

    public static BaseText coord(Vec3d pos, DimensionWrapper dim) {
        return coord(null, pos, dim);
    }

    public static BaseText coord(Vec3i pos, DimensionWrapper dim) {
        return coord(null, pos, dim);
    }

    public static BaseText coord(ChunkPos pos, DimensionWrapper dim) {
        return coord(null, pos, dim);
    }

    public static BaseText coord(Vec3d pos) {
        return coord(null, pos);
    }

    public static BaseText coord(Vec3i pos) {
        return coord(null, pos);
    }

    public static BaseText coord(ChunkPos pos) {
        return coord(null, pos);
    }

    public static Style parseCarpetStyle(String style) {
        //#if MC >= 11500
        return carpet.utils.Messenger.parseStyle(style);
        //#else
        //$$ return MessengerInvoker.invokeApplyStyleToTextComponent(Messenger.s(""), style).getStyle();
        //#endif
    }
}
