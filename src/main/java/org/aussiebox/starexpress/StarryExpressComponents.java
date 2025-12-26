package org.aussiebox.starexpress;

import net.minecraft.world.entity.player.Player;
import org.aussiebox.starexpress.cca.AbilityComponent;
import org.aussiebox.starexpress.cca.AllergicComponent;
import org.aussiebox.starexpress.cca.StarstruckComponent;
import org.ladysnake.cca.api.v3.entity.EntityComponentFactoryRegistry;
import org.ladysnake.cca.api.v3.entity.EntityComponentInitializer;
import org.ladysnake.cca.api.v3.entity.RespawnCopyStrategy;
import org.ladysnake.cca.api.v3.world.WorldComponentFactoryRegistry;
import org.ladysnake.cca.api.v3.world.WorldComponentInitializer;

public class StarryExpressComponents implements EntityComponentInitializer, WorldComponentInitializer {

    @Override
    public void registerEntityComponentFactories(EntityComponentFactoryRegistry registry) {
        registry.beginRegistration(Player.class, AbilityComponent.KEY)
                .respawnStrategy(RespawnCopyStrategy.NEVER_COPY)
                .end(AbilityComponent::new);
        registry.beginRegistration(Player.class, StarstruckComponent.KEY)
                .respawnStrategy(RespawnCopyStrategy.NEVER_COPY)
                .end(StarstruckComponent::new);
        registry.beginRegistration(Player.class, AllergicComponent.KEY)
                .respawnStrategy(RespawnCopyStrategy.NEVER_COPY)
                .end(AllergicComponent::new);
    }

    @Override
    public void registerWorldComponentFactories(WorldComponentFactoryRegistry registry) {

    }
}
