package iafenvoy.hypextension.Mixins.GUI;

import iafenvoy.hypextension.Data.Config.Configs;
import iafenvoy.hypextension.Event.ChatReceive;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.hud.ChatHud;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.text.SimpleDateFormat;
import java.util.Date;

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
    private Text addMessageHandler(Text text) {
        ChatReceive.onChatReceive(text.getString());
        if (Configs.INSTANCE.chatTimeStamp.getBooleanValue()) {
            LiteralText newText = new LiteralText(new SimpleDateFormat("[HH:mm:ss]").format(new Date(System.currentTimeMillis())) + " ");
            newText.append(text);
            return newText;
        }
        return text;
    }
}
