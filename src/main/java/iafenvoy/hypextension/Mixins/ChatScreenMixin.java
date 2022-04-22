package iafenvoy.hypextension.Mixins;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.At.Shift;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.Slice;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import iafenvoy.hypextension.Config.Configs;
import net.minecraft.client.gui.screen.ChatScreen;
import net.minecraft.client.gui.widget.TextFieldWidget;

@Mixin(ChatScreen.class)
public abstract class ChatScreenMixin {
  @Shadow
  protected TextFieldWidget chatField;
  @Shadow
  private String originalChatText;

  private static String lastChatText = "";

  @Inject(method = "removed", at = @At("HEAD"))
  private void storeChatText(CallbackInfo ci) {
    if (Configs.saveChatMessage.getBooleanValue())
      lastChatText = this.chatField.getText();
  }

  @Inject(method = "<init>(Ljava/lang/String;)V", at = @At("RETURN"))
  private void restoreText(String defaultText, CallbackInfo ci) {
    if (Configs.saveChatMessage.getBooleanValue())
      this.originalChatText = lastChatText;
  }

  @Inject(method = "keyPressed(III)Z", slice = @Slice(from = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screen/ChatScreen;sendMessage(Ljava/lang/String;)V")), at = @At(value = "INVOKE", target = "Lnet/minecraft/client/MinecraftClient;openScreen(Lnet/minecraft/client/gui/screen/Screen;)V", shift = Shift.AFTER))
  private void onSendMessage(int keyCode, int scancode, int modifiers, CallbackInfoReturnable<Boolean> cir) {
    lastChatText = "";
  }

  @ModifyConstant(method = "render", constant = @Constant(intValue = Integer.MIN_VALUE))
  private int overrideChatBackgroundColor(int original) {
    if (Configs.chatBackgroundOverride.getBooleanValue())
      return Configs.chatBackgroundColor.getIntegerValue();
    return original;
  }
}
