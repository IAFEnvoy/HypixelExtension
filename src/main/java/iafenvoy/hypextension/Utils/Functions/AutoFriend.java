package iafenvoy.hypextension.Utils.Functions;

import iafenvoy.hypextension.Utils.ClientUtil;

public class AutoFriend {
  public static final String lineString = "[ACCEPT] - [DENY] - [IGNORE]";

  public static void checkMessage(String message) {
    if (message.contains(lineString)) {
      String[] s = message.split("\\n");
      String playername = s[1].replace("Friend request from ", "");
      ClientUtil.sendMessage("/friend accept " + playername, true);
    }
  }
}
