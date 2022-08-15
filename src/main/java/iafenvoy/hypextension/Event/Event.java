package iafenvoy.hypextension.Event;

public interface Event {
    boolean shouldExecute();

    interface ChatReceiveEvent extends Event {
        void execute(String message);
    }

    interface LifeCycleEvent extends Event {
        void execute();
    }
}
