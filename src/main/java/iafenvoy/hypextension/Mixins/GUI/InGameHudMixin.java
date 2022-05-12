package iafenvoy.hypextension.Mixins.GUI;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import iafenvoy.hypextension.Config.Configs;
import iafenvoy.hypextension.Utils.RenderUtils;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.gui.hud.PlayerListHud;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.scoreboard.ScoreboardObjective;

@Mixin(InGameHud.class)
public abstract class InGameHudMixin extends DrawableHelper {
  @Shadow
  @Final
  private PlayerListHud playerListHud;
  @Shadow
  @Final
  private MinecraftClient client;
  @Shadow
  private int scaledWidth;

  @Inject(method = "renderCrosshair", at = @At(value = "FIELD", target = "Lnet/minecraft/client/option/GameOptions;debugEnabled:Z", ordinal = 0), cancellable = true)
  private void overrideCursorRender(CallbackInfo ci) {
    if (Configs.INSTANCE.f3Cursor.getBooleanValue()) {
      RenderUtils.renderDirectionsCursor(this.getZOffset(), this.client.getTickDelta());
      ci.cancel();
    }
  }

  @Inject(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/hud/PlayerListHud;tick(Z)V", ordinal = 1, shift = At.Shift.AFTER))
  private void alwaysRenderPlayerList(MatrixStack matrixStack, float partialTicks, CallbackInfo ci) {
    if (Configs.INSTANCE.playerListAlwaysOn.getBooleanValue()) {
      Scoreboard scoreboard = this.client.world.getScoreboard();
      ScoreboardObjective objective = scoreboard.getObjectiveForSlot(0);

      this.playerListHud.tick(true);
      this.playerListHud.render(matrixStack, this.scaledWidth, scoreboard, objective);
    }
  }
}
