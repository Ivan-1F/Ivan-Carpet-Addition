package me.ivan.ivancarpetaddition.mixins.carpet;

import carpet.CarpetServer;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(CarpetServer.class)
public abstract class CarpetServerMixin {
    // don't registerExtension here
}
