package me.ivan.ivancarpetaddition.mixins.rule.editableSign;

import me.ivan.ivancarpetaddition.IvanCarpetAdditionSettings;
import net.minecraft.block.entity.SignBlockEntity;
import net.minecraft.util.Hand;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

//#if MC >= 11700
//$$ import net.minecraft.server.network.ServerPlayerEntity;
//#else
import net.minecraft.entity.player.PlayerEntity;
//#endif

@Mixin(SignBlockEntity.class)
public class SignBlockEntityMixin {
    @Shadow private boolean editable;

    @Inject(method = "onActivate", at = @At("HEAD"))
    private void reopenEditSignScreen(
            //#if MC >= 11700
            //$$ ServerPlayerEntity playerEntity,
            //#else
            PlayerEntity playerEntity,
            //#endif
            CallbackInfoReturnable<Boolean> cir
    ) {
        if (
                IvanCarpetAdditionSettings.editableSign
                        //#if MC >= 11700
                        //$$ && playerEntity.getAbilities().allowModifyWorld
                        //#else
                        && playerEntity.abilities.allowModifyWorld
                        //#endif
                        && playerEntity.getActiveHand() == Hand.MAIN_HAND
                        && playerEntity.isSneaking()
        ) {
            this.editable = true;
            playerEntity.openEditSignScreen((SignBlockEntity)(Object) this);
        }
    }
}
