package me.ivan.ivancarpetaddition.mixins.rule.creeperIgnitedByFire;

import me.ivan.ivancarpetaddition.IvanCarpetAdditionSettings;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.CreeperEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(CreeperEntity.class)
public abstract class CreeperEntityMixin extends Entity {
    @Shadow public abstract void setIgnited();

    public CreeperEntityMixin(EntityType<?> type, World world) {
        super(type, world);
    }

    @Inject(method = "tick", at = @At("HEAD"))
    private void tick(CallbackInfo ci) {
        if (this.isOnFire() && IvanCarpetAdditionSettings.creeperIgnitedByFire) {
            this.setIgnited();
        }
    }
}
