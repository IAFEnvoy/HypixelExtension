package iafenvoy.hypextension.Utils.Interface;

public interface ChatReceiveEvent extends Event {
  public void execute(String message);
}
