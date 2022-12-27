package me.ivan.ivancarpetaddition.mixins.rule.mendableSnowGolem;

import me.ivan.ivancarpetaddition.IvanCarpetAdditionSettings;
import me.ivan.ivancarpetaddition.helpers.rule.mendableGolem.MendableGolemHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
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
public abstract class SnowGolemEntityMixin extends Entity {
    public SnowGolemEntityMixin(EntityType<?> type, World world) {
        super(type, world);
    }

    @Inject(method = "interactMob", at = @At("HEAD"), cancellable = true)
    private void mendSnowGolem(PlayerEntity player, Hand hand, CallbackInfoReturnable<ActionResult> cir) {
        ItemStack itemStack = player.getStackInHand(hand);
        if (itemStack.getItem() == Items.SNOWBALL && IvanCarpetAdditionSettings.mendableSnowGolem) {
            cir.setReturnValue(MendableGolemHelper.mendGolemFromStack(
                    (SnowGolemEntity) (Object) this,
                    1.0F,
                    SoundEvents.BLOCK_SNOW_PLACE,
                    player,
                    itemStack
            ) ? ActionResult.success(this.world.isClient) : ActionResult.PASS);
        }
    }
}
