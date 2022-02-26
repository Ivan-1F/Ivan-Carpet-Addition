package me.ivan.ivancarpetaddition.helpers.xpcounter;

import carpet.utils.Messenger;
import com.google.common.collect.Maps;
import me.ivan.ivancarpetaddition.commands.xpcounter.SpawnReason;
import me.ivan.ivancarpetaddition.commands.xpcounter.interfaces.IExperienceOrbEntity;
import me.ivan.ivancarpetaddition.mixins.command.xpcounter.ExperienceOrbEntityAccessor;
import me.ivan.ivancarpetaddition.translations.TranslationContext;
import me.ivan.ivancarpetaddition.translations.Translator;
import net.minecraft.entity.ExperienceOrbEntity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.BaseText;
import net.minecraft.text.TranslatableText;
import net.minecraft.world.dimension.DimensionType;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ExperienceCounter extends TranslationContext {
    public static final Map<ServerPlayerEntity, ExperienceCounter> COUNTERS = Maps.newHashMap();
    private static MinecraftServer attachedServer;

    private static final Translator TRANSLATOR = (new ExperienceCounter(null)).getTranslator();

    private final ServerPlayerEntity player;
    public final Map<SpawnReason, Integer> counter = Maps.newHashMap();
    private long startTick;
    private long startMillis;

    public static Translator getStaticTranslator()
    {
        return TRANSLATOR;
    }

    public static void attachServer(MinecraftServer server) {
        attachedServer = server;
        for (ServerPlayerEntity serverPlayerEntity : server.getPlayerManager().getPlayerList()) {
            onPlayerLoggedIn(serverPlayerEntity);
        }
    }

    public static void onPlayerLoggedIn(ServerPlayerEntity serverPlayerEntity) {
        COUNTERS.put(serverPlayerEntity, new ExperienceCounter(serverPlayerEntity));
    }

    public static void onPlayerLoggedOut(ServerPlayerEntity serverPlayerEntity) {
        COUNTERS.remove(serverPlayerEntity);
    }

    public ExperienceCounter(ServerPlayerEntity player) {
        super("counter.xp");
        this.player = player;
    }

    public static ExperienceCounter getCounter(ServerPlayerEntity player) {
        return COUNTERS.get(player);
    }

    public void add(ExperienceOrbEntity experienceOrb) {
        if (startTick == 0) {
            startTick = attachedServer.getWorld(DimensionType.OVERWORLD).getTime();
            startMillis = System.currentTimeMillis();
        }
        SpawnReason spawnReason = ((IExperienceOrbEntity) experienceOrb).getSpawnReason();
        int amount = ((ExperienceOrbEntityAccessor) experienceOrb).getAmount();
        this.counter.putIfAbsent(spawnReason, 0);
        this.counter.put(spawnReason, this.counter.get(spawnReason) + amount);
    }

    public List<BaseText> format(boolean realTime) {
        int total = this.getTotalExperience();
        long ticks = Math.max(realTime ? (System.currentTimeMillis() - startMillis) / 50 : attachedServer.getWorld(DimensionType.OVERWORLD).getTime() - startTick, 1);

        List<BaseText> items = new ArrayList<>();
        String time = String.format("%.2f", ticks * 1.0 / (20 * 60));
        String rate = String.format("%.1f", total * 1.0 * (20 * 60 * 60) / ticks);
        items.add(Messenger.c("w ", tr(realTime ? "counter_summary_realtime" : "counter_summary",
                        player.getName().getString(), time, total, rate),
                "nb [X]", "^g reset", "!/counter " + player.getName() + " reset"
        ));
        this.counter.keySet().forEach(key -> {
            int count = this.counter.get(key);
            System.out.println(key.toText());
            items.add(Messenger.c("w - ", key.toText(), String.format("w : %d, %.1f/h",
                    count,
                    count * (20.0 * 60.0 * 60.0) / ticks)));
        });

        return items;
    }

    public int getTotalExperience() {
        return this.counter.values().stream().mapToInt(Integer::intValue).sum();
    }
}
