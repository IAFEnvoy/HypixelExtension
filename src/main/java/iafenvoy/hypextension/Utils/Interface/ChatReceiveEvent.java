package iafenvoy.hypextension.Utils.Interface;

public interface ChatReceiveEvent extends Event {
    void execute(String message);
}
