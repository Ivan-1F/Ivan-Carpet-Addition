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
    @Shadow
    //#if MC >= 11600
    //$$ public abstract void ignite();
    //#else
    public abstract void setIgnited();
    //#endif

    public CreeperEntityMixin(EntityType<?> type, World world) {
        super(type, world);
    }

    @Override
    //#if MC >= 12005
    //$$ public void setOnFireForTicks(int ticks) {
    //$$     super.setOnFireForTicks(ticks);
    //#else
    public void setOnFireFor(int seconds) {
        super.setOnFireFor(seconds);
    //#endif
        if (IvanCarpetAdditionSettings.creeperIgnitedByFire) {
            //#if MC >= 11600
            //$$ this.ignite();
            //#else
            this.setIgnited();
            //#endif
        }
    }
}
