package iafenvoy.hypextension;

import fi.dy.masa.malilib.config.ConfigManager;
import fi.dy.masa.malilib.event.InputEventHandler;
import iafenvoy.hypextension.config.Configs;
import iafenvoy.hypextension.ingame.function.AutoTip;
import iafenvoy.hypextension.ingame.function.InputHandler;
import iafenvoy.hypextension.ingame.function.KeepAliveDetect;
import iafenvoy.hypextension.searcher.Commands;
import iafenvoy.hypextension.utils.ItemsUtil;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.loader.api.FabricLoader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Environment(EnvType.CLIENT)
public class HypClient implements ClientModInitializer {
    public static final String MOD_ID = "hypextension";
    public static final Logger logger = LogManager.getLogger();

    @Override
    public void onInitializeClient() {
        if (!FabricLoader.getInstance().isModLoaded("malilib")) {
            logger.fatal("Malilib is not loaded, please download it.");
            return;
        }
        logger.info("[Hypixel Extension] Initializing");

        ConfigManager.getInstance().registerConfigHandler(MOD_ID, Configs.INSTANCE);
        Configs.INSTANCE.load();
        InputEventHandler.getKeybindManager().registerKeybindProvider(InputHandler.INSTANCE);
        InputEventHandler.getInputManager().registerKeyboardInputHandler(InputHandler.INSTANCE);

        AutoTip.start();
        KeepAliveDetect.start();

        ItemsUtil.registerProviders();
        Commands.register();
    }
}
