package iafenvoy.hypextension.Config;

import java.io.File;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import fi.dy.masa.malilib.config.ConfigUtils;
import fi.dy.masa.malilib.config.IConfigHandler;
import fi.dy.masa.malilib.config.options.*;
import fi.dy.masa.malilib.util.JsonUtils;
import iafenvoy.hypextension.Config.ConfigGUI.Category;
import iafenvoy.hypextension.Config.NativeConfigType.*;
import iafenvoy.hypextension.Functions.SettingCallback;

public class Configs implements IConfigHandler {
  private static final String FILE_PATH = "./config/hypextension.json";
  private static final File CONFIG_DIR = new File("./config");

  public static class Hotkeys {
    public static final ConfigHotkey menuOpenKey = Category.Hotkeys.add(new NativeHotkey("menuOpenKey", "F6"));
    public static final ConfigHotkey fastGameMenuKey = Category.Hotkeys.add(new NativeHotkey("fastGameMenuKey", "R"));

    public static void Init() {
      menuOpenKey.getKeybind().setCallback(new HotKeyHandler());
      fastGameMenuKey.getKeybind().setCallback(new HotKeyHandler());
    }
  }

  public static class Options {
    public static final HotkeyBoolean gammaOverride = new HotkeyBoolean("gammaOverride")
        .setCallback(new SettingCallback());;
    public static final HotkeyBoolean autoTip = new HotkeyBoolean("autoTip");
    public static final HotkeyBoolean autoGG = new HotkeyBoolean("autoGG");
    public static final HotkeyBoolean sneak_1_15_2 = new HotkeyBoolean("sneak_1_15_2");// TODO: Buggy.
    public static final HotkeyBoolean chatTimeStamp = new HotkeyBoolean("chatTimeStamp");
    public static final HotkeyBoolean chatBackgroundOverride = new HotkeyBoolean("chatBackgroundOverride");
    public static final HotkeyBoolean saveChatMessage = new HotkeyBoolean("saveChatMessage");// TODO: Buggy with '/'
    public static final HotkeyBoolean reduceExplosionParticles = new HotkeyBoolean("reduceExplosionParticles");
    public static final HotkeyBoolean movementKeysLast = new HotkeyBoolean("movementKeysLast");
    public static final HotkeyBoolean f3Cursor = new HotkeyBoolean("f3Cursor");
    public static final HotkeyBoolean playerListAlwaysOn = new HotkeyBoolean("playerListAlwaysOn");
    public static final HotkeyBoolean renderEdgeChunks = new HotkeyBoolean("renderEdgeChunks");
    public static final HotkeyBoolean removeOwnPotionEffects = new HotkeyBoolean("removeOwnPotionEffects");
    public static final HotkeyBoolean tntCountDown = new HotkeyBoolean("tntCountDown");
    public static final HotkeyBoolean fastGameMenu = new HotkeyBoolean("fastGameMenu");
    public static final HotkeyBoolean renderOwnNameTag = new HotkeyBoolean("renderOwnNameTag");
    public static final HotkeyBoolean autoFriend = new HotkeyBoolean("autoFriend");

    // public static final HotkeyBoolean copyChatButton = new
    // HotkeyBoolean("copyChatButton");// TODO
    // public static final HotkeyBoolean headLevel = new
    // HotkeyBoolean("headLevel");// TODO
    // public static final HotkeyBoolean chatFilter = new
    // HotkeyBoolean("chatFilter");// TODO
    // public static final HotkeyBoolean timer = new HotkeyBoolean("timer");// TODO

    public static void Init() {
      gammaOverride.register();
      autoTip.register();
      autoGG.register();
      sneak_1_15_2.register();
      chatTimeStamp.register();
      chatBackgroundOverride.register();
      saveChatMessage.register();
      reduceExplosionParticles.register();
      movementKeysLast.register();
      f3Cursor.register();
      playerListAlwaysOn.register();
      renderEdgeChunks.register();
      removeOwnPotionEffects.register();
      tntCountDown.register();
      fastGameMenu.register();
      renderOwnNameTag.register();
      autoFriend.register();
      // copyChatButton.register();
      // headLevel.register();
      // chatFilter.register();
      // timer.register();
    }
  }

  public static class Settings {
    public static final ConfigDouble gammaOverrideValue = new NativeDouble("gammaOverrideValue", 16.0F,
        0.0F, 128F, false);
    public static final ConfigString timeStamp = new NativeString("timeStamp", "[HH:mm:ss]");
    public static final ConfigColor chatBackgroundColor = new NativeColor("chatBackgroundColor", "#80000000");

    public static final ConfigDouble x = new NativeDouble("x", 0, -180, 180, true);
    public static final ConfigDouble y = new NativeDouble("y", 0, -180, 180, true);
    public static final ConfigDouble z = new NativeDouble("z", 0, -180, 180, true);

    public static void Init() {
      Category.Settings.add(gammaOverrideValue);
      Category.Settings.add(timeStamp);
      Category.Settings.add(chatBackgroundColor);
      // Category.Settings.add(x);
      // Category.Settings.add(y);
      // Category.Settings.add(z);
    }
  }

  public static void Init() {
    Hotkeys.Init();
    Options.Init();
    Settings.Init();
  }

  @Override
  public void load() {
    loadFile();
  }

  public static void loadFile() {
    File settingFile = new File(FILE_PATH);
    if (settingFile.isFile() && settingFile.exists()) {
      JsonElement jsonElement = JsonUtils.parseJsonFile(settingFile);
      if (jsonElement instanceof JsonObject)
        for (Category category : Category.values())
          ConfigUtils.readConfigBase((JsonObject) jsonElement, category.name(), category.getConfigs());
    }
  }

  @Override
  public void save() {
    if ((CONFIG_DIR.exists() && CONFIG_DIR.isDirectory()) || CONFIG_DIR.mkdirs()) {
      JsonObject configRoot = new JsonObject();
      for (Category category : Category.values())
        ConfigUtils.writeConfigBase(configRoot, category.name(), category.getConfigs());
      JsonUtils.writeJsonToFile(configRoot, new File(FILE_PATH));
    }
  }
}
