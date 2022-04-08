package me.ivan.ivancarpetaddition.helpers.xpcounter;

import com.google.common.collect.Maps;
import me.ivan.ivancarpetaddition.IvanCarpetAdditionSettings;
import me.ivan.ivancarpetaddition.commands.xpcounter.SpawnReason;
import me.ivan.ivancarpetaddition.commands.xpcounter.interfaces.IExperienceOrbEntity;
import me.ivan.ivancarpetaddition.mixins.command.xpcounter.ExperienceOrbEntityAccessor;
import me.ivan.ivancarpetaddition.translations.TranslationContext;
import me.ivan.ivancarpetaddition.translations.Translator;
import me.ivan.ivancarpetaddition.utils.Messenger;
import net.minecraft.entity.ExperienceOrbEntity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.BaseText;
import net.minecraft.text.ClickEvent;
import net.minecraft.text.Text;
import net.minecraft.world.dimension.DimensionType;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

public class ExperienceCounter extends TranslationContext {
    public static final Map<ServerPlayerEntity, ExperienceCounter> COUNTERS = Maps.newHashMap();

    public static MinecraftServer getAttachedServer() {
        return attachedServer;
    }

    private static MinecraftServer attachedServer;

    private static final Translator TRANSLATOR = (new ExperienceCounter(null)).getTranslator();

    private final ServerPlayerEntity player;
    public final Map<SpawnReason, Integer> counter = Maps.newHashMap();
    private long startTick;
    private long startMillis;

    public static Translator getStaticTranslator() {
        return TRANSLATOR;
    }

    public static void attachServer(MinecraftServer server) {
        attachedServer = server;
    }

    public static void detachServer() {
        attachedServer = null;
    }

    public static void onPlayerLoggedIn(ServerPlayerEntity serverPlayerEntity) {
        if (IvanCarpetAdditionSettings.experienceCounter) {
            COUNTERS.put(serverPlayerEntity, new ExperienceCounter(serverPlayerEntity));
        }
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
            startTick = getAttachedServer().getWorld(DimensionType.OVERWORLD).getTime();
            startMillis = System.currentTimeMillis();
        }
        int amount = ((ExperienceOrbEntityAccessor) experienceOrb).getAmount();
        if (!(experienceOrb instanceof IExperienceOrbEntity)) {
            this.counter.putIfAbsent(SpawnReason.UNKNOWN, 0);
            this.counter.put(SpawnReason.UNKNOWN, this.counter.get(SpawnReason.UNKNOWN) + amount);
        } else {
            SpawnReason spawnReason = ((IExperienceOrbEntity) experienceOrb).getSpawnReason();
            if (spawnReason == null) {
                this.counter.putIfAbsent(SpawnReason.UNKNOWN, 0);
                this.counter.put(SpawnReason.UNKNOWN, this.counter.get(SpawnReason.UNKNOWN) + amount);
            } else {
                this.counter.putIfAbsent(spawnReason, 0);
                this.counter.put(spawnReason, this.counter.get(spawnReason) + amount);
            }
        }
    }

    public static Stream<String> getPlayers() {
        return COUNTERS.keySet().stream().map(ServerPlayerEntity::getName).map(Text::asString);
    }

    public void reset() {
        counter.clear();
        startTick = getAttachedServer().getWorld(DimensionType.OVERWORLD).getTime();
        startMillis = System.currentTimeMillis();
    }

    public static void resetAll() {
        COUNTERS.values().forEach(ExperienceCounter::reset);
    }

    public boolean isEmpty() {
        return this.counter.isEmpty() || this.getTotalExperience() == 0;
    }

    public static List<BaseText> formatAll(boolean realTime) {
        List<BaseText> text = new ArrayList<>();
        COUNTERS.forEach((player, counter) -> {
            if (!counter.isEmpty()) {
                List<BaseText> temp = counter.format(realTime, false);
                if (temp.size() > 1) {
                    if (!text.isEmpty()) text.add(Messenger.s(""));
                    text.addAll(temp);
                }
            }
        });
        if (text.isEmpty()) {
            text.add(getStaticTranslator().tr("no_counter"));
        }
        return text;
    }

    public List<BaseText> format(boolean realTime, boolean brief) {
        if (this.counter.isEmpty()) {
            if (brief) {
                return Collections.singletonList(Messenger.formatting(
                        tr("no_xp.brief", this.player.getName()),
                        "g"
                ));
            }
            return Collections.singletonList(tr("no_xp", this.player.getName()));
        }

        int total = this.getTotalExperience();
        long ticks = Math.max(realTime ? (System.currentTimeMillis() - startMillis) / 50 : getAttachedServer().getWorld(DimensionType.OVERWORLD).getTime() - startTick, 1);

        if (total == 0) {
            if (brief) {
                return Collections.singletonList(
                        Messenger.formatting(
                                tr("no_xp.timed.brief", this.player, ticks / (20.0 * 60.0)),
                                "c"
                        )
                );
            }
            String translationKey = "no_xp.timed" + (realTime ? ".realtime" : "");
            return Collections.singletonList(Messenger.c(
                    tr(translationKey, this.player.getName(), ticks / (20.0 * 60.0))
            ));
        }
        if (brief) {
            return Collections.singletonList(Messenger.formatting(
                    tr(
                            "summary.brief",
                            this.player.getName(),
                            total,
                            total * (20 * 60 * 60) / ticks,
                            ticks / (20.0 * 60.0)
                    ), "c")
            );
        }

        List<BaseText> items = new ArrayList<>();
        String time = String.format("%.2f", ticks * 1.0 / (20 * 60));
        String rate = String.format("%.1f", total * 1.0 * (20 * 60 * 60) / ticks);
        String translationKey = "summary" + (realTime ? ".realtime" : "");
        items.add(Messenger.c(
                tr(translationKey, player.getName().getString(), time, total, rate),
                Messenger.fancy(
                        "nb",
                        Messenger.s("[X]"),
                        tr("click_to_reset", this.player.getName().getString()),
                        new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/xpcounter " + this.player.getName().getString() + " reset"))
        ));
        this.counter.keySet().forEach(key -> {
            int count = this.counter.get(key);
            items.add(Messenger.c(
                    Messenger.s("- "),
                    key.toText(),
                    Messenger.s(String.format(
                            ": %d, %.1f/h",
                            count,
                            count * (20.0 * 60.0 * 60.0) / ticks)
                    )
            ));
        });

        return items;
    }

    public int getTotalExperience() {
        return this.counter.values().stream().mapToInt(Integer::intValue).sum();
    }

    public static void sendRestarted(ServerCommandSource source, ServerPlayerEntity player) {
        if (player == null) {
            Messenger.tell(source, getStaticTranslator().tr("restarted_all"));
        } else {
            Messenger.tell(source, getStaticTranslator().tr("restarted", player.getName()));
        }
    }

    public static void onEnable() {
        COUNTERS.clear();
        getAttachedServer().getPlayerManager().getPlayerList().forEach(ExperienceCounter::onPlayerLoggedIn);
    }

    public static void onDisable() {
        COUNTERS.clear();
    }
}
