package me.ivan.ivancarpetaddition.mixins.rule.functionalSpongeItem;

import me.ivan.ivancarpetaddition.IvanCarpetAdditionSettings;
import me.ivan.ivancarpetaddition.utils.EntityUtil;
import net.minecraft.block.Blocks;
import net.minecraft.entity.ItemEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemEntity.class)
public class ItemEntityMixin {
    @Inject(method = "tick", at = @At("HEAD"))
    private void tick(CallbackInfo ci) {
        if (!IvanCarpetAdditionSettings.functionalSpongeItem) return;

        ItemEntity self = (ItemEntity) (Object) this;
        if (self.getStack().getItem() == Items.SPONGE) {
            if (
                    ((SpongeBlockInvoker) Blocks.SPONGE)
                            .invokeAbsorbWater(
                                    EntityUtil.getEntityWorld(self),
                                    //#if MC >= 11904
                                    //$$ BlockPos.ofFloored
                                    //#else
                                    new BlockPos
                                    //#endif
                                    (
                                            //#if MC >= 11600
                                            //$$ self.getPos()
                                            //#else
                                            self
                                            //#endif
                                    )
                            )
            ) {
                ItemEntity wetSponge = new ItemEntity(
                        EntityUtil.getEntityWorld(self),
                        //#if MC >= 11500
                        self.getX(), self.getY(), self.getZ(),
                        //#else
                        //$$ self.x, self.y, self.z,
                        //#endif
                        new ItemStack(Items.WET_SPONGE, self.getStack().getCount())
                );
                wetSponge.setVelocity(self.getVelocity());
                EntityUtil.getEntityWorld(self).spawnEntity(wetSponge);
                //#if MC >= 11700
                //$$ self.discard();
                //#else
                self.remove();
                //#endif
            }
        }

        if (
                self.getStack().getItem() == Items.WET_SPONGE
                        //#if MC >= 11600
                        //$$ && EntityUtil.getEntityWorld(self).getDimension().isUltrawarm()
                        //#else
                        && EntityUtil.getEntityWorld(self).getDimension().doesWaterVaporize()
                        //#endif
                        && ((ItemEntityAccessor) self).getAge() > 60
        ) {
            ItemEntity sponge = new ItemEntity(
                    EntityUtil.getEntityWorld(self),
                    //#if MC >= 11500
                    self.getX(), self.getY(), self.getZ(),
                    //#else
                    //$$ self.x, self.y, self.z,
                    //#endif
                    new ItemStack(Items.SPONGE, self.getStack().getCount())
            );
            sponge.setVelocity(self.getVelocity());
            EntityUtil.getEntityWorld(self).spawnEntity(sponge);
            EntityUtil.getEntityWorld(self).playSound(
                    null,
                    //#if MC >= 11904
                    //$$ BlockPos.ofFloored
                    //#else
                    new BlockPos
                    //#endif
                    (
                            //#if MC >= 11600
                            //$$ self.getPos()
                            //#else
                            self
                            //#endif
                    ),
                    SoundEvents.BLOCK_FIRE_EXTINGUISH,
                    SoundCategory.BLOCKS,
                    1.0F,
                    (1.0F + EntityUtil.getEntityWorld(self).getRandom().nextFloat() * 0.2F) * 0.7F
            );
            //#if MC >= 11700
            //$$ self.discard();
            //#else
            self.remove();
            //#endif
        }
    }
}
