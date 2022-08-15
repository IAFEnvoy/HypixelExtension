package iafenvoy.hypextension.Event;

import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

public class LifeCycle {
    private static final HashMap<String, Event.LifeCycleEvent> events = new HashMap<>();

    public static void register(String id, Event.LifeCycleEvent event) {
        events.put(id, event);
    }

    public static void startLifeCycle() {
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                for (Event.LifeCycleEvent event : events.values())
                    if (event.shouldExecute())
                        try {
                            event.execute();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
            }
        };
        new Timer("HypE Life Cycle").schedule(task, 0, 60000);
    }
}
