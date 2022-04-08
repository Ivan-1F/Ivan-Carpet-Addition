package me.ivan.ivancarpetaddition.mixins.rule.unbreakableHelmetInSunlight;

import me.ivan.ivancarpetaddition.IvanCarpetAdditionSettings;
import net.minecraft.entity.mob.ZombieEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.Random;

@Mixin(ZombieEntity.class)
public class ZombieEntityMixin {
    @Redirect(method = "tickMovement", at = @At(value = "INVOKE", target = "Ljava/util/Random;nextInt(I)I"))
    private int stopDamagingHelmet(Random instance, int bound) {
        return IvanCarpetAdditionSettings.unbreakableHelmetInSunlight ? 0 : instance.nextInt(bound);
    }
}
