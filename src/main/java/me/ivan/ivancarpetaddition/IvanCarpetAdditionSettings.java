package me.ivan.ivancarpetaddition;

import me.ivan.ivancarpetaddition.settings.Rule;
import me.ivan.ivancarpetaddition.commands.xpcounter.XPCounter;
import me.ivan.ivancarpetaddition.settings.RuleObserver;

import static carpet.settings.RuleCategory.*;

public class IvanCarpetAdditionSettings {
    public static final String ICA = "ICA";
    public static final String PROTOCOL = "protocol";
    public static final String PORTING = "porting";

    @Rule(options = {"_"}, strict = false, categories = {ICA, EXPERIMENTAL})
    public static String customVersion = "_";

    @Rule(options = {"0.05000000074505806"}, strict = false, categories = {ICA, CREATIVE})
    public static double cobwebSlowdownSpeed = 0.05000000074505806D;

    @Rule(options = {"Steve,Alex", "Steve,Alex,bot_loader", "bot_loader"}, strict = false, categories = {ICA, CREATIVE, SURVIVAL})
    public static String fakePlayerNameSuggestions = "Steve,Alex";

    @Rule(categories = {ICA, SURVIVAL})
    public static boolean playerCommandNoControlSelf = false;

    @Rule(categories = {ICA, CREATIVE, SURVIVAL})
    public static boolean flippinCactusSound = false;

    //#if MC < 12000
    @Rule(categories = {ICA, EXPERIMENTAL, SURVIVAL})
    public static boolean editableSign = false;
    //#endif

    @Rule(options = {"_", "zombie", "skeleton", "zombie,skeleton"}, strict = false, categories = {ICA, CREATIVE})
    public static String mobBlackList = "_";

    @Rule(options = {"_", "zombie", "skeleton", "zombie,skeleton"}, strict = false, categories = {ICA, CREATIVE})
    public static String mobWhiteList = "_";

    @Rule(options = {"none", "whitelist", "blacklist"}, categories = {ICA, CREATIVE})
    public static String mobSpawningRestrictionMode = "none";

    @Rule(categories = {ICA, FEATURE})
    public static boolean creeperDropCompletely = false;

    @Rule(categories = {ICA, FEATURE})
    public static boolean creeperIgnitedByFire = false;

    @Rule(categories = {ICA, BUGFIX})
    public static boolean pistonBedrockBreakingFix = false;

    @Rule(categories = {ICA, FEATURE, EXPERIMENTAL})
    public static boolean blockEventChunkLoading = false;

    @Rule(categories = {ICA, FEATURE, PORTING})
    public static boolean mendableIronGolem = true;

    @Rule(categories = {ICA, FEATURE, EXPERIMENTAL})
    public static boolean mendableSnowGolem = false;

    @Rule(categories = {ICA, FEATURE, PORTING})
    //#if MC < 11500
    //$$ public static boolean spongeDryInNether = false;
    //#else
    public static boolean spongeDryInNether = true;
    //#endif

    @Rule(categories = {ICA, FEATURE})
    public static boolean magmaBlockDamageItem = false;

    @Rule(categories = {ICA, FEATURE, EXPERIMENTAL})
    public static boolean functionalSpongeItem = false;

    @Rule(options = {"#none", "bot_"}, strict = false, categories = {ICA, SURVIVAL, CREATIVE})
    public static String fakePlayerPrefixRestriction = "#none";

    @Rule(
            options = {"#none", "_fake"},
            strict = false, categories = {ICA, SURVIVAL, CREATIVE})
    public static String fakePlayerSuffixRestriction = "#none";

    @Rule(categories = {ICA, FEATURE, EXPERIMENTAL})
    public static boolean infiniteWaterDisabled = false;

    @Rule(categories = {ICA, FEATURE, SURVIVAL})
    public static boolean renewableSoulSand = false;

    @Rule(categories = {ICA, CREATIVE})
    public static boolean containerDropInventoryDisabled = false;

    @Rule(categories = {ICA, FEATURE})
    public static boolean endLightningRod = false;

    @Rule(categories = {ICA, FEATURE, EXPERIMENTAL})
    public static boolean undeadImmuneToSunlight = false;

    @Rule(categories = {ICA, FEATURE, EXPERIMENTAL})
    public static boolean unbreakableHelmetInSunlight = false;

    @Rule(categories = {ICA, CREATIVE})
    public static boolean mobAlwaysPickUpLoot = false;

    @Rule(categories = {ICA, CREATIVE})
    public static boolean dispensersNotAffectPlayers = false;

    @Rule(categories = {ICA, PROTOCOL})
    public static boolean icaSyncProtocol = false;

    @Rule(categories = {ICA, FEATURE})
    public static boolean stopFreezing = false;

    @Rule(validators = XPCounterObserver.class, categories = {ICA, CREATIVE, FEATURE, COMMAND})
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

    @Rule(categories = {ICA, SURVIVAL})
    public static boolean strictBlockPlacement = false;

    @Rule(categories = {ICA, CREATIVE, BUGFIX})
    public static boolean endermitesInconsistencyFix = false;

    @Rule(categories = {ICA, COMMAND}, options = {"true", "false", "ops", "0", "1", "2", "3", "4"})
    public static String commandReplaceProperties = "false";
}
