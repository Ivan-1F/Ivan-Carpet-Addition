package me.ivan.ivancarpetaddition.mixins.setting;

import carpet.api.settings.CarpetRule;
import carpet.api.settings.SettingsManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.Map;

@Mixin(SettingsManager.class)
public interface SettingsManagerAccessor {
    @Accessor(value = "rules", remap = false)
    Map<String, CarpetRule<?>> getRules$ICA();
}
