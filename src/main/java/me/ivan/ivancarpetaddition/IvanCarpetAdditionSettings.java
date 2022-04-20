package me.ivan.ivancarpetaddition;

import carpet.settings.Rule;
import me.ivan.ivancarpetaddition.commands.xpcounter.XPCounter;
import me.ivan.ivancarpetaddition.utils.RuleObserver;

import static carpet.settings.RuleCategory.*;

public class IvanCarpetAdditionSettings {
    public static final String ICA = "ICA";
    public static final String PROTOCOL = "protocol";
    public static final String PORTING = "porting";

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

    @Rule(
            desc = "Overwrite the player list suggested when using /player command",
            extra = {"Use ',' to split each name"},
            options = {"Steve,Alex", "Steve,Alex,bot_loader", "bot_loader"},
            strict = false,
            category = {ICA, CREATIVE, SURVIVAL}
    )
    public static String fakePlayerNameSuggestions = "Steve,Alex";

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
            desc = "Use a sign block with main hand empty when you are sneaking to reopen the sign editor",
            category = {ICA, EXPERIMENTAL, SURVIVAL}
    )
    public static boolean editableSign = false;

    @Rule(
            desc = "Stop some mobs from spawning",
            extra = {
                    "Use ',' to split each mob",
                    "Set rule 'mobSpawningRestrictionMode' to 'blacklist' to enable",
                    "Set rule 'mobSpawningRestrictionMode' to 'none' to disable"
            },
            options = {"_", "zombie", "skeleton", "zombie,skeleton"},
            strict = false,
            category = {ICA, CREATIVE}
    )
    public static String mobBlackList = "_";

    @Rule(
            desc = "Only allow some mobs to spawn",
            extra = {
                    "Use ',' to split each mob",
                    "Set rule 'mobSpawningRestrictionMode' to 'whitelist' to enable",
                    "Set rule 'mobSpawningRestrictionMode' to 'none' to disable"
            },
            options = {"_", "zombie", "skeleton", "zombie,skeleton"},
            strict = false,
            category = {ICA, CREATIVE}
    )
    public static String mobWhiteList = "_";

    @Rule(
            desc = "Modify the way to restrict mob spawning",
            extra = {
                    "whitelist: Only mobs defined in rule 'mobWhiteList' can spawn in the world. Rule 'mobBlackList' will be ignored",
                    "blacklist: Mobs defined in rule 'mobBlackList' cannot spawn in the world. Rule 'mobWhiteList' will be ignored"
            },
            options = {"none", "whitelist", "blacklist"},
            category = {ICA, CREATIVE}
    )
    public static String mobSpawningRestrictionMode = "none";

    @Rule(
            desc = "Creeper explosions will have a 100% drop rate",
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
            extra = {"Technically piston heads will not remove any block other than itself"},
            category = {ICA, BUGFIX}
    )
    public static boolean pistonBedrockBreakingFix = false;

    @Rule(
            desc = "Block event can load 3x3 chunks for 8gt",
            category = {ICA, FEATURE, EXPERIMENTAL}
    )
    public static boolean blockEventChunkLoading = false;

    @Rule(
            desc = "Use an iron ingot at an iron golem to mend it (+25 Health)",
            extra = {"Only works in 1.14"},
            category = {ICA, FEATURE, PORTING}
    )
    public static boolean mendableIronGolem = true;

    @Rule(
            desc = "Use a snowball at a snow golem or hit a snow golem with a snowball to mend it (+1 Health)",
            category = {ICA, FEATURE, EXPERIMENTAL}
    )
    public static boolean mendableSnowGolem = false;

    @Rule(
            desc = "Wet sponge will transform to sponge immediately when placing in the nether",
            extra = {"Only works in 1.14"},
            category = {ICA, FEATURE, PORTING}
    )
    public static boolean spongeDryInNether = true;

    @Rule(
            desc = "Items on magma block get damages",
            category = {ICA, FEATURE}
    )
    public static boolean magmaBlockDamageItem = true;

    @Rule(
            desc = "Sponge items do water clearance and dry in the nether after 60gt",
            category = {ICA, FEATURE, EXPERIMENTAL}
    )
    public static boolean functionalSpongeItem = false;

    @Rule(
            desc = "/player command will only be able to spawn fake players with the given prefix",
            extra = {"Set to '#none' to disable"},
            options = {"#none", "bot_"},
            strict = false,
            category = {ICA, SURVIVAL, CREATIVE}
    )
    public static String fakePlayerPrefixRestriction = "#none";

    @Rule(
            desc = "Player command will only be able to spawn fake players with the given suffix",
            extra = {"Set to '#none' to disable"},
            options = {"#none", "_fake"},
            strict = false,
            category = {ICA, SURVIVAL, CREATIVE}
    )
    public static String fakePlayerSuffixRestriction = "#none";

    @Rule(
            desc = "Infinite water will not form",
            category = {ICA, FEATURE, EXPERIMENTAL}
    )
    public static boolean infiniteWaterDisabled = false;

    @Rule(
            desc = "A sand turn into a soul sand when a mob suffered in it",
            category = {ICA, FEATURE, SURVIVAL}
    )
    public static boolean renewableSoulSand = false;

    @Rule(
            desc = "Containers such as chests and barrels won't drop their inventories when being broke",
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
            desc = "Helmet equipped by an undead will not be damaged by sunlight",
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
            desc = "A tool like 'hopperCounter' to use players as xp counters",
            validate = XPCounterObserver.class,
            extra = {
                    "Enables '/xpcounter' command",
                    "Use '/xpcounter <player> reset' to reset the counter",
                    "Use '/xpcounter <player>' to query the counter",
                    "Use '/log xpcounter <players>' to subscribe xp counters"
            },
            category = {ICA, CREATIVE, FEATURE}
    )
    public static boolean xpCounter = false;

    private static class XPCounterObserver extends RuleObserver<Boolean> {
        @Override
        public void onValueChanged(Boolean oldValue, Boolean newValue) {
            if (newValue) {
                XPCounter.onEnable();
            } else {
                XPCounter.onDisable();
            }
        }
    }
}
