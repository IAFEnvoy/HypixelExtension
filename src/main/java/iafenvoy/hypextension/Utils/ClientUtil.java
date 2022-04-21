package iafenvoy.hypextension.Utils;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.entity.Entity;
import net.minecraft.text.Text;

public class ClientUtil {
  private static final MinecraftClient client = MinecraftClient.getInstance();

  public static void sendMessage(String message, boolean sendToServer) {
    if (sendToServer)
      client.player.sendChatMessage(message);
    else
      client.player.sendMessage(Text.of(message), true);
  }

  public static Text getPlayerName() {
    return client.player.getName();
  }

  public static float getTextBackgroundOpacity(float opacity) {
    return client.options.getTextBackgroundOpacity(opacity);
  }

  public static EntityRenderDispatcher getEntityDispatcher() {
    return MinecraftClient.getInstance().getEntityRenderDispatcher();
  }

  public static Entity getCameraEntity() {
    return MinecraftClient.getInstance().getCameraEntity();
  }
}
