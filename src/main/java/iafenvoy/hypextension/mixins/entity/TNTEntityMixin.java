package iafenvoy.hypextension.mixins.entity;

//Code from "Time To Life" mod

import net.minecraft.entity.TntEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(TntEntity.class)
public interface TNTEntityMixin {
    @Accessor("fuseTimer")
    int getCurrentFuseTime();
}
