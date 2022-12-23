package me.ivan.ivancarpetaddition.utils.doc;

import com.google.common.collect.ImmutableList;
import me.ivan.ivancarpetaddition.translations.TranslationConstants;

import java.nio.file.Path;
import java.util.List;

public class LoggerDocumentGenerator extends AbstractDocumentGenerator {
    private final List<String> loggers = ImmutableList.of(
            "xpcounter",
            "shulker"
    );
    @Override
    public void generate() {
        DocumentGeneration.getIndexGenerator().startNewSection(String.format("[%s](%s)", this.tr("logger.header"), this.getFileName()));
        for (String logger : this.loggers) {
            DocumentGeneration.getIndexGenerator().accept(String.format("[%s](%s)", logger, this.getFileName() + "#" + logger));
        }
        DocumentGeneration.getIndexGenerator().writeln.accept("");
    }

    @Override
    public void toFile(Path path) {
        // do not overwrite the file, document for loggers will be written manually
    }

    @Override
    public String getHeader() {
        return this.tr("logger.header");
    }

    @Override
    public String getFileName(String lang) {
        if (lang.equals(TranslationConstants.DEFAULT_LANGUAGE)) {
            return "loggers.md";
        }
        return String.format("loggers-%s.md", lang);
    }
}
