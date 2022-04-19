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
    public static final ConfigHotkey menuOpenKey = new NativeHotkey("menuOpenKey", "F9");
    public static final ConfigHotkey fastGameMenuKey = new NativeHotkey("fastGameMenuKey", "R");

    public static void Init() {
      Category.Hotkeys.add(menuOpenKey);
      menuOpenKey.getKeybind().setCallback(new HotKeyHandler());
      Category.Hotkeys.add(fastGameMenuKey);
      fastGameMenuKey.getKeybind().setCallback(new HotKeyHandler());
    }
  }

  public static class Options {
    public static final ConfigBoolean gammaOverride = new NativeBoolean("gammaOverride", false);
    public static final ConfigBoolean autoTip = new NativeBoolean("autoTip", false);
    public static final ConfigBoolean autoGG = new NativeBoolean("autoGG", false);
    public static final ConfigBoolean sneak_1_15_2 = new NativeBoolean("sneak_1_15_2", false);// TODO: Buggy.
    public static final ConfigBoolean chatTimeStamp = new NativeBoolean("chatTimeStamp", false);
    public static final ConfigBoolean chatBackgroundOverride = new NativeBoolean("chatBackgroundOverride", false);
    public static final ConfigBoolean saveChatMessage = new NativeBoolean("saveChatMessage", false);// TODO: Buggy with '/'
    public static final ConfigBoolean reduceExplosionParticles = new NativeBoolean("reduceExplosionParticles", false);
    public static final ConfigBoolean movementKeysLast = new NativeBoolean("movementKeysLast", false);
    public static final ConfigBoolean f3Cursor = new NativeBoolean("f3Cursor", false);
    public static final ConfigBoolean playerListAlwaysOn = new NativeBoolean("playerListAlwaysOn", false);
    public static final ConfigBoolean renderEdgeChunks = new NativeBoolean("renderEdgeChunks", false);
    public static final ConfigBoolean removeOwnPotionEffects = new NativeBoolean("removeOwnPotionEffects", false);
    public static final ConfigBoolean tntCountDown = new NativeBoolean("tntCountDown", false);
    public static final ConfigBoolean fastGameMenu = new NativeBoolean("fastGameMenu", false);

    public static final ConfigBoolean copyChatButton = new NativeBoolean("copyChatButton", false);// TODO
    public static final ConfigBoolean headLevel = new NativeBoolean("headLevel", false);// TODO
    public static final ConfigBoolean chatFilter = new NativeBoolean("chatFilter", false);// TODO
    public static final ConfigBoolean timer = new NativeBoolean("timer", false);// TODO
    public static final ConfigBoolean autoFriend = new NativeBoolean("autoFriend", false);// TODO

    public static void Init() {
      Category.Options.add(gammaOverride);
      gammaOverride.setValueChangeCallback(new SettingCallback());
      Category.Options.add(autoTip);
      Category.Options.add(autoGG);
      Category.Options.add(sneak_1_15_2);
      Category.Options.add(chatTimeStamp);
      Category.Options.add(chatBackgroundOverride);
      Category.Options.add(saveChatMessage);
      Category.Options.add(reduceExplosionParticles);
      Category.Options.add(movementKeysLast);
      Category.Options.add(f3Cursor);
      Category.Options.add(playerListAlwaysOn);
      Category.Options.add(renderEdgeChunks);
      Category.Options.add(removeOwnPotionEffects);
      Category.Options.add(tntCountDown);
      Category.Options.add(fastGameMenu);
    }
  }

  public static class Settings {
    public static final ConfigDouble gammaOverrideValue = new NativeDouble("gammaOverrideValue", 16.0F,
        0.0F, 128F, false);
    public static final ConfigString timeStamp = new NativeString("timeStamp", "[HH:mm:ss]");
    public static final ConfigColor chatBackgroundColor = new NativeColor("chatBackgroundColor", "#80000000");

    public static void Init() {
      Category.Settings.add(gammaOverrideValue);
      Category.Settings.add(timeStamp);
      Category.Settings.add(chatBackgroundColor);
    }
  }

  public static void Init() {
    Options.Init();
    Hotkeys.Init();
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
