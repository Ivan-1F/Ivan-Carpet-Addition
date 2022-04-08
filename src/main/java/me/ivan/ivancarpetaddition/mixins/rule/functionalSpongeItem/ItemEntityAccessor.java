package me.ivan.ivancarpetaddition.mixins.rule.functionalSpongeItem;

import net.minecraft.entity.ItemEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(ItemEntity.class)
public interface ItemEntityAccessor {
    // ItemEntity#getAge only works on client side
    @Accessor
    int getAge();
}
