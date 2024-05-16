package me.ivan.ivancarpetaddition.mixins.rule.customVersion;

import me.ivan.ivancarpetaddition.IvanCarpetAdditionSettings;
import net.minecraft.server.ServerMetadata;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

//#if MC >= 11904
//$$ import java.util.Optional;
//#endif

@Mixin(ServerMetadata.class)
public class ServerMetadataMixin {
    @Inject(
            //#if MC >= 11904
            //$$ method = "version",
            //#else
            method = "getVersion",
            //#endif
            at = @At("HEAD"),
            cancellable = true
    )
    private void overwriteVersion(
            //#if MC >= 11904
            //$$ CallbackInfoReturnable<Optional<ServerMetadata.Version>> cir
            //#else
            CallbackInfoReturnable<ServerMetadata.Version> cir
            //#endif
    ) {
        if (!IvanCarpetAdditionSettings.customVersion.equals("_")) {
            cir.setReturnValue(
                    //#if MC >= 11904
                    //$$ Optional.of(
                    //#endif
                            new ServerMetadata.Version(IvanCarpetAdditionSettings.customVersion, 1000)
                    //#if MC >= 11904
                    //$$ )
                    //#endif
            );
        }
    }
}
