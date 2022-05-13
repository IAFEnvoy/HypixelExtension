package iafenvoy.hypextension.Mixins.GUI;

import com.mojang.blaze3d.systems.RenderSystem;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import iafenvoy.hypextension.Config.Configs;
import iafenvoy.hypextension.Utils.ColorUtil;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.hud.PlayerListHud;
import net.minecraft.client.network.PlayerListEntry;
import net.minecraft.client.util.math.MatrixStack;

@Mixin(PlayerListHud.class)
public class PlayerListHudMixin {
  @Shadow
  @Final
  private MinecraftClient client;

  private static final int PING_TEXT_RENDER_OFFSET = -13;
  private static final int PING_BARS_WIDTH = 19;

  @ModifyConstant(method = "render", constant = @Constant(intValue = 13))
  private int change13(int value) {
    return Configs.INSTANCE.betterPingShow.getBooleanValue() ? value + PING_BARS_WIDTH * 2 : value;
  }

  @Inject(method = "renderLatencyIcon", at = @At("HEAD"))
  private void addPingRender(MatrixStack matrices, int x, int offsetX, int y, PlayerListEntry player,
      CallbackInfo info) {
    if (Configs.INSTANCE.betterPingShow.getBooleanValue()) {
      TextRenderer textRenderer = client.textRenderer;
      String pingString = String.format("%dms", player.getLatency());
      int pingStringWidth = textRenderer.getWidth(pingString);
      int textX = x + offsetX - pingStringWidth + PING_TEXT_RENDER_OFFSET;
      int pingTextColor = ColorUtil.getColor(player.getLatency());
      textRenderer.drawWithShadow(matrices, pingString, (float) textX, (float) y, pingTextColor);
    } else
      // If we don't render ping bars, we need to reset the render system color so the
      // rest of the player list renders properly
      RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
  }
}
