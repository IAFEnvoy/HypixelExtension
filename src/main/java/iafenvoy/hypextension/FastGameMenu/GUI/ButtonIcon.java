package iafenvoy.hypextension.FastGameMenu.GUI;

import fi.dy.masa.malilib.gui.interfaces.IGuiIcon;
import fi.dy.masa.malilib.render.RenderUtils;
import iafenvoy.hypextension.HypClient;
import net.minecraft.util.Identifier;

public enum ButtonIcon implements IGuiIcon {
  AREA_EDITOR("111",102, 70, 14, 14);

  //TODO : Textures
  public static final Identifier TEXTURE = new Identifier(HypClient.MOD_ID, "textures/gui/gui_widgets.png");

  private final String name;
  private final int u;
  private final int v;
  private final int w;
  private final int h;

  private ButtonIcon(String name,int u, int v, int w, int h) {
    this.name = name;
    this.u = u;
    this.v = v;
    this.w = w;
    this.h = h;
  }

  @Override
  public int getWidth() {
    return this.w;
  }

  @Override
  public int getHeight() {
    return this.h;
  }

  @Override
  public int getU() {
    return this.u;
  }

  @Override
  public int getV() {
    return this.v;
  }

  @Override
  public void renderAt(int x, int y, float zLevel, boolean enabled, boolean selected) {
    int u = this.u;
    if (enabled)
      u += this.w;
    if (selected)
      u += this.w;
    RenderUtils.drawTexturedRect(x, y, u, this.v, this.w, this.h, zLevel);
  }

  @Override
  public Identifier getTexture() {
    return TEXTURE;
  }

  public String getName() {
    return this.name;
  }

  public static ButtonIcon getByName(String name){
    for(ButtonIcon icon : values())
      if(icon.getName().equals(name))
        return icon;
    return null;
  }
}
