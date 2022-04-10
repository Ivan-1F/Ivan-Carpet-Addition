package me.ivan.ivancarpetaddition.utils.doc;

import com.google.common.collect.ImmutableList;
import me.ivan.ivancarpetaddition.IvanCarpetAdditionServer;
import me.ivan.ivancarpetaddition.translations.ICATranslations;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class DocumentGeneration {
    private static final Path DOC_DIRECTORY = Paths.get("docs");
    public static final List<AbstractDocumentGenerator> GENERATORS = ImmutableList.of(
            new RuleDocumentGenerator()
    );
    private static final IndexGenerator indexGenerator = new IndexGenerator();

    public static IndexGenerator getIndexGenerator() {
        return indexGenerator;
    }

    public static void generateDocuments() {
        for (String language : ICATranslations.languages) {
            IvanCarpetAdditionServer.LOGGER.info("Generating ICA documents of language " + language);
            indexGenerator.setLanguage(language);
            for (AbstractDocumentGenerator generator : GENERATORS) {
                generator.setLanguage(language);
                generator.generate();
                generator.toFile(DOC_DIRECTORY.resolve(generator.getFileName()));
                IvanCarpetAdditionServer.LOGGER.info("Generated: " + DOC_DIRECTORY.resolve(generator.getFileName()));
            }
            IvanCarpetAdditionServer.LOGGER.info("The index of language " + language + " has been generated at " + DOC_DIRECTORY.resolve(indexGenerator.getFileName()));
            indexGenerator.toFile(DOC_DIRECTORY.resolve(indexGenerator.getFileName()));
        }
    }
}
