package iafenvoy.hypextension.Functions;

import java.util.Timer;
import java.util.TimerTask;

import iafenvoy.hypextension.Config.Configs.Options;
import net.minecraft.client.MinecraftClient;

public class AutoTip {
  private static final MinecraftClient client = MinecraftClient.getInstance();
  private static Timer timer = new Timer("Auto Tip");
  private static TimerTask task = new TimerTask() {
    public void run() {
      try {
        if (Options.autoTip.getBooleanValue())
          client.player.sendChatMessage("/tip all");
      } catch (Exception e) {
      }
    }
  };

  public static void start() {
    timer.schedule(task, 0, 60000);
  }
}
