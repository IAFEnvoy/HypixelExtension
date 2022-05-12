package iafenvoy.hypextension.Config;

import fi.dy.masa.malilib.gui.GuiBase;
import fi.dy.masa.malilib.gui.button.ButtonGeneric;
import fi.dy.masa.malilib.util.StringUtils;
import iafenvoy.hypextension.Config.GUI.OptionGUI;
import iafenvoy.hypextension.Config.GUI.SettingGUI;

public class TabButton {
  static final ButtonGeneric options = new ButtonGeneric(10, 30, 130, 20,
      StringUtils.translate("hypextension.options"));
  static final ButtonGeneric settings = new ButtonGeneric(140, 30, 130, 20,
      StringUtils.translate("hypextension.settings"));

  public static void addTabButton(GuiBase base) {
    base.addButton(options, (buttonBase, mouseButton) -> GuiBase.openGui(new OptionGUI()));
    base.addButton(settings, (buttonBase, mouseButton) -> GuiBase.openGui(new SettingGUI()));
    options.setEnabled(!(base instanceof OptionGUI));
    settings.setEnabled(!(base instanceof SettingGUI));
  }
}
