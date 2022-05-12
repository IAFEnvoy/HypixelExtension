package iafenvoy.hypextension.Utils.Functions;

import iafenvoy.hypextension.Config.Configs;
import iafenvoy.hypextension.Utils.Interface.Callback;
import net.minecraft.client.MinecraftClient;

public class SettingCallback implements Callback {
  private static final MinecraftClient client = MinecraftClient.getInstance();

  @Override
  public void onValueChanged(boolean value) {
    new Thread(() -> {
      while (client == null)
        ;
      while (client.options == null)
        ;
      client.options.gamma = value ? Configs.INSTANCE.gammaValue.getDoubleValue() : 1.0F;
    }).start();
  }
}
