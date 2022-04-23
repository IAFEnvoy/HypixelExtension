package iafenvoy.hypextension.Config.NativeConfigType;

import fi.dy.masa.malilib.config.options.ConfigHotkey;
import fi.dy.masa.malilib.hotkeys.KeybindSettings;
import iafenvoy.hypextension.HypClient;
import iafenvoy.hypextension.Config.ConfigGUI.Category;
import net.minecraft.text.TranslatableText;

public class NHotkey extends ConfigHotkey {
  private static final String MOD_ID = HypClient.MOD_ID;

  public NHotkey(String keyname, Category category) {
    this(keyname, "", category);
  }

  public NHotkey(String keyname, String defaultHotkey, Category category) {
    super(new TranslatableText("config." + MOD_ID + "." + keyname).getString(), defaultHotkey,
        KeybindSettings.DEFAULT, new TranslatableText("config." + MOD_ID + "." + keyname + ".help").getString());
    category.add(this);
  }
}
