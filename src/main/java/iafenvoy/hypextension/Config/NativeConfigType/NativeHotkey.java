package iafenvoy.hypextension.Config.NativeConfigType;

import fi.dy.masa.malilib.config.options.ConfigHotkey;
import fi.dy.masa.malilib.hotkeys.KeybindSettings;
import iafenvoy.hypextension.HypClient;
import net.minecraft.text.TranslatableText;

public class NativeHotkey extends ConfigHotkey {
  private static final String MOD_ID = HypClient.MOD_ID;

  public NativeHotkey(String keyname, String defaultHotkey) {
    super(new TranslatableText("config." + MOD_ID + "." + keyname).getString(), defaultHotkey,
        KeybindSettings.DEFAULT, new TranslatableText("config." + MOD_ID + "." + keyname + ".help").getString());
  }
}
