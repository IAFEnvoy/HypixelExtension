package iafenvoy.hypextension.Mixins.GUI;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import iafenvoy.hypextension.Config.Configs;
import iafenvoy.hypextension.Utils.Functions.AutoFriend;
import iafenvoy.hypextension.Utils.Functions.AutoGG;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.hud.ChatHud;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;

@Mixin(value = ChatHud.class, priority = 1100)
public abstract class ChatHudMixin extends DrawableHelper {
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
