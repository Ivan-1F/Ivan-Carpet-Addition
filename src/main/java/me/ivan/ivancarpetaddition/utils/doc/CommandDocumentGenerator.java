package me.ivan.ivancarpetaddition.utils.doc;

import com.google.common.collect.ImmutableList;
import me.ivan.ivancarpetaddition.translations.ICATranslations;

import java.nio.file.Path;
import java.util.List;

public class CommandDocumentGenerator extends AbstractDocumentGenerator {
    private final List<String> commands = ImmutableList.of(
            "xpcounter",
            "replaceproperties"
    );
    @Override
    public void generate() {
        DocumentGeneration.getIndexGenerator().startNewSection(String.format("[%s](%s)", this.tr("command.header"), this.getFileName()));
        for (String command : this.commands) {
            DocumentGeneration.getIndexGenerator().accept(String.format("[%s](%s)", command, this.getFileName() + "#" + command));
        }
        DocumentGeneration.getIndexGenerator().writeln.accept("");
    }

    @Override
    public void toFile(Path path) {
        // do not overwrite the file, document for commands will be written manually
    }

    @Override
    public String getHeader() {
        return this.tr("command.header");
    }

    @Override
    public String getFileName(String lang) {
        if (lang.equals(ICATranslations.DEFAULT_LANGUAGE)) {
            return "commands.md";
        }
        return String.format("commands-%s.md", lang);
    }
}
