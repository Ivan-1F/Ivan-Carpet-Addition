package me.ivan.ivancarpetaddition.mixins.rule.mendableIronGolem;

import me.fallenbreath.conditionalmixin.api.annotation.Condition;
import me.fallenbreath.conditionalmixin.api.annotation.Restriction;
import me.ivan.ivancarpetaddition.IvanCarpetAdditionSettings;
import me.ivan.ivancarpetaddition.helpers.rule.mendableGolem.MendableGolemHelper;
import me.ivan.ivancarpetaddition.utils.ModIds;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.GolemEntity;
import net.minecraft.entity.passive.IronGolemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;

@Restriction(require = @Condition(value = ModIds.minecraft, versionPredicates = "<1.15"))
@Mixin(IronGolemEntity.class)
public class IronGolemEntityMixin extends GolemEntity {
    protected IronGolemEntityMixin(EntityType<? extends GolemEntity> type, World world) {
        super(type, world);
    }

    @Override
    protected boolean interactMob(PlayerEntity player, Hand hand) {
        ItemStack itemStack = player.getStackInHand(hand);
        if (IvanCarpetAdditionSettings.mendableIronGolem && itemStack.getItem() == Items.IRON_INGOT) {
            return MendableGolemHelper.mendGolemFromStack(
                    (IronGolemEntity) (Object) this,
                    25.0F,
                    SoundEvents.BLOCK_ANVIL_LAND,
                    player,
                    itemStack
            );
        }
        return false;
    }
}