package ladysnake.scenarios.mixin;

import com.google.common.collect.Lists;
import net.minecraft.block.OreBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.RecipeType;
import net.minecraft.recipe.SmeltingRecipe;
import net.minecraft.recipe.SmokingRecipe;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;
import java.util.Optional;

// thousand degree knife
@Mixin(Entity.class)
public class EntityMixin {
    @Inject(at = @At(value = "RETURN"), method = "dropStack(Lnet/minecraft/item/ItemStack;F)Lnet/minecraft/entity/ItemEntity;")
    public void dropStack(ItemStack stack, float yOffset, CallbackInfoReturnable<ItemEntity> ci) {
        Optional<SmokingRecipe> optional = ci.getReturnValue().getEntityWorld().getRecipeManager().getFirstMatch(RecipeType.SMOKING, new SimpleInventory(new ItemStack[]{stack}), ci.getReturnValue().getEntityWorld());
        if (optional.isPresent()) {
            ItemStack itemStack1 = (optional.get()).getOutput();
            if (!itemStack1.isEmpty()) {
                ItemStack smeltedStack = itemStack1.copy();
                smeltedStack.setCount(stack.getCount());
                ci.getReturnValue().setStack(smeltedStack);
            }
        }
    }
}
