package org.aussiebox.starexpress.mixin;

import dev.doctor4t.wathe.game.GameFunctions;
import net.minecraft.server.level.ServerPlayer;
import org.aussiebox.starexpress.cca.AbilityComponent;
import org.aussiebox.starexpress.cca.StarstruckComponent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GameFunctions.class)
public abstract class ResetterMixin {

    @Inject(method = "resetPlayer", at = @At("TAIL"))
    private static void reset(ServerPlayer player, CallbackInfo ci) {
        AbilityComponent.KEY.get(player).reset();
        StarstruckComponent.KEY.get(player).reset();
    }
}
