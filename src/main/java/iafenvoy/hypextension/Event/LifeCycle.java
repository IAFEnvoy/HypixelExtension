package iafenvoy.hypextension.Event;

import iafenvoy.hypextension.Utils.Interface.LifeCycleEvent;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

public class LifeCycle {
  private static HashMap<String, LifeCycleEvent> events = new HashMap<>();
  private static HashMap<String, Integer> time = new HashMap<>();

  public static void registry(String id, LifeCycleEvent event) {
    events.put(id, event);
  }

  public static void startLifeCycle() {
    TimerTask task = new TimerTask() {
      @Override
      public void run() {
        for (Integer i : time.values())
          i++;
        for (String id : events.keySet())
          if (events.get(id).shouldExecute() && time.get(id).equals(events.get(id).getPeriod())) {
            events.get(id).execute();
            time.put(id, 0);
          }
      }
    };
    new Timer("HypE Life Cycle").schedule(task, 0, 1000);
  };
}
