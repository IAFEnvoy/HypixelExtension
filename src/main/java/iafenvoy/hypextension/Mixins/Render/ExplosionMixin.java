package iafenvoy.hypextension.Mixins.Render;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.Slice;

import iafenvoy.hypextension.Config.Configs;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.world.explosion.Explosion;

@Mixin(Explosion.class)
public abstract class ExplosionMixin {
  @Redirect(method = "affectWorld", slice = @Slice(from = @At("HEAD"), to = @At(value = "FIELD", target = "Lnet/minecraft/world/explosion/Explosion;affectedBlocks:Ljava/util/List;")), at = @At(value = "FIELD", target = "Lnet/minecraft/particle/ParticleTypes;EXPLOSION_EMITTER:Lnet/minecraft/particle/DefaultParticleType;"))
  private DefaultParticleType redirectSpawnParticles() {
    if (Configs.INSTANCE.reduceExplosionParticles.getBooleanValue())
      return ParticleTypes.EXPLOSION;
    return ParticleTypes.EXPLOSION_EMITTER;
  }
}
