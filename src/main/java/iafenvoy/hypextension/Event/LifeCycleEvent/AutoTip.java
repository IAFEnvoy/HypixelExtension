package iafenvoy.hypextension.Event.LifeCycleEvent;

import iafenvoy.hypextension.Data.Config.Configs;
import iafenvoy.hypextension.Utils.ClientUtil;
import iafenvoy.hypextension.Utils.Interface.LifeCycleEvent;

public class AutoTip implements LifeCycleEvent {
  public static final AutoTip INSTANCE = new AutoTip();

  @Override
  public boolean shouldExecute() {
    return Configs.INSTANCE.autoTip.getBooleanValue();
  }

  @Override
  public void execute() {
    ClientUtil.sendMessage("/tip all", true);
  }
}
