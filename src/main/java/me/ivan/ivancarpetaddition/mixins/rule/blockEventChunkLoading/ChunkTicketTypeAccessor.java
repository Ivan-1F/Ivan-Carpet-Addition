package me.ivan.ivancarpetaddition.mixins.rule.blockEventChunkLoading;

import net.minecraft.server.world.ChunkTicketType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(ChunkTicketType.class)
public interface ChunkTicketTypeAccessor {
    @Accessor("expiryTicks")
    public void setExpiryTicks(long expiryTicks);
}
