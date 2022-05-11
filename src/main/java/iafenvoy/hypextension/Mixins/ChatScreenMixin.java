package iafenvoy.hypextension.Mixins;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.At.Shift;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Slice;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import iafenvoy.hypextension.Config.Configs;
import iafenvoy.hypextension.Utils.InputHandler;
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
    lastChatText = this.chatField.getText();
  }

  @Inject(method = "<init>(Ljava/lang/String;)V", at = @At("RETURN"))
  private void restoreText(String defaultText, CallbackInfo ci) {
    if (Configs.INSTANCE.saveChatMessage.getBooleanValue()) {
      if (InputHandler.lastKeyCode == 47)// 47 is the ascii code of '/'
        lastChatText = "/";
      this.originalChatText = lastChatText;
    }
  }

  @Inject(method = "keyPressed(III)Z", slice = @Slice(from = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screen/ChatScreen;sendMessage(Ljava/lang/String;)V")), at = @At(value = "INVOKE", target = "Lnet/minecraft/client/MinecraftClient;openScreen(Lnet/minecraft/client/gui/screen/Screen;)V", shift = Shift.AFTER))
  private void onSendMessage(int keyCode, int scancode, int modifiers, CallbackInfoReturnable<Boolean> cir) {
    lastChatText = "";
  }
}
