package iafenvoy.hypextension.Functions;

import fi.dy.masa.malilib.config.options.ConfigBoolean;
import fi.dy.masa.malilib.interfaces.IValueChangeCallback;
import iafenvoy.hypextension.Config.Configs.Settings;
import net.minecraft.client.MinecraftClient;

public class SettingCallback implements IValueChangeCallback<ConfigBoolean> {
  private static final MinecraftClient client = MinecraftClient.getInstance();

  @Override
  public void onValueChanged(ConfigBoolean configKey) {
    client.options.gamma = configKey.getBooleanValue() ? Settings.gammaOverrideValue.getDoubleValue() : 1.0F;
  }
}
