package me.ivan.ivancarpetaddition;

import carpet.CarpetExtension;
import carpet.CarpetServer;
import com.google.common.collect.Maps;
import com.mojang.brigadier.CommandDispatcher;
import me.ivan.ivancarpetaddition.commands.replaceproperties.ReplacePropertiesCommand;
import me.ivan.ivancarpetaddition.commands.xpcounter.XPCounterCommand;
import me.ivan.ivancarpetaddition.commands.xpcounter.XPCounter;
import me.ivan.ivancarpetaddition.logging.ICALoggerRegistry;
import me.ivan.ivancarpetaddition.settings.CarpetRuleRegistrar;
import me.ivan.ivancarpetaddition.translations.ICATranslations;
import me.ivan.ivancarpetaddition.translations.TranslationConstants;
import me.ivan.ivancarpetaddition.utils.doc.DocumentGeneration;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map;

//#if MC >= 11900
//$$ import net.minecraft.command.CommandRegistryAccess;
//#endif

//#if MC < 11900
import me.ivan.ivancarpetaddition.network.ICASyncProtocol;
import me.ivan.ivancarpetaddition.network.carpetclient.CarpetClient;
//#endif

public class IvanCarpetAdditionServer implements CarpetExtension {
    private static final IvanCarpetAdditionServer INSTANCE = new IvanCarpetAdditionServer();
    public static final String shortName = "ica";
    public static final String name = IvanCarpetAdditionMod.getModId();
    public static final String fancyName = "Ivan Carpet Addition";
    public static final String compactName = name.replace("-", "");  // ivancarpetaddition
    public static final Logger LOGGER = LogManager.getLogger(fancyName);
    public static MinecraftServer minecraftServer;

    @Override
    public String version() {
        return name;
    }

    public static IvanCarpetAdditionServer getInstance() {
        return INSTANCE;
    }

    public static void init() {
        CarpetServer.manageExtension(INSTANCE);
        ICATranslations.loadTranslations();
    }

    @Override
    public void onGameStarted() {
        LOGGER.info(fancyName + " " + IvanCarpetAdditionMod.getVersion() + " loaded");
        LOGGER.info("Thank you for using " + shortName.toUpperCase() + "!");
        LOGGER.info(shortName.toUpperCase() + " is open source, u can find it here: https://github.com/Ivan-1F/Ivan-Carpet-Addition");
        LOGGER.info(shortName.toUpperCase() + " is still in development, it may not work well");
        LOGGER.info("If u find any bug, please report them here: https://github.com/Ivan-1F/Ivan-Carpet-Addition/issues");

        ICATranslations.loadTranslations();
        CarpetRuleRegistrar.register(CarpetServer.settingsManager, IvanCarpetAdditionSettings.class);

        //#if MC < 11900
        CarpetServer.settingsManager.addRuleObserver((serverCommandSource, currentRuleState, originalUserTest) -> {
            if (IvanCarpetAdditionSettings.icaSyncProtocol) {
                CarpetClient.onValueChanged(currentRuleState.name, currentRuleState.get().toString());
            }
        });

        ICASyncProtocol.init();
        //#endif

        if (FabricLoader.getInstance().isDevelopmentEnvironment()) {
            DocumentGeneration.generateDocuments();
        }

        // Let's do some logging things
        //#if MC < 11500
        //$$ ICALoggerRegistry.registerLoggers();
        //#endif
    }

    @Override
    public void onServerLoaded(MinecraftServer server) {
        minecraftServer = server;
        XPCounter.attachServer(server);
    }

    @Override
    public void onServerClosed(MinecraftServer server) {
        XPCounter.detachServer();
    }

    @Override
    public void onTick(MinecraftServer server) {

    }

    @Override
    public void registerCommands(
            CommandDispatcher<ServerCommandSource> dispatcher
            //#if MC >= 11900
            //$$ , CommandRegistryAccess commandBuildContext
            //#endif
    ) {
        XPCounterCommand
                .getInstance()
                .registerCommand(
                        dispatcher
                        //#if MC >= 11900
                        //$$ , commandBuildContext
                        //#endif
                );
        ReplacePropertiesCommand
                .getInstance()
                .registerCommand(
                        dispatcher
                        //#if MC >= 11900
                        //$$ , commandBuildContext
                        //#endif
                );
    }

    @Override
    public void onPlayerLoggedIn(ServerPlayerEntity player) {
        //#if MC < 11900
        if (IvanCarpetAdditionSettings.icaSyncProtocol) {
            ICASyncProtocol.onPlayerLoggedIn(player);
        }
        //#endif
        XPCounter.onPlayerLoggedIn(player);
    }

    @Override
    public void onPlayerLoggedOut(ServerPlayerEntity player) {
        //#if MC < 11900
        if (IvanCarpetAdditionSettings.icaSyncProtocol) {
            ICASyncProtocol.onPlayerLoggedOut(player);
        }
        //#endif
        if (IvanCarpetAdditionSettings.xpCounter) {
            XPCounter.onPlayerLoggedOut(player);
        }
    }

    //#if MC >= 11500
    @Override
    public void registerLoggers() {
        ICALoggerRegistry.registerLoggers();
    }

    @Override
    public Map<String, String> canHasTranslations(String lang) {
        Map<String, String> trimmedTranslation = Maps.newHashMap();
        String prefix = TranslationConstants.CARPET_TRANSLATIONS_KEY_PREFIX;
        ICATranslations.getTranslation(lang).forEach((key, value) -> {
            if (key.startsWith(prefix)) {
                String newKey = key.substring(prefix.length());
                //#if MC >= 11901
                //$$ newKey = "carpet." + newKey;
                //#endif
                trimmedTranslation.put(newKey, value);
            }
        });
        return trimmedTranslation;
    }
    //#endif
}
