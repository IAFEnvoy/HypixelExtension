package iafenvoy.hypextension.Mixins.GUI;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import iafenvoy.hypextension.Config.Configs;
import iafenvoy.hypextension.Utils.Functions.AutoFriend;
import iafenvoy.hypextension.Utils.Functions.AutoGG;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.hud.ChatHud;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;

@Mixin(value = ChatHud.class, priority = 1100)
public abstract class ChatHudMixin extends DrawableHelper {
  @Inject(method = "clear", at = @At("HEAD"), cancellable = true)
  private void onClear(boolean clearHistory, CallbackInfo ci) {
    if (Configs.INSTANCE.saveChatHistory.getBooleanValue())
      ci.cancel();
  }

  @ModifyConstant(method = "addMessage(Lnet/minecraft/text/Text;IIZ)V", constant = @Constant(intValue = 100), expect = 2)
  public int changeMaxHistory(int original) {
    return Configs.INSTANCE.extendHistorySize.getBooleanValue() ? 16384 : original;
  }

  @ModifyVariable(method = "addMessage(Lnet/minecraft/text/Text;IIZ)V", at = @At("HEAD"), argsOnly = true)
  private Text addMessageHandler(Text componentIn) {
    if (Configs.INSTANCE.autoGG.getBooleanValue())
      AutoGG.checkMessage(componentIn.getString());
    if (Configs.INSTANCE.autoFriend.getBooleanValue())
      AutoFriend.checkMessage(componentIn.getString());
    if (Configs.INSTANCE.chatTimeStamp.getBooleanValue()) {
      LiteralText newComponent = new LiteralText(
          new SimpleDateFormat("[HH:mm:ss]").format(new Date(System.currentTimeMillis())) + " ");
      newComponent.append(componentIn);
      return newComponent;
    }
    return componentIn;
  }
}
