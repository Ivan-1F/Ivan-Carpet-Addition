package me.ivan.ivancarpetaddition.mixins.rule.fakePlayerNameRestriction;

import carpet.commands.PlayerCommand;
import com.mojang.brigadier.context.CommandContext;
import me.ivan.ivancarpetaddition.helpers.rule.fakePlayerNameRestriction.FakePlayerNameRestrictionHelper;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.util.math.Vec2f;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.GameMode;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(PlayerCommand.class)
public class PlayerCommandMixin {
    @Inject(
            method = "spawn",
            at = @At(
                    value = "INVOKE",
                    target = "Ljava/lang/String;length()I"
            ),
            locals = LocalCapture.CAPTURE_FAILHARD,
            cancellable = true,
            remap = false
    )
    private static void checkPlayerName(CommandContext<ServerCommandSource> context, CallbackInfoReturnable<Integer> cir, ServerCommandSource source, Vec3d pos, Vec2f facing, RegistryKey<World> dimType, GameMode mode, boolean flying, String playerName) {
        if (!FakePlayerNameRestrictionHelper.checkCanSpawn(context, playerName)) {
            cir.setReturnValue(0);
        }
    }
}
