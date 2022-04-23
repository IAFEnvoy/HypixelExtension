package iafenvoy.hypextension.Config.NativeConfigType;

import fi.dy.masa.malilib.config.options.ConfigBoolean;
import iafenvoy.hypextension.HypClient;
import iafenvoy.hypextension.Config.ConfigGUI.Category;
import net.minecraft.text.TranslatableText;

public class NBoolean extends ConfigBoolean {
  private static final String MOD_ID = HypClient.MOD_ID;

  public NBoolean(String keyname, Category category) {
    super(new TranslatableText("config." + MOD_ID + "." + keyname).getString(), false,
        new TranslatableText("config." + MOD_ID + "." + keyname + ".help").getString());
    category.add(this);
  }
}
