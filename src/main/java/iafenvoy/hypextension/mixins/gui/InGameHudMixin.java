package iafenvoy.hypextension.mixins.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import fi.dy.masa.malilib.util.GuiUtils;
import iafenvoy.hypextension.config.Configs;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.gui.hud.PlayerListHud;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.scoreboard.ScoreboardObjective;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@SuppressWarnings("ALL")
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
            RenderSystem.pushMatrix();
            int width = GuiUtils.getScaledWindowWidth();
            int height = GuiUtils.getScaledWindowHeight();
            float partialTicks = this.client.getTickDelta();
            RenderSystem.translated(width / 2, height / 2, this.getZOffset());
            Entity entity = client.getCameraEntity();
            assert entity != null;
            RenderSystem.rotatef(entity.prevPitch + (entity.pitch - entity.prevPitch) * partialTicks, -1.0F, 0.0F, 0.0F);
            RenderSystem.rotatef(entity.prevYaw + (entity.yaw - entity.prevYaw) * partialTicks, 0.0F, 1.0F, 0.0F);
            RenderSystem.scalef(-1.0F, -1.0F, -1.0F);
            RenderSystem.renderCrosshair(10);
            RenderSystem.popMatrix();
            ci.cancel();
        }
    }

    @Inject(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/hud/PlayerListHud;tick(Z)V", ordinal = 1, shift = At.Shift.AFTER))
    private void alwaysRenderPlayerList(MatrixStack matrices, float tick, CallbackInfo ci) {
        if (Configs.INSTANCE.playerListAlwaysOn.getBooleanValue()) {
            assert this.client.world != null;
            Scoreboard scoreboard = this.client.world.getScoreboard();
            ScoreboardObjective objective = scoreboard.getObjectiveForSlot(0);
            this.playerListHud.tick(true);
            this.playerListHud.render(matrices, this.scaledWidth, scoreboard, objective);
        }
    }

    @Redirect(method = "renderScoreboardSidebar", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/font/TextRenderer;draw(Lnet/minecraft/client/util/math/MatrixStack;Ljava/lang/String;FFI)I"))
    private int deleteScoreRender(TextRenderer renderer, MatrixStack matrices, String text, float x, float y, int color) {
        if (!Configs.INSTANCE.scoreboardFix.getBooleanValue())
            return renderer.draw(matrices, text, x, y, color);
        return 0;
    }
}
