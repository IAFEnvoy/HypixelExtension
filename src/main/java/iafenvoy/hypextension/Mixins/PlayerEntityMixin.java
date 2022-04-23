package iafenvoy.hypextension.Mixins;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import iafenvoy.hypextension.Config.Configs;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin extends LivingEntity {
  protected PlayerEntityMixin(EntityType<? extends LivingEntity> entityType, World world) {
    super(entityType, world);
  }

  @Inject(method = "method_30263", at = @At("HEAD"), cancellable = true)
  private void restore_1_15_2_sneaking(CallbackInfoReturnable<Boolean> cir) {
    if (Configs.INSTANCE.sneak_1_15_2.getBooleanValue())
      cir.setReturnValue(this.onGround);
  }
}
