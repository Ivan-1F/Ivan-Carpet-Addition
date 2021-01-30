package me.ivan.ivancarpetaddition.mixins.rule.playerCommandNoControlSelf;

import carpet.commands.PlayerCommand;
import carpet.patches.EntityPlayerMPFake;
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
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(PlayerCommand.class)
public class PlayerCommandMixin {
    private static ServerPlayerEntity getPlayer(CommandContext<ServerCommandSource> context) {
        String playerName = StringArgumentType.getString(context, "player");
        MinecraftServer server = ((ServerCommandSource)context.getSource()).getMinecraftServer();
        return server.getPlayerManager().getPlayer(playerName);
    }

    @Overwrite
    private static boolean cantManipulate(CommandContext<ServerCommandSource> context) {
        PlayerEntity player = getPlayer(context);
        if (player == null) {
            Messenger.m((ServerCommandSource)context.getSource(), new Object[]{"r Can only manipulate existing players"});
            return true;
        } else {
            ServerPlayerEntity sendingPlayer;
            try {
                sendingPlayer = ((ServerCommandSource)context.getSource()).getPlayer();
            } catch (CommandSyntaxException var4) {
                return false;
            }

            if (IvanCarpetAdditionSettings.playerCommandNoControlSelf && sendingPlayer == player) {
                Messenger.m((ServerCommandSource)context.getSource(), new Object[]{"r You are not allowed to manipulate yourself"});
                return true;
            }

            if (!((ServerCommandSource)context.getSource()).getMinecraftServer().getPlayerManager().isOperator(sendingPlayer.getGameProfile()) && sendingPlayer != player && !(player instanceof EntityPlayerMPFake)) {
                Messenger.m((ServerCommandSource)context.getSource(), new Object[]{"r Non OP players can't control other real players"});
                return true;
            } else {
                return false;
            }
        }
    }
}
