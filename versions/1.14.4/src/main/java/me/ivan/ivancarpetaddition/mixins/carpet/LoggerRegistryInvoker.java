package me.ivan.ivancarpetaddition.mixins.carpet;

import carpet.logging.Logger;
import carpet.logging.LoggerRegistry;
import me.fallenbreath.conditionalmixin.api.annotation.Condition;
import me.fallenbreath.conditionalmixin.api.annotation.Restriction;
import me.ivan.ivancarpetaddition.utils.ModIds;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

/**
 * for extension logging supports in 1.14.4
 */
@Restriction(require = @Condition(value = ModIds.minecraft, versionPredicates = "<1.15"))
@Mixin(LoggerRegistry.class)
public interface LoggerRegistryInvoker {
    @Invoker(remap = false)
    static void callRegisterLogger(String name, Logger logger) {
    }
}
