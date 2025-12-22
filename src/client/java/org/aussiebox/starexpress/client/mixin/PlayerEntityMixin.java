package org.aussiebox.starexpress.client.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import dev.doctor4t.wathe.cca.GameWorldComponent;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.aussiebox.starexpress.StarryExpressRoles;
import org.aussiebox.starexpress.cca.StarstruckComponent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(value = Player.class, priority = 1500)
public abstract class PlayerEntityMixin extends LivingEntity {
    protected PlayerEntityMixin(EntityType<? extends LivingEntity> entityType, Level level) {
        super(entityType, level);
    }

    @ModifyReturnValue(
            method = {"getSpeed()F"},
            at = {@At("RETURN")}
    )
    public float overrideMovementSpeed(float original) {
        Player player = (Player) (Object) this;
        if (GameWorldComponent.KEY.get(player.level()).isRole(player, StarryExpressRoles.STARSTRUCK) && StarstruckComponent.KEY.get(player).ticks > 0) {
            return this.isSprinting() ? 0.15F : 0.12F;
        } else {
            return original;
        }
    }
}
