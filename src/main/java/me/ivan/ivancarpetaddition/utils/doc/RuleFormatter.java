package me.ivan.ivancarpetaddition.utils.doc;

import carpet.api.settings.Rule;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import me.ivan.ivancarpetaddition.translations.ICATranslations;
import me.ivan.ivancarpetaddition.translations.TranslationConstants;
import me.ivan.ivancarpetaddition.translations.Translator;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class RuleFormatter {
    private final static Translator translator = new Translator("carpet_extension");

    public final Rule annotation;
    public final Field field;
    private final String lang;

    private Optional<String> tr(String key, Object... args) {
        String translated = ICATranslations.translate(translator.tr(key, args), this.lang, true).getString();
        if (lang.equals(TranslationConstants.DEFAULT_LANGUAGE)) {
            return Optional.empty();
        }
        return Optional.of(translated);
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
        String translated = tr("rule." + this.getId() + ".name").orElse(this.getId());
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
        return tr("rule." + this.getId() + ".desc").orElse(this.annotation.desc());
    }

    public String getType() {
        return this.field.getType().getSimpleName();
    }

    public Optional<List<String>> getExtras() {
        int length = this.annotation.extra().length;
        List<String> extras = Lists.newArrayList();
        for (int i = 0; i < length; i++) {
            extras.add(tr("rule." + this.getId() + ".extra." + i).orElse(this.annotation.extra()[i]));
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
        return Arrays.stream(this.annotation.category())
                .map(String::toUpperCase)
                .collect(Collectors.toList());
    }
}
