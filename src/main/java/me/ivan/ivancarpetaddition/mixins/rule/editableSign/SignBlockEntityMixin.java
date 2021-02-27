package me.ivan.ivancarpetaddition.mixins.rule.editableSign;

import me.ivan.ivancarpetaddition.IvanCarpetAdditionSettings;
import net.minecraft.block.entity.SignBlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Hand;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(SignBlockEntity.class)
public class SignBlockEntityMixin {
    @Shadow private boolean editable;

    @Inject(method = "onActivate", at = @At("HEAD"))
    private void onActivate(PlayerEntity playerEntity, CallbackInfoReturnable<Boolean> cir) {
        if (IvanCarpetAdditionSettings.editableSign && playerEntity.abilities.allowModifyWorld && playerEntity.getActiveHand() == Hand.MAIN_HAND && playerEntity.isSneaking()) {
            editable = true;
            playerEntity.openEditSignScreen((SignBlockEntity)(Object) this);
        }
    }
}
