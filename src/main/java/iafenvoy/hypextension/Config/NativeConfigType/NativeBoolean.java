package iafenvoy.hypextension.Config.NativeConfigType;

import fi.dy.masa.malilib.config.options.ConfigBoolean;
import iafenvoy.hypextension.HypClient;
import net.minecraft.text.TranslatableText;

public class NativeBoolean extends ConfigBoolean {
  private static final String MOD_ID = HypClient.MOD_ID;

  public NativeBoolean(String keyname, boolean defaultValue) {
    super(new TranslatableText("config." + MOD_ID + "." + keyname).getString(), defaultValue,
        new TranslatableText("config." + MOD_ID + "." + keyname + ".help").getString());
  }
}
