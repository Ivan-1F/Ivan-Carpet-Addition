package me.ivan.ivancarpetaddition.mixins.rule.mendableSnowGolem;

import me.ivan.ivancarpetaddition.IvanCarpetAdditionSettings;
import me.ivan.ivancarpetaddition.helpers.rule.mendableGolem.MendableGolemHelper;
import net.minecraft.entity.passive.SnowGolemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

//#if MC >= 11600
//$$ import net.minecraft.entity.Entity;
//$$ import net.minecraft.entity.EntityType;
//$$ import net.minecraft.util.ActionResult;
//$$ import net.minecraft.world.World;
//#endif

@Mixin(SnowGolemEntity.class)
//#if MC >= 11600
//$$ public abstract class SnowGolemEntityMixin extends Entity
//#else
public abstract class SnowGolemEntityMixin
//#endif
{
    //#if MC >= 11600
    //$$ public SnowGolemEntityMixin(EntityType<?> type, World world) {
    //$$     super(type, world);
    //$$ }
    //#endif

    @Inject(method = "interactMob", at = @At("HEAD"), cancellable = true)
    private void mendSnowGolem(
            PlayerEntity player,
            Hand hand,
            //#if MC >= 11600
            //$$ CallbackInfoReturnable<ActionResult> cir
            //#else
            CallbackInfoReturnable<Boolean> cir
            //#endif
    ) {
        ItemStack itemStack = player.getStackInHand(hand);
        if (itemStack.getItem() == Items.SNOWBALL && IvanCarpetAdditionSettings.mendableSnowGolem) {
            boolean success = MendableGolemHelper.mendGolemFromStack(
                    (SnowGolemEntity) (Object) this,
                    1.0F,
                    SoundEvents.BLOCK_SNOW_PLACE,
                    player,
                    itemStack
            );
            cir.setReturnValue(
                    //#if MC >= 12103
                    //$$ this.getEntityWorld().isClient ? ActionResult.SUCCESS : ActionResult.SUCCESS_SERVER
                    //#elseif MC >= 11600
                    //$$ success ? ActionResult.success(this.getEntityWorld().isClient) : ActionResult.PASS
                    //#else
                    success
                    //#endif
            );
        }
    }
}
