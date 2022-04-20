package me.ivan.ivancarpetaddition.utils.doc;

import carpet.settings.Rule;
import com.google.common.base.Joiner;
import me.ivan.ivancarpetaddition.IvanCarpetAdditionSettings;
import me.ivan.ivancarpetaddition.translations.ICATranslations;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Comparator;

public class RuleDocumentGenerator extends AbstractDocumentGenerator {
    public void generate() {
        DocumentGeneration.getIndexGenerator().startNewSection(String.format("[%s](%s)", this.tr("rule"), this.getFileName()));
        Field[] fields = IvanCarpetAdditionSettings.class.getDeclaredFields();
        fields = Arrays.stream(fields).sorted(Comparator.comparing(Field::getName)).toArray(Field[]::new);
        for (Field field : fields) {
            Rule annotation = field.getAnnotation(Rule.class);
            if (annotation == null) {
                continue;
            }

            RuleFormatter rule = new RuleFormatter(annotation, field, this.getLanguage());
            this.writeln.accept("### " + rule.getName());
            this.writeln.accept("");
            this.writeln.accept(rule.getDescription());
            this.writeln.accept("");
            rule.getExtras().ifPresent(extras -> this.writeln.accept(Joiner.on("\n\n").join(extras) + "\n"));
            this.writeln.accept(String.format(" - %s: %s", this.tr("type"), inlineCode(rule.getType())));
            this.writeln.accept(String.format(" - %s: %s", this.tr("default_value"), inlineCode(rule.getDefaultValue())));
            this.writeln.accept(String.format(" - %s: %s", this.tr("suggested_options"), Joiner.on(", ").join(inlineCode(rule.getSuggestedOptions()))));
            this.writeln.accept(String.format(" - %s: %s", this.tr("categories"), Joiner.on(", ").join(inlineCode(rule.getCategories()))));
            this.writeln.accept("");
            DocumentGeneration.getIndexGenerator().accept(String.format("[%s](%s)", rule.getNameSimple(), rule.getLink(this.getLanguage())));
        }
    }

    @Override
    public String getHeader() {
        return this.tr("rule");
    }

    @Override
    public String getFileName(String lang) {
        if (lang.equals(ICATranslations.DEFAULT_LANGUAGE)) {
            return "rules.md";
        }
        return String.format("rules-%s.md", lang);
    }
}
