package me.ivan.ivancarpetaddition.mixins.translations;

import me.ivan.ivancarpetaddition.translations.ICATranslations;
import me.ivan.ivancarpetaddition.translations.ServerPlayerEntityWithClientLanguage;
import net.minecraft.network.packet.c2s.play.ClientSettingsC2SPacket;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.BaseText;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
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

    /**
     * This handle all ICA translation on chat messages
     */
    @ModifyVariable(
            method = {
                    //#if MC >= 11901
                    //$$ "sendMessageToClient",
                    //#elseif MC >= 11600
                    //$$ "sendMessage(Lnet/minecraft/text/Text;Z)V",
                    //$$ "sendMessage(Lnet/minecraft/text/Text;Lnet/minecraft/network/MessageType;Ljava/util/UUID;)V",
                    //#else
                    "addChatMessage",
                    "sendChatMessage",
                    //#endif
            },
            at = @At("HEAD"),
            argsOnly = true
    )
    private Text applyICACarpetTranslationToChatMessage(Text message) {
        if (message instanceof BaseText) {
            message = ICATranslations.translate((BaseText) message, (ServerPlayerEntity) (Object) this);
        }
        return message;
    }
}
