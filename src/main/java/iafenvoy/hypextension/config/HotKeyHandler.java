package iafenvoy.hypextension.config;

import fi.dy.masa.malilib.gui.GuiBase;
import fi.dy.masa.malilib.hotkeys.IHotkeyCallback;
import fi.dy.masa.malilib.hotkeys.IKeybind;
import fi.dy.masa.malilib.hotkeys.KeyAction;
import iafenvoy.hypextension.ingame.minigame.GameList;
import iafenvoy.hypextension.gui.FastGameMenuGUI;
import iafenvoy.hypextension.gui.OptionGUI;
import net.minecraft.client.MinecraftClient;

public class HotKeyHandler implements IHotkeyCallback {
    private static final MinecraftClient client = MinecraftClient.getInstance();

    @Override
    public boolean onKeyAction(KeyAction action, IKeybind key) {
        if (key == Configs.INSTANCE.menuOpenKey.getKeybind())
            client.openScreen(new OptionGUI());
        else if (key == Configs.INSTANCE.fastGameMenuKey.getKeybind()) {
            if (Configs.INSTANCE.fastGameMenu.getBooleanValue())
                GuiBase.openGui(new FastGameMenuGUI(GameList.INSTANCE.DATA, null));
        } else
            System.out.print("Unknown HotKey");
        return true;
    }
}
