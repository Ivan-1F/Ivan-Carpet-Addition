package me.ivan.ivancarpetaddition;

import carpet.settings.ParsedRule;
import carpet.settings.Rule;
import carpet.settings.Validator;
import carpet.utils.Messenger;
import me.ivan.ivancarpetaddition.mixins.rule.blockEventChunkLoading.ChunkTicketTypeAccessor;
import me.ivan.ivancarpetaddition.utils.registry.ChunkTicketTypeRegistry;
import net.minecraft.server.command.ServerCommandSource;

import static carpet.settings.RuleCategory.*;

public class IvanCarpetAdditionSettings {
    public static final String ICA = "ICA";
    public static final String PROTOCOL = "protocol";

    @Rule(
            desc = "Set a custom version on client trying to connect to the server",
            extra = "Use '_' to disable",
            options = "_",
            strict = false,
            category = {ICA, EXPERIMENTAL}
    )
    public static String customVersion = "_";

    @Rule(
            desc = "A protocol to sync entities between the client and the server",
            category = {ICA, PROTOCOL}
    )
    public static boolean icaSyncProtocol = false;

    @Rule(
            desc = "Override the slow down speed of cobwebs",
            options = "0.05000000074505806",
            strict = false,
            category = {ICA, CREATIVE}
    )
    public static double cobwebSlowDownSpeed = 0.05000000074505806D;

    @Rule(
            desc = "Override the player list when using /player command",
            extra = "Use ',' to split each name",
            options = {"Steve,Alex", "Steve,Alex,bot_loader", "bot_loader"},
            strict = false,
            category = {ICA, CREATIVE, SURVIVAL}
    )
    public static String fakePlayerPreset = "Steve,Alex";

    @Rule(
            desc = "Players can't control themselves using /player command",
            category = {ICA, SURVIVAL}
    )
    public static boolean playerCommandNoControlSelf = false;

    @Rule(
            desc = "Play 'BLOCK_DISPENSER_LAUNCH' sound when using cactus to flip block",
            category = {ICA, CREATIVE, SURVIVAL}
    )
    public static boolean flippinCactusSound = false;

    @Rule(
            desc = "Right click a sign block with an empty hand when you are sneaking to reopen the sign editor",
            category = {ICA, EXPERIMENTAL}
    )
    public static boolean editableSign = false;

    @Rule(
            desc = "Avoid certain mob from spawning",
            extra = {"Use ',' to split each mob", "Use '_' to disable", "This will also remove the existing mobs"},
            options = {"zombie", "skeleton", "zombie,skeleton"},
            strict = false,
            category = {ICA, CREATIVE}
    )
    public static String bannedMobs = "_";

    @Rule(
            desc = "Creeper explosion 100% drop",
            category = {ICA, FEATURE}
    )
    public static boolean creeperDropCompletely = false;

    @Rule(
            desc = "Creepers can be ignited when they are on fire",
            category = {ICA, FEATURE}
    )
    public static boolean creeperIgnitedByFire = false;

    @Rule(
            desc = "Fix bedrock breaking with head-less piston",
            category = {ICA, BUGFIX}
    )
    public static boolean pistonBedrockBreakingFix = false;

    @Rule(
            desc = "Block event can load chunks",
            category = {ICA, FEATURE, EXPERIMENTAL}
    )
    public static boolean blockEventChunkLoading = false;

    @Rule(
            desc = "The load duration of block event",
            options = {"4", "8", "16"},
            strict = false,
            validate = {BlockEventChunkLoadingTicksValidator.class},
            category = {ICA, FEATURE, EXPERIMENTAL}
    )
    public static int blockEventChunkLoadingTicks = 4;

    @Rule(
            desc = "A villager with a bed can load 3*3 chunks",
            category = {ICA, FEATURE, EXPERIMENTAL}
    )
    public static boolean villageChunkLoading = false;

    private static class BlockEventChunkLoadingTicksValidator extends Validator<Integer> {
        @Override
        public Integer validate(ServerCommandSource serverCommandSource, ParsedRule<Integer> parsedRule, Integer integer, String s) {
            if (integer > 0) {
                ((ChunkTicketTypeAccessor) ChunkTicketTypeRegistry.BLOCK_EVENT).setExpiryTicks(integer);
                return integer;
            }
            Messenger.m(serverCommandSource, new Object[]{"r You must input a positive number!"});
            return 4;
        }
    }
}
