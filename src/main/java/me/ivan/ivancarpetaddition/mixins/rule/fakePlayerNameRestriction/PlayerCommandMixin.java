package me.ivan.ivancarpetaddition.mixins.rule.fakePlayerNameRestriction;

import carpet.commands.PlayerCommand;
import com.llamalad7.mixinextras.sugar.Local;
import com.mojang.brigadier.context.CommandContext;
import me.ivan.ivancarpetaddition.helpers.rule.fakePlayerNameRestriction.FakePlayerNameRestrictionHelper;
import net.minecraft.server.command.ServerCommandSource;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerCommand.class)
public class PlayerCommandMixin {
    @Inject(
            method = "spawn",
            at = @At(
                    value = "INVOKE",
                    //#if MC >= 11500
                    target = "Ljava/lang/String;length()I"
                    //#else
                    //$$ target = "Lnet/minecraft/server/command/ServerCommandSource;getMinecraftServer()Lnet/minecraft/server/MinecraftServer;"
                    //#endif
            ),
            cancellable = true,
            remap = false
    )
    private static void checkPlayerName(
            CommandContext<ServerCommandSource> context,
            CallbackInfoReturnable<Integer> cir,
            @Local(ordinal = 0) String playerName
    ) {
        if (!FakePlayerNameRestrictionHelper.checkCanSpawn(context, playerName)) {
            cir.setReturnValue(0);
        }
    }
}
