package iafenvoy.hypextension;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import fi.dy.masa.malilib.config.ConfigManager;
import fi.dy.masa.malilib.event.InputEventHandler;
import iafenvoy.hypextension.Config.Configs;
import iafenvoy.hypextension.Functions.Function;
import iafenvoy.hypextension.Utils.InputHandler;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.loader.api.FabricLoader;

@Environment(EnvType.CLIENT)
public class HypClient implements ClientModInitializer {
  public static final String MOD_ID = "hypextension";
  public static Logger logger = LogManager.getLogger();

  @Override
  public void onInitializeClient() {
    if (!FabricLoader.getInstance().isModLoaded("malilib")) {
      logger.fatal("Malilib is not loaded, please download it.");
      return;
    }

    logger.info("[Hypixel Extension] Initializing...");

    ConfigManager.getInstance().registerConfigHandler(MOD_ID, new Configs());
    Configs.loadFile();
    Configs.Init();
    InputEventHandler.getKeybindManager().registerKeybindProvider(InputHandler.getInstance());
    InputEventHandler.getInputManager().registerKeyboardInputHandler(InputHandler.getInstance());
    InputEventHandler.getInputManager().registerMouseInputHandler(InputHandler.getInstance());
    Function.Init();
  }
}
