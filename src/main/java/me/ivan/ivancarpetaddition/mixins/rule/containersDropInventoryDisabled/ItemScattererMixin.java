package me.ivan.ivancarpetaddition.mixins.rule.containersDropInventoryDisabled;

import me.ivan.ivancarpetaddition.IvanCarpetAdditionSettings;
import net.minecraft.inventory.Inventory;
import net.minecraft.util.ItemScatterer;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemScatterer.class)
public class ItemScattererMixin {
    @Inject(method = "spawn(Lnet/minecraft/world/World;DDDLnet/minecraft/inventory/Inventory;)V", at = @At("HEAD"), cancellable = true)
    private static void stopDroppingInventory(World world, double x, double y, double z, Inventory inventory, CallbackInfo ci) {
        if (IvanCarpetAdditionSettings.containerDropInventoryDisabled) ci.cancel();
    }
}
