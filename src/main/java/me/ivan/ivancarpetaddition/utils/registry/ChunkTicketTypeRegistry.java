package me.ivan.ivancarpetaddition.utils.registry;

import net.minecraft.server.world.ChunkTicketType;
import net.minecraft.util.math.ChunkPos;

import java.util.Comparator;

public class ChunkTicketTypeRegistry {
    public static ChunkTicketType<ChunkPos> BLOCK_EVENT = ChunkTicketType.create("block_event", Comparator.comparingLong(ChunkPos::toLong), 8);
    public static ChunkTicketType<ChunkPos> VILLAGE = ChunkTicketType.create("village", Comparator.comparingLong(ChunkPos::toLong), 1);
}
