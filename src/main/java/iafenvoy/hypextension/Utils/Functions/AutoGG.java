package iafenvoy.hypextension.Utils.Functions;

import iafenvoy.hypextension.Hud.FastGameMenu.MiniGame;
import iafenvoy.hypextension.Utils.ClientUtil;

public class AutoGG {
  private static final String lineString = "▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬";
  private static String previousMessage = "";
  private static long prev = 0L;
  private static long coolDown = 0L;
  private static boolean shouldSend = false;
  private static int count = 0;

  public static void checkMessage(String message) {
    if (message.contains("The game starts in"))
      prev = System.currentTimeMillis();
    if (previousMessage.contains(lineString)) {
      if (previousMessage.split(lineString).length > 2 || shouldSend) {
        count = 0;
        shouldSend = false;
      } else {
        shouldSend = true;
        long now = System.currentTimeMillis();
        if (prev != 0L) {
          if (now - prev <= 1500L) {
            prev = 0L;
            previousMessage = message;
            return;
          }
          prev = 0L;
        }
        if (MiniGame.containGameName(message)) {
          if (now - coolDown >= 1500L)
            ClientUtil.sendMessage("gg", true);
          coolDown = now;
        }
      }
    }
    if (shouldSend)
      count++;
    if (count > 15) {
      count = 0;
      shouldSend = false;
    }
    previousMessage = message;
  }
}
