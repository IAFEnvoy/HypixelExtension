package iafenvoy.hypextension.Render;

import iafenvoy.hypextension.Data.Config.Configs;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.option.Perspective;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.scoreboard.ScoreboardObjective;
import net.minecraft.scoreboard.ScoreboardPlayerScore;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.util.math.Matrix4f;
import net.minecraft.util.math.Vec3f;

public class PlayerTagRenderer<T extends LivingEntity, M extends EntityModel<T>> extends FeatureRenderer<T, M> {
  private static final MinecraftClient client = MinecraftClient.getInstance();
  private EntityRenderDispatcher dispatcher;

  public PlayerTagRenderer(FeatureRendererContext<T, M> context) {
    super(context);
  }

  @Override
  public void render(MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int light, T entity,
      float limbAngle, float limbDistance, float tickDelta, float animationProgress, float headYaw, float headPitch) {
    if (!Configs.INSTANCE.renderOwnNameTag.getBooleanValue())
      return;
    if (entity.isSpectator() || entity.isInvisible() || !entity.isAlive())
      return;
    if (entity instanceof PlayerEntity) {
      PlayerEntity player = (PlayerEntity) entity;
      if (player.getName().equals(client.player.getName())) {
        this.dispatcher = client.getEntityRenderDispatcher();
        matrixStack.push();
        Scoreboard scoreboard = player.getScoreboard();
        ScoreboardObjective scoreboardObjective = scoreboard.getObjectiveForSlot(2);
        if (scoreboardObjective != null) {
          ScoreboardPlayerScore scoreboardPlayerScore = scoreboard.getPlayerScore(player.getEntityName(),
              scoreboardObjective);
          renderLabelIfPresent(entity, (Text) (new LiteralText(Integer.toString(scoreboardPlayerScore.getScore())))
              .append(" ").append(scoreboardObjective.getDisplayName()), matrixStack, vertexConsumerProvider, light,
              tickDelta, headYaw, headPitch);
          dispatcher.getTextRenderer().getClass();
          matrixStack.translate(0.0D, (double) (9.0F * 1.15F * 0.025F), 0.0D);
        }
        renderLabelIfPresent(entity, player.getDisplayName(), matrixStack, vertexConsumerProvider, light, tickDelta,
            headYaw, headPitch);
        matrixStack.pop();
      }
    }
  }

  private void renderLabelIfPresent(T entity, Text text, MatrixStack matrices, VertexConsumerProvider vertexConsumers,
      int light, float tickDelta, float headYaw, float headPitch) {
    boolean bl = !entity.isSneaky();
    int i = "deadmau5".equals(text.getString()) ? -10 : 0;
    matrices.push();
    matrices.translate(0, -0.8, 0);

    if (client.options.getPerspective() == Perspective.THIRD_PERSON_BACK) {
      headYaw += 180;
      headPitch = -headPitch;
    }
    matrices.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(headYaw));
    matrices.multiply(Vec3f.POSITIVE_X.getDegreesQuaternion(headPitch));
    float rotate = 180;
    matrices.multiply(Vec3f.POSITIVE_Z.getDegreesQuaternion(rotate));

    matrices.scale(-0.025F, -0.025F, 0.025F);
    Matrix4f matrix4f = matrices.peek().getModel();
    float g = client.options.getTextBackgroundOpacity(0.25F);
    int j = (int) (g * 255.0F) << 24;
    TextRenderer textRenderer = dispatcher.getTextRenderer();
    float h = (float) (-textRenderer.getWidth(text) / 2);
    textRenderer.draw(text, h, (float) i, 553648127, false, matrix4f, vertexConsumers, bl, j, light);
    if (bl)
      textRenderer.draw(text, h, (float) i, -1, false, matrix4f, vertexConsumers, false, 0, light);
    matrices.pop();
  }
}
