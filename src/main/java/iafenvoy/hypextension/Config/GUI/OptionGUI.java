package iafenvoy.hypextension.Config.GUI;

import java.util.ArrayList;
import java.util.List;

import fi.dy.masa.malilib.gui.GuiBase;
import fi.dy.masa.malilib.gui.button.ButtonGeneric;
import fi.dy.masa.malilib.util.StringUtils;
import iafenvoy.hypextension.HypClient;
import iafenvoy.hypextension.Config.Configs;
import iafenvoy.hypextension.Config.TabButton;
import iafenvoy.hypextension.Utils.Interface.Callback;
import net.minecraft.client.util.math.MatrixStack;

public class OptionGUI extends GuiBase {
  public static final List<OptionButton> INSTANCE = new ArrayList<>();

  public OptionGUI() {
    this.title = StringUtils.translate(HypClient.MOD_ID + ".title");
  }

  @Override
  public void initGui() {
    super.initGui();
    int l = this.width / 135;
    TabButton.addTabButton(this);
    for (int i = 0; i < INSTANCE.size(); i++) {
      OptionButton button = INSTANCE.get(i);
      ButtonGeneric button2 = button.createButton((i % l) * 135 + 10, (i / l) * 25 + 60, 130, 20);
      this.addButton(button2, (buttonBase, mouseButton) -> button.onClick());
    }
  }

  @Override
  protected void drawTitle(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
    this.drawString(matrixStack, this.getTitleString(), 20, 10, -1);
  }

  public static class OptionButton {
    private boolean booleanValue;
    private String translateKey;
    private Callback callback = null;
    private Button button = null;

    public OptionButton(String translateKey) {
      booleanValue = false;
      this.translateKey = "config." + HypClient.MOD_ID + "." + translateKey;
      this.setDisplayString();
      INSTANCE.add(this);
    }

    public ButtonGeneric createButton(int x, int y, int width, int height) {
      button = new Button(x, y, width, height, StringUtils.translate(translateKey));
      setDisplayString();
      return button;
    }

    public String getTranslateKey() {
      return translateKey;
    }

    public void setBooleanValue(boolean value) {
      booleanValue = value;
      this.setDisplayString();
      if (callback != null)
        callback.onValueChanged(booleanValue);
    }

    private void setDisplayString() {
      if (button != null)
        button.setDisplayString();
    }

    public void onClick() {
      booleanValue = !booleanValue;
      this.setDisplayString();
      Configs.INSTANCE.save();
      if (callback != null)
        callback.onValueChanged(booleanValue);
    }

    public boolean getBooleanValue() {
      return booleanValue;
    }

    public void setValueChangeCallback(Callback callback) {
      this.callback = callback;
    }

    private class Button extends ButtonGeneric {
      public Button(int x, int y, int width, int height, String displayString) {
        super(x, y, width, height, displayString);
      }

      public void setDisplayString() {
        super.setDisplayString(StringUtils.translate(translateKey)
            + (booleanValue ? GuiBase.TXT_GREEN + "  ON" : GuiBase.TXT_RED + " OFF"));
      }
    }
  }
}
