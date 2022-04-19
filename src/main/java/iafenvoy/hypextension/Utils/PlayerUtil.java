package iafenvoy.hypextension.Utils;

import net.minecraft.client.MinecraftClient;

public class PlayerUtil {
  private static final MinecraftClient client = MinecraftClient.getInstance();

  public static void sendMessage(String message) {
    client.player.sendChatMessage(message);
  }
}
