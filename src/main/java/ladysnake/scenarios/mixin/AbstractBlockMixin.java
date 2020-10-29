package ladysnake.scenarios.mixin;

import com.google.common.collect.Lists;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.OreBlock;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.context.LootContext;
import net.minecraft.recipe.RecipeType;
import net.minecraft.recipe.SmeltingRecipe;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;
import java.util.Optional;

// metal masters ingot smelting
@Mixin(AbstractBlock.class)
public abstract class AbstractBlockMixin {
    @Inject(at = @At(value = "RETURN"), method = "getDroppedStacks", cancellable = true)
    public void getDroppedStacks(BlockState state, LootContext.Builder builder, CallbackInfoReturnable<List<ItemStack>> ci) {
        if (state.getBlock() instanceof OreBlock) {
            List<ItemStack> smeltedReturn = Lists.newArrayList();

            ci.getReturnValue().forEach(itemStack -> {
                Optional<SmeltingRecipe> optional = builder.getWorld().getRecipeManager().getFirstMatch(RecipeType.SMELTING, new SimpleInventory(new ItemStack[]{itemStack}), builder.getWorld());
                if (optional.isPresent()) {
                    ItemStack itemStack1 = ((SmeltingRecipe) optional.get()).getOutput();
                    if (!itemStack1.isEmpty()) {
                        ItemStack smeltedStack = itemStack1.copy();
                        smeltedStack.setCount(itemStack.getCount());
                        smeltedReturn.add(smeltedStack);
                    } else {
                        smeltedReturn.add(itemStack);
                    }
                } else {
                    smeltedReturn.add(itemStack);
                }
            });

            ci.setReturnValue(smeltedReturn);
        }
    }
}