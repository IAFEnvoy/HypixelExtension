package iafenvoy.hypextension.Mixins;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import iafenvoy.hypextension.Config.Configs;
import iafenvoy.hypextension.Functions.AutoFriend;
import iafenvoy.hypextension.Functions.AutoGG;
import iafenvoy.hypextension.Functions.ChatFilter;
import iafenvoy.hypextension.Utils.MiscUtils;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.hud.ChatHud;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;

@Mixin(value = ChatHud.class, priority = 1100)
public abstract class ChatHudMixin extends DrawableHelper {
  @ModifyVariable(method = "addMessage(Lnet/minecraft/text/Text;I)V", at = @At("HEAD"), argsOnly = true)
  private Text addMessageHandler(Text componentIn) {
    if (Configs.autoGG.getBooleanValue())
      AutoGG.checkMessage(componentIn.getString());
    if (Configs.autoFriend.getBooleanValue())
      AutoFriend.checkMessage(componentIn.getString());
    if (Configs.chatTimeStamp.getBooleanValue()) {
      LiteralText newComponent = new LiteralText(MiscUtils.getChatTimestamp() + " ");
      newComponent.append(componentIn);
      return newComponent;
    }
    return componentIn;
  }

  @Inject(method = "addMessage(Lnet/minecraft/text/Text;IIZ)V", at = @At("HEAD"), cancellable = true)
  private void addMessage(Text message, int messageId, int timestamp, boolean refresh, CallbackInfo ret) {
    if (Configs.chatFilter.getBooleanValue() && !ChatFilter.checkMessage(message.getString())) {
      System.out.println("Chat Blocked !");
      ret.cancel();
    }
  }

  @Redirect(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/hud/ChatHud;fill(Lnet/minecraft/client/util/math/MatrixStack;IIIII)V", ordinal = 0))
  private void overrideChatBackgroundColor(net.minecraft.client.util.math.MatrixStack matrixStack, int left, int top,
      int right, int bottom, int color) {
    if (Configs.chatBackgroundOverride.getBooleanValue())
      color = MiscUtils.getChatBackgroundColor(color);
    fill(matrixStack, left, top, right, bottom, color);
  }
}
