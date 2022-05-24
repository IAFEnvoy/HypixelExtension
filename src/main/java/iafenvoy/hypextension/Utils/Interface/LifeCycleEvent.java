package iafenvoy.hypextension.Utils.Interface;

public interface LifeCycleEvent extends Event {
  public int getPeriod();

  public void execute();
}
