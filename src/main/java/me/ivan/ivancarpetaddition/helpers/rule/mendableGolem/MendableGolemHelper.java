package me.ivan.ivancarpetaddition.helpers.rule.mendableGolem;

import net.minecraft.entity.passive.GolemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundEvent;

public class MendableGolemHelper {
    /**
     * Mend a golem entity will an ItemStack
     *
     * @param entity    the golem entity to mend
     * @param heal      the amount to mend
     * @param sound     the sound played after mended successfully
     * @param player    the player to mend the golem
     * @param itemStack the stack used to mend
     * @return if the golem is successfully mended
     */
    public static boolean mendGolemFromStack(
            GolemEntity entity,
            float heal,
            SoundEvent sound,
            PlayerEntity player,
            ItemStack itemStack
    ) {
        if (mendGolem(entity, heal, sound)) {
            if (!player.abilities.creativeMode) {
                itemStack.decrement(1);
            }
            return true;
        }
        return false;
    }

    /**
     * Stolen from {@link net.minecraft.entity.passive.IronGolemEntity}
     * Mend a golem entity
     *
     * @param entity the golem entity to mend
     * @param heal   the amount to mend
     * @param sound  the sound played after mended successfully
     * @return if the golem is successfully mended
     */
    public static boolean mendGolem(GolemEntity entity, float heal, SoundEvent sound) {
        float health = entity.getHealth();
        entity.heal(heal);
        if (entity.getHealth() == health) {
            return false;
        } else {
            float pitch = 1.0F + (entity.getRandom().nextFloat() - entity.getRandom().nextFloat()) * 0.2F;
            entity.playSound(sound, 1.0F, pitch);
            return true;
        }
    }
}
