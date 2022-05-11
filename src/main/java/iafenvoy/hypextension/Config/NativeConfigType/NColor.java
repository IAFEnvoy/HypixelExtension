package iafenvoy.hypextension.Config.NativeConfigType;

import fi.dy.masa.malilib.config.options.ConfigColor;
import iafenvoy.hypextension.HypClient;
import iafenvoy.hypextension.Config.SettingGUI;
import net.minecraft.text.TranslatableText;

public class NColor extends ConfigColor {
  private static final String MOD_ID = HypClient.MOD_ID;

  public NColor(String keyname) {
    this(keyname, "#FF000000");
  }

  public NColor(String keyname, String defaultValue) {
    super(new TranslatableText("config." + MOD_ID + "." + keyname).getString(), defaultValue,
        new TranslatableText("config." + MOD_ID + "." + keyname + ".help").getString());
    SettingGUI.configOptions.add(this);
  }

}
