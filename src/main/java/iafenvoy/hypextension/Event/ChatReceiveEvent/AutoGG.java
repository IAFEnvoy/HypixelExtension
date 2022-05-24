package iafenvoy.hypextension.Event.ChatReceiveEvent;

import iafenvoy.hypextension.Data.Config.Configs;
import iafenvoy.hypextension.Data.Game.MiniGame;
import iafenvoy.hypextension.Utils.ClientUtil;
import iafenvoy.hypextension.Utils.Interface.ChatReceiveEvent;

public class AutoGG implements ChatReceiveEvent {
  public static final AutoGG INSTANCE = new AutoGG();
  private static final String lineString = "▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬";
  private static String previousMessage = "";
  private static long prev = 0L;
  private static long coolDown = 0L;
  private static boolean shouldSend = false;
  private static int count = 0;

  @Override
  public boolean shouldExecute() {
    return Configs.INSTANCE.autoGG.getBooleanValue();
  }

  @Override
  public void execute(String message) {
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
