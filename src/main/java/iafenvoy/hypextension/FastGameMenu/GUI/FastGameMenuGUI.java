package iafenvoy.hypextension.FastGameMenu.GUI;

import java.util.List;

import org.jetbrains.annotations.Nullable;

import fi.dy.masa.malilib.gui.GuiBase;
import fi.dy.masa.malilib.gui.button.ButtonBase;
import fi.dy.masa.malilib.gui.button.ButtonGeneric;
import fi.dy.masa.malilib.gui.button.IButtonActionListener;
import fi.dy.masa.malilib.util.StringUtils;
import iafenvoy.hypextension.HypClient;
import iafenvoy.hypextension.FastGameMenu.GameList;
import iafenvoy.hypextension.FastGameMenu.MiniGame;
import net.minecraft.client.util.math.MatrixStack;

public class FastGameMenuGUI extends GuiBase {
  private final List<MiniGame> games;
  @Nullable
  private final GuiBase parent;

  public FastGameMenuGUI(List<MiniGame> games, GuiBase parent) {
    this.title = StringUtils.translate(HypClient.MOD_ID + ".fastgamemenu.title");
    this.games = games;
    this.parent = parent;
    if (parent != null && !this.games.contains(GameList.BACK))
      this.games.add(0, GameList.BACK);
  }

  @Override
  public void initGui() {
    super.initGui();
    for (int i = 0; i < games.size(); i++)
      createButton(i % 4, i / 4, games.get(i));
  }

  @Override
  protected void drawTitle(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
    this.drawString(matrixStack, this.getTitleString(), 20, 10, -1);
 }

  private void createButton(int locateX, int locateY, MiniGame game) {
    int height = ButtonIcon.getByName(game.getTranslateKey()) == null ? 20 : 40;
    ButtonGeneric button = new ButtonGeneric(locateX * 125 + 10, locateY * (height + 5) + 30, 120, height,
        game.getDisplayName(), ButtonIcon.getByName(game.getTranslateKey()));
    this.addButton(button, new ButtonActionListener(game, this));
  }

  public class ButtonActionListener implements IButtonActionListener {
    private final MiniGame game;
    private final GuiBase currentGUI;

    public ButtonActionListener(MiniGame game, GuiBase currentGUI) {
      this.game = game;
      this.currentGUI = currentGUI;
    }

    @Override
    public void actionPerformedWithButton(ButtonBase button, int mouseButton) {
      if (game.getTranslateKey().equals(GameList.BACK.getTranslateKey())) {
        GuiBase.openGui(parent);
      } else if (game.getModes().size() == 0) {
        game.sendCommand();
        currentGUI.onClose();
      } else {
        GuiBase gui = new FastGameMenuGUI(this.game.getModes(), currentGUI);
        GuiBase.openGui(gui.setParent(currentGUI));
      }
    }
  }
}
