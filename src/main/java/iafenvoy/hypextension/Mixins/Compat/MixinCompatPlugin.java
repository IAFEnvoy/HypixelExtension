package iafenvoy.hypextension.Mixins.Compat;

import java.util.List;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.objectweb.asm.tree.ClassNode;
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;

import net.fabricmc.loader.api.FabricLoader;

public class MixinCompatPlugin implements IMixinConfigPlugin {
  Logger logger = LogManager.getLogger("HypixelExtension | Mixin Compat");

  @Override
  public void onLoad(String mixinPackage) {
  }

  @Override
  public String getRefMapperConfig() {
    return null;
  }

  @Override
  public boolean shouldApplyMixin(String targetClassName, String mixinClassName) {
    if (FabricLoader.getInstance().isModLoaded("kronhud")) {
      logger.info("KronHud detected, inject HypixelExtension's Mixin compat");
      return true;
    } else {
      logger.info("KronHud not detected, skip HypixelExtension's Mixin compat");
      return false;
    }
  }

  @Override
  public void acceptTargets(Set<String> myTargets, Set<String> otherTargets) {
  }

  @Override
  public List<String> getMixins() {
    return null;
  }

  @Override
  public void preApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {
  }

  @Override
  public void postApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {
  }
}
