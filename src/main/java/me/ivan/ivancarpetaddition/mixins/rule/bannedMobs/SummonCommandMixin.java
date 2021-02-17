package me.ivan.ivancarpetaddition.mixins.rule.bannedMobs;

import com.google.common.collect.Sets;
import me.ivan.ivancarpetaddition.IvanCarpetAdditionSettings;
import me.ivan.ivancarpetaddition.translations.TranslatableBase;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.command.SummonCommand;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Arrays;
import java.util.Set;

@Mixin(SummonCommand.class)
public class SummonCommandMixin {
    @Inject(method = "execute", at = @At("HEAD"), cancellable = true)
    private static void execute(ServerCommandSource source, Identifier entity, Vec3d pos, CompoundTag nbt, boolean initialize, CallbackInfoReturnable<Integer> cir) {
        if (IvanCarpetAdditionSettings.bannedMobs.equals("_")) return;
        Set<String> bannedMobs = Sets.newLinkedHashSet(Arrays.asList(IvanCarpetAdditionSettings.bannedMobs.split(",")));
        if (bannedMobs.contains(entity.getPath())) {
            cir.cancel();
        }
    }
}
