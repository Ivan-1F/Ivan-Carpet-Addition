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

    @Override
    public void prepare() {
        super.prepare();
        this.writeHeader();
    }

    @Override
    public void generate() {

    }

    @Override
    public String getFileName(String lang) {
        if (lang.equals(ICATranslations.DEFAULT_LANGUAGE)) {
            return "readme.md";
        }
        return String.format("readme-%s.md", lang);
    }
}
