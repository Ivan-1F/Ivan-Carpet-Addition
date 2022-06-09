package me.ivan.ivancarpetaddition.translations;

import carpet.CarpetSettings;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import me.ivan.ivancarpetaddition.IvanCarpetAdditionServer;
import me.ivan.ivancarpetaddition.mixins.translations.StyleAccessor;
import me.ivan.ivancarpetaddition.mixins.translations.TranslatableTextAccessor;
import me.ivan.ivancarpetaddition.utils.FileUtil;
import me.ivan.ivancarpetaddition.utils.Messenger;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.yaml.snakeyaml.Yaml;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Reference: Carpet TIS Addition
 */
public class ICATranslations {
    public static final String DEFAULT_LANGUAGE = "en_us";
    public static final String TRANSLATION_NAMESPACE = IvanCarpetAdditionServer.compactName;  // "ivancarpetaddition"
    public static final String TRANSLATION_KEY_PREFIX = TRANSLATION_NAMESPACE + ".";  // "ivancarpetaddition."
    private static final String LANG_DIR = String.format("assets/%s/lang", TRANSLATION_NAMESPACE);

    public static final Map<String, Map<String, String>> translations = Maps.newLinkedHashMap();
    public static final Set<String> languages = Sets.newHashSet();

    @SuppressWarnings("unchecked")
    private static List<String> getAvailableTranslations() {
        try {
            String dataStr = FileUtil.readFile(LANG_DIR + "/meta/languages.yml");
            Map<String, Object> yamlMap = new Yaml().load(dataStr);
            return (List<String>) yamlMap.get("languages");
        } catch (Exception e) {
            IvanCarpetAdditionServer.LOGGER.warn("Failed to load translations");
            return Lists.newArrayList();
        }
    }

    public static void loadTranslations() {
        getAvailableTranslations().forEach(ICATranslations::loadTranslation);
    }

    public static void loadTranslation(String language) {
        String path = String.format("%s/%s.yml", LANG_DIR, language);
        String data;
        try {
            data = FileUtil.readFile(path);
        } catch (IOException e) {
            IvanCarpetAdditionServer.LOGGER.warn("Failed to load translation: " + language);
            return;
        }
        Map<String, Object> yaml = new Yaml().load(data);
        Map<String, String> translation = Maps.newLinkedHashMap();
        build(translation, yaml, "");
        translations.put(language, translation);
        languages.add(language);
    }

    @SuppressWarnings("unchecked")
    public static void build(Map<String, String> translation, Map<String, Object> yaml, String prefix) {
        yaml.forEach((key, value) -> {
            String fullKey = prefix.isEmpty() ? key : (!key.equals(".") ? prefix + "." + key : prefix);
            if (value instanceof String) {
                translation.put(fullKey, (String) value);
            } else if (value instanceof Map) {
                build(translation, (Map<String, Object>) value, fullKey);
            } else {
                throw new RuntimeException(String.format("Unknown type %s in with key %s", value.getClass(), fullKey));
            }
        });
    }

    public static String getServerLanguage() {
        return CarpetSettings.language.equalsIgnoreCase("none") ? DEFAULT_LANGUAGE : CarpetSettings.language;
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
            String formattedString = translateKeyToFormattedString(lang, translatableText.getKey());
            if (formattedString == null) {
                // not supported language
                formattedString = translateKeyToFormattedString(DEFAULT_LANGUAGE, translatableText.getKey());
            }
            if (formattedString != null) {
                BaseText origin = text;
                TranslatableTextAccessor fixedTranslatableText = (TranslatableTextAccessor) (new TranslatableText(formattedString, translatableText.getArgs()));
                try {
                    fixedTranslatableText.getTranslations().clear();
                    fixedTranslatableText.invokeSetTranslation(formattedString);
                    text = Messenger.c(fixedTranslatableText.getTranslations().stream().map(stringVisitable -> {
                        if (stringVisitable instanceof BaseText) {
                            return (BaseText) stringVisitable;
                        }
                        return Messenger.s(stringVisitable.getString());
                    }).toArray());
                } catch (TranslationException e) {
                    text = Messenger.s(formattedString);
                }
                text.getSiblings().addAll(origin.getSiblings());
                text.setStyle(origin.getStyle());
            } else if (!suppressWarnings) {
                IvanCarpetAdditionServer.LOGGER.warn("Unknown translation key {}", translatableText.getKey());
            }
        }

        // translate hover text
        HoverEvent hoverEvent = ((StyleAccessor) text.getStyle()).getHoverEventField();
        if (hoverEvent != null) {
            Object hoverText = hoverEvent.getValue(hoverEvent.getAction());
            if (hoverEvent.getAction() == HoverEvent.Action.SHOW_TEXT && hoverText instanceof BaseText) {
                text.setStyle(text.getStyle().withHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, translate((BaseText) hoverText, lang))));
            }
        }

        // translate sibling texts
        List<Text> siblings = text.getSiblings();
        siblings.replaceAll(text1 -> translate((BaseText) text1, lang));
        return text;
    }
}
