package me.ivan.ivancarpetaddition.mixins.rule.icaSyncProtocol;

import carpet.settings.ParsedRule;
import carpet.settings.SettingsManager;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import io.netty.buffer.Unpooled;
import me.ivan.ivancarpetaddition.network.Identifiers;
import net.minecraft.network.packet.s2c.play.CustomPayloadS2CPacket;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.PacketByteBuf;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(SettingsManager.class)
public class SettingsManagerMixin {
    @Inject(method = "setRule", at = @At("TAIL"), remap = false)
    private void setRule(ServerCommandSource source, ParsedRule<?> rule, String newValue, CallbackInfoReturnable<Integer> cir) {
        if (rule.name.equals("icaSyncProtocol")) {
            try {
                ServerPlayerEntity player = source.getPlayer();
                if (newValue.equals("true")) {
                    player.networkHandler.sendPacket(new CustomPayloadS2CPacket(Identifiers.ENABLE_ICA_SYNC_PROTOCOL, new PacketByteBuf(Unpooled.buffer())));
                } else {
                    player.networkHandler.sendPacket(new CustomPayloadS2CPacket(Identifiers.DISABLE_ICA_SYNC_PROTOCOL, new PacketByteBuf(Unpooled.buffer())));
                }
            } catch (CommandSyntaxException ignored) {

            }
        }
    }
}
