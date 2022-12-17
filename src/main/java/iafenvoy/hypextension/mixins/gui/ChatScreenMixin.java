package iafenvoy.hypextension.mixins.gui;

import iafenvoy.hypextension.config.Configs;
import net.minecraft.client.gui.screen.ChatScreen;
import net.minecraft.client.gui.widget.TextFieldWidget;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.At.Shift;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Slice;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ChatScreen.class)
public abstract class ChatScreenMixin {
    private static String lastChatText = "";
    private static boolean shouldSave;
    @Shadow
    protected TextFieldWidget chatField;
    @Shadow
    private String originalChatText;

    @Inject(method = "removed", at = @At("HEAD"))
    private void storeChatText(CallbackInfo ci) {
        if (shouldSave)
            lastChatText = this.chatField.getText();
    }

    @Inject(method = "<init>", at = @At("RETURN"))
    private void restoreText(String defaultText, CallbackInfo ci) {
        if (Configs.INSTANCE.saveChatInput.getBooleanValue()) {
            if (defaultText.equals("/"))
                this.originalChatText = "/";
            else
                this.originalChatText = lastChatText;
            shouldSave = !defaultText.equals("/");
        }
    }

    @Inject(method = "keyPressed(III)Z", slice = @Slice(from = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screen/ChatScreen;sendMessage(Ljava/lang/String;)V")), at = @At(value = "INVOKE", target = "Lnet/minecraft/client/MinecraftClient;openScreen(Lnet/minecraft/client/gui/screen/Screen;)V", shift = Shift.AFTER))
    private void onSendMessage(int keyCode, int scancode, int modifiers, CallbackInfoReturnable<Boolean> cir) {
        lastChatText = "";
    }
}
