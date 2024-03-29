package iafenvoy.hypextension.mixins.block;

import iafenvoy.hypextension.config.Configs;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.EnderChestBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EnderChestBlock.class)
public class EnderChestBlockMixin {
    @Inject(method = "getRenderType", at = @At("HEAD"), cancellable = true)
    private void getRenderType(BlockState state, CallbackInfoReturnable<BlockRenderType> cir) {
        if (Configs.INSTANCE.fastChestRender.getBooleanValue())
            cir.setReturnValue(BlockRenderType.MODEL);
    }
}
