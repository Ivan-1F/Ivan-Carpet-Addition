package me.ivan.ivancarpetaddition.mixins.rule.cobwebSlowDown;

import me.ivan.ivancarpetaddition.IvanCarpetAdditionSettings;
import net.minecraft.block.CobwebBlock;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

@Mixin(CobwebBlock.class)
public class CobwebBlockMixin {
    @ModifyArgs(method = "onEntityCollision", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/Entity;slowMovement(Lnet/minecraft/block/BlockState;Lnet/minecraft/util/math/Vec3d;)V"))
    public void modifySlowdownSpeed(Args args) {
        args.set(1, new Vec3d(0.25D, IvanCarpetAdditionSettings.cobwebSlowdownSpeed, 0.25D));
    }
}
