package iafenvoy.hypextension.utils;

import net.minecraft.client.MinecraftClient;
import net.minecraft.text.LiteralText;

public class ClientUtil {
    private static final MinecraftClient client = MinecraftClient.getInstance();

    public static void sendMessage(String message, boolean sendToServer) {
        if (client.player == null) return;
        if (sendToServer)
            client.player.sendChatMessage(message);
        else
            client.player.sendMessage(new LiteralText(message), false);
    }
}
