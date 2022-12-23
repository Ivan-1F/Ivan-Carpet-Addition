package me.ivan.ivancarpetaddition.mixins.setting;

import carpet.settings.ParsedRule;
import carpet.settings.Rule;
import me.fallenbreath.conditionalmixin.api.annotation.Condition;
import me.fallenbreath.conditionalmixin.api.annotation.Restriction;
import me.ivan.ivancarpetaddition.utils.ModIds;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

import java.lang.reflect.Field;

@Restriction(require = @Condition(value = ModIds.minecraft, versionPredicates = "<=1.19"))
@Mixin(ParsedRule.class)
public interface ParsedRuleAccessor {
    @SuppressWarnings("rawtypes")
    @Invoker(value = "<init>", remap = false)
    static ParsedRule invokeConstructor(Field field, Rule rule, carpet.settings.SettingsManager settingsManager) {
        throw new RuntimeException();
    }
}
