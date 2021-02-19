package me.ivan.ivancarpetaddition.mixins.rule.blockEventChunkLoading;

import me.ivan.ivancarpetaddition.IvanCarpetAdditionSettings;
import me.ivan.ivancarpetaddition.utils.registry.ChunkTicketTypeRegistry;
import net.minecraft.server.world.BlockAction;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(ServerWorld.class)
public class ServerWorldMixin {
    @Inject(method = "method_14174", at = @At("RETURN"), locals = LocalCapture.CAPTURE_FAILHARD)
    private void afterBlockEventExecuted(BlockAction blockAction, CallbackInfoReturnable<Boolean> cir) {
        if (!IvanCarpetAdditionSettings.blockEventChunkLoading) return;
        BlockPos pos = blockAction.getPos();
        ServerWorld world = (ServerWorld)(Object) this;
//        System.out.println("loading chunk: " + new ChunkPos(pos).x + ", " + new ChunkPos(pos).z);
        world.getChunkManager().addTicket(ChunkTicketTypeRegistry.BLOCK_EVENT, new ChunkPos(pos), 3, new ChunkPos(pos));

    }
}
