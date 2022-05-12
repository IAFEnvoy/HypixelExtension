package iafenvoy.hypextension.Mixins.Render;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import iafenvoy.hypextension.Config.Configs;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.item.HeldItemRenderer;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ShieldItem;

@Mixin(HeldItemRenderer.class)
public class HeldItemRendererMixin {
  @Inject(method = "renderItem", at = @At("HEAD"), cancellable = true)
  private void render(LivingEntity entity, ItemStack stack, ModelTransformation.Mode renderMode, boolean leftHanded,
      MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, CallbackInfo info) {
    if (Configs.INSTANCE.sword_1_8_9.getBooleanValue() && stack.getItem() instanceof ShieldItem && leftHanded)
      info.cancel();
  }
}
