package me.ivan.ivancarpetaddition;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;

public class IvanCarpetAdditionMod implements ModInitializer {
    private static final String MOD_ID = "ivan-carpet-addition";
    private static String version;
    @Override
    public void onInitialize() {
        version = FabricLoader.getInstance().getModContainer(MOD_ID).orElseThrow(RuntimeException::new).getMetadata().getVersion().getFriendlyString();
    }

    public static String getModId()
    {
        return MOD_ID;
    }

    public static String getVersion()
    {
        return version;
    }
}
