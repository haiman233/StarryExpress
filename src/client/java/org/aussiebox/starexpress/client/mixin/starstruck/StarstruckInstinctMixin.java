package org.aussiebox.starexpress.client.mixin.starstruck;

import dev.doctor4t.wathe.cca.GameWorldComponent;
import dev.doctor4t.wathe.client.WatheClient;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import org.aussiebox.starexpress.StarryExpressRoles;
import org.aussiebox.starexpress.cca.StarstruckComponent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(WatheClient.class)
public abstract class StarstruckInstinctMixin {

    @Inject(method = "isInstinctEnabled", at = @At("HEAD"), cancellable = true)
    private static void isInstinctEnabled(CallbackInfoReturnable<Boolean> cir) {
        if (Minecraft.getInstance().player != null) {
            GameWorldComponent gameWorldComponent = GameWorldComponent.KEY.get(Minecraft.getInstance().player.level());
            StarstruckComponent component = StarstruckComponent.KEY.get(Minecraft.getInstance().player);
            if (gameWorldComponent.isRole(Minecraft.getInstance().player, StarryExpressRoles.STARSTRUCK) && component.ticks > 0) {
                cir.setReturnValue(true);
                cir.cancel();
            }
        }
    }

    @Inject(method = "getInstinctHighlight", at = @At("HEAD"), cancellable = true)
    private static void getInstinctHighlightColor(Entity target, CallbackInfoReturnable<Integer> cir) {
        if (target instanceof Player) {
            if (!target.isSpectator()) {
                if (Minecraft.getInstance().player != null) {
                    GameWorldComponent gameWorldComponent = GameWorldComponent.KEY.get(Minecraft.getInstance().player.level());
                    StarstruckComponent component = StarstruckComponent.KEY.get(Minecraft.getInstance().player);
                    if (gameWorldComponent.isRole(Minecraft.getInstance().player, StarryExpressRoles.STARSTRUCK) && component.ticks > 0) {
                        cir.setReturnValue(StarryExpressRoles.STARSTRUCK.color());
                    }
                }
            }
        }
    }
}
