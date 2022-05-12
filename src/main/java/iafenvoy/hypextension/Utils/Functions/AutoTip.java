package iafenvoy.hypextension.Utils.Functions;

import java.util.Timer;
import java.util.TimerTask;

import iafenvoy.hypextension.Config.Configs;
import iafenvoy.hypextension.Utils.ClientUtil;

public class AutoTip {
  private static Timer timer = new Timer("Auto Tip");
  private static TimerTask task = new TimerTask() {
    public void run() {
      try {
        if (Configs.INSTANCE.autoTip.getBooleanValue())
          ClientUtil.sendMessage("/tip all", true);
      } catch (Exception e) {
      }
    }
  };

  public static void start() {
    timer.schedule(task, 0, 60000);
  }
}
