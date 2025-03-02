package me.ivan.ivancarpetaddition.mixins.rule.dispensersNotAffectPlayers;

import me.fallenbreath.conditionalmixin.api.annotation.Condition;
import me.fallenbreath.conditionalmixin.api.annotation.Restriction;
import me.ivan.ivancarpetaddition.utils.ModIds;
import me.ivan.ivancarpetaddition.utils.compat.DummyClass;
import org.spongepowered.asm.mixin.Mixin;

@Restriction(require = @Condition(value = ModIds.minecraft, versionPredicates = "<1.21.3"))
@Mixin(DummyClass.class)
public class ArmorItemMixin {
}
