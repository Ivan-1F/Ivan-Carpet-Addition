package me.ivan.ivancarpetaddition;

import carpet.CarpetExtension;
import carpet.CarpetServer;
import com.google.common.collect.Maps;
import com.mojang.brigadier.CommandDispatcher;
import me.ivan.ivancarpetaddition.commands.replaceproperties.ReplacePropertiesCommand;
import me.ivan.ivancarpetaddition.commands.xpcounter.XPCounterCommand;
import me.ivan.ivancarpetaddition.commands.xpcounter.XPCounter;
import me.ivan.ivancarpetaddition.logging.ICALoggerRegistry;
import me.ivan.ivancarpetaddition.network.ICASyncProtocol;
import me.ivan.ivancarpetaddition.network.carpetclient.CarpetClient;
import me.ivan.ivancarpetaddition.translations.ICATranslations;
import me.ivan.ivancarpetaddition.utils.doc.DocumentGeneration;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map;

public class IvanCarpetAdditionServer implements CarpetExtension {
	public static final IvanCarpetAdditionServer INSTANCE = new IvanCarpetAdditionServer();
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

	public static void registerExtension() {
		CarpetServer.manageExtension(INSTANCE);
	}

	@Override
	public void onGameStarted() {
		LOGGER.info(fancyName + " " + IvanCarpetAdditionMod.getVersion() + " loaded");
		LOGGER.info("Thank you for using " + shortName.toUpperCase() + "!");
		LOGGER.info(shortName.toUpperCase() + " is open source, u can find it here: https://github.com/Ivan-1F/Ivan-Carpet-Addition");
		LOGGER.info(shortName.toUpperCase() + " is still in development, it may not work well");
		LOGGER.info("If u find any bug, please report them here: https://github.com/Ivan-1F/Ivan-Carpet-Addition/issues");

		CarpetServer.settingsManager.parseSettingsClass(IvanCarpetAdditionSettings.class);

		CarpetServer.settingsManager.addRuleObserver((serverCommandSource, currentRuleState, originalUserTest) -> {
			if (IvanCarpetAdditionSettings.icaSyncProtocol) {
				CarpetClient.onValueChanged(currentRuleState.name, currentRuleState.get().toString());
			}
		});

		ICATranslations.loadTranslations();
		ICASyncProtocol.init();

		if (FabricLoader.getInstance().isDevelopmentEnvironment()) {
			DocumentGeneration.generateDocuments();
		}
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
	public void registerCommands(CommandDispatcher<ServerCommandSource> dispatcher) {
		XPCounterCommand.getInstance().registerCommand(dispatcher);
		ReplacePropertiesCommand.getInstance().registerCommand(dispatcher);
	}

	@Override
	public void onPlayerLoggedIn(ServerPlayerEntity player) {
		if (IvanCarpetAdditionSettings.icaSyncProtocol) {
			ICASyncProtocol.onPlayerLoggedIn(player);
		}
		XPCounter.onPlayerLoggedIn(player);
	}

	@Override
	public void onPlayerLoggedOut(ServerPlayerEntity player) {
		if (IvanCarpetAdditionSettings.icaSyncProtocol) {
			ICASyncProtocol.onPlayerLoggedOut(player);
		}
		if (IvanCarpetAdditionSettings.xpCounter) {
			XPCounter.onPlayerLoggedOut(player);
		}
	}

	@Override
	public void registerLoggers() {
		ICALoggerRegistry.registerLoggers();
	}

	@Override
	public Map<String, String> canHasTranslations(String lang) {
		Map<String, String> trimmedTranslation = Maps.newHashMap();
		String prefix = ICATranslations.TRANSLATION_KEY_PREFIX + "carpet_extension.";
		ICATranslations.getTranslation(lang).forEach((key, value) -> {
			if (key.startsWith(prefix)) {
				trimmedTranslation.put(key.substring(prefix.length()), value);
			}
		});
		return trimmedTranslation;
	}
}
