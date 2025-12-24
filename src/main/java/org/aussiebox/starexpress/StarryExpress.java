package org.aussiebox.starexpress;

import dev.doctor4t.wathe.cca.GameWorldComponent;
import dev.doctor4t.wathe.game.GameConstants;
import dev.doctor4t.wathe.game.GameFunctions;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import org.aussiebox.starexpress.block.ModBlocks;
import org.aussiebox.starexpress.block.entity.ModBlockEntities;
import org.aussiebox.starexpress.cca.AbilityComponent;
import org.aussiebox.starexpress.cca.StarstruckComponent;
import org.aussiebox.starexpress.item.ModItems;
import org.aussiebox.starexpress.packet.AbilityC2SPacket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StarryExpress implements ModInitializer {

    public static String MOD_ID = "starexpress";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitialize() {
        ModSounds.init();
        ModBlockEntities.init();
        ModBlocks.init();
        ModItems.init();

        StarryExpressRoles.init();
        StarryExpressModifiers.init();

        PayloadTypeRegistry.playC2S().register(AbilityC2SPacket.TYPE, AbilityC2SPacket.CODEC);

        registerPackets();
    }

    public void registerPackets() {
        ServerPlayNetworking.registerGlobalReceiver(AbilityC2SPacket.TYPE, (payload, context) -> {
            AbilityComponent abilityComponent = AbilityComponent.KEY.get(context.player());
            GameWorldComponent gameWorldComponent = GameWorldComponent.KEY.get(context.player().level());

            if (!GameFunctions.isPlayerAliveAndSurvival(context.player())) return;

            if (gameWorldComponent.isRole(context.player(), StarryExpressRoles.STARSTRUCK) && abilityComponent.cooldown <= 0) {
                abilityComponent.setCooldown(GameConstants.getInTicks(1, 30));
                StarstruckComponent.KEY.get(context.player()).setTicks(GameConstants.getInTicks(0, 15));

                ServerLevel level = context.player().serverLevel();
                level.playSound(null, BlockPos.containing(context.player().position()), SoundEvents.RESPAWN_ANCHOR_CHARGE, SoundSource.PLAYERS, 1.0F, 1.0F);
                level.sendParticles(ParticleTypes.END_ROD, context.player().getX(), context.player().getY(), context.player().getZ(), 75,  0.5,  1.5,  0.5,  0.1);
            }

        });
    }

    public static ResourceLocation id(String key) {
        return ResourceLocation.fromNamespaceAndPath(MOD_ID, key);
    }

}
