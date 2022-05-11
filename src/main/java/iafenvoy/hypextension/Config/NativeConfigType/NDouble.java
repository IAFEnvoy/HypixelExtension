package iafenvoy.hypextension.Config.NativeConfigType;

import fi.dy.masa.malilib.config.options.ConfigDouble;
import iafenvoy.hypextension.HypClient;
import iafenvoy.hypextension.Config.SettingGUI;
import net.minecraft.text.TranslatableText;

public class NDouble extends ConfigDouble {
  private static final String MOD_ID = HypClient.MOD_ID;

  public NDouble(String keyname) {
    this(keyname, 0, Double.MIN_VALUE, Double.MAX_VALUE, false);
  }

  public NDouble(String keyname, double defaultValue, double minValue, double maxValue, boolean useSlider) {
    super(new TranslatableText("config." + MOD_ID + "." + keyname).getString(), defaultValue, minValue, maxValue,
        useSlider, new TranslatableText("config." + MOD_ID + "." + keyname + ".help").getString());
    SettingGUI.configOptions.add(this);
  }
}
