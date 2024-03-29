package me.ivan.ivancarpetaddition.mixins.rule.customVersion;

import me.ivan.ivancarpetaddition.IvanCarpetAdditionSettings;
import net.minecraft.server.ServerMetadata;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Optional;

@Mixin(ServerMetadata.class)
public class ServerMetadataMixin {
    @Inject(method = "version", at = @At("HEAD"), cancellable = true)
    private void overwriteVersion(CallbackInfoReturnable<Optional<ServerMetadata.Version>> cir) {
        if (!IvanCarpetAdditionSettings.customVersion.equals("_")) {
            cir.setReturnValue(Optional.of(new ServerMetadata.Version(IvanCarpetAdditionSettings.customVersion, 1000)));
        }
    }
}
