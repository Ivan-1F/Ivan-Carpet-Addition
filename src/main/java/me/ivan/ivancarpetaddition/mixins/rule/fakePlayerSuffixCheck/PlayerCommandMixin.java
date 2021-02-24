package me.ivan.ivancarpetaddition.mixins.rule.fakePlayerSuffixCheck;

import carpet.commands.PlayerCommand;
import carpet.utils.Messenger;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import me.ivan.ivancarpetaddition.IvanCarpetAdditionSettings;
import net.minecraft.server.command.ServerCommandSource;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerCommand.class)
public class PlayerCommandMixin {
    @Inject(method = "spawn", at = @At("HEAD"), cancellable = true, remap = false)
    private static void spawn(CommandContext<ServerCommandSource> context, CallbackInfoReturnable<Integer> cir) {
        String playerName = StringArgumentType.getString(context, "player");
        String suffix = IvanCarpetAdditionSettings.fakePlayerSuffixCheck;
        if (!suffix.equals("#none")) {
            if (!playerName.endsWith(suffix)) {
                Messenger.m(context.getSource(), new Object[]{"r Name of fake players are only allowed to end with '" + IvanCarpetAdditionSettings.fakePlayerSuffixCheck + "'"});
                cir.setReturnValue(0);
            }
        }
    }
}
