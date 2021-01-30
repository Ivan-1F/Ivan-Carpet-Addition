package me.ivan.ivancarpetaddition.mixins.rule.icaSyncProtocol;

import me.ivan.ivancarpetaddition.IvanCarpetAdditionSettings;
import me.ivan.ivancarpetaddition.network.IcaSyncProtocol;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.PlayerManager;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionType;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

@Mixin(MinecraftServer.class)
public class MinecraftServerMixin {
    @Shadow @Final
    Map<DimensionType, ServerWorld> worlds;
    @Shadow
    PlayerManager playerManager;

    @Inject(method = "tick", at = @At("TAIL"))
    private void tick(CallbackInfo ci) {
        if (IvanCarpetAdditionSettings.icaSyncProtocol) {
            List<Entity> entities = new LinkedList<>();

            worlds.forEach((type, world) -> {
                world.getEntitiesByType(null, entity -> true).forEach(entity -> {
                    entities.add(entity);
//                    playerManager.getPlayerList().forEach(player -> {
//                        IcaSyncProtocol.updateEntity(player, entity);
//                    });
                });
//                playerManager.getPlayerList().forEach(player -> {
//                    IcaSyncProtocol.updateEntities(player, world.getEntities(null, entity -> true));
//                });
            });
            playerManager.getPlayerList().forEach(player -> {
                IcaSyncProtocol.updateEntities(player, entities);
            });
        }
    }
}
