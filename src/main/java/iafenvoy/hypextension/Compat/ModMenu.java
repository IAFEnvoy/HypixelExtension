package iafenvoy.hypextension.Compat;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;

import iafenvoy.hypextension.Config.GUI.OptionGUI;

public class ModMenu implements ModMenuApi {
  @Override
  public ConfigScreenFactory<?> getModConfigScreenFactory() {
    return (screen) -> {
      return new OptionGUI();
    };
  }
}
