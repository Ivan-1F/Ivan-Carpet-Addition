package me.ivan.ivancarpetaddition.mixins.translations;

import carpet.utils.Messenger;
import me.fallenbreath.conditionalmixin.api.annotation.Condition;
import me.fallenbreath.conditionalmixin.api.annotation.Restriction;
import me.ivan.ivancarpetaddition.utils.ModIds;
import net.minecraft.text.BaseText;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Restriction(require = @Condition(value = ModIds.minecraft, versionPredicates = "<1.15"))
@Mixin(Messenger.class)
public interface MessengerInvoker {
    @Invoker(value = "_applyStyleToTextComponent", remap = false)
    static BaseText invokeApplyStyleToTextComponent(BaseText comp, String style) {
        return null;
    }
}
