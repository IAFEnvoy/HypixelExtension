package iafenvoy.hypextension.mixins.gui;

import iafenvoy.hypextension.config.Configs;
import iafenvoy.hypextension.ingame.function.AutoFriend;
import iafenvoy.hypextension.ingame.function.AutoGG;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.hud.ChatHud;
import net.minecraft.text.*;
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
        if (Configs.INSTANCE.autoFriend.getBooleanValue())
            AutoFriend.execute(text.getString());
        if (Configs.INSTANCE.autoGG.getBooleanValue())
            AutoGG.execute(text.getString());
        if (Configs.INSTANCE.copyChatButton.getBooleanValue())
            if (text instanceof MutableText) {
                LiteralText copyButton = new LiteralText(" [C]");
                Style style = Style.EMPTY;
                style = style.withColor(TextColor.parse("#888888"));
                style = style.withClickEvent(new ClickEvent(ClickEvent.Action.COPY_TO_CLIPBOARD, text.getString()));
                style = style.withHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new TranslatableText("hypextension.copyToClipboard")));
                copyButton.setStyle(style);
                ((MutableText) text).append(copyButton);
            }
        if (Configs.INSTANCE.chatTimeStamp.getBooleanValue()) {
            LiteralText newText = new LiteralText(new SimpleDateFormat("[HH:mm:ss]").format(new Date(System.currentTimeMillis())) + " ");
            newText.append(text);
            return newText;
        }
        return text;
    }
}
