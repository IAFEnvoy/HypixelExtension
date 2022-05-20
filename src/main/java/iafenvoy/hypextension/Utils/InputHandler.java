package iafenvoy.hypextension.Utils;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.input.Input;
import net.minecraft.client.option.GameOptions;
import fi.dy.masa.malilib.hotkeys.IKeybindManager;
import fi.dy.masa.malilib.hotkeys.IKeybindProvider;
import fi.dy.masa.malilib.hotkeys.IKeyboardInputHandler;
import fi.dy.masa.malilib.util.GuiUtils;
import iafenvoy.hypextension.Config.Configs;

public class InputHandler implements IKeybindProvider, IKeyboardInputHandler {
  private static final MinecraftClient client = MinecraftClient.getInstance();
  private static final InputHandler INSTANCE = new InputHandler();
  private LeftRight lastSidewaysInput = LeftRight.NONE;
  private ForwardBack lastForwardInput = ForwardBack.NONE;

  public InputHandler() {
    super();
  }

  public static InputHandler getInstance() {
    return INSTANCE;
  }

  @Override
  public boolean onKeyInput(int keyCode, int scanCode, int modifiers, boolean eventKeyState) {
    if (GuiUtils.getCurrentScreen() == null && eventKeyState)
      this.storeLastMovementDirection(keyCode, scanCode);
    return false;
  }

  private void storeLastMovementDirection(int eventKey, int scanCode) {
    if (client.options.keyForward.matchesKey(eventKey, scanCode))
      this.lastForwardInput = ForwardBack.FORWARD;
    else if (client.options.keyBack.matchesKey(eventKey, scanCode))
      this.lastForwardInput = ForwardBack.BACK;
    else if (client.options.keyLeft.matchesKey(eventKey, scanCode))
      this.lastSidewaysInput = LeftRight.LEFT;
    else if (client.options.keyRight.matchesKey(eventKey, scanCode))
      this.lastSidewaysInput = LeftRight.RIGHT;
  }

  public void handleMovementKeys(Input movement) {
    GameOptions settings = client.options;
    if (settings.keyLeft.isPressed() && settings.keyRight.isPressed())
      if (this.lastSidewaysInput == LeftRight.LEFT) {
        movement.movementSideways = 1;
        movement.pressingLeft = true;
        movement.pressingRight = false;
      } else if (this.lastSidewaysInput == LeftRight.RIGHT) {
        movement.movementSideways = -1;
        movement.pressingLeft = false;
        movement.pressingRight = true;
      }
    if (settings.keyBack.isPressed() && settings.keyForward.isPressed())
      if (this.lastForwardInput == ForwardBack.FORWARD) {
        movement.movementForward = 1;
        movement.pressingForward = true;
        movement.pressingBack = false;
      } else if (this.lastForwardInput == ForwardBack.BACK) {
        movement.movementForward = -1;
        movement.pressingForward = false;
        movement.pressingBack = true;
      }
  }

  @Override
  public void addHotkeys(IKeybindManager manager) {
  }

  @Override
  public void addKeysToMap(IKeybindManager manager) {
    manager.addKeybindToMap(Configs.INSTANCE.menuOpenKey.getKeybind());
    manager.addKeybindToMap(Configs.INSTANCE.fastGameMenuKey.getKeybind());
  }

  public enum LeftRight {
    NONE,
    LEFT,
    RIGHT
  }

  public enum ForwardBack {
    NONE,
    FORWARD,
    BACK
  }
}
