package me.ivan.ivancarpetaddition.mixins.rule.villageChunkLoading;

import me.ivan.ivancarpetaddition.IvanCarpetAdditionServer;
import me.ivan.ivancarpetaddition.IvanCarpetAdditionSettings;
import me.ivan.ivancarpetaddition.utils.registry.ChunkTicketTypeRegistry;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(VillagerEntity.class)
public class VillagerEntityMixin {
    @Inject(method = "tick", at = @At("HEAD"))
    private void tick(CallbackInfo ci) {
        if (IvanCarpetAdditionSettings.villageChunkLoading) {
            VillagerEntity villager = (VillagerEntity) (Object) this;
            if (villager.getBrain().getOptionalMemory(MemoryModuleType.HOME).isPresent()) {
                ServerWorld world = villager.getServer().getWorld(villager.dimension);
                BlockPos pos = new BlockPos(villager.getPos());
//                System.out.println("loading chunk: " + new ChunkPos(pos).x + ", " + new ChunkPos(pos).z);
                world.getChunkManager().addTicket(ChunkTicketTypeRegistry.VILLAGE, new ChunkPos(pos), 3, new ChunkPos(pos));
            }
        }
    }
}
