package me.ivan.ivancarpetaddition.network;

import net.minecraft.network.packet.c2s.play.CustomPayloadC2SPacket;
import net.minecraft.server.network.ServerPlayerEntity;

public interface IIcaClient {
    String getNamespace();
    boolean onCustomPayload(ServerPlayerEntity sender, CustomPayloadC2SPacket packet);
    default void onPlayerLoggedIn(ServerPlayerEntity player) {

    }

    default void onPlayerLoggedOut(ServerPlayerEntity player) {

    }
}
