package iafenvoy.hypextension.Mixins.GUI;

import iafenvoy.hypextension.Data.Config.Configs;
import iafenvoy.hypextension.Event.ChatReceive;
import java.text.SimpleDateFormat;
import java.util.Date;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.hud.ChatHud;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

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
    ChatReceive.onChatReceive(componentIn.getString());
    if (Configs.INSTANCE.chatTimeStamp.getBooleanValue()) {
      LiteralText newComponent = new LiteralText(
          new SimpleDateFormat("[HH:mm:ss]").format(new Date(System.currentTimeMillis())) + " ");
      newComponent.append(componentIn);
      return newComponent;
    }
    return componentIn;
  }
}
