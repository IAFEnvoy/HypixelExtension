package iafenvoy.hypextension.Utils;

import iafenvoy.hypextension.Data.Config.Configs;
import net.fabricmc.fabric.api.object.builder.v1.client.model.FabricModelPredicateProviderRegistry;
import net.minecraft.item.Items;
import net.minecraft.item.SwordItem;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ItemsUtil {
  public static void registerProviders() {
    // code from "Sword Blocking" mod by midnightdust team
    // License: MIT
    Registry.ITEM.forEach((item) -> {
      if (item instanceof SwordItem) {
        FabricModelPredicateProviderRegistry.register(item, new Identifier("blocking"), (stack, world,
            entity) -> entity != null && entity.getOffHandStack().getItem().equals(Items.SHIELD) && entity.isUsingItem()
                && Configs.INSTANCE.sword_1_8_9.getBooleanValue()
                    ? 1.0F
                    : 0.0F);
      }
    });
  }
}
