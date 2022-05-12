package iafenvoy.hypextension.Mixins.Render;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import iafenvoy.hypextension.Config.Configs;
import iafenvoy.hypextension.Render.PlayerTagRenderer;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.entity.PlayerEntityRenderer;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.render.entity.model.PlayerEntityModel;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ShieldItem;
import net.minecraft.util.Hand;

@Mixin(PlayerEntityRenderer.class)
public abstract class PlayerEntityRendererMixin
    extends LivingEntityRenderer<AbstractClientPlayerEntity, PlayerEntityModel<AbstractClientPlayerEntity>> {
  public PlayerEntityRendererMixin(EntityRenderDispatcher dispatcher,
      PlayerEntityModel<AbstractClientPlayerEntity> model, float shadowRadius) {
    super(dispatcher, model, shadowRadius);
  }

  @Inject(method = "<init>(Lnet/minecraft/client/render/entity/EntityRenderDispatcher;Z)V", at = @At(value = "INVOKE", shift = At.Shift.AFTER, target = "net/minecraft/client/render/entity/PlayerEntityRenderer.addFeature(Lnet/minecraft/client/render/entity/feature/FeatureRenderer;)Z", ordinal = 6))
  public void postConstructor(CallbackInfo callbackInfo) {
    this.addFeature(new PlayerTagRenderer<>(this));
  }

  @Inject(method = "getArmPose", at = @At("HEAD"), cancellable = true)
  private static void getArmPose(AbstractClientPlayerEntity abstractClientPlayerEntity, Hand hand,
      CallbackInfoReturnable<BipedEntityModel.ArmPose> cir) {
    if (Configs.INSTANCE.sword_1_8_9.getBooleanValue()) {
      ItemStack itemStack = abstractClientPlayerEntity.getStackInHand(hand);
      ItemStack itemStack2 = abstractClientPlayerEntity.getOffHandStack();
      if (itemStack2.getItem() instanceof ShieldItem && abstractClientPlayerEntity.isUsingItem())
        cir.setReturnValue(BipedEntityModel.ArmPose.BLOCK);
      if (itemStack.getItem() instanceof ShieldItem)
        cir.setReturnValue(BipedEntityModel.ArmPose.EMPTY);
    }
  }
}
