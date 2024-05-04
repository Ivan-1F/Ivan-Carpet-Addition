package me.ivan.ivancarpetaddition.network.carpetclient;

import carpet.CarpetServer;
import carpet.settings.ParsedRule;
//#if MC >= 11500
import carpet.utils.Translations;
//#endif
import com.google.common.collect.Lists;
import io.netty.buffer.Unpooled;
import me.ivan.ivancarpetaddition.mixins.network.CustomPayloadC2SPacketAccessor;
import me.ivan.ivancarpetaddition.network.IICAClient;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.network.packet.c2s.play.CustomPayloadC2SPacket;
import net.minecraft.network.packet.s2c.play.CustomPayloadS2CPacket;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.PacketByteBuf;

import java.util.List;

public class CarpetClient implements IICAClient {
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
        ListTag rules = new ListTag();
        for (ParsedRule<?> rule : CarpetServer.settingsManager.getRules()) {
            CompoundTag packedRule = new CompoundTag();
            packedRule.putString("name", rule.name);
            packedRule.putString("type", rule.type.getSimpleName());
            packedRule.putString("value", rule.getAsString());
            packedRule.putString("defaultValue", rule.defaultValue.toString());
            //#if MC >= 11500
            packedRule.putString("description", rule.translatedDescription());
            packedRule.putString("translatedName", rule.translatedName().replaceAll(" \\(.*\\)", ""));
            packedRule.putString("translatedDescription", rule.translatedDescription());
            //#else
            //$$ packedRule.putString("description", rule.description);
            //$$ packedRule.putString("translatedName", rule.name.replaceAll(" \\(.*\\)", ""));
            //$$ packedRule.putString("translatedDescription", rule.description);
            //#endif

            ListTag categories = new ListTag();
            //#if MC >= 11500
            rule.categories.forEach(category -> categories.add(StringTag.of(Translations.tr("category." + category))));
            //#else
            //$$ rule.categories.forEach(category -> categories.add(new StringTag(category)));
            //#endif
            packedRule.put("categories", categories);

            rules.add(packedRule);
        }
        CompoundTag data = new CompoundTag();
        data.put("rules", rules);
        player.networkHandler.sendPacket(new CustomPayloadS2CPacket(CARPET_RULES,
                new PacketByteBuf(Unpooled.buffer()).writeCompoundTag(data)));
    }

    public static void onValueChanged(String rule, String newValue) {
        CompoundTag data = new CompoundTag();
        data.putString("rule", rule);
        data.putString("newValue", newValue);
        playerWithCarpetClient.forEach(player -> player.networkHandler.sendPacket(new CustomPayloadS2CPacket(VALUE_CHANGED,
                new PacketByteBuf(Unpooled.buffer()).writeCompoundTag(data))));

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
