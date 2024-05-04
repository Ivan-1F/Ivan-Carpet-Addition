package me.ivan.ivancarpetaddition.translations;

//#if MC >= 11500
import carpet.CarpetSettings;
//#else
//$$ import me.ivan.ivancarpetaddition.utils.compat.carpet.CarpetSettings;
//#endif
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.gson.Gson;
import me.ivan.ivancarpetaddition.IvanCarpetAdditionServer;
import me.ivan.ivancarpetaddition.mixins.translations.StyleAccessor;
import me.ivan.ivancarpetaddition.mixins.translations.TranslatableTextAccessor;
import me.ivan.ivancarpetaddition.utils.FileUtil;
import me.ivan.ivancarpetaddition.utils.Messenger;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.util.*;

/**
 * Reference: Carpet TIS Addition
 */
public class ICATranslations {
    private static final String LANG_DIR = String.format("assets/%s/lang", TranslationConstants.TRANSLATION_NAMESPACE);

    public static final Map<String, Map<String, String>> translations = Maps.newLinkedHashMap();
    public static final Set<String> languages = Sets.newHashSet();

    private static class LanguageList extends ArrayList<String> {}
    private static class TranslationMapping extends LinkedHashMap<String, String> {}

    private static List<String> getAvailableTranslations() {
        try {
            String dataStr = FileUtil.readFile(LANG_DIR + "/meta/languages.json");
            return new Gson().fromJson(dataStr, LanguageList.class);
        } catch (Exception e) {
            IvanCarpetAdditionServer.LOGGER.warn("Failed to load translations");
            return Lists.newArrayList();
        }
    }

    public static void loadTranslations() {
        getAvailableTranslations().forEach(ICATranslations::loadTranslation);
    }

    public static void loadTranslation(String language) {
        String path = String.format("%s/%s.json", LANG_DIR, language);
        String data;
        try {
            data = FileUtil.readFile(path);
            Map<String, String> translation = new Gson().fromJson(data, TranslationMapping.class);
            translations.put(language, translation);
            languages.add(language);
        } catch (IOException e) {
            IvanCarpetAdditionServer.LOGGER.warn("Failed to load translation: " + language);
        }
    }

    public static String getServerLanguage() {
        return CarpetSettings.language.equalsIgnoreCase("none") ? TranslationConstants.DEFAULT_LANGUAGE : CarpetSettings.language;
    }

    @NotNull
    public static Map<String, String> getTranslation(String lang) {
        return translations.getOrDefault(lang, Collections.emptyMap());
    }

    @Nullable
    public static String translateKeyToFormattedString(String lang, String key) {
        return getTranslation(lang.toLowerCase()).get(key);
    }

    public static BaseText translate(BaseText text, ServerPlayerEntity player) {
        return translate(text, ((ServerPlayerEntityWithClientLanguage) player).getClientLanguage$ICA().toLowerCase());
    }

    public static BaseText translate(BaseText text) {
        return translate(text, getServerLanguage());
    }

    public static BaseText translate(BaseText text, String lang) {
        return translate(text, lang, false);
    }

    public static BaseText translate(BaseText text, String lang, boolean suppressWarnings) {
        if (text instanceof TranslatableText) {
            TranslatableText translatableText = (TranslatableText) text;
            if (translatableText.getKey().startsWith(TranslationConstants.TRANSLATION_KEY_PREFIX)) {
                String formattedString = translateKeyToFormattedString(lang, translatableText.getKey());
                if (formattedString == null) {
                    // not supported language
                    formattedString = translateKeyToFormattedString(TranslationConstants.DEFAULT_LANGUAGE, translatableText.getKey());
                }
                if (formattedString != null) {
                    BaseText origin = text;
                    TranslatableTextAccessor fixedTranslatableText = (TranslatableTextAccessor) (new TranslatableText(formattedString, translatableText.getArgs()));
                    try {
                        fixedTranslatableText.getTranslations().clear();
                        fixedTranslatableText.invokeSetTranslation(formattedString);
                        //#if MC >= 11600
                        //$$ text = Messenger.c(fixedTranslatableText.getTranslations().stream().map(stringVisitable -> {
                        //$$     if (stringVisitable instanceof BaseText) {
                        //$$         return (BaseText) stringVisitable;
                        //$$     }
                        //$$     return Messenger.s(stringVisitable.getString());
                        //$$ }).toArray());
                        //#else
                        text = Messenger.c(fixedTranslatableText.getTranslations().toArray(new Object[0]));
                        //#endif
                    } catch (TranslationException e) {
                        text = Messenger.s(formattedString);
                    }
                    text.getSiblings().addAll(origin.getSiblings());
                    text.setStyle(origin.getStyle());
                } else if (!suppressWarnings) {
                    IvanCarpetAdditionServer.LOGGER.warn("Unknown translation key {}", translatableText.getKey());
                }
            }
        }

        // translate hover text
        HoverEvent hoverEvent = ((StyleAccessor) text.getStyle()).getHoverEventField();
        if (hoverEvent != null) {
            //#if MC >= 11600
            //$$ Object hoverText = hoverEvent.getValue(hoverEvent.getAction());
            //$$ if (hoverEvent.getAction() == HoverEvent.Action.SHOW_TEXT && hoverText instanceof BaseText) {
            //$$     text.setStyle(text.getStyle().withHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, translate((BaseText) hoverText, lang))));
            //$$ }
            //#else
            text.getStyle().setHoverEvent(new HoverEvent(hoverEvent.getAction(), translate((BaseText) hoverEvent.getValue(), lang)));
            //#endif
        }

        // translate sibling texts
        List<Text> siblings = text.getSiblings();
        siblings.replaceAll(text1 -> translate((BaseText) text1, lang));
        return text;
    }
}
