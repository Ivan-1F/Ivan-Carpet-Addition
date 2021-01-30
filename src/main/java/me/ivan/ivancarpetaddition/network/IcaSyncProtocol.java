package me.ivan.ivancarpetaddition.network;

import io.netty.buffer.Unpooled;
import me.ivan.ivancarpetaddition.IvanCarpetAdditionServer;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.packet.s2c.play.CustomPayloadS2CPacket;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.network.PacketByteBuf;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class IcaSyncProtocol {
    private static final Identifier UPDATE_ENTITY = IvanCarpetAdditionServer.getIdentifier("update_entity");
    // { entity nbt, entityId: (entity id) }
    public static void updateEntity(@NotNull ServerPlayerEntity player, @NotNull Entity entity) {
        CompoundTag tag = entity.toTag(new CompoundTag());
        tag.putInt("EntityId", entity.getId());
        player.networkHandler.sendPacket(new CustomPayloadS2CPacket(
                UPDATE_ENTITY,
                new PacketByteBuf(Unpooled.buffer())
                .writeCompoundTag(tag)
        ));
    }
    // { data: [ { (entity nbt), EntityId: (entity id) }, { (entity nbt), EntityId: (entity id) }, ...] }
    public static void updateEntities(@NotNull ServerPlayerEntity player, @NotNull List<Entity> entities) {
        ListTag nbts = new ListTag();
        entities.forEach(entity -> {
            CompoundTag tag = entity.toTag(new CompoundTag());
            tag.putInt("EntityId", entity.getId());
            nbts.add(tag);
        });
        CompoundTag ret = new CompoundTag();
        ret.put("entities", nbts);
        player.networkHandler.sendPacket(new CustomPayloadS2CPacket(
                UPDATE_ENTITY,
                new PacketByteBuf(Unpooled.buffer())
                .writeCompoundTag(ret)
        ));
    }
}
