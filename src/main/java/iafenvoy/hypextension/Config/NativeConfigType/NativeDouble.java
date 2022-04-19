package iafenvoy.hypextension.Config.NativeConfigType;

import fi.dy.masa.malilib.config.options.ConfigDouble;
import iafenvoy.hypextension.HypClient;
import net.minecraft.text.TranslatableText;

public class NativeDouble extends ConfigDouble {
  private static final String MOD_ID = HypClient.MOD_ID;

  public NativeDouble(String keyname, double defaultValue, double minValue, double maxValue, boolean useSlider) {
    super(new TranslatableText("config." + MOD_ID + "." + keyname).getString(), defaultValue, minValue, maxValue,
        useSlider, new TranslatableText("config." + MOD_ID + "." + keyname + ".help").getString());
  }
}
