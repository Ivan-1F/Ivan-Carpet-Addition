package me.ivan.ivancarpetaddition;

import carpet.CarpetExtension;
import carpet.CarpetServer;
import com.mojang.brigadier.CommandDispatcher;
import me.ivan.ivancarpetaddition.translations.ExtensionTranslations;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map;

public class IvanCarpetAdditionServer implements CarpetExtension {
	public static final IvanCarpetAdditionServer instance = new IvanCarpetAdditionServer();
	public static final String shortName = "ica";
	public static final String name = "ivan-carpet-addition";
	public static final String fancyName = "Ivan Carpet Addition";
	public static final String compactName = name.replace("-","");  // ivancarpetaddition
	// should be the same as the version in gradlew.properties
	// "undefined" will be replaced with build number during github action
	public static final String version = "1.0.0+build.undefined";
	public static final Logger LOGGER = LogManager.getLogger(fancyName);
	public static MinecraftServer minecraftServer;

	@Override
	public String version() {
		return name;
	}

	public static void noop() { }

	static {
		CarpetServer.manageExtension(instance);
	}

	@Override
	public void onGameStarted() {
		// let's /carpet handle our few simple settings
		CarpetServer.settingsManager.parseSettingsClass(IvanCarpetAdditionSettings.class);

		// set-up a snooper to observe how rules are changing in carpet
		CarpetServer.settingsManager.addRuleObserver( (serverCommandSource, currentRuleState, originalUserTest) ->
		{
			// here we will be snooping for command changes
		});
	}

	@Override
	public void onServerLoaded(MinecraftServer server) {
		// reloading of /carpet settings is handled by carpet
		// reloading of own settings is handled as an extension, since we claim own settings manager
		// in case something else falls into
		minecraftServer = server;
	}

	@Override
	public void onServerClosed(MinecraftServer server) {

	}

	@Override
	public void onTick(MinecraftServer server) {

	}

	@Override
	public void registerCommands(CommandDispatcher<ServerCommandSource> dispatcher) {

	}

	@Override
	public void onPlayerLoggedIn(ServerPlayerEntity player) {

	}

	@Override
	public void onPlayerLoggedOut(ServerPlayerEntity player) {

	}

	@Override
	public void registerLoggers() {

	}

	@Override
	public Map<String, String> canHasTranslations(String lang) {
		return ExtensionTranslations.getTranslationFromResourcePath(lang);
	}

	public static Identifier getIdentifier(String id) {
		return new Identifier(shortName, id);
	}
}
