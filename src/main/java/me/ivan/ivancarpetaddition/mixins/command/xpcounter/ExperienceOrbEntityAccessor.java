package me.ivan.ivancarpetaddition.mixins.command.xpcounter;

import net.minecraft.entity.ExperienceOrbEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(ExperienceOrbEntity.class)
public interface ExperienceOrbEntityAccessor {
    @Accessor("amount")
    int getAmount();
}
