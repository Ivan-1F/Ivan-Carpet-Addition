package me.ivan.ivancarpetaddition.utils.doc;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import me.ivan.ivancarpetaddition.settings.Rule;
import me.ivan.ivancarpetaddition.translations.ICATranslations;
import me.ivan.ivancarpetaddition.translations.Translator;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class RuleFormatter {
    private final static Translator translator = new Translator("carpet_translations");

    public final Rule annotation;
    public final Field field;
    private final String lang;

    private String tr(String key, Object... args) {
        return ICATranslations.translate(translator.tr(key, args), this.lang).getString();
    }

    public RuleFormatter(Rule annotation, Field field, String lang) {
        this.annotation = annotation;
        this.field = field;
        this.lang = lang;
    }

    public String getId() {
        return this.field.getName();
    }

    public String getName() {
        if (this.getNameSimple().equals(this.getId())) {
            return this.getId();
        } else {
            return String.format("%s (%s)", getNameSimple(), this.getId());
        }
    }

    public String getNameSimple() {
        String translated = tr("rule." + this.getId() + ".name");
        if (translated.equals(this.getId())) {
            return this.getId();
        }
        return String.format("%s", translated);
    }

    public String getLink(String lang) {
        String base = new RuleDocumentGenerator().getFileName(lang);
        if (this.getNameSimple().equals(this.getId())) {
            return base + "#" + this.getId();
        } else {
            return base + String.format("#%s-%s", this.getNameSimple(), this.getId());
        }
    }

    public String getDescription() {
        return tr("rule." + this.getId() + ".desc");
    }

    public String getType() {
        return this.field.getType().getSimpleName();
    }

    public Optional<List<String>> getExtras() {
        List<String> extras = Lists.newArrayList();
        for (int i = 0; ; i++) {
            String key = "rule." + this.getId() + ".extra." + i;
            String extra = tr(key);
            if (extra.contains(key)) {
                break;
            } else {
                extras.add(extra);
            }
        }
        if (!extras.isEmpty()) {
            return Optional.of(extras);
        } else {
            return Optional.empty();
        }
    }

    @Nullable
    public String getDefaultValue() {
        try {
            return this.field.get(null).toString();
        } catch (IllegalAccessException e) {
            return null;
        }
    }

    public List<String> getSuggestedOptions() {
        if (this.field.getType() == boolean.class) {
            return ImmutableList.of("true", "false");
        } else {
            if (this.annotation.options().length == 0) {
                return this.getDefaultValue() == null ? ImmutableList.of() : ImmutableList.of(this.getDefaultValue());
            } else {
                return Arrays.asList(this.annotation.options());
            }
        }
    }

    public List<String> getCategories() {
        return Arrays.stream(this.annotation.categories())
                .map(String::toUpperCase)
                .collect(Collectors.toList());
    }
}
