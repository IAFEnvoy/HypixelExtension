package iafenvoy.hypextension.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import fi.dy.masa.malilib.gui.GuiBase;
import fi.dy.masa.malilib.gui.button.ButtonGeneric;
import fi.dy.masa.malilib.gui.widgets.WidgetLabel;
import fi.dy.masa.malilib.render.RenderUtils;
import fi.dy.masa.malilib.util.StringUtils;
import iafenvoy.hypextension.HypClient;
import iafenvoy.hypextension.searcher.data.PlayerData;
import iafenvoy.hypextension.searcher.HypixelDataParser;
import iafenvoy.hypextension.searcher.HypixelInfo;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.math.MatrixStack;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SearcherGUI extends GuiBase {
    private static final MinecraftClient client = MinecraftClient.getInstance();
    private static Category currentTab = Category.General;
    private final HypixelInfo info;
    private final List<FontSizeableLabel> label = new ArrayList<>();
    private int prevWidth = 0, scale = 1;

    public SearcherGUI(HypixelInfo info) {
        super();
        this.title = StringUtils.translate(HypClient.MOD_ID + ".title");
        this.info = info;
        currentTab = Category.General;
    }

    @Override
    protected void drawTitle(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        //should not render title
    }

    @Override
    public void initGui() {
        super.initGui();
        this.label.clear();
        int x = 10, y = 10;
        for (Category category : Category.values()) {
            if (category == Category.General) continue;
            TabButton tab = new TabButton(category, x, y, 100, 20, category.getText());
            tab.setScale(this.scale);
            this.addButton(tab, (button, i) -> {
                currentTab = ((TabButton) button).category;
                this.initGui();
            });
            y += tab.getHeight() + 2;
        }

        this.addText(80, 5, 100, 20, 1.5F, Collections.singletonList(info.getNameWithRank()));
        this.addText(120, 25, 100, 100, 1, HypixelDataParser.getRoot(info.getMain()));

        this.addText(300, 25, 100, 100, 1, HypixelDataParser.getGuild(this.info.getGuild(), this.info.getMain().getValue("uuid").getAsString()));
        this.addText(300, 85, 100, 100, 1, HypixelDataParser.getStatus(this.info.getStatus()));

        this.addText(80, 75, 100, 20, 1.5F, Collections.singletonList(currentTab.getText()));
        this.addText(120, 130, 100, 100, 1, currentTab.getData(this.info.getMain()));
    }

    public void addText(int x, int y, int width, int height, float fontScale, List<String> lines) {
        if (lines.size() > 0)
            if (width == -1)
                for (String line : lines)
                    width = Math.max(width, this.getStringWidth(line));
        this.label.add(new FontSizeableLabel(x, y, width, height, fontScale, lines));
    }

    private void detectResize() {
        int width = client.getWindow().getWidth();
        if (this.prevWidth != width) {
            this.prevWidth = width;
            this.scale = this.prevWidth / 800;
            this.initGui();
        }
    }

    @Override
    protected void drawWidgets(int mouseX, int mouseY, MatrixStack matrices) {
        this.detectResize();
        for (int i = 0; i < this.label.size(); i++) {
            final float scale = this.label.get(i).getFontScale(this.scale);
            matrices.scale(scale, scale, scale);
            this.label.get(i).render(matrices);
            matrices.scale(1.0F / scale, 1.0F / scale, 1.0F / scale);
        }
    }

    public enum Category {
        General("", data -> new ArrayList<>()),
        Bedwar("Bedwar", data -> HypixelDataParser.getBedwar(data)),
        SkyWars("Sky Wars", data -> HypixelDataParser.getSkyWars(data)),
        MurderMystery("Murder Mystery", data -> HypixelDataParser.getMurderMystery(data)),
        Duel("Duel", data -> HypixelDataParser.getDuel(data)),
        UHC("UHC", data -> HypixelDataParser.getUhc(data)),
        MegaWall("Mega Wall", data -> HypixelDataParser.getMegaWall(data));

        private final String key;
        private final Callback callback;

        Category(String key, Callback callback) {
            this.key = key;
            this.callback = callback;
        }

        public String getText() {
            return StringUtils.translate(this.key);
        }

        public List<String> getData(PlayerData data) {
            return this.callback.getData(data);
        }

        public interface Callback {
            List<String> getData(PlayerData data);
        }
    }

    private static class FontSizeableLabel extends WidgetLabel {
        private final float fontScale;
        private final List<String> lines;

        public FontSizeableLabel(int x, int y, int width, int height, float fontScale, List<String> lines) {
            super(x, y, width, height, 0xFFFFFFFF, lines);
            this.fontScale = fontScale;
            this.lines = lines;
        }

        public float getFontScale(int global) {
            return fontScale * global;
        }

        public void render(MatrixStack matrixStack) {
            RenderUtils.setupBlend();
            this.drawLabelBackground();

            float fontHeight = this.fontScale * this.fontHeight;
            for (int i = 0; i < this.lines.size(); i++)
                this.drawStringWithShadow(this.x, (int) (this.y + i * fontHeight), this.textColor, this.lines.get(i), matrixStack);
        }
    }

    public static class TabButton extends ButtonGeneric {
        private final Category category;
        private int scale;

        public TabButton(Category category, int x, int y, int width, int height, String text, String... hoverStrings) {
            super(x, y, width, height, text, hoverStrings);
            this.category = category;
        }

        public void setScale(int scale) {
            this.scale = scale;
        }

        @Override
        public void render(int mouseX, int mouseY, boolean selected, MatrixStack matrices) {
            RenderSystem.scalef(this.scale, this.scale, this.scale);

            this.hovered = this.isMouseOver(mouseX, mouseY);
            int buttonStyle = this.getTextureOffset(this.hovered);

            RenderUtils.color(1f, 1f, 1f, 1f);
            RenderUtils.setupBlend();
            RenderUtils.setupBlendSimple();
            this.bindTexture(BUTTON_TEXTURES);
            RenderUtils.drawTexturedRect(this.x, this.y, 0, 46 + buttonStyle * 20, this.width / 2, this.height);
            RenderUtils.drawTexturedRect(this.x + this.width / 2, this.y, 200 - this.width / 2, 46 + buttonStyle * 20, this.width / 2, this.height);

            int y = this.y + (this.height - 8) / 2;
            int color = this.hovered ? 0xFFFFA0 : 0xE0E0E0;
            this.drawStringWithShadow(this.x + 8, y, color, this.displayString, matrices);

            RenderSystem.scalef(1.0F / this.scale, 1.0F / this.scale, 1.0F / this.scale);
        }

        @Override
        public boolean isMouseOver(int mouseX, int mouseY) {
            return mouseX >= this.x * this.scale && mouseX < (this.x + this.width) * this.scale &&
                    mouseY >= this.y * this.scale && mouseY < (this.y + this.height) * this.scale;
        }
    }
}
