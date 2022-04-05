package me.ivan.ivancarpetaddition.mixins.translations;

import carpet.utils.Messenger;
import net.minecraft.text.BaseText;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(Messenger.class)
public interface MessengerInvoker {
    @Invoker(value = "_applyStyleToTextComponent", remap = false)
    static BaseText invokeApplyStyleToTextComponent(BaseText comp, String style) {
        return null;
    }
}

