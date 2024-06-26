package me.ivan.ivancarpetaddition.mixins.rule.endLightningRod;

import me.fallenbreath.conditionalmixin.api.annotation.Condition;
import me.fallenbreath.conditionalmixin.api.annotation.Restriction;
import me.ivan.ivancarpetaddition.utils.ModIds;
import net.minecraft.entity.projectile.TridentEntity;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Restriction(require = @Condition(value = ModIds.minecraft, versionPredicates = "<1.20.4"))
@Mixin(TridentEntity.class)
public interface TridentEntityAccessor {
    @Accessor
    public ItemStack getTridentStack();
}
