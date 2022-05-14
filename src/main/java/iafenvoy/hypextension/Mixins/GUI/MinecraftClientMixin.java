package iafenvoy.hypextension.Mixins.GUI;

import iafenvoy.hypextension.Config.Configs;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.DownloadingTerrainScreen;
import net.minecraft.client.gui.screen.Screen;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MinecraftClient.class)
public abstract class MinecraftClientMixin {
  @Shadow
  public abstract void openScreen(@Nullable Screen screen);

  @Inject(at = @At("HEAD"), method = "openScreen", cancellable = true)
  public void setScreen(Screen screen, CallbackInfo ci) {
    if (Configs.INSTANCE.forceCloseLoadingScreen.getBooleanValue() && screen instanceof DownloadingTerrainScreen) {
      ci.cancel();
      openScreen(null);
    }
  }
}
