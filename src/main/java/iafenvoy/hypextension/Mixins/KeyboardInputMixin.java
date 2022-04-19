package iafenvoy.hypextension.Mixins;

import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.At.Shift;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import net.minecraft.client.input.Input;
import net.minecraft.client.input.KeyboardInput;
import iafenvoy.hypextension.Config.Configs.Options;
import iafenvoy.hypextension.Utils.InputHandler;

@Mixin(KeyboardInput.class)
public abstract class KeyboardInputMixin extends Input {
  @Inject(method = "tick(Z)V", at = @At(value = "FIELD", target = "Lnet/minecraft/client/input/KeyboardInput;sneaking:Z", ordinal = 0, shift = Shift.AFTER, opcode = Opcodes.PUTFIELD))
  private void customMovement(boolean val1, CallbackInfo ci) {
    if (Options.movementKeysLast.getBooleanValue())
      InputHandler.getInstance().handleMovementKeys(this);
  }
}
