package org.aussiebox.starexpress.packet;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import org.aussiebox.starexpress.StarryExpress;
import org.jetbrains.annotations.NotNull;

public record AbilityC2SPacket() implements CustomPacketPayload {
    public static final ResourceLocation ABILITY_PAYLOAD_ID = StarryExpress.id("ability");
    public static final CustomPacketPayload.Type<AbilityC2SPacket> TYPE = new CustomPacketPayload.Type<>(ABILITY_PAYLOAD_ID);
    public static final StreamCodec<RegistryFriendlyByteBuf, AbilityC2SPacket> CODEC = StreamCodec.of(
            AbilityC2SPacket::write,
            AbilityC2SPacket::read
    );

    public AbilityC2SPacket() {
    }

    public CustomPacketPayload.@NotNull Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static void write(FriendlyByteBuf buf, AbilityC2SPacket packet) {

    }

    public static AbilityC2SPacket read(FriendlyByteBuf buf) {
        return new AbilityC2SPacket();
    }
}
