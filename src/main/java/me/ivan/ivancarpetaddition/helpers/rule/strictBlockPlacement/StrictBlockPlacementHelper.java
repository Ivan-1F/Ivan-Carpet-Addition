package me.ivan.ivancarpetaddition.helpers.rule.strictBlockPlacement;

import me.ivan.ivancarpetaddition.IvanCarpetAdditionSettings;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.world.World;

public class StrictBlockPlacementHelper {
    public static boolean canPlaceBlock(PlayerEntity player, World world, ItemStack stack, Hand hand, BlockHitResult hitResult) {
        if (!IvanCarpetAdditionSettings.strictBlockPlacement) {
            return true;
        }
        return checkHitResult(world, hitResult);
    }

    private static boolean checkHitResult(World world, BlockHitResult hitResult) {
        return !world.getBlockState(hitResult.getBlockPos()).isAir();
    }
}
