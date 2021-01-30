package me.ivan.ivancarpetaddition.mixins.rule.flippinCactusSound;

import carpet.helpers.BlockRotator;
import me.ivan.ivancarpetaddition.IvanCarpetAdditionServer;
import me.ivan.ivancarpetaddition.IvanCarpetAdditionSettings;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BlockRotator.class)
public class BlockRotatorMixin {
    @Inject(
            method = "flipBlockWithCactus",
            at = @At(
                    value = "INVOKE",
                    target = "Lcarpet/helpers/BlockRotator;flip_block(Lnet/minecraft/block/BlockState;Lnet/minecraft/world/World;Lnet/minecraft/entity/player/PlayerEntity;Lnet/minecraft/util/Hand;Lnet/minecraft/util/hit/BlockHitResult;)Z"),
            remap = false
    )
    private static void flipBlockWithCactus(BlockState state, World world, PlayerEntity player, Hand hand, BlockHitResult hit, CallbackInfoReturnable<Boolean> cir) {
        if (IvanCarpetAdditionSettings.flippinCactusSound) {
            player.playSound(SoundEvents.BLOCK_DISPENSER_LAUNCH, SoundCategory.AMBIENT, 1.0F, 1.0F);
        }
    }
}
