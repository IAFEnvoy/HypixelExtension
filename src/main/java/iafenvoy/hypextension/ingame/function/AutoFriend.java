package iafenvoy.hypextension.ingame.function;

import iafenvoy.hypextension.utils.ClientUtil;

public class AutoFriend {
    public static final String lineString = "[ACCEPT] - [DENY] - [IGNORE]";

    public static void execute(String message) {
        if (message.contains(lineString)) {
            String[] s = message.split("\\n");
            String playername = s[1].replace("Friend request from ", "");
            if (playername.contains("]"))
                playername = playername.split(" ")[1];
            ClientUtil.sendMessage("/friend accept " + playername, true);
        }
    }
}
