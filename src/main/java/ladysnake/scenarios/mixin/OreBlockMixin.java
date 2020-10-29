package ladysnake.scenarios.mixin;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.OreBlock;
import net.minecraft.util.math.MathHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Random;

@Mixin(OreBlock.class)
public class OreBlockMixin extends Block {
    public OreBlockMixin(Settings settings) {
        super(settings);
    }

    // metal masters xp drop
    @Inject(at = @At(value = "RETURN"), method = "getExperienceWhenMined", cancellable = true)
    protected void getExperienceWhenMined(Random random, CallbackInfoReturnable<Integer> ci) {
        if (this == Blocks.IRON_ORE) {
            ci.setReturnValue(MathHelper.nextInt(random, 1, 3));
        } else if (this == Blocks.GOLD_ORE) {
            ci.setReturnValue(MathHelper.nextInt(random, 2, 5));
        }
    }

}
