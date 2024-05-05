package me.ivan.ivancarpetaddition.mixins.rule.fakePlayerNameSuggestions;

import carpet.commands.PlayerCommand;
import com.google.common.collect.Sets;
import me.ivan.ivancarpetaddition.IvanCarpetAdditionSettings;
import net.minecraft.server.command.ServerCommandSource;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Arrays;
import java.util.Collection;
import java.util.Set;

@Mixin(PlayerCommand.class)
public class PlayerCommandMixin {
    @Inject(
            //#if MC >= 12000
            //$$ method = "getPlayerSuggestions",
            //#else
            method = "getPlayers",
            //#endif
            at = @At("HEAD"),
            remap = false,
            cancellable = true
    )
    private static void overwriteSuggestsPlayerList(ServerCommandSource source, CallbackInfoReturnable<Collection<String>> cir) {
        Set<String> players = Sets.newLinkedHashSet(Arrays.asList(IvanCarpetAdditionSettings.fakePlayerNameSuggestions.split(",")));
        players.addAll(source.getPlayerNames());
        cir.setReturnValue(players);
    }
}
