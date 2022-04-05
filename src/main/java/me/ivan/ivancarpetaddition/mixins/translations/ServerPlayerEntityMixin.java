package me.ivan.ivancarpetaddition.mixins.translations;

import me.ivan.ivancarpetaddition.translations.ServerPlayerEntityWithClientLanguage;
import net.minecraft.network.packet.c2s.play.ClientSettingsC2SPacket;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerPlayerEntity.class)
public class ServerPlayerEntityMixin implements ServerPlayerEntityWithClientLanguage {
    private String clientLanguage$ICA = "en_US";

    @Inject(method = "setClientSettings", at = @At("HEAD"))
    private void recordClientLanguage(ClientSettingsC2SPacket packet, CallbackInfo ci) {
        this.clientLanguage$ICA = ((ClientSettingsC2SPacketAccessor) packet).getLanguage$ICA();
    }

    @Override
    public String getClientLanguage$ICA() {
        return this.clientLanguage$ICA;
    }
}
