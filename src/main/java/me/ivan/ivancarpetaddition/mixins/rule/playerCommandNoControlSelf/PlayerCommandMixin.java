package me.ivan.ivancarpetaddition.mixins.rule.playerCommandNoControlSelf;

import carpet.commands.PlayerCommand;
import com.mojang.brigadier.context.CommandContext;
import me.ivan.ivancarpetaddition.IvanCarpetAdditionSettings;
import me.ivan.ivancarpetaddition.translations.Translator;
import me.ivan.ivancarpetaddition.utils.Messenger;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.command.ServerCommandSource;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(PlayerCommand.class)
public class PlayerCommandMixin {
    @Inject(
            method = "cantManipulate",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/server/command/ServerCommandSource;getServer()Lnet/minecraft/server/MinecraftServer;"
            ),
            cancellable = true,
            remap = false,
            locals = LocalCapture.CAPTURE_FAILHARD
    )
    private static void stopControllingSelf(CommandContext<ServerCommandSource> context, CallbackInfoReturnable<Boolean> cir, PlayerEntity player, PlayerEntity sendingPlayer) {
        Translator translator = new Translator("rule.playerCommandNoControlSelf");
        if (IvanCarpetAdditionSettings.playerCommandNoControlSelf && sendingPlayer == player) {
            Messenger.tell(context.getSource(), Messenger.formatting(translator.tr("warning"), "r"));
            cir.setReturnValue(true);
        }
    }
}
