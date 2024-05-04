package me.ivan.ivancarpetaddition.mixins.translations;

import net.minecraft.text.TranslatableText;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

//#if MC >= 11600
//$$ import net.minecraft.text.StringVisitable;
//#else
import net.minecraft.text.Text;
//#endif

import java.util.List;

@Mixin(TranslatableText.class)
public interface TranslatableTextAccessor {
    @Accessor
    //#if MC >= 11600
    //$$ List<StringVisitable> getTranslations();
    //#else
    List<Text> getTranslations();
    //#endif

    @Invoker
    void invokeSetTranslation(String translation);
}
