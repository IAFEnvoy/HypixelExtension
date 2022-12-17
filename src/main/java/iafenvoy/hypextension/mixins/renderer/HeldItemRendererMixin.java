package iafenvoy.hypextension.mixins.renderer;

import iafenvoy.hypextension.config.Configs;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.item.HeldItemRenderer;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ShieldItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(HeldItemRenderer.class)
public class HeldItemRendererMixin {
    @Inject(method = "renderItem*", at = @At("HEAD"), cancellable = true)
    private void render(LivingEntity entity, ItemStack stack, ModelTransformation.Mode mode, boolean leftHanded, MatrixStack matrices, VertexConsumerProvider provider, int light, CallbackInfo ci) {
        if (Configs.INSTANCE.sword_1_8_9.getBooleanValue() && stack.getItem() instanceof ShieldItem && leftHanded)
            ci.cancel();
    }
}
