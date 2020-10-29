package ladysnake.scenarios.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.vehicle.BoatEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity {
    @Shadow public abstract ItemStack getEquippedStack(EquipmentSlot slot);

    @Shadow public abstract void sendEquipmentBreakStatus(EquipmentSlot slot);

    public LivingEntityMixin(EntityType<?> type, World world) {
        super(type, world);
    }

    // bombers
    @Inject(at = @At(value = "RETURN"), method = "onDeath", cancellable = true)
    public void onDeath(DamageSource source, CallbackInfo ci) {
        if (source.getAttacker() instanceof PlayerEntity && this.random.nextInt(5) == 0) {
            this.dropStack(new ItemStack(Items.TNT, 4 + random.nextInt(3)));
        }
    }

    // global warming
    @Inject(at = @At(value = "RETURN"), method = "tick", cancellable = true)
    public void tick(CallbackInfo ci) {
        boolean affectedByLight = false;

        if (this.world.isDay() && !this.world.isClient) {
            float f = this.getBrightnessAtEyes();
            BlockPos blockPos = this.getVehicle() instanceof BoatEntity ? (new BlockPos(this.getX(), (double) Math.round(this.getY()), this.getZ())).up() : new BlockPos(this.getX(), (double) Math.round(this.getY()), this.getZ());
            if (f > 0.5F && this.random.nextFloat() * 30.0F < (f - 0.4F) * 2.0F && this.world.isSkyVisible(blockPos)) {
                affectedByLight = true;
            }
        }

        if (affectedByLight) {
            ItemStack itemStack = this.getEquippedStack(EquipmentSlot.HEAD);
            if (itemStack != null && !itemStack.isEmpty()) {
                if (itemStack.isDamageable()) {
                    itemStack.setDamage(itemStack.getDamage() + this.random.nextInt(2));
                    if (itemStack.getDamage() >= itemStack.getMaxDamage()) {
                        this.sendEquipmentBreakStatus(EquipmentSlot.HEAD);
                        this.equipStack(EquipmentSlot.HEAD, ItemStack.EMPTY);
                    }
                }

                affectedByLight = false;
            }

            if (affectedByLight) {
                this.setOnFireFor(8);
            }
        }
    }
}
