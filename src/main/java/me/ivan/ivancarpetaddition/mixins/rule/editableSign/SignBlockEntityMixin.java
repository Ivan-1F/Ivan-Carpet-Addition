package me.ivan.ivancarpetaddition.mixins.rule.editableSign;

import me.fallenbreath.conditionalmixin.api.annotation.Condition;
import me.fallenbreath.conditionalmixin.api.annotation.Restriction;
import me.ivan.ivancarpetaddition.IvanCarpetAdditionSettings;
import me.ivan.ivancarpetaddition.utils.ModIds;
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

@Restriction(require = @Condition(value = ModIds.minecraft, versionPredicates = "<1.20"))
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
