package me.ivan.ivancarpetaddition.settings;

import carpet.settings.ParsedRule;
import carpet.settings.SettingsManager;
import carpet.settings.Validator;
import com.google.common.collect.Lists;
import me.ivan.ivancarpetaddition.IvanCarpetAdditionServer;
import me.ivan.ivancarpetaddition.mixins.setting.ParsedRuleAccessor;
import me.ivan.ivancarpetaddition.mixins.setting.SettingsManagerAccessor;
import me.ivan.ivancarpetaddition.translations.ICATranslations;
import me.ivan.ivancarpetaddition.translations.TranslationConstants;
import org.jetbrains.annotations.Nullable;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.List;

public class CarpetRuleRegistrar {
    private final SettingsManager settingsManager;
    private final List<ParsedRule<?>> rules = Lists.newArrayList();

    private CarpetRuleRegistrar(SettingsManager settingsManager) {
        this.settingsManager = settingsManager;
    }

    public static void register(SettingsManager settingsManager, Class<?> settingsClass) {
        CarpetRuleRegistrar registrar = new CarpetRuleRegistrar(settingsManager);
        registrar.parseSettingsClass(settingsClass);
        registrar.registerToCarpet();
    }

    public void parseSettingsClass(Class<?> settingsClass) {
        for (Field field : settingsClass.getDeclaredFields()) {
            Rule rule = field.getAnnotation(Rule.class);
            if (rule != null) {
                this.parseRule(field, rule);
            }
        }
    }

    @SuppressWarnings("rawtypes")
    private void parseRule(Field field, Rule rule) {
        carpet.settings.Rule cmRule = new carpet.settings.Rule() {
            private final String basedKey = TranslationConstants.CARPET_TRANSLATIONS_KEY_PREFIX + "rule." + this.name() + ".";

            @Nullable
            private String tr(String key) {
                return ICATranslations.translateKeyToFormattedString(TranslationConstants.DEFAULT_LANGUAGE, this.basedKey + key);
            }

            @Override
            public String desc() {
                String desc = this.tr("desc");
                if (desc == null) {
                    throw new NullPointerException(String.format("Rule %s has no translated desc", this.name()));
                }
                return desc;
            }

            @Override
            public String[] extra() {
                List<String> extraMessages = Lists.newArrayList();
                for (int i = 0; ; i++) {
                    String message = this.tr("extra." + i);
                    if (message == null) {
                        break;
                    }
                    extraMessages.add(message);
                }
                return extraMessages.toArray(new String[0]);
            }

            @Override
            public String name() {
                return field.getName();
            }

            @Override
            public String[] category() {
                return rule.categories();
            }

            @Override
            public String[] options() {
                return rule.options();
            }

            @Override
            public boolean strict() {
                return rule.strict();
            }

            @Override
            public Class<? extends Validator>[] validate() {
                return rule.validators();
            }

            @Override
            public Class<? extends Annotation> annotationType() {
                return rule.annotationType();
            }
        };

        ParsedRule<?> parsedRule = ParsedRuleAccessor.invokeConstructor(
                field, cmRule
        );
        this.rules.add(parsedRule);
    }

    public void registerToCarpet() {
        for (ParsedRule<?> rule : this.rules) {
            Object existingRule = ((SettingsManagerAccessor) this.settingsManager).getRules$ICA().put(rule.name, rule);
            if (existingRule != null) {
                IvanCarpetAdditionServer.LOGGER.warn("Overwriting existing rule {}", existingRule);
            }
        }
    }
}
