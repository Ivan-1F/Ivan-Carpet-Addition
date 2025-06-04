package me.ivan.ivancarpetaddition.mixins.rule.magmaBlockDamageItem;

import me.fallenbreath.conditionalmixin.api.annotation.Condition;
import me.fallenbreath.conditionalmixin.api.annotation.Restriction;
import me.ivan.ivancarpetaddition.IvanCarpetAdditionSettings;
import me.ivan.ivancarpetaddition.utils.ModIds;
import net.minecraft.block.MagmaBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

//#if MC >= 11700
//$$ import net.minecraft.block.BlockState;
//#endif

//#if MC >= 12103
//$$ import net.minecraft.server.world.ServerWorld;
//#endif

@Restriction(require = @Condition(value = ModIds.minecraft, versionPredicates = ">=1.15"))
@Mixin(MagmaBlock.class)
public class MagmaBlockMixin {
    @Inject(method = "onSteppedOn", at = @At("HEAD"))
    public void damageItems(
            World world, BlockPos pos,
            //#if MC >= 11700
            //$$ BlockState state,
            //#endif
            Entity entity, CallbackInfo ci
    ) {
        if (entity instanceof ItemEntity && IvanCarpetAdditionSettings.magmaBlockDamageItem) {
            // prevent damage(1.21.4) from being remapped into clientDamage(1.21.5)
            //#if MC >= 12105
            //$$ entity.damage(
            //#else
            entity.damage(
            //#endif
                    //#if MC >= 12103
                    //$$ (ServerWorld) world,
                    //#endif
                    //#if MC >= 11904
                    //$$ world.getDamageSources().hotFloor(),
                    //#else
                    DamageSource.HOT_FLOOR,
                    //#endif
                    1.0F
            );
        }
    }
}
