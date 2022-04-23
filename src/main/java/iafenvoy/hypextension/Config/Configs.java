package iafenvoy.hypextension.Config;

import java.io.File;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import fi.dy.masa.malilib.config.ConfigUtils;
import fi.dy.masa.malilib.config.IConfigHandler;
import fi.dy.masa.malilib.util.JsonUtils;
import iafenvoy.hypextension.Config.ConfigGUI.Category;
import iafenvoy.hypextension.Config.NativeConfigType.NBoolean;
import iafenvoy.hypextension.Config.NativeConfigType.NColor;
import iafenvoy.hypextension.Config.NativeConfigType.NDouble;
import iafenvoy.hypextension.Config.NativeConfigType.NHotkey;
import iafenvoy.hypextension.Config.NativeConfigType.NString;
import iafenvoy.hypextension.Functions.SettingCallback;

public class Configs implements IConfigHandler {
  private static final String FILE_PATH = "./config/hypextension.json";
  private static final File CONFIG_DIR = new File("./config");
  // Hotkeys
  public static final NHotkey menuOpenKey = new NHotkey("menuOpenKey", "F6", Category.Hotkeys);
  public static final NHotkey fastGameMenuKey = new NHotkey("fastGameMenuKey", "R", Category.Hotkeys);

  // Options
  public static final NBoolean gammaOverride = new NBoolean("gammaOverride", Category.Options);
  public static final NBoolean autoTip = new NBoolean("autoTip", Category.Options);
  public static final NBoolean autoGG = new NBoolean("autoGG", Category.Options);
  public static final NBoolean sneak_1_15_2 = new NBoolean("sneak_1_15_2", Category.Options);// TODO: Buggy.
  public static final NBoolean chatTimeStamp = new NBoolean("chatTimeStamp", Category.Options);
  public static final NBoolean chatBackgroundOverride = new NBoolean("chatBackgroundOverride", Category.Options);
  public static final NBoolean saveChatMessage = new NBoolean("saveChatMessage", Category.Options);// TODO: Buggy
  public static final NBoolean reduceExplosionParticles = new NBoolean("reduceExplosionParticles", Category.Options);
  public static final NBoolean movementKeysLast = new NBoolean("movementKeysLast", Category.Options);
  public static final NBoolean f3Cursor = new NBoolean("f3Cursor", Category.Options);
  public static final NBoolean playerListAlwaysOn = new NBoolean("playerListAlwaysOn", Category.Options);
  public static final NBoolean renderEdgeChunks = new NBoolean("renderEdgeChunks", Category.Options);
  public static final NBoolean removeOwnPotionEffects = new NBoolean("removeOwnPotionEffects", Category.Options);
  public static final NBoolean tntCountDown = new NBoolean("tntCountDown", Category.Options);
  public static final NBoolean fastGameMenu = new NBoolean("fastGameMenu", Category.Options);
  public static final NBoolean renderOwnNameTag = new NBoolean("renderOwnNameTag", Category.Options);
  public static final NBoolean autoFriend = new NBoolean("autoFriend", Category.Options);
  // public static final NBoolean copyChatButton = new
  // NativeBoolean("copyChatButton");// TODO
  // public static final NBoolean headLevel = new NativeBoolean("headLevel");//
  // TODO
  // public static final NBoolean timer = new NativeBoolean("timer");// TODO

  // Settings
  public static final NDouble gammaValue = new NDouble("gammaValue", 16.0F, 0.0F, 128F, false, Category.Settings);
  public static final NString timeStamp = new NString("timeStamp", "[HH:mm:ss]", Category.Settings);
  public static final NColor chatBackgroundColor = new NColor("chatBackgroundColor", Category.Settings);

  // Chat Filter
  public static final NBoolean chatFilter = new NBoolean("chatFilter", Category.ChatFilter);
  public static final NBoolean blockNormal = new NBoolean("blockNormal", Category.ChatFilter);
  public static final NBoolean blockTeam = new NBoolean("blockTeam", Category.ChatFilter);
  public static final NBoolean blockGuild = new NBoolean("blockGuild", Category.ChatFilter);
  public static final NBoolean blockParty = new NBoolean("blockParty", Category.ChatFilter);
  public static final NBoolean blockShout = new NBoolean("blockShout", Category.ChatFilter);
  public static final NBoolean blockSpectator = new NBoolean("blockSpectator", Category.ChatFilter);

  public static void Init() {
    menuOpenKey.getKeybind().setCallback(new HotKeyHandler());
    fastGameMenuKey.getKeybind().setCallback(new HotKeyHandler());
    gammaOverride.setValueChangeCallback(new SettingCallback());
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
