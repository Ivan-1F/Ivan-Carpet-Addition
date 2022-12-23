package me.ivan.ivancarpetaddition.settings;

import carpet.api.settings.CarpetRule;
import carpet.api.settings.SettingsManager;
import com.google.common.collect.Lists;
import me.ivan.ivancarpetaddition.IvanCarpetAdditionServer;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.List;

public class CarpetRuleRegistrar {
    private final SettingsManager settingsManager;
    private final List<CarpetRule<?>> rules = Lists.newArrayList();

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

    private void parseRule(Field field, Rule rule) {
        try {
            Class<?> ruleAnnotationClass = Class.forName("carpet.settings.ParsedRule$RuleAnnotation");
            Constructor<?> ctr1 = ruleAnnotationClass.getDeclaredConstructors()[0];
            ctr1.setAccessible(true);
            Object ruleAnnotation = ctr1.newInstance(false, null, null, null, rule.categories(), rule.options(), rule.strict(), "", rule.validators());

            Class<?> parsedRuleClass = Class.forName("carpet.settings.ParsedRule");
            Constructor<?> ctr2 = parsedRuleClass.getDeclaredConstructors()[0];
            ctr2.setAccessible(true);
            Object carpetRule = ctr2.newInstance(field, ruleAnnotation, this.settingsManager);

            this.rules.add((CarpetRule<?>) carpetRule);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void registerToCarpet() {
        for (CarpetRule<?> rule : this.rules) {
            try {
                this.settingsManager.addCarpetRule(rule);
            } catch (UnsupportedOperationException e) {
                IvanCarpetAdditionServer.LOGGER.warn("Failed to register rule {} to fabric carpet: {}", rule.name(), e);
            }
        }
    }
}
