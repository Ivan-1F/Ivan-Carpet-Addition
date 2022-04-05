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

    // RULE BEGIN

    // DOCS APPEND ![screenshot](https://raw.githubusercontent.com/Ivan-YFw/Ivan-Carpet-Addition/fabric-1.15.2/screenshots/customVersion.png)
    @Rule(
            desc = "Set a custom version on client trying to connect to the server",
            extra = {"Use '_' to disable"},
            options = {"_"},
            strict = false,
            category = {ICA, EXPERIMENTAL}
    )
    public static String customVersion = "_";

    @Rule(
            desc = "Overwrite the slowdown speed of cobwebs",
            options = {"0.05000000074505806"},
            strict = false,
            category = {ICA, CREATIVE}
    )
    public static double cobwebSlowdownSpeed = 0.05000000074505806D;

    // DOCS APPEND ![screenshot](https://raw.githubusercontent.com/Ivan-YFw/Ivan-Carpet-Addition/fabric-1.15.2/screenshots/fakePlayerPreset.png)
    @Rule(
            desc = "Overwrite the player list when using /player command",
            extra = {"Use ',' to split each name"},
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
            desc = "Right click a sign block with main hand empty when you are sneaking to reopen the sign editor",
            category = {ICA, EXPERIMENTAL, SURVIVAL}
    )
    public static boolean editableSign = false;

    @Rule(
            desc = "Avoid some mobs from spawning",
            extra = {"Use ',' to split each mob", "Set rule 'mobSpawningRestrictionMode' to 'blacklist' to enable", "Set rule 'mobSpawningRestrictionMode' to 'none' to disable"},
            options = {"_", "zombie", "skeleton", "zombie,skeleton"},
            strict = false,
            category = {ICA, CREATIVE}
    )
    public static String mobBlackList = "_";

    @Rule(
            desc = "Only allow some mobs to spawn",
            extra = {"Use ',' to split each mob", "Set rule 'mobSpawningRestrictionMode' to 'whitelist' to enable", "Set rule 'mobSpawningRestrictionMode' to 'none' to disable"},
            options = {"_", "zombie", "skeleton", "zombie,skeleton"},
            strict = false,
            category = {ICA, CREATIVE}
    )
    public static String mobWhiteList = "_";

    @Rule(
            desc = "Modify the way to restrict mob spawning (black list or white list)",
            extra = {"Set the list with rule 'mobBlackList' and 'mobWhiteList'"},
            options = {"none", "whitelist", "blacklist"},
            category = {ICA, CREATIVE}
    )
    public static String mobSpawningRestrictionMode = "none";

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

    // DOCS IGNORE extra
    // DOCS MODIFY default_value: "\n    - 1.14: `false`\n    - 1.15+: `true`"
    @Rule(
            desc = "Right click a iron golem with the iron ingot to mend it (+25 Health)",
            extra = {"Default values:", "1.14: false", "1.15+: true"},
            category = {ICA, FEATURE}
    )
    public static boolean mendableIronGolem = true;

    @Rule(
            desc = "Right click a snow golem with the snowball or hit it with the snowball to mend it (+1 Health)",
            category = {ICA, FEATURE, EXPERIMENTAL}
    )
    public static boolean mendableSnowGolem = false;

    // DOCS IGNORE extra
    // DOCS MODIFY default_value: "\n    - 1.14: `false`\n    - 1.15+: `true`"
    @Rule(
            desc = "Wet sponge will dry in nether",
            extra = {"Default values:", "1.14: false", "1.15+: true"},
            category = {ICA, FEATURE}
    )
    public static boolean spongeDryInNether = true;

    @Rule(
            desc = "Items on magma block get damage",
            category = {ICA, FEATURE, EXPERIMENTAL}
    )
    public static boolean magmaBlockDamageItem = true;

    @Rule(
            desc = "Sponge item do water clearance and dry in the nether",
            category = {ICA, FEATURE, EXPERIMENTAL}
    )
    public static boolean functionalSpongeItem = false;

    @Rule(
            desc = "Check the prefix of fake players when using player command",
            extra = {"Set to #none to disable"},
            options = {"#none", "bot_"},
            strict = false,
            category = {ICA, SURVIVAL, CREATIVE}
    )
    public static String fakePlayerPrefixCheck = "#none";

    @Rule(
            desc = "Check the suffix of fake players when using player command",
            extra = {"Set to #none to disable"},
            options = {"#none", "_fake"},
            strict = false,
            category = {ICA, SURVIVAL, CREATIVE}
    )
    public static String fakePlayerSuffixCheck = "#none";

    @Rule(
            desc = "Water will act like lava",
            category = {ICA, FEATURE, EXPERIMENTAL}
    )
    public static boolean infiniteWaterDisabled = false;

    @Rule(
            desc = "A sand turn into a soul sand when a mob suffered in it",
            category = {ICA, FEATURE, SURVIVAL}
    )
    public static boolean renewableSoulSand = false;

    @Rule(
            desc = "Containers such as chests and barrels won't drop their inventories when removal",
            category = {ICA, CREATIVE}
    )
    public static boolean containerDropInventoryDisabled = false;

    @Rule(
            desc = "End rods will act like lightning rod in 1.17",
            extra = {"Lightning will NOT naturally spawn on end rods, but it will when end rods are hit by a trident with the Channeling enchantment"},
            category = {ICA, FEATURE}
    )
    public static boolean endLightningRod = false;

    @Rule(
            desc = "Undead will not burn in sunlight",
            extra = {"If a undead is equipped with a helmet, the helmet will not be damaged"},
            category = {ICA, FEATURE, EXPERIMENTAL}
    )
    public static boolean undeadImmuneToSunlight = false;

    @Rule(
            desc = "Helmet equipped by undead will not be damaged in sunlight",
            category = {ICA, FEATURE, EXPERIMENTAL}
    )
    public static boolean unbreakableHelmetInSunlight = false;

    @Rule(
            desc = "Zombies and skeletons and their variants will always be able to pick up loot like other mobs",
            category = {ICA, CREATIVE}
    )
    public static boolean mobAlwaysPickUpLoot = false;

    @Rule(
            desc = "Dispensers will not dispense armor to players",
            category = {ICA, CREATIVE}
    )
    public static boolean dispensersNotAffectPlayers = false;

    @Rule(
            desc = "A protocol to sync server data to client mods",
            category = {ICA, PROTOCOL}
    )
    public static boolean icaSyncProtocol = false;

    @Rule(
            desc = "Stop generating ice at any biome",
            category = {ICA, FEATURE}
    )
    public static boolean stopFreezing = false;

    @Rule(
            desc = "Use players as experience counters",
            extra = {
                    "Enables /xpcounter command",
                    "Use /xpcounter <player?> reset to reset the counter",
                    "Use /xpcounter <player?> to query the counter"
            },
            category = {ICA, CREATIVE, FEATURE}
    )
    public static boolean experienceCounter = false;

    // RULE END

    private static class BlockEventChunkLoadingTicksValidator extends Validator<Integer> {
        @Override
        public Integer validate(ServerCommandSource serverCommandSource, ParsedRule<Integer> parsedRule, Integer integer, String s) {
            if (integer > 0) {
                ((ChunkTicketTypeAccessor) ChunkTicketTypeRegistry.BLOCK_EVENT).setExpiryTicks(integer);
                return integer;
            }
            Messenger.m(serverCommandSource, "r You must input a positive number!");
            return 4;
        }
    }
}
