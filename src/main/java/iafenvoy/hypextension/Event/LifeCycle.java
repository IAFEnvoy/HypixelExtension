package iafenvoy.hypextension.Event;

import iafenvoy.hypextension.Utils.Interface.LifeCycleEvent;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

public class LifeCycle {
  private static final HashMap<String, LifeCycleEvent> events = new HashMap<>();

  public static void registry(String id, LifeCycleEvent event) {
    events.put(id, event);
  }

  public static void startLifeCycle() {
    TimerTask task = new TimerTask() {
      @Override
      public void run() {
        for (LifeCycleEvent event : events.values())
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
