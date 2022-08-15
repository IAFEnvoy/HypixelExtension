package iafenvoy.hypextension.Event.ChatReceiveEvent;

import iafenvoy.hypextension.Data.Config.Configs;
import iafenvoy.hypextension.Utils.ClientUtil;
import iafenvoy.hypextension.Event.Event;

public class AutoFriend implements Event.ChatReceiveEvent {
    public static final AutoFriend INSTANCE = new AutoFriend();
    public static final String lineString = "[ACCEPT] - [DENY] - [IGNORE]";

    @Override
    public boolean shouldExecute() {
        return Configs.INSTANCE.autoFriend.getBooleanValue();
    }

    @Override
    public void execute(String message) {
        if (message.contains(lineString)) {
            String[] s = message.split("\\n");
            String playername = s[1].replace("Friend request from ", "");
            if (playername.contains("]"))
                playername = playername.split(" ")[1];
            ClientUtil.sendMessage("/friend accept " + playername, true);
        }
    }
}
