package me.ivan.ivancarpetaddition.translations;

import carpet.CarpetSettings;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import me.ivan.ivancarpetaddition.IvanCarpetAdditionServer;
import me.ivan.ivancarpetaddition.utils.FileUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.yaml.snakeyaml.Yaml;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ICATranslations {
    public static final String DEFAULT_LANGUAGE = "en_us";
    public static final String TRANSLATION_NAMESPACE = IvanCarpetAdditionServer.compactName;  // "ivancarpetaddition"
    public static final String TRANSLATION_KEY_PREFIX = TRANSLATION_NAMESPACE + ".";  // "ivancarpetaddition."
    private static final String LANG_DIR = String.format("assets/%s/lang", TRANSLATION_NAMESPACE);

    public static final Map<String, Map<String, String>> translations = Maps.newLinkedHashMap();

    private static List<String> getAvailableTranslations() {
        try {
            return FileUtil.listDir(LANG_DIR).stream()
                    .filter(file -> FileUtil.getFileExtension(file).equalsIgnoreCase("yml"))
                    .map(FileUtil::removeFileExtension)
                    .collect(Collectors.toList());
        } catch (IOException e) {
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
    }

    @SuppressWarnings("unchecked")
    public static void build(Map<String, String> translation, Map<String, Object> yaml, String prefix) {
        yaml.forEach((key, value) -> {
            String fullKey = prefix + key;
            if (value instanceof String) {
                translation.put(fullKey, (String) value);
            } else if (value instanceof Map) {
                build(translation, (Map<String, Object>) value, fullKey + ".");
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
    public static String tr(String lang, String key, Object... args) {
        return getTranslation(lang.toLowerCase()).get(key);
    }

    @Nullable
    public static String tr(String key, Object... args) {
        return tr(getServerLanguage(), key, args);
    }
}
