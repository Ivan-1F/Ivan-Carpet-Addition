package me.ivan.ivancarpetaddition.helpers.xpcounter.rule.fakePlayerNameRestriction;

import com.mojang.brigadier.context.CommandContext;
import me.ivan.ivancarpetaddition.IvanCarpetAdditionSettings;
import me.ivan.ivancarpetaddition.translations.Translator;
import me.ivan.ivancarpetaddition.utils.Messenger;
import net.minecraft.server.command.ServerCommandSource;

public class FakePlayerNameRestrictionHelper {
    private static final Translator translator = new Translator("rule.fakePlayerNameRestriction");
    public static boolean checkCanSpawn(CommandContext<ServerCommandSource> context, String playerName) {
        String prefix = IvanCarpetAdditionSettings.fakePlayerPrefixCheck;
        String suffix = IvanCarpetAdditionSettings.fakePlayerSuffixCheck;
        if (!prefix.equals("#none") && !playerName.startsWith(prefix)) {
            Messenger.tell(context.getSource(), Messenger.formatting(translator.tr("prefix_not_satisfied", prefix), "r"));
            return false;
        }

        if (!suffix.equals("#none") && !playerName.endsWith(prefix)) {
            Messenger.tell(context.getSource(), Messenger.formatting(translator.tr("suffix_not_satisfied", suffix), "r"));
            return false;
        }
        return true;
    }
}
