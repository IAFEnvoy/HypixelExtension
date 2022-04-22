package iafenvoy.hypextension.Config;

import java.util.ArrayList;
import java.util.List;

import fi.dy.masa.malilib.config.options.ConfigBoolean;
import fi.dy.masa.malilib.config.options.ConfigHotkey;
import fi.dy.masa.malilib.gui.GuiBase;
import fi.dy.masa.malilib.hotkeys.IHotkeyCallback;
import fi.dy.masa.malilib.hotkeys.IKeybind;
import fi.dy.masa.malilib.hotkeys.KeyAction;
import fi.dy.masa.malilib.interfaces.IValueChangeCallback;
import fi.dy.masa.malilib.util.InfoUtils;
import iafenvoy.hypextension.Config.ConfigGUI.Category;
import iafenvoy.hypextension.Config.NativeConfigType.NativeBoolean;
import iafenvoy.hypextension.Config.NativeConfigType.NativeHotkey;
import net.minecraft.text.TranslatableText;

public class HotkeyBoolean {
  public static final List<ConfigHotkey> hotkeyInstance = new ArrayList<>();
  private final NativeBoolean bool;
  private final NativeHotkey hotkey;
  private IValueChangeCallback<ConfigBoolean> callback;
  private boolean hasCallback = false;

  public HotkeyBoolean(String keyname) {
    this(keyname, false, "");
  }

  public HotkeyBoolean(String keyname, boolean defaultValue) {
    this(keyname, defaultValue, "");
  }

  public HotkeyBoolean(String keyname, boolean defaultValue, String defaultHotkey) {
    this.bool = new NativeBoolean(keyname, defaultValue);
    this.hotkey = new NativeHotkey(keyname + ".hotkey", defaultHotkey);
    hotkeyInstance.add(this.hotkey);
    this.hotkey.getKeybind().setCallback(new HotKeyHandler());
    Category.Hotkeys.add(this.hotkey);
    Category.Options.add(bool);
  }

  public boolean getBooleanValue() {
    return this.bool.getBooleanValue();
  }

  public ConfigBoolean getBoolean() {
    return this.bool;
  }

  public ConfigHotkey getHotkey() {
    return this.hotkey;
  }

  public HotkeyBoolean setCallback(IValueChangeCallback<ConfigBoolean> callback) {
    this.callback = callback;
    this.hasCallback = true;
    return this;
  }

  private class HotKeyHandler implements IHotkeyCallback {
    @Override
    public boolean onKeyAction(KeyAction action, IKeybind key) {
      bool.setBooleanValue(!bool.getBooleanValue());
      sendMessage(bool.getConfigGuiDisplayName(), bool.getBooleanValue());
      if (hasCallback)
        callback.onValueChanged(bool);
      return true;
    }

    public void sendMessage(String key, boolean value) {
      String pre = value ? GuiBase.TXT_GREEN + "ON " : GuiBase.TXT_RED + "OFF";
      String message = new TranslatableText(key).getString() + " : " + pre + GuiBase.TXT_RST;
      InfoUtils.printActionbarMessage(message);
    }
  }
}
