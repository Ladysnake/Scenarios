package ladysnake.scenarios.mixin;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.ArrowItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.potion.PotionUtil;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ArrowItem.class)
public class ArrowItemMixin {
    // arrowmatized
    @Inject(at = @At(value = "RETURN"), method = "createArrow", cancellable = true)
    public void createArrow(World world, ItemStack stack, LivingEntity shooter, CallbackInfoReturnable<PersistentProjectileEntity> ci) {
        ArrowEntity arrow = (ArrowEntity) ci.getReturnValue();
        if (stack.getItem() == Items.ARROW) {
            ItemStack tippedStack = new ItemStack(Items.TIPPED_ARROW, stack.getCount());
            PotionUtil.setPotion(tippedStack, Registry.POTION.getRandom(world.random));

            ArrowEntity arrowEntity = new ArrowEntity(world, shooter);
            arrowEntity.initFromStack(tippedStack);
            ci.setReturnValue(arrowEntity);
        }
    }
}
