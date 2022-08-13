package iafenvoy.hypextension.Mixins.Compat;

import iafenvoy.hypextension.Data.Config.Configs;
import io.github.darkkronicle.kronhud.gui.hud.ScoreboardHud;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.util.math.MatrixStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Pseudo
@Mixin(ScoreboardHud.class)
public class ScoreboardHudMixin {
    @Redirect(method = "renderScoreboardSidebar", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/font/TextRenderer;draw(Lnet/minecraft/client/util/math/MatrixStack;Ljava/lang/String;FFI)I"))
    private int deleteScoreRender(TextRenderer renderer, MatrixStack matrices, String text, float x, float y, int color) {
        if (!Configs.INSTANCE.scoreboardFix.getBooleanValue())
            return renderer.draw(matrices, text, x, y, color);
        return 0;
    }
}
