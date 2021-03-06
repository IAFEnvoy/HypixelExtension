package iafenvoy.hypextension;

import fi.dy.masa.malilib.config.ConfigManager;
import fi.dy.masa.malilib.event.InputEventHandler;
import iafenvoy.hypextension.Data.Config.Configs;
import iafenvoy.hypextension.Event.ChatReceive;
import iafenvoy.hypextension.Event.LifeCycle;
import iafenvoy.hypextension.Event.ChatReceiveEvent.AutoFriend;
import iafenvoy.hypextension.Event.ChatReceiveEvent.AutoGG;
import iafenvoy.hypextension.Event.LifeCycleEvent.AutoTip;
import iafenvoy.hypextension.Utils.InputHandler;
import iafenvoy.hypextension.Utils.ItemsUtil;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.loader.api.FabricLoader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Environment(EnvType.CLIENT)
public class HypClient implements ClientModInitializer {
  public static final String MOD_ID = "hypextension";
  public static Logger logger = LogManager.getLogger();

  @Override
  public void onInitializeClient() {
    logger.info("[Hypixel Extension] Initializing");

    if (!FabricLoader.getInstance().isModLoaded("malilib")) {
      logger.fatal("Malilib is not loaded, please download it.");
      return;
    }

    ConfigManager.getInstance().registerConfigHandler(MOD_ID, Configs.INSTANCE);
    Configs.INSTANCE.load();
    InputEventHandler.getKeybindManager().registerKeybindProvider(InputHandler.INSTANCE);
    InputEventHandler.getInputManager().registerKeyboardInputHandler(InputHandler.INSTANCE);
    ItemsUtil.registerProviders();

    ChatReceive.registry("autogg", AutoGG.INSTANCE);
    ChatReceive.registry("autofriend", AutoFriend.INSTANCE);

    LifeCycle.registry("autotip", AutoTip.INSTANCE);
    LifeCycle.startLifeCycle();
  }
}
