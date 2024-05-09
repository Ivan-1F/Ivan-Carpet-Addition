package me.ivan.ivancarpetaddition.translations;

import com.google.gson.Gson;
import me.ivan.ivancarpetaddition.IvanCarpetAdditionServer;
import me.ivan.ivancarpetaddition.utils.FileUtil;
import net.fabricmc.loader.api.FabricLoader;

import java.util.*;

public class TranslationLoader {
    private static final String LANG_DIR = String.format("assets/%s/lang", TranslationConstants.TRANSLATION_NAMESPACE);

    public static void loadTranslations(Map<String, Map<String, String>> translationStorage) {
        List<String> languageList;

        try {
            String fileContent = FileUtil.readFile(LANG_DIR + "/meta/languages.json");
            languageList = new Gson().fromJson(fileContent, LanguageList.class);
        }
        catch (Exception e) {
            IvanCarpetAdditionServer.LOGGER.error("Failed to read language list", e);
            return;
        }

        languageList.forEach(lang -> translationStorage.computeIfAbsent(lang, TranslationLoader::loadTranslation));
    }

    private static Map<String, String> loadTranslation(String lang) {
        try {
            String fileContent = FileUtil.readFile(String.format("%s/%s.json", LANG_DIR, lang));
            return new Gson().fromJson(fileContent, TranslationMapping.class);
        }
        catch (Exception e) {
            String message = "Failed to load translation of language " + lang;
            IvanCarpetAdditionServer.LOGGER.error(message, e);
            if (FabricLoader.getInstance().isDevelopmentEnvironment()) {
                throw new RuntimeException(message, e);
            }
            return Collections.emptyMap();
        }
    }

    private static class LanguageList extends ArrayList<String> {}
    private static class TranslationMapping extends LinkedHashMap<String, String> {}
}
