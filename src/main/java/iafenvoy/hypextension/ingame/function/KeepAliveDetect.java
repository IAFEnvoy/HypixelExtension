package iafenvoy.hypextension.ingame.function;

import iafenvoy.hypextension.config.Configs;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.TranslatableText;

import java.util.Timer;
import java.util.TimerTask;

public class KeepAliveDetect {
    private static boolean hasPacket = false;

    private static boolean isConnected() {
        if (MinecraftClient.getInstance().getNetworkHandler() == null) return false;
        else if (MinecraftClient.getInstance().getNetworkHandler().getConnection() == null) return false;
        return MinecraftClient.getInstance().getNetworkHandler().getConnection().isOpen();
    }

    public static void onKeepAlivePacket() {
        hasPacket = true;
    }

    public static void start() {
        new Timer("Keep Alive Detect").schedule(new TimerTask() {
            @Override
            public void run() {
                if (isConnected() && Configs.INSTANCE.lagDetect.getBooleanValue()) {
                    if (!hasPacket && MinecraftClient.getInstance().player != null)
                        MinecraftClient.getInstance().player.sendMessage(new TranslatableText("hypextension.hasLag"), true);
                    hasPacket = false;
                }
            }
        }, 0, 2 * 1000);
    }
}
