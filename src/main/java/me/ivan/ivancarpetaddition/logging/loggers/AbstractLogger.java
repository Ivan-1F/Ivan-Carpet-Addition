package me.ivan.ivancarpetaddition.logging.loggers;

import carpet.logging.Logger;
import carpet.logging.LoggerRegistry;
import com.google.common.base.Joiner;
import me.ivan.ivancarpetaddition.IvanCarpetAdditionServer;
import me.ivan.ivancarpetaddition.logging.ICALoggerRegistry;
import me.ivan.ivancarpetaddition.translations.TranslationContext;
import me.ivan.ivancarpetaddition.translations.Translator;
import net.minecraft.text.BaseText;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.function.Consumer;
import java.util.function.Supplier;

//#if MC >= 11900
//$$ import net.minecraft.text.Text;
//#endif

//#if MC >= 11600
//$$ import com.mojang.brigadier.StringReader;
//#endif

/**
 * Reference: Carpet TIS Addition
 */
public abstract class AbstractLogger extends TranslationContext {
    private static final Translator TRANSLATOR = new Translator("logger");
    protected final static String MULTI_OPTION_SEP_REG = "[,. ]";
    protected final static String OPTION_SEP = ",";

    private final String name;
    @SuppressWarnings("FieldCanBeLocal")
    private final boolean strictOption;

    public AbstractLogger(String name, boolean strictOption) {
        super(TRANSLATOR.getDerivedTranslator(name));
        this.name = name;
        this.strictOption = strictOption;
    }

    public String getName() {
        return this.name;
    }

    public Logger getLogger() {
        return LoggerRegistry.getLogger(this.getName());
    }

    protected void actionWithLogger(Consumer<Logger> action) {
        Logger logger = getLogger();
        if (logger != null) {
            action.accept(logger);
        } else {
            IvanCarpetAdditionServer.LOGGER.warn("Failed to get carpet logger {}", this.getName());
        }
    }

    public void log(
            //#if MC >= 11900
            //$$ Supplier<Text[]> messagePromise
            //#else
            Supplier<BaseText[]> messagePromise
            //#endif
    ) {
        actionWithLogger(logger -> logger.log(messagePromise));
    }

    public void log(Logger.lMessage messagePromise) {
        actionWithLogger(logger -> logger.log(messagePromise));
    }

    public void log(Logger.lMessageIgnorePlayer messagePromise) {
        actionWithLogger(logger -> logger.log(messagePromise));
    }

    @Nullable
    public String getDefaultLoggingOption() {
        String[] suggested = this.getSuggestedLoggingOption();
        return suggested != null && suggested.length > 0 ? suggested[0] : null;
    }

    @Nullable
    public String[] getSuggestedLoggingOption() {
        return null;
    }

    public Logger createCarpetLogger() {
        return ICALoggerRegistry.standardLogger(
                this.getName(),
                wrapOption(this.getDefaultLoggingOption()),
                wrapOptions(this.getSuggestedLoggingOption())
                //#if MC >= 11700
                //$$ , this.strictOption
                //#endif
        );
    }

    /**
     * Fabric carpet 1.4.25+ (mc1.16+) uses {@code StringArgumentType.string()} as the option argument in the `/log` command
     * So we might need to wrap our option with quotes if necessary
     */
    protected static String wrapOption(@Nullable String option) {
        //#if MC >= 11600
        //$$ if (option == null) {
        //$$ 	return null;
        //$$ }
        //$$ boolean requiresQuotes = false;
        //$$ for (int i = 0; i < option.length(); i++) {
        //$$ 	if (!StringReader.isAllowedInUnquotedString(option.charAt(i))) {
        //$$ 		requiresQuotes = true;
        //$$ 		break;
        //$$ 	}
        //$$ }
        //$$ if (requiresQuotes) {
        //$$ 	option = "\"" + option.replace("\"", "\"\"") + "\"";
        //$$ }
        //#endif
        return option;
    }

    protected static String[] wrapOptions(@Nullable String... options) {
        if (options == null) {
            return null;
        }
        options = options.clone();
        for (int i = 0; i < options.length; i++) {
            options[i] = wrapOption(options[i]);
        }
        return options;
    }

    protected static String createCompoundOption(Iterable<String> options) {
        return Joiner.on(OPTION_SEP).join(options);
    }

    protected static String createCompoundOption(String... options) {
        return createCompoundOption(Arrays.asList(options));
    }
}
