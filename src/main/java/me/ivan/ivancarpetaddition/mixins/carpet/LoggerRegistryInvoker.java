package me.ivan.ivancarpetaddition.mixins.carpet;

import me.fallenbreath.conditionalmixin.api.annotation.Condition;
import me.fallenbreath.conditionalmixin.api.annotation.Restriction;
import me.ivan.ivancarpetaddition.utils.ModIds;
import me.ivan.ivancarpetaddition.utils.compat.DummyClass;
import org.spongepowered.asm.mixin.Mixin;

/**
 * for extension logging supports in 1.14.4
 */
@Restriction(require = @Condition(value = ModIds.minecraft, versionPredicates = "<1.15"))
@Mixin(DummyClass.class)
public interface LoggerRegistryInvoker {
}
