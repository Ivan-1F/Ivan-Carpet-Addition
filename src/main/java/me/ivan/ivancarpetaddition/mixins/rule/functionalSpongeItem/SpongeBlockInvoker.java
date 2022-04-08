package me.ivan.ivancarpetaddition.mixins.rule.functionalSpongeItem;

import net.minecraft.block.SpongeBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(SpongeBlock.class)
public interface SpongeBlockInvoker {
    @Invoker("absorbWater")
    boolean invokeAbsorbWater(World world, BlockPos pos);
}
