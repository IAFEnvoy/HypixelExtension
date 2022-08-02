package iafenvoy.hypextension.Mixins.Render;

import iafenvoy.hypextension.Data.Config.Configs;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.entity.PlayerEntityRenderer;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.render.entity.model.PlayerEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ShieldItem;
import net.minecraft.util.Hand;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerEntityRenderer.class)
public abstract class PlayerEntityRendererMixin
    extends LivingEntityRenderer<AbstractClientPlayerEntity, PlayerEntityModel<AbstractClientPlayerEntity>> {
  private final MinecraftClient client = MinecraftClient.getInstance();

  public PlayerEntityRendererMixin(EntityRenderDispatcher dispatcher,
      PlayerEntityModel<AbstractClientPlayerEntity> model, float shadowRadius) {
    super(dispatcher, model, shadowRadius);
  }

  @Inject(method = "render", at = @At("RETURN"))
  public void addOwnNameTag(AbstractClientPlayerEntity entity, float f, float g,
      MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, CallbackInfo ci) {
    if (Configs.INSTANCE.renderOwnNameTag.getBooleanValue())
      if (entity.getEntityName().equals(client.player.getEntityName()))
        super.renderLabelIfPresent(entity, entity.getDisplayName(), matrixStack, vertexConsumerProvider, i);
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
