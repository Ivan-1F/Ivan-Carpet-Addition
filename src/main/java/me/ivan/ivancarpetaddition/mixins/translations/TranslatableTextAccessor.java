package me.ivan.ivancarpetaddition.mixins.translations;

import net.minecraft.text.TranslatableText;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

//#if MC >= 11600
//$$ import net.minecraft.text.StringVisitable;
//#else
import net.minecraft.text.Text;
//#endif

//#if MC >= 11800
//$$ import java.util.function.Consumer;
//#else
import java.util.List;
//#endif

@Mixin(TranslatableText.class)
public interface TranslatableTextAccessor {
    @Accessor
    @Mutable
    void setArgs(Object[] args);

    //#if MC < 11800

    @Accessor
    //#if MC >= 11600
    //$$ List<StringVisitable> getTranslations();
    //#else
    List<Text> getTranslations();
    //#endif

    //#endif

    @Invoker
    //#if MC >= 11800
    //$$ void invokeForEachPart(String translation, Consumer<StringVisitable> partsConsumer);
    //#else
    void invokeSetTranslation(String translation);
    //#endif
}
