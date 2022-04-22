package iafenvoy.hypextension.Functions;

import iafenvoy.hypextension.Utils.ClientUtil;

public class AutoFriend {
  public static final String lineString = "[ACCEPT] - [DENY] - [IGNORE]";

  public static void checkMessage(String message) {
    if (message.contains(lineString))
      ClientUtil.sendMessage("/f accept", true);
  }
}
