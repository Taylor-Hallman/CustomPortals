package dev.custom.portals.util;

import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload.Type;

public record ScreenTransitionPayload(Boolean isTransitioning) implements CustomPacketPayload {
    public static final CustomPacketPayload.Type<ScreenTransitionPayload> ID = new CustomPacketPayload.Type<>(PortalHelper.SCREEN_TRANSITION_PACKET_ID);
    public static final StreamCodec<RegistryFriendlyByteBuf, ScreenTransitionPayload> CODEC = StreamCodec.composite(ByteBufCodecs.BOOL, ScreenTransitionPayload::isTransitioning, ScreenTransitionPayload::new);
    @Override
    public Type<? extends CustomPacketPayload> type() {
        return ID;
    }
}
