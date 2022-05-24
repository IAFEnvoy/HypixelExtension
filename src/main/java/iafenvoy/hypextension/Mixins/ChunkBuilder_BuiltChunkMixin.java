package iafenvoy.hypextension.Mixins;

import iafenvoy.hypextension.Data.Config.Configs;
import net.minecraft.client.render.chunk.ChunkBuilder;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ChunkBuilder.BuiltChunk.class)
public abstract class ChunkBuilder_BuiltChunkMixin {
  @Inject(method = "isChunkNonEmpty", at = @At("HEAD"), cancellable = true)
  private void allowEdgeChunksToRender(BlockPos pos, CallbackInfoReturnable<Boolean> cir) {
    if (Configs.INSTANCE.renderEdgeChunks.getBooleanValue())
      cir.setReturnValue(true);
  }
}
