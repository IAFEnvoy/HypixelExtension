package iafenvoy.hypextension.mixins;

import iafenvoy.hypextension.ingame.function.KeepAliveDetect;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.network.packet.s2c.play.KeepAliveS2CPacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPlayNetworkHandler.class)
public class ClientPlayNetworkHandlerMixin {
    @Inject(method = "onKeepAlive", at = @At("RETURN"))
    private void onKeepAlive(KeepAliveS2CPacket packet, CallbackInfo ci) {
        KeepAliveDetect.onKeepAlivePacket();
    }
}
