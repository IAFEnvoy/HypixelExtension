package iafenvoy.hypextension.Mixins;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import iafenvoy.hypextension.Config.Configs.Options;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.Perspective;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.world.World;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity {
  public LivingEntityMixin(EntityType<?> type, World worldIn) {
    super(type, worldIn);
  }

  @Inject(method = "tickStatusEffects", at = @At(value = "INVOKE", ordinal = 0, target = "Lnet/minecraft/entity/data/DataTracker;get(Lnet/minecraft/entity/data/TrackedData;)Ljava/lang/Object;"), cancellable = true)
  private void removeOwnPotionEffects(CallbackInfo ci) {
    MinecraftClient mc = MinecraftClient.getInstance();
    if (Options.removeOwnPotionEffects.getBooleanValue() && ((Object) this) == mc.player
        && mc.options.getPerspective() == Perspective.FIRST_PERSON) 
      ci.cancel();
  }
}