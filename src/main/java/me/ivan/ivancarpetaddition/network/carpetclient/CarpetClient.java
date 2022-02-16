package me.ivan.ivancarpetaddition.network.carpetclient;

import carpet.CarpetServer;
import carpet.settings.ParsedRule;
import com.google.common.collect.Lists;
import io.netty.buffer.Unpooled;
import me.ivan.ivancarpetaddition.mixins.network.CustomPayloadC2SPacketAccessor;
import me.ivan.ivancarpetaddition.network.IIcaClient;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.nbt.NbtString;
import net.minecraft.network.packet.c2s.play.CustomPayloadC2SPacket;
import net.minecraft.network.packet.s2c.play.CustomPayloadS2CPacket;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.network.PacketByteBuf;

import java.util.List;

import static carpet.utils.Translations.tr;

public class CarpetClient implements IIcaClient {
    public static String MOD_ID = "carpetclient";

    // S2C
    public static Identifier CARPET_RULES = id("carpet_rules");
    public static Identifier VALUE_CHANGED = id("value_changed");
    public static Identifier HI = id("hi");
    // C2S
    public static Identifier HELLO = id("hello");

    private static Identifier id(String path) {
        return new Identifier(MOD_ID, path);
    }

    public static final List<ServerPlayerEntity> playerWithCarpetClient = Lists.newArrayList();

    public static void onRequestingRules(ServerPlayerEntity player) {
        playerWithCarpetClient.add(player);
        sendRulesToPlayer(player);
    }

    public static void sendRulesToPlayer(ServerPlayerEntity player) {
        NbtList rules = new NbtList();
        for (ParsedRule<?> rule : CarpetServer.settingsManager.getRules()) {
            NbtCompound packedRule = new NbtCompound();
            packedRule.putString("name", rule.name);
            packedRule.putString("type", rule.type.getSimpleName());
            packedRule.putString("value", rule.getAsString());
            packedRule.putString("defaultValue", rule.defaultValue.toString());
            packedRule.putString("description", rule.translatedDescription());
            packedRule.putString("translatedName", rule.translatedName().replaceAll(" \\(.*\\)", ""));
            packedRule.putString("translatedDescription", rule.translatedDescription());

            NbtList categories = new NbtList();
            rule.categories.forEach(category -> categories.add(NbtString.of(tr("category." + category))));
            packedRule.put("categories", categories);

            rules.add(packedRule);
        }
        NbtCompound data = new NbtCompound();
        data.put("rules", rules);
        player.networkHandler.sendPacket(new CustomPayloadS2CPacket(CARPET_RULES,
                new PacketByteBuf(Unpooled.buffer()).writeNbt(data)));
    }

    public static void onValueChanged(String rule, String newValue) {
        NbtCompound data = new NbtCompound();
        data.putString("rule", rule);
        data.putString("newValue", newValue);
        playerWithCarpetClient.forEach(player -> player.networkHandler.sendPacket(new CustomPayloadS2CPacket(VALUE_CHANGED,
                new PacketByteBuf(Unpooled.buffer()).writeNbt(data))));

    }

    @Override
    public String getNamespace() {
        return MOD_ID;
    }

    @Override
    public boolean onCustomPayload(ServerPlayerEntity sender, CustomPayloadC2SPacket packet) {
        Identifier channel = ((CustomPayloadC2SPacketAccessor) packet).getChannel();
        if (HELLO.equals(channel)) {
            onRequestingRules(sender);
            return true;
        }
        return false;
    }

    @Override
    public void onPlayerLoggedIn(ServerPlayerEntity player) {
        player.networkHandler.sendPacket(new CustomPayloadS2CPacket(HI, new PacketByteBuf(Unpooled.buffer())));
    }

    @Override
    public void onPlayerLoggedOut(ServerPlayerEntity player) {
        playerWithCarpetClient.remove(player);
    }
}
