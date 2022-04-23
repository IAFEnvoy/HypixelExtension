package iafenvoy.hypextension.Config.NativeConfigType;

import fi.dy.masa.malilib.config.options.ConfigString;
import iafenvoy.hypextension.HypClient;
import iafenvoy.hypextension.Config.ConfigGUI.Category;
import net.minecraft.text.TranslatableText;

public class NString extends ConfigString {
  private static final String MOD_ID = HypClient.MOD_ID;

  public NString(String keyname, Category category) {
    this(keyname, "", category);
  }

  public NString(String keyname, String defaultValue, Category category) {
    super(new TranslatableText("config." + MOD_ID + "." + keyname).getString(), defaultValue,
        new TranslatableText("config." + MOD_ID + "." + keyname + ".help").getString());
    category.add(this);
  }
}
