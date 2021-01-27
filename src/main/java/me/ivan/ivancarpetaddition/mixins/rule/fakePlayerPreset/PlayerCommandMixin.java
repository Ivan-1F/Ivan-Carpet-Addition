package me.ivan.ivancarpetaddition.mixins.rule.fakePlayerPreset;

import carpet.commands.PlayerCommand;
import com.google.common.collect.Sets;
import me.ivan.ivancarpetaddition.IvanCarpetAdditionSettings;
import net.minecraft.server.command.ServerCommandSource;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import java.util.Arrays;
import java.util.Collection;
import java.util.Set;

@Mixin(PlayerCommand.class)
public class PlayerCommandMixin {
    @Overwrite
    private static Collection<String> getPlayers(ServerCommandSource source) {
        Set<String> players = Sets.newLinkedHashSet(Arrays.asList(IvanCarpetAdditionSettings.fakePlayerPreset.split(",")));
        players.addAll(source.getPlayerNames());
        return players;
    }
}
