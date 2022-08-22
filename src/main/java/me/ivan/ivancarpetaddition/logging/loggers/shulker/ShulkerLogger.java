package me.ivan.ivancarpetaddition.logging.loggers.shulker;

import com.google.common.collect.Lists;
import me.ivan.ivancarpetaddition.logging.loggers.AbstractLogger;
import me.ivan.ivancarpetaddition.utils.Messenger;
import net.minecraft.entity.mob.ShulkerEntity;
import net.minecraft.text.BaseText;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ShulkerLogger extends AbstractLogger {
    private static final String NAME = "shulker";
    private static final ShulkerLogger INSTANCE = new ShulkerLogger();

    private ShulkerLogger() {
        super(NAME, false);
    }

    public static ShulkerLogger getInstance() {
        return INSTANCE;
    }

    @Override
    public @Nullable String getDefaultLoggingOption() {
        return LoggingOption.DEFAULT.getName();
    }

    @Override
    public @Nullable String[] getSuggestedLoggingOption() {
        return LoggingOption.getSuggestions();
    }

    public void onShulkerTeleport(ShulkerEntity shulker, BlockPos from, BlockPos to) {
        this.log(option -> {
            if (LoggingOption.TELEPORT.isContainedIn(option)) {
                return new BaseText[]{
                        Messenger.c(
                                Messenger.entity("b", shulker),
                                "  @ ",
                                Messenger.coord(from),
                                "g  -> ",
                                Messenger.coord(to)
                        )
                };
            }
            return null;
        });
    }

    public enum LoggingOption {
        DUPE,
        TELEPORT;

        public static final LoggingOption DEFAULT = TELEPORT;

        public String getName() {
            return this.name().toLowerCase();
        }

        public static String[] getSuggestions() {
            List<String> suggestions = Lists.newArrayList();
            suggestions.addAll(Arrays.stream(values()).map(LoggingOption::getName).collect(Collectors.toList()));
            suggestions.add(createCompoundOption(DUPE.getName(), TELEPORT.getName()));
            return suggestions.toArray(new String[0]);
        }

        public boolean isContainedIn(String option) {
            return Arrays.asList(option.split(MULTI_OPTION_SEP_REG)).contains(this.getName());
        }
    }
}
