package iafenvoy.hypextension.Functions;

import fi.dy.masa.malilib.config.options.ConfigBoolean;
import fi.dy.masa.malilib.interfaces.IValueChangeCallback;
import iafenvoy.hypextension.Config.Configs;
import net.minecraft.client.MinecraftClient;

public class SettingCallback implements IValueChangeCallback<ConfigBoolean> {
  private static final MinecraftClient client = MinecraftClient.getInstance();

  @Override
  public void onValueChanged(ConfigBoolean configKey) {
    client.options.gamma = configKey.getBooleanValue() ? Configs.INSTANCE.gammaValue.getDoubleValue() : 1.0F;
  }
}
