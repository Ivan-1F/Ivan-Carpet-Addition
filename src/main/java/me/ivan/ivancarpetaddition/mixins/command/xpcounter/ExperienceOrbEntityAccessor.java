package me.ivan.ivancarpetaddition.mixins.command.xpcounter;

import me.fallenbreath.conditionalmixin.api.annotation.Condition;
import me.fallenbreath.conditionalmixin.api.annotation.Restriction;
import me.ivan.ivancarpetaddition.utils.ModIds;
import net.minecraft.entity.ExperienceOrbEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(ExperienceOrbEntity.class)
@Restriction(require = @Condition(value = ModIds.minecraft, versionPredicates = "<1.21.5"))
public interface ExperienceOrbEntityAccessor {
    @Accessor("amount")
    int getAmount();
}
