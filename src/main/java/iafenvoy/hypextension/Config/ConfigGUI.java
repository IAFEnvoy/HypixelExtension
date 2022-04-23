package iafenvoy.hypextension.Config;

import fi.dy.masa.malilib.config.IConfigBase;
import fi.dy.masa.malilib.gui.GuiConfigsBase;
import fi.dy.masa.malilib.gui.button.ButtonGeneric;
import fi.dy.masa.malilib.util.StringUtils;
import iafenvoy.hypextension.HypClient;

import java.util.ArrayList;
import java.util.List;

import com.google.common.collect.ImmutableList;

public class ConfigGUI extends GuiConfigsBase {
  private static Category currentTab = Category.Hotkeys;

  public ConfigGUI() {
    super(10, 50, HypClient.MOD_ID, null, "hypextension.title");
  }

  @Override
  public void initGui() {
    super.initGui();
    this.clearOptions();
    int x = 10, y = 26;
    // tab buttons are set
    for (Category category : Category.values()) {
      ButtonGeneric tabButton = new TabButton(category, x, y, -1, 20, StringUtils.translate(category.getKey()));
      tabButton.setEnabled(true);
      this.addButton(tabButton, (buttonBase, i) -> {
        this.onSettingsChanged();
        // reload the GUI when tab button is clicked
        currentTab = ((TabButton) buttonBase).category;
        this.reCreateListWidget();
        // noinspection ConstantConditions
        this.getListWidget().resetScrollbarPosition();
        this.initGui();
      });
      x += tabButton.getWidth() + 2;
    }
  }

  @Override
  public List<ConfigOptionWrapper> getConfigs() {
    // option buttons are set
    return ConfigOptionWrapper.createFor(currentTab.getConfigs());
  }

  public static class TabButton extends ButtonGeneric {
    private final Category category;

    public TabButton(Category category, int x, int y, int width, int height, String text, String... hoverStrings) {
      super(x, y, width, height, text, hoverStrings);
      this.category = category;
    }
  }

  public static enum Category {
    Hotkeys("hypextension.hotkeys"),
    Options("hypextension.options"),
    Settings("hypextension.settings"),
    ChatFilter("hypextension.chatfilter"),;

    private final String key;
    private final List<IConfigBase> configs = new ArrayList<>();

    Category(String key) {
      this.key = key;
    }

    public <T extends IConfigBase> T add(T config) {
      this.configs.add(config);
      return config;
    }

    public List<IConfigBase> getConfigs() {
      return ImmutableList.copyOf(this.configs);
    }

    public String getKey() {
      return this.key;
    }
  }
}
