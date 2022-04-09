package me.ivan.ivancarpetaddition.utils.doc;

import carpet.settings.Rule;
import com.google.common.base.Joiner;
import com.google.common.collect.Sets;
import me.ivan.ivancarpetaddition.IvanCarpetAdditionServer;
import me.ivan.ivancarpetaddition.IvanCarpetAdditionSettings;
import me.ivan.ivancarpetaddition.translations.ICATranslations;
import me.ivan.ivancarpetaddition.translations.Translator;
import net.fabricmc.loader.api.FabricLoader;

import java.io.BufferedWriter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class DocumentGenerator {
    private static final Path DOC_DIRECTORY = Paths.get("docs");
    private final static Translator translator = new Translator("doc");
    private static String lang;

    public static String inlineCode(String text) {
        return "`" + text + "`";
    }

    public static List<String> inlineCode(List<String> text) {
        return text.stream().map(DocumentGenerator::inlineCode).collect(Collectors.toList());
    }

    public static String tr(String key, Object... args) {
        return ICATranslations.translate(translator.tr(key, args), lang).getString();
    }

    public static void generateDocuments() {
        ICATranslations.loadTranslations();
        for (String language : ICATranslations.languages) {
            generateDocument(language);
        }
    }

    public static void generateDocument(String language) {
        // ICATranslations#loadTranslations should be called before calling this method
        lang = language;
        IvanCarpetAdditionServer.LOGGER.info("Generating document with language {}", lang);

        StringBuffer buffer = new StringBuffer();
        Consumer<String> writeln = line -> buffer.append(line).append("\n");

        Field[] fields = IvanCarpetAdditionSettings.class.getDeclaredFields();
        for (Field field : fields) {
            Rule annotation = field.getAnnotation(Rule.class);
            if (annotation == null) {
                continue;
            }
            RuleFormatter rule = new RuleFormatter(annotation, field, lang);
            writeln.accept("### " + rule.getName());
            writeln.accept("");
            writeln.accept(rule.getDescription());
            writeln.accept("");
            rule.getExtras().ifPresent(extras -> writeln.accept(Joiner.on("\n\n").join(extras) + "\n"));
            writeln.accept(String.format(" - %s: %s", tr("type"), rule.getType()));
            writeln.accept(String.format(" - %s: %s", tr("default_value"), inlineCode(rule.getDefaultValue())));
            writeln.accept(String.format(" - %s: %s", tr("suggested_options"), Joiner.on(", ").join(inlineCode(rule.getSuggestedOptions()))));
            writeln.accept(String.format(" - %s: %s", tr("categories"), Joiner.on(", ").join(inlineCode(rule.getCategories()))));
            writeln.accept("");
        }
        Path filePath = DOC_DIRECTORY.resolve(String.format("rules-%s.md", lang));
        IvanCarpetAdditionServer.LOGGER.info("Doc file path: {}", filePath);
        try {
            if (!Files.exists(filePath.getParent())) {
                Files.createDirectories(filePath.getParent());
            }
            try (BufferedWriter writer = Files.newBufferedWriter(filePath, StandardCharsets.UTF_8)) {
                writer.write(buffer.toString());
            }
        } catch (IOException e) {
            IvanCarpetAdditionServer.LOGGER.error(e);
        }
    }
}
