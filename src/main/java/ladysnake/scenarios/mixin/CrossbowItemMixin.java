package ladysnake.scenarios.mixin;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.CrossbowItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.RangedWeaponItem;
import net.minecraft.item.Vanishable;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static net.minecraft.item.CrossbowItem.isCharged;

@Mixin(CrossbowItem.class)
public abstract class CrossbowItemMixin extends RangedWeaponItem implements Vanishable {
    public CrossbowItemMixin(Settings settings) {
        super(settings);
    }

    // heavy machine crossbow
    @Inject(at = @At(value = "RETURN"), method = "use", cancellable = true)
    public void use(World world, PlayerEntity user, Hand hand, CallbackInfoReturnable<TypedActionResult<ItemStack>> ci) {
        ItemStack itemStack = user.getStackInHand(hand);

        if (!user.getArrowType(itemStack).isEmpty()) {
            if (!isCharged(itemStack)) {
                onStoppedUsing(itemStack, world, user, 0);
            }
        }
    }
}
