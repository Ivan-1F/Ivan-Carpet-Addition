package me.ivan.ivancarpetaddition.mixins.rule.icaSyncProtocol;

import io.netty.buffer.Unpooled;
import me.ivan.ivancarpetaddition.IvanCarpetAdditionServer;
import me.ivan.ivancarpetaddition.IvanCarpetAdditionSettings;
import me.ivan.ivancarpetaddition.network.IcaSyncProtocol;
import me.ivan.ivancarpetaddition.network.Identifiers;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.packet.c2s.play.CustomPayloadC2SPacket;
import net.minecraft.network.packet.s2c.play.CustomPayloadS2CPacket;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.network.PacketByteBuf;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.concurrent.atomic.AtomicReference;

@Mixin(ServerPlayNetworkHandler.class)
public class ServerPlayNetworkHandlerMixin {
    @Shadow @Final private MinecraftServer server;

    @Shadow public ServerPlayerEntity player;

    @Inject(method = "onCustomPayload", at = @At("HEAD"), cancellable = true)
    private void onCustomPayload(CustomPayloadC2SPacket packet, CallbackInfo ci) {
        if (((CustomPayloadC2SPacketAccessor)packet).getChannel().equals(Identifiers.HELLO)) {
            IcaSyncProtocol.validPlayers.add(player);
            IvanCarpetAdditionServer.LOGGER.info("Player");
            if (IvanCarpetAdditionSettings.icaSyncProtocol) {
                player.networkHandler.sendPacket(new CustomPayloadS2CPacket(Identifiers.ENABLE_ICA_SYNC_PROTOCOL, new PacketByteBuf(Unpooled.buffer())));
            } else {
                player.networkHandler.sendPacket(new CustomPayloadS2CPacket(Identifiers.DISABLE_ICA_SYNC_PROTOCOL, new PacketByteBuf(Unpooled.buffer())));
            }
            ci.cancel();
        }

        if (((CustomPayloadC2SPacketAccessor)packet).getChannel().toString().equals(Identifiers.REQUIRE_ALL_ENTITIES.toString())) {
            AtomicReference<String> ret = new AtomicReference<>("");
            server.getWorlds().forEach(world -> {
                world.getEntitiesByType(null, entity -> true).forEach(entity -> {
                    // Each entity
                    NbtCompound tag = new NbtCompound();
                    entity.writeNbt(tag);
                    ret.set(ret.get() + tag);
                });
            });
            player.networkHandler.sendPacket(new CustomPayloadS2CPacket(Identifiers.REQUIRE_ALL_ENTITIES, new PacketByteBuf(Unpooled.buffer()).writeString(ret.get())));

            ci.cancel();
        }

        if (((CustomPayloadC2SPacketAccessor)packet).getChannel().toString().equals(Identifiers.REQUIRE_ALL_BLOCK_ENTITIES.toString())) {
            server.getWorlds().forEach(world -> {
                // TO-DO
            });
            ci.cancel();
        }
    }
}
