package iafenvoy.hypextension.GUI;

import fi.dy.masa.malilib.gui.GuiBase;
import fi.dy.masa.malilib.gui.button.ButtonBase;
import fi.dy.masa.malilib.gui.button.ButtonGeneric;
import fi.dy.masa.malilib.gui.button.IButtonActionListener;
import fi.dy.masa.malilib.util.StringUtils;
import iafenvoy.hypextension.Data.Game.GameList;
import iafenvoy.hypextension.Data.Game.MiniGame;
import iafenvoy.hypextension.HypClient;
import net.minecraft.client.util.math.MatrixStack;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class FastGameMenuGUI extends GuiBase {
    private final List<MiniGame> games;
    @Nullable
    private final GuiBase parent;

    public FastGameMenuGUI(List<MiniGame> games, @Nullable GuiBase parent) {
        this.title = StringUtils.translate(HypClient.MOD_ID + ".fastgamemenu.title");
        this.games = games;
        this.parent = parent;
        if (parent != null && !this.games.contains(GameList.BACK))
            this.games.add(0, GameList.BACK);
        // if (parent != null && !this.games.contains(GameList.LOBBY))
        // this.games.add(0, GameList.LOBBY);
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
        ButtonGeneric button = new ButtonGeneric(locateX * 125 + 10, locateY * 25 + 30, 120, 20, game.getDisplayName());
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
            if (game == GameList.BACK) {
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
