package me.ivan.ivancarpetaddition.utils.registry;

import net.minecraft.server.world.ChunkTicketType;
import net.minecraft.util.math.ChunkPos;

import java.util.Comparator;

public class ChunkTicketTypeRegistry {
    //#if MC >= 12105
    //$$ public static ChunkTicketType BLOCK_EVENT = ChunkTicketType.register("block_event", 8, false, ChunkTicketType.Use.LOADING_AND_SIMULATION);
    //#else
    public static ChunkTicketType<ChunkPos> BLOCK_EVENT = ChunkTicketType.create("block_event", Comparator.comparingLong(ChunkPos::toLong), 8);
    //#endif
}
