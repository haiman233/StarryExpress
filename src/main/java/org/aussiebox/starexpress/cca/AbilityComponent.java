package org.aussiebox.starexpress.cca;

import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import org.aussiebox.starexpress.StarryExpress;
import org.jetbrains.annotations.NotNull;
import org.ladysnake.cca.api.v3.component.ComponentKey;
import org.ladysnake.cca.api.v3.component.ComponentRegistry;
import org.ladysnake.cca.api.v3.component.sync.AutoSyncedComponent;
import org.ladysnake.cca.api.v3.component.tick.ClientTickingComponent;
import org.ladysnake.cca.api.v3.component.tick.ServerTickingComponent;

public class AbilityComponent implements AutoSyncedComponent, ServerTickingComponent, ClientTickingComponent {
    public static final ComponentKey<AbilityComponent> KEY = ComponentRegistry.getOrCreate(ResourceLocation.fromNamespaceAndPath(StarryExpress.MOD_ID, "ability"), AbilityComponent.class);
    private final Player player;
    public int cooldown = 0;


    public AbilityComponent(Player player) {
        this.player = player;
    }

    public void clientTick() {
    }

    public void serverTick() {
        if (this.cooldown > 0) {
            --this.cooldown;
            this.sync();
        }

    }

    public void setCooldown(int ticks) {
        this.cooldown = ticks;
        this.sync();
    }

    public void changeCooldown(int ticks) {
        this.cooldown += ticks;
        if (this.cooldown < 0) {
            this.cooldown = 0;
        }
        this.sync();
    }

    public void sync() {
        KEY.sync(this.player);
    }

    public void reset() {
        this.cooldown = 0;
        this.sync();
    }

    public void writeToNbt(@NotNull CompoundTag tag, HolderLookup.@NotNull Provider registryLookup) {
        tag.putInt("cooldown", this.cooldown);
    }

    public void readFromNbt(@NotNull CompoundTag tag, HolderLookup.@NotNull Provider registryLookup) {
        this.cooldown = tag.contains("cooldown") ? tag.getInt("cooldown") : 0;
    }
}
