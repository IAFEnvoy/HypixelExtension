package iafenvoy.hypextension.Config;

import java.io.File;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import fi.dy.masa.malilib.config.ConfigUtils;
import fi.dy.masa.malilib.config.IConfigHandler;
import fi.dy.masa.malilib.config.options.ConfigBoolean;
import fi.dy.masa.malilib.config.options.ConfigColor;
import fi.dy.masa.malilib.config.options.ConfigDouble;
import fi.dy.masa.malilib.config.options.ConfigHotkey;
import fi.dy.masa.malilib.config.options.ConfigString;
import fi.dy.masa.malilib.util.JsonUtils;
import iafenvoy.hypextension.Config.ConfigGUI.Category;
import iafenvoy.hypextension.Config.NativeConfigType.NativeBoolean;
import iafenvoy.hypextension.Config.NativeConfigType.NativeColor;
import iafenvoy.hypextension.Config.NativeConfigType.NativeDouble;
import iafenvoy.hypextension.Config.NativeConfigType.NativeHotkey;
import iafenvoy.hypextension.Config.NativeConfigType.NativeString;
import iafenvoy.hypextension.Functions.SettingCallback;

public class Configs implements IConfigHandler {
  private static final String FILE_PATH = "./config/hypextension.json";
  private static final File CONFIG_DIR = new File("./config");
  // Hotkeys
  public static final ConfigHotkey menuOpenKey = Category.Hotkeys.add(new NativeHotkey("menuOpenKey", "F6"));
  public static final ConfigHotkey fastGameMenuKey = Category.Hotkeys.add(new NativeHotkey("fastGameMenuKey", "R"));

  // Options
  public static final HotkeyBoolean gammaOverride = new HotkeyBoolean("gammaOverride")
      .setCallback(new SettingCallback());
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
  // public static final HotkeyBoolean timer = new HotkeyBoolean("timer");// TODO

  // Settings
  public static final ConfigDouble gammaOverrideValue = Category.Settings
      .add(new NativeDouble("gammaOverrideValue", 16.0F, 0.0F, 128F, false));
  public static final ConfigString timeStamp = Category.Settings.add(new NativeString("timeStamp", "[HH:mm:ss]"));
  public static final ConfigColor chatBackgroundColor = Category.Settings
      .add(new NativeColor("chatBackgroundColor", "#80000000"));

  // Chat Filter
  public static final ConfigBoolean chatFilter = Category.ChatFilter.add(new NativeBoolean("chatFilter", false));
  public static final ConfigBoolean blockNormal = Category.ChatFilter.add(new NativeBoolean("blockNormal", false));
  public static final ConfigBoolean blockTeam = Category.ChatFilter.add(new NativeBoolean("blockTeam", false));
  public static final ConfigBoolean blockGuild = Category.ChatFilter.add(new NativeBoolean("blockGuild", false));
  public static final ConfigBoolean blockParty = Category.ChatFilter.add(new NativeBoolean("blockParty", false));
  public static final ConfigBoolean blockShout = Category.ChatFilter.add(new NativeBoolean("blockShout", false));
  public static final ConfigBoolean blockSpectator = Category.ChatFilter.add(new NativeBoolean("blockSpectator", false));

  public static void Init() {
    menuOpenKey.getKeybind().setCallback(new HotKeyHandler());
    fastGameMenuKey.getKeybind().setCallback(new HotKeyHandler());
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
