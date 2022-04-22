package iafenvoy.hypextension.Config;

import fi.dy.masa.malilib.gui.GuiBase;
import fi.dy.masa.malilib.hotkeys.IHotkeyCallback;
import fi.dy.masa.malilib.hotkeys.IKeybind;
import fi.dy.masa.malilib.hotkeys.KeyAction;
import iafenvoy.hypextension.FastGameMenu.GameList;
import iafenvoy.hypextension.FastGameMenu.GUI.FastGameMenuGUI;
import net.minecraft.client.MinecraftClient;

public class HotKeyHandler implements IHotkeyCallback {
  private static final MinecraftClient client = MinecraftClient.getInstance();

  public HotKeyHandler() {
  }

  @Override
  public boolean onKeyAction(KeyAction action, IKeybind key) {
    if (key == Configs.menuOpenKey.getKeybind())
      client.openScreen(new ConfigGUI());
    else if (key == Configs.fastGameMenuKey.getKeybind()) {
      if (Configs.fastGameMenu.getBooleanValue())
        GuiBase.openGui(new FastGameMenuGUI(GameList.INSTANCE.DATA, null));
    } else
      System.out.print("Unknown HotKey");
    return true;
  }
}
