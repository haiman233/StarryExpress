package org.aussiebox.starexpress.mixin.allergic;

import dev.doctor4t.wathe.cca.PlayerPoisonComponent;
import dev.doctor4t.wathe.item.CocktailItem;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.aussiebox.starexpress.StarryExpressModifiers;
import org.aussiebox.starexpress.cca.AllergicComponent;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;

@Mixin(Player.class)
public abstract class AllergicEatMixin extends LivingEntity {

    protected AllergicEatMixin(EntityType<? extends LivingEntity> entityType, Level level) {
        super(entityType, level);
    }

    @Inject(
            method = {"eat(Lnet/minecraft/world/level/Level;Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/food/FoodProperties;)Lnet/minecraft/world/item/ItemStack;"},
            at = {@At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/food/FoodData;eat(Lnet/minecraft/world/food/FoodProperties;)V",
                    shift = At.Shift.AFTER
            )}
    )
    private void allergicConsume(@NotNull Level world, ItemStack stack, FoodProperties foodComponent, CallbackInfoReturnable<ItemStack> cir) {
        if (world.isClientSide) return;

        Player player = (Player) (Object) this;
        AllergicComponent allergy = AllergicComponent.KEY.get(player);

        if (!allergy.isAllergic()) return;
        if (Objects.equals(allergy.getAllergyType(), "food") && (stack.getItem() instanceof CocktailItem)) return;
        if (Objects.equals(allergy.getAllergyType(), "drink") && !(stack.getItem() instanceof CocktailItem)) return;

        int random = ThreadLocalRandom.current().nextInt(0, 6);
        if (random == 0) {
            int poisonTicks = PlayerPoisonComponent.KEY.get(player).poisonTicks;
            if (poisonTicks == -1) {
                PlayerPoisonComponent.KEY.get(player).setPoisonTicks(world.getRandom().nextIntBetweenInclusive(PlayerPoisonComponent.clampTime.getA(), PlayerPoisonComponent.clampTime.getB()), player.getUUID());
            } else {
                PlayerPoisonComponent.KEY.get(player).setPoisonTicks(Mth.clamp(poisonTicks - world.getRandom().nextIntBetweenInclusive(100, 300), 0, PlayerPoisonComponent.clampTime.getB()), player.getUUID());
            }

            player.sendSystemMessage(
                    Component.translatable(
                            "hud.allergic.effect.poison"
                    ).withColor(StarryExpressModifiers.ALLERGIC.color())
            );
        }
        if (random == 1) {
            allergy.setGlowTicks(60);
            allergy.sync();

            player.displayClientMessage(
                    Component.translatable(
                            "hud.allergic.effect.instinct"
                    ).withColor(StarryExpressModifiers.ALLERGIC.color()),
                    true
            );
        }
        if (random == 2) {
            allergy.giveArmor();

            player.displayClientMessage(
                    Component.translatable(
                            "hud.allergic.effect.armor"
                    ).withColor(StarryExpressModifiers.ALLERGIC.color()),
                    true
            );
        }
    }
}
