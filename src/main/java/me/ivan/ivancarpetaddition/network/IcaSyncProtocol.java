package me.ivan.ivancarpetaddition.network;

import io.netty.buffer.Unpooled;
import me.ivan.ivancarpetaddition.IvanCarpetAdditionServer;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.packet.s2c.play.CustomPayloadS2CPacket;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.NotNull;
import net.minecraft.util.PacketByteBuf;

import java.util.LinkedList;
import java.util.List;

public class IcaSyncProtocol {
    public static List<ServerPlayerEntity> validPlayers = new LinkedList<>();

    public static void onPlayerJoin(ServerPlayerEntity player) {
        player.networkHandler.sendPacket(new CustomPayloadS2CPacket(Identifiers.HI, new PacketByteBuf(Unpooled.buffer())));
    }
}
