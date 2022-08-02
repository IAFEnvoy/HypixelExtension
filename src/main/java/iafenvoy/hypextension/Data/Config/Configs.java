package iafenvoy.hypextension.Data.Config;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import fi.dy.masa.malilib.config.ConfigUtils;
import fi.dy.masa.malilib.config.IConfigHandler;
import fi.dy.masa.malilib.config.options.ConfigHotkey;
import fi.dy.masa.malilib.hotkeys.KeybindSettings;
import fi.dy.masa.malilib.util.JsonUtils;
import iafenvoy.hypextension.HypClient;
import iafenvoy.hypextension.Event.Callback.SettingCallback;
import iafenvoy.hypextension.GUI.OptionGUI;
import iafenvoy.hypextension.GUI.SettingGUI;
import iafenvoy.hypextension.GUI.OptionGUI.OptionButton;
import java.io.File;
import net.minecraft.text.TranslatableText;

public class Configs implements IConfigHandler {
  public static final Configs INSTANCE = new Configs();

  private final String OPTIONS_PATH = "./config/hypextension/options.json";
  private final String SETTINGS_PATH = "./config/hypextension/settings.json";
  private final File CONFIG_DIR = new File("./config/hypextension");
  // Hotkeys
  public final NHotkey menuOpenKey = new NHotkey("menuOpenKey", "F6");
  public final NHotkey fastGameMenuKey = new NHotkey("fastGameMenuKey", "R");
  // Options
  public final OptionButton gammaOverride = new OptionButton("gammaOverride");
  public final OptionButton autoTip = new OptionButton("autoTip");
  public final OptionButton autoGG = new OptionButton("autoGG");
  public final OptionButton sneak_1_15_2 = new OptionButton("sneak_1_15_2");// TODO: Buggy.
  public final OptionButton chatTimeStamp = new OptionButton("chatTimeStamp");
  public final OptionButton saveChatInput = new OptionButton("saveChatInput");
  public final OptionButton reduceExplosionParticles = new OptionButton("reduceExplosionParticles");
  public final OptionButton movementKeysLast = new OptionButton("movementKeysLast");
  public final OptionButton f3Cursor = new OptionButton("f3Cursor");
  public final OptionButton playerListAlwaysOn = new OptionButton("playerListAlwaysOn");
  public final OptionButton renderEdgeChunks = new OptionButton("renderEdgeChunks");
  public final OptionButton removeOwnPotionEffects = new OptionButton("removeOwnPotionEffects");
  public final OptionButton tntCountDown = new OptionButton("tntCountDown");
  public final OptionButton fastGameMenu = new OptionButton("fastGameMenu");
  public final OptionButton renderOwnNameTag = new OptionButton("renderOwnNameTag");
  public final OptionButton autoFriend = new OptionButton("autoFriend");
  public final OptionButton sword_1_8_9 = new OptionButton("sword_1_8_9");
  public final OptionButton betterDropItem = new OptionButton("betterDropItem");
  public final OptionButton betterPingShow = new OptionButton("betterPingShow");
  public final OptionButton fastChestRender = new OptionButton("fastChestRender");
  public final OptionButton extendHistorySize = new OptionButton("extendHistorySize");
  public final OptionButton saveChatHistory = new OptionButton("saveChatHistory");
  public final OptionButton scoreboardFix = new OptionButton("scoreboardFix");
  // public final OptionButton copyChatButton = new
  // OptionButton("copyChatButton");// TODO
  // public final OptionButton headLevel = new OptionButton("headLevel"); //TODO
  // public final OptionButton timer = new OptionButton("timer");// TODO

  public Configs() {
    menuOpenKey.getKeybind().setCallback(new HotKeyHandler());
    fastGameMenuKey.getKeybind().setCallback(new HotKeyHandler());
    gammaOverride.setValueChangeCallback(new SettingCallback());
  }

  @Override
  public void load() {
    File optionFile = new File(OPTIONS_PATH);
    if (optionFile.exists()) {
      JsonElement options = JsonUtils.parseJsonFile(optionFile);
      if (options instanceof JsonObject)
        for (OptionButton option : OptionGUI.INSTANCE)
          if (options.getAsJsonObject().has(option.getTranslateKey()))
            option.setBooleanValue(options.getAsJsonObject().get(option.getTranslateKey()).getAsBoolean());
    }
    File settingFile = new File(SETTINGS_PATH);
    if (settingFile.exists()) {
      JsonElement jsonElement = JsonUtils.parseJsonFile(settingFile);
      if (jsonElement instanceof JsonObject)
        ConfigUtils.readConfigBase((JsonObject) jsonElement, "Settings", SettingGUI.configOptions);
    }
  }

  @Override
  public void save() {
    if ((CONFIG_DIR.exists() && CONFIG_DIR.isDirectory()) || CONFIG_DIR.mkdirs()) {
      JsonObject options = new JsonObject();
      for (OptionButton option : OptionGUI.INSTANCE)
        options.addProperty(option.getTranslateKey(), option.getBooleanValue());
      JsonUtils.writeJsonToFile(options, new File(OPTIONS_PATH));
      JsonObject settings = new JsonObject();
      ConfigUtils.writeConfigBase(settings, "Settings", SettingGUI.configOptions);
      JsonUtils.writeJsonToFile(settings, new File(SETTINGS_PATH));
    }
  }

  public class NHotkey extends ConfigHotkey {
    public NHotkey(String keyname, String defaultHotkey) {
      super(new TranslatableText("config." + HypClient.MOD_ID + "." + keyname).getString(), defaultHotkey,
          KeybindSettings.DEFAULT,
          new TranslatableText("config." + HypClient.MOD_ID + "." + keyname + ".help").getString());
      SettingGUI.configOptions.add(this);
    }
  }
}
