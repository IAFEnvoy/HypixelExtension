package iafenvoy.hypextension.Config.NativeConfigType;

import fi.dy.masa.malilib.config.options.ConfigColor;
import iafenvoy.hypextension.HypClient;
import iafenvoy.hypextension.Config.ConfigGUI.Category;
import net.minecraft.text.TranslatableText;

public class NColor extends ConfigColor {
  private static final String MOD_ID = HypClient.MOD_ID;

  public NColor(String keyname, Category category) {
    this(keyname, "#FF000000", category);
  }

  public NColor(String keyname, String defaultValue, Category category) {
    super(new TranslatableText("config." + MOD_ID + "." + keyname).getString(), defaultValue,
        new TranslatableText("config." + MOD_ID + "." + keyname + ".help").getString());
    category.add(this);
  }

}
