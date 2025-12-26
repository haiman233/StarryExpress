package org.aussiebox.starexpress;

import dev.doctor4t.wathe.api.event.AllowPlayerDeath;
import dev.doctor4t.wathe.cca.PlayerPoisonComponent;
import dev.doctor4t.wathe.index.WatheSounds;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import org.agmas.harpymodloader.events.ModifierAssigned;
import org.agmas.harpymodloader.modifiers.HMLModifiers;
import org.agmas.harpymodloader.modifiers.Modifier;
import org.aussiebox.starexpress.cca.AllergicComponent;

import java.util.concurrent.ThreadLocalRandom;

public class StarryExpressModifiers {

    public static Modifier ALLERGIC = HMLModifiers.registerModifier(new Modifier(
            StarryExpress.id("allergic"),
            0x70ffa2,
            null,
            null,
            false,
            false
    ));

    public static void init() {

        assignModifierComponents();

        /// ALLERGIC
        AllowPlayerDeath.EVENT.register(((victim, killer, resourceLocation) -> {
            AllergicComponent allergy = AllergicComponent.KEY.get(victim);
            PlayerPoisonComponent poison = PlayerPoisonComponent.KEY.get(victim);
            if (allergy.isAllergic()) {
                if (poison.poisoner != victim.getUUID()) {
                    if (allergy.armor > 0) {
                        victim.level().playSound(victim, victim.getOnPos().above(1), WatheSounds.ITEM_PSYCHO_ARMOUR, SoundSource.MASTER, 5.0F, 1.0F);
                        poison.setPoisonTicks(-1, victim.getUUID());
                        allergy.armor--;
                        return false;
                    }
                }
            }
            return true;
        }));

    }

    public static void assignModifierComponents() {

        /// ALLERGIC
        ModifierAssigned.EVENT.register(((player, modifier) -> {
            if (!modifier.equals(ALLERGIC)) {
                return;
            }
            if (!(player instanceof ServerPlayer allergicPlayer)) {
                return;
            }

            var allergicComponent = AllergicComponent.KEY.get(allergicPlayer);

            allergicComponent.setAllergic(allergicPlayer.getUUID());
            allergicComponent.setAllergyType(ThreadLocalRandom.current().nextBoolean() ? "food" : "drink");
            allergicComponent.sync();

        }));

    }

}
