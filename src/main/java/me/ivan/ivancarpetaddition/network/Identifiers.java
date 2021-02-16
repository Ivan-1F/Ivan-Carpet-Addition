package me.ivan.ivancarpetaddition.network;

import me.ivan.ivancarpetaddition.IvanCarpetAdditionServer;
import net.minecraft.util.Identifier;

public class Identifiers {
    // S2C
    public static Identifier HI = IvanCarpetAdditionServer.getIdentifier("hi");
    public static Identifier ENABLE_ICA_SYNC_PROTOCOL = IvanCarpetAdditionServer.getIdentifier("enable_ica_sync_protocol");
    public static Identifier DISABLE_ICA_SYNC_PROTOCOL = IvanCarpetAdditionServer.getIdentifier("disable_ica_sync_protocol");
    // C2S
    public static Identifier HELLO = IvanCarpetAdditionServer.getIdentifier("hello");
    public static Identifier REQUIRE_ALL_ENTITIES = IvanCarpetAdditionServer.getIdentifier("require_entity");
    public static Identifier REQUIRE_ALL_BLOCK_ENTITIES = IvanCarpetAdditionServer.getIdentifier("require_block_entity");
}
