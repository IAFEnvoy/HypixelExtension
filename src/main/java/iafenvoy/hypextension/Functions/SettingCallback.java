package iafenvoy.hypextension.Functions;

import fi.dy.masa.malilib.config.options.ConfigBoolean;
import fi.dy.masa.malilib.gui.GuiBase;
import fi.dy.masa.malilib.interfaces.IValueChangeCallback;
import fi.dy.masa.malilib.util.InfoUtils;
import iafenvoy.hypextension.Config.Configs.Options;
import iafenvoy.hypextension.Config.Configs.Settings;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.TranslatableText;

public class SettingCallback implements IValueChangeCallback<ConfigBoolean> {
  private static final MinecraftClient client = MinecraftClient.getInstance();

  @Override
  public void onValueChanged(ConfigBoolean configKey) {
    if (configKey.equals(Options.gammaOverride)) {
      client.options.gamma = configKey.getBooleanValue() ? Settings.gammaOverrideValue.getDoubleValue() : 1.0F;
      sendMessage("message.gammaoverride", configKey.getBooleanValue());
    }
  }

  public void sendMessage(String key, boolean value) {
    String pre = value ? GuiBase.TXT_GREEN + "ON" : GuiBase.TXT_RED + "OFF";
    String message = new TranslatableText(key).getString() + pre + GuiBase.TXT_RST;
    InfoUtils.printActionbarMessage(message);
  }
}
