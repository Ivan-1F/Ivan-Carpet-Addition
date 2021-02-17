package me.ivan.ivancarpetaddition.mixins.rule.bannedMobs;

import carpet.settings.ParsedRule;
import carpet.settings.SettingsManager;
import com.google.common.collect.Sets;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import io.netty.buffer.Unpooled;
import me.ivan.ivancarpetaddition.IvanCarpetAdditionSettings;
import me.ivan.ivancarpetaddition.network.Identifiers;
import net.minecraft.network.packet.s2c.play.CustomPayloadS2CPacket;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.PacketByteBuf;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Arrays;
import java.util.Set;

@Mixin(SettingsManager.class)
public class SettingsManagerMixin {
    @Shadow private MinecraftServer server;

    @Inject(method = "setRule", at = @At("TAIL"), remap = false)
    private void setRule(ServerCommandSource source, ParsedRule<?> rule, String newValue, CallbackInfoReturnable<Integer> cir) {
        if (rule.name.equals("bannedMobs")) {
            if (IvanCarpetAdditionSettings.bannedMobs.equals("_")) return;
            Set<String> bannedMobs = Sets.newLinkedHashSet(Arrays.asList(IvanCarpetAdditionSettings.bannedMobs.split(",")));
            server.getWorlds().forEach(world -> {
                world.getEntities(null, entity -> true).forEach(entity -> {
                    if (bannedMobs.contains(entity.getName().getString().toLowerCase())) {
                        world.removeEntity(entity);
                    }
                });
            });
        }
    }
}
