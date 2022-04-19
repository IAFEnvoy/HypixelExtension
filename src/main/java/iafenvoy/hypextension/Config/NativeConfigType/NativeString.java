package iafenvoy.hypextension.Config.NativeConfigType;

import fi.dy.masa.malilib.config.options.ConfigString;
import iafenvoy.hypextension.HypClient;
import net.minecraft.text.TranslatableText;

public class NativeString extends ConfigString {
  private static final String MOD_ID = HypClient.MOD_ID;

  public NativeString(String keyname, String defaultValue) {
    super(new TranslatableText("config." + MOD_ID + "." + keyname).getString(), defaultValue,
        new TranslatableText("config." + MOD_ID + "." + keyname + ".help").getString());
  }
}
