package iafenvoy.hypextension.Event;

import java.util.HashMap;

public class ChatReceive {
    private static final HashMap<String, Event.ChatReceiveEvent> events = new HashMap<>();

    public static void register(String id, Event.ChatReceiveEvent event) {
        events.put(id, event);
    }

    public static void onChatReceive(String message) {
        for (Event.ChatReceiveEvent event : events.values())
            if (event.shouldExecute())
                event.execute(message);
    }
}
