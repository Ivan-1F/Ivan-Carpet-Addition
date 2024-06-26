package me.ivan.ivancarpetaddition.network;

//#if MC < 11900
import com.google.common.collect.Lists;
import me.ivan.ivancarpetaddition.mixins.network.CustomPayloadC2SPacketAccessor;
import me.ivan.ivancarpetaddition.network.carpetclient.CarpetClient;
import net.minecraft.network.packet.c2s.play.CustomPayloadC2SPacket;
import net.minecraft.server.network.ServerPlayerEntity;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
//#endif

public class ICASyncProtocol {
    //#if MC < 11900
    private static final List<IICAClient> clients = Lists.newArrayList();
    public static boolean onCustomPayload(ServerPlayerEntity sender, CustomPayloadC2SPacket packet) {
        AtomicBoolean handled = new AtomicBoolean(false);
        clients.forEach(client -> {
            if (client.getNamespace().equals(((CustomPayloadC2SPacketAccessor) packet).getChannel().getNamespace())) {
                handled.set(handled.get() || client.onCustomPayload(sender, packet));
            }
        });
        return handled.get();
    }

    public static void onPlayerLoggedIn(ServerPlayerEntity player) {
        clients.forEach(client -> client.onPlayerLoggedIn(player));
    }

    public static void onPlayerLoggedOut(ServerPlayerEntity player) {
        clients.forEach(client -> client.onPlayerLoggedOut(player));
    }

    public static void registerClient(IICAClient client) {
        clients.add(client);
    }

    public static void init() {
        registerClient(new CarpetClient());
    }
    //#endif
}
