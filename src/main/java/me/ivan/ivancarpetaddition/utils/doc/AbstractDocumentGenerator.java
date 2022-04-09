package me.ivan.ivancarpetaddition.utils.doc;

import me.ivan.ivancarpetaddition.IvanCarpetAdditionServer;
import me.ivan.ivancarpetaddition.translations.ICATranslations;
import me.ivan.ivancarpetaddition.translations.Translator;
import me.ivan.ivancarpetaddition.utils.FileUtil;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public abstract class AbstractDocumentGenerator {
    private final static Translator translator = new Translator("doc");
    protected final StringBuffer buffer = new StringBuffer();
    protected final Consumer<String> writeln = line -> this.buffer.append(line).append("\n");
    protected String language = ICATranslations.DEFAULT_LANGUAGE;

    public static String inlineCode(String text) {
        return "`" + text + "`";
    }

    public static List<String> inlineCode(List<String> text) {
        return text.stream().map(AbstractDocumentGenerator::inlineCode).collect(Collectors.toList());
    }

    public String tr(String key, Object... args) {
        return ICATranslations.translate(translator.tr(key, args), this.getLanguage()).getString();
    }

    public void setLanguage(String language) {
        this.language = language;
        this.buffer.setLength(0);
    }

    public String getLanguage() {
        return language;
    }

    abstract public void generate();
    abstract public String getFileName();

    public void toFile(Path path) {
        try {
            FileUtil.writeToFile(path, this.buffer.toString());
        } catch (IOException e) {
            IvanCarpetAdditionServer.LOGGER.error(e);
        }
    }
}
