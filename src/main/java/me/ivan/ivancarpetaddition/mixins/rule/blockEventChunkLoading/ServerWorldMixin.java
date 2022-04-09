package me.ivan.ivancarpetaddition.mixins.rule.blockEventChunkLoading;

import me.ivan.ivancarpetaddition.IvanCarpetAdditionSettings;
import me.ivan.ivancarpetaddition.utils.registry.ChunkTicketTypeRegistry;
import net.minecraft.server.world.BlockEvent;
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
    @Inject(method = "processBlockEvent", at = @At("RETURN"), locals = LocalCapture.CAPTURE_FAILHARD)
    private void afterBlockEventExecuted(BlockEvent blockAction, CallbackInfoReturnable<Boolean> cir) {
        if (!IvanCarpetAdditionSettings.blockEventChunkLoading) return;
        BlockPos pos = blockAction.pos();
        ServerWorld world = (ServerWorld)(Object) this;
        world.getChunkManager().addTicket(ChunkTicketTypeRegistry.BLOCK_EVENT, new ChunkPos(pos), 3, new ChunkPos(pos));
    }
}
