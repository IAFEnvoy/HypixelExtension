package iafenvoy.hypextension.Config.NativeConfigType;

import fi.dy.masa.malilib.config.options.ConfigInteger;
import iafenvoy.hypextension.HypClient;
import net.minecraft.text.TranslatableText;

public class NativeInt extends ConfigInteger {
  private static final String MOD_ID = HypClient.MOD_ID;

  public NativeInt(String keyname, int defaultValue, int minValue, int maxValue, boolean useSlider) {
    super(new TranslatableText("config." + MOD_ID + "." + keyname).getString(), defaultValue, minValue, maxValue,
        useSlider, new TranslatableText("config." + MOD_ID + "." + keyname + ".help").getString());
  }
}
