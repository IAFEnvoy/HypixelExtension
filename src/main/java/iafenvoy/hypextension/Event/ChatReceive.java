package iafenvoy.hypextension.Event;

import iafenvoy.hypextension.Utils.Interface.ChatReceiveEvent;

import java.util.HashMap;

public class ChatReceive {
    private static final HashMap<String, ChatReceiveEvent> events = new HashMap<>();

    public static void register(String id, ChatReceiveEvent event) {
        events.put(id, event);
    }

    public static void onChatReceive(String message) {
        for (ChatReceiveEvent event : events.values())
            if (event.shouldExecute())
                event.execute(message);
    }
}
