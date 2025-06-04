package me.ivan.ivancarpetaddition.mixins.command.xpcounter;

import me.fallenbreath.conditionalmixin.api.annotation.Condition;
import me.fallenbreath.conditionalmixin.api.annotation.Restriction;
import me.ivan.ivancarpetaddition.utils.ModIds;
import me.ivan.ivancarpetaddition.utils.compat.DummyClass;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(DummyClass.class)
@Restriction(require = @Condition(value = ModIds.minecraft, versionPredicates = "<1.21.5"))
public interface ExperienceOrbEntityAccessor {
}
