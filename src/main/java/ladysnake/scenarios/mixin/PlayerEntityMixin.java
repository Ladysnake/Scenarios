package ladysnake.scenarios.mixin;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

// cat's eyes
@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin extends LivingEntity {
    protected PlayerEntityMixin(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(at = @At(value = "HEAD"), method = "tick")
    public void tick(CallbackInfo ci) {
        if (this.getStatusEffect(StatusEffects.NIGHT_VISION) == null || this.getStatusEffect(StatusEffects.NIGHT_VISION).getDuration() < 900000) {
            this.applyStatusEffect(new StatusEffectInstance(StatusEffects.NIGHT_VISION, 1000000, 1, true, false, false));
        }
    }
}
