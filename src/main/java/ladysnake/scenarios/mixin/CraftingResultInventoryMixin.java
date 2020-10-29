package ladysnake.scenarios.mixin;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.inventory.CraftingResultInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

// power tools
@Mixin(CraftingResultInventory.class)
public class CraftingResultInventoryMixin {
    @Inject(at = @At(value = "HEAD"), method = "setStack", cancellable = true)
    public void setStack(int slot, ItemStack stack, CallbackInfo ci) {
        if (stack.getItem() instanceof ToolItem && !(stack.getItem() instanceof SwordItem)) {
            stack.addEnchantment(Enchantments.EFFICIENCY, 5);
        }
    }
}
