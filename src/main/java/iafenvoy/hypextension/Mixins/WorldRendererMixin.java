package iafenvoy.hypextension.Mixins;

//Code from "Time To Life" mod
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.Frustum;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.render.LightmapTextureManager;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.Matrix4f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import iafenvoy.hypextension.Config.Configs;
import iafenvoy.hypextension.Render.TNTCountdownRenderer;

@Mixin(WorldRenderer.class)
public class WorldRendererMixin {
  @Shadow
  private Frustum capturedFrustum;

  @Inject(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/WorldRenderer;checkEmpty(Lnet/minecraft/client/util/math/MatrixStack;)V", ordinal = 0))
  private void render(MatrixStack matrices, float tickDelta, long limitTime, boolean renderBlockOutline, Camera camera,
      GameRenderer gameRenderer, LightmapTextureManager lightmapTextureManager, Matrix4f projection, CallbackInfo ci) {
    if (Configs.tntCountDown.getBooleanValue())
      TNTCountdownRenderer.render(matrices, tickDelta, camera, projection, this.capturedFrustum);
  }
}
