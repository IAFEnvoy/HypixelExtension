package iafenvoy.hypextension.Utils;

import com.mojang.blaze3d.systems.RenderSystem;

import fi.dy.masa.malilib.util.GuiUtils;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;

public class RenderUtils {
  private static final MinecraftClient client = MinecraftClient.getInstance();

  public static void renderDirectionsCursor(float zLevel, float partialTicks) {
    RenderSystem.pushMatrix();

    int width = GuiUtils.getScaledWindowWidth();
    int height = GuiUtils.getScaledWindowHeight();
    RenderSystem.translated(width / 2, height / 2, zLevel);
    Entity entity = client.getCameraEntity();
    RenderSystem.rotatef(entity.prevPitch + (entity.pitch - entity.prevPitch) * partialTicks, -1.0F, 0.0F, 0.0F);
    RenderSystem.rotatef(entity.prevYaw + (entity.yaw - entity.prevYaw) * partialTicks, 0.0F, 1.0F, 0.0F);
    RenderSystem.scalef(-1.0F, -1.0F, -1.0F);
    RenderSystem.renderCrosshair(10);

    RenderSystem.popMatrix();
  }
}
