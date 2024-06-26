package me.ivan.ivancarpetaddition.mixins.rule.strictBlockPlacement;

import me.ivan.ivancarpetaddition.helpers.rule.strictBlockPlacement.StrictBlockPlacementHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerInteractionManager;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

//#if MC >= 11600
//$$ import net.minecraft.server.network.ServerPlayerEntity;
//#else
import net.minecraft.entity.player.PlayerEntity;
//#endif

@Mixin(ServerPlayerInteractionManager.class)
public class ServerPlayerInteractionManagerMixin {
    @Inject(method = "interactBlock", at = @At("HEAD"), cancellable = true)
    private void interactBlock(
            //#if MC >= 11600
            //$$ ServerPlayerEntity player,
            //#else
            PlayerEntity player,
            //#endif
            World world,
            ItemStack stack,
            Hand hand,
            BlockHitResult hitResult,
            CallbackInfoReturnable<ActionResult> cir
    ) {
        if (!StrictBlockPlacementHelper.canPlaceBlock(player, world, stack, hand, hitResult)) {
            cir.setReturnValue(ActionResult.PASS);
        }
    }
}
