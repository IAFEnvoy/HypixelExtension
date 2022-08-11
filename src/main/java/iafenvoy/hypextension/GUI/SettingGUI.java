package iafenvoy.hypextension.GUI;

import fi.dy.masa.malilib.config.IConfigBase;
import fi.dy.masa.malilib.gui.GuiConfigsBase;
import iafenvoy.hypextension.HypClient;

import java.util.ArrayList;
import java.util.List;

public class SettingGUI extends GuiConfigsBase {
    public static final List<IConfigBase> configOptions = new ArrayList<>();

    public SettingGUI() {
        super(10, 50, HypClient.MOD_ID, null, "hypextension.title");
    }

    @Override
    public void initGui() {
        super.initGui();
        TabButton.addTabButton(this);
    }

    @Override
    public List<ConfigOptionWrapper> getConfigs() {
        return ConfigOptionWrapper.createFor(configOptions);
    }

}
