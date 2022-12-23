package me.ivan.ivancarpetaddition.mixins.setting;

import carpet.api.settings.CarpetRule;
import carpet.api.settings.Rule;
import me.fallenbreath.conditionalmixin.api.annotation.Condition;
import me.fallenbreath.conditionalmixin.api.annotation.Restriction;
import me.ivan.ivancarpetaddition.utils.ModIds;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

import java.lang.reflect.Field;

@Restriction(require = @Condition(value = ModIds.minecraft, versionPredicates = "<=1.19"))
@Mixin(CarpetRule.class)
public interface ParsedRuleAccessor {
    @SuppressWarnings("rawtypes")
    @Invoker(value = "<init>", remap = false)
    static CarpetRule invokeConstructor(Field field, Rule rule, carpet.api.settings.SettingsManager settingsManager) {
        throw new RuntimeException();
    }
}
