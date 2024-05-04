package me.ivan.ivancarpetaddition.mixins.carpet;

import carpet.settings.SettingsManager;
import carpet.utils.Messenger;
import carpet.utils.Translations;
import me.ivan.ivancarpetaddition.IvanCarpetAdditionMod;
import me.ivan.ivancarpetaddition.IvanCarpetAdditionServer;
import net.minecraft.server.command.ServerCommandSource;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Slice;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(SettingsManager.class)
public class SettingsManagerMixin {
    @Inject(
            method = "listAllSettings",
            slice = @Slice(
                    from = @At(
                            value = "CONSTANT",
                            // after printed fabric-carpet version
                            //#if MC >= 11500
                            args = "stringValue=ui.version",
                            //#else
                            //$$ args = "stringValue= version: ",
                            //#endif
                            ordinal = 0
                    )
            ),
            at = @At(
                    value = "INVOKE",
                    //#if MC >= 11600
                    //$$ target = "Lcarpet/settings/SettingsManager;getCategories()Ljava/lang/Iterable;",
                    //$$ ordinal = 0
                    //#else
                    target = "Lnet/minecraft/server/command/ServerCommandSource;getPlayer()Lnet/minecraft/server/network/ServerPlayerEntity;",
                    ordinal = 0,
                    remap = true
                    //#endif
            ),
            remap = false
    )
    private void printICAVersion(ServerCommandSource source, CallbackInfoReturnable<Integer> cir) {
        Messenger.m(source,
                String.format("g %s ", IvanCarpetAdditionServer.fancyName),
                String.format("g %s: ", Translations.tr("ui.version",  "version")),
                String.format("g %s", IvanCarpetAdditionMod.getVersion())
        );
    }
}
