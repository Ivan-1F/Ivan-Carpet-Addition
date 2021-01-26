package me.ivan.ivancarpetaddition.mixins.rule.customVersion;

import net.minecraft.server.ServerMetadata;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(ServerMetadata.class)
public interface ServerMetadataAccessor {
    @Accessor("version")
    public void setVersion(ServerMetadata.Version version);
}
