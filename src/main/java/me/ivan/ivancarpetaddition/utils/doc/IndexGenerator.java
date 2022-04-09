package me.ivan.ivancarpetaddition.utils.doc;

import me.ivan.ivancarpetaddition.translations.ICATranslations;

public class IndexGenerator extends AbstractDocumentGenerator {
    public void accept(String index) {
        this.writeln.accept(" - " + index);
    }

    public void startNewSection(String title) {
        this.writeln.accept("## " + title);
        this.writeln.accept("");
    }

    private void writeHeader() {
        this.writeln.accept("# " + this.tr("index"));
        this.writeln.accept("");
    }

    public IndexGenerator() {
        this.writeHeader();
    }

    @Override
    public void setLanguage(String language) {
        super.setLanguage(language);
        this.writeHeader();
    }

    @Override
    public void generate() {

    }

    @Override
    public String getFileName() {
        if (this.getLanguage().equals(ICATranslations.DEFAULT_LANGUAGE)) {
            return "readme.md";
        }
        return String.format("readme-%s.md", this.getLanguage());
    }
}
