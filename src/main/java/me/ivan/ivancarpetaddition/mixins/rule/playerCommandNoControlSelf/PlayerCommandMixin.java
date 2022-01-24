package me.ivan.ivancarpetaddition.mixins.rule.playerCommandNoControlSelf;

import carpet.commands.PlayerCommand;
import carpet.utils.Messenger;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import me.ivan.ivancarpetaddition.IvanCarpetAdditionSettings;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerCommand.class)
public class PlayerCommandMixin {
    private static ServerPlayerEntity getPlayer(CommandContext<ServerCommandSource> context) {
        String playerName = StringArgumentType.getString(context, "player");
        MinecraftServer server = (context.getSource()).getServer();
        return server.getPlayerManager().getPlayer(playerName);
    }

    @Inject(method = "cantManipulate", at = @At("HEAD"), cancellable = true, remap = false)
    private static void cantManipulate(CommandContext<ServerCommandSource> context, CallbackInfoReturnable<Boolean> cir) {
        PlayerEntity player = getPlayer(context);
        if (player == null) {
            Messenger.m(context.getSource(), "r Can only manipulate existing players");
            cir.setReturnValue(true);
        } else {
            ServerPlayerEntity sendingPlayer;
            try {
                sendingPlayer = (context.getSource()).getPlayer();
                if (IvanCarpetAdditionSettings.playerCommandNoControlSelf && sendingPlayer == player) {
                    Messenger.m(context.getSource(), "r You are not allowed to manipulate yourself");
                    cir.setReturnValue(true);
                }
            } catch (CommandSyntaxException var4) {
                cir.setReturnValue(false);
            }
        }
    }
}
