package me.ivan.ivancarpetaddition;

import carpet.CarpetExtension;
import carpet.CarpetServer;
import com.google.common.collect.Maps;
import com.mojang.brigadier.CommandDispatcher;
import me.ivan.ivancarpetaddition.commands.xpcounter.ExperienceCounterCommand;
import me.ivan.ivancarpetaddition.helpers.xpcounter.ExperienceCounter;
import me.ivan.ivancarpetaddition.network.IcaSyncProtocol;
import me.ivan.ivancarpetaddition.network.carpetclient.CarpetClient;
import me.ivan.ivancarpetaddition.translations.ICATranslations;
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
		// Display info
		LOGGER.info(fancyName + " " + IvanCarpetAdditionMod.getVersion() + " loaded");
		LOGGER.info("Thank you for using " + shortName.toUpperCase() + "!");
		LOGGER.info(shortName.toUpperCase() + " is open source, u can find it here: https://github.com/Ivan-1F/Ivan-Carpet-Addition");
		LOGGER.info(shortName.toUpperCase() + " is still in development, it may not work well");
		LOGGER.info("If u find any bug, please report them here: https://github.com/Ivan-1F/Ivan-Carpet-Addition/issues");

		// let's /carpet handle our few simple settings
		CarpetServer.settingsManager.parseSettingsClass(IvanCarpetAdditionSettings.class);

		// set-up a snooper to observe how rules are changing in carpet
		CarpetServer.settingsManager.addRuleObserver((serverCommandSource, currentRuleState, originalUserTest) -> {
			// here we will be snooping for command changes
			if (IvanCarpetAdditionSettings.icaSyncProtocol) {
				CarpetClient.onValueChanged(currentRuleState.name, currentRuleState.get().toString());
			}
		});

		IcaSyncProtocol.init();
		ICATranslations.loadTranslations();
	}

	@Override
	public void onServerLoaded(MinecraftServer server) {
		// reloading of /carpet settings is handled by carpet
		// reloading of own settings is handled as an extension, since we claim own settings manager
		// in case something else falls into
		minecraftServer = server;
		ExperienceCounter.attachServer(server);
	}

	@Override
	public void onServerClosed(MinecraftServer server) {

	}

	@Override
	public void onTick(MinecraftServer server) {

	}

	@Override
	public void registerCommands(CommandDispatcher<ServerCommandSource> dispatcher) {
		ExperienceCounterCommand.register(dispatcher);
	}

	@Override
	public void onPlayerLoggedIn(ServerPlayerEntity player) {
		if (IvanCarpetAdditionSettings.icaSyncProtocol) {
			IcaSyncProtocol.onPlayerLoggedIn(player);
		}
		if (IvanCarpetAdditionSettings.experienceCounter) {
			ExperienceCounter.onPlayerLoggedIn(player);
		}
	}

	@Override
	public void onPlayerLoggedOut(ServerPlayerEntity player) {
		if (IvanCarpetAdditionSettings.icaSyncProtocol) {
			IcaSyncProtocol.onPlayerLoggedOut(player);
		}
		if (IvanCarpetAdditionSettings.experienceCounter) {
			ExperienceCounter.onPlayerLoggedOut(player);
		}
	}

	@Override
	public void registerLoggers() {

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
