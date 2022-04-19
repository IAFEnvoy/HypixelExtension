package iafenvoy.hypextension.Mixins;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.Redirect;

import iafenvoy.hypextension.Config.Configs.Options;
import iafenvoy.hypextension.Functions.AutoGG;
import iafenvoy.hypextension.Utils.MiscUtils;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.hud.ChatHud;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;

@Mixin(value = ChatHud.class, priority = 1100)
public abstract class ChatHudMixin extends DrawableHelper {
  @ModifyVariable(method = "addMessage(Lnet/minecraft/text/Text;I)V", at = @At("HEAD"), argsOnly = true)
  private Text addMessageHandler(Text componentIn) {
    if (Options.autoGG.getBooleanValue())
      AutoGG.checkMessage(componentIn.getString());
    if (Options.chatTimeStamp.getBooleanValue()) {
      LiteralText newComponent = new LiteralText(MiscUtils.getChatTimestamp() + " ");
      newComponent.append(componentIn);
      return newComponent;
    }
    return componentIn;
  }

  @Redirect(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/hud/ChatHud;fill(Lnet/minecraft/client/util/math/MatrixStack;IIIII)V", ordinal = 0))
  private void overrideChatBackgroundColor(net.minecraft.client.util.math.MatrixStack matrixStack, int left, int top,
      int right, int bottom, int color) {
    if (Options.chatBackgroundOverride.getBooleanValue())
      color = MiscUtils.getChatBackgroundColor(color);
    fill(matrixStack, left, top, right, bottom, color);
  }
}
