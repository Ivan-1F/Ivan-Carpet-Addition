package me.ivan.ivancarpetaddition.mixins.rule.mendableSnowGolem;

import me.ivan.ivancarpetaddition.IvanCarpetAdditionSettings;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.GolemEntity;
import net.minecraft.entity.passive.IronGolemEntity;
import net.minecraft.entity.passive.SnowGolemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(SnowGolemEntity.class)
public class SnowGolemEntityMixin extends GolemEntity {
    protected SnowGolemEntityMixin(EntityType<? extends GolemEntity> type, World world) {
        super(type, world);
    }

    @Inject(method = "interactMob", at = @At("HEAD"), cancellable = true)
    private void interactMob(PlayerEntity player, Hand hand, CallbackInfoReturnable<ActionResult> cir) {
        ItemStack itemStack = player.getStackInHand(hand);
        if (itemStack.getItem() == Items.SNOWBALL && IvanCarpetAdditionSettings.mendableSnowGolem) {
            float f = this.getHealth();
            this.heal(1.0F);
            if (this.getHealth() == f) {
                cir.setReturnValue(ActionResult.PASS);
            } else {
                float g = 1.0F + (this.random.nextFloat() - this.random.nextFloat()) * 0.2F;
                this.playSound(SoundEvents.BLOCK_SNOW_PLACE, 1.0F, g);
                if (!player.getAbilities().creativeMode) {
                    itemStack.decrement(1);
                }
                cir.setReturnValue(ActionResult.SUCCESS);
            }
            cir.cancel();
        }
        if (!IvanCarpetAdditionSettings.mendableIronGolem) {
            cir.cancel();
        }
    }


}
