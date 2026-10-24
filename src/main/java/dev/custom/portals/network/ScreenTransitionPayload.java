package dev.custom.portals.network;

import dev.custom.portals.CustomPortals;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;

/** Client -> server: whether the loading screen with the portal background is showing. */
public record ScreenTransitionPayload(boolean isTransitioning) implements CustomPacketPayload {
    public static final CustomPacketPayload.Type<ScreenTransitionPayload> TYPE =
            new CustomPacketPayload.Type<>(Identifier.fromNamespaceAndPath(CustomPortals.MOD_ID, "is_in_transition"));
    public static final StreamCodec<RegistryFriendlyByteBuf, ScreenTransitionPayload> STREAM_CODEC =
            StreamCodec.composite(ByteBufCodecs.BOOL, ScreenTransitionPayload::isTransitioning, ScreenTransitionPayload::new);

    @Override
    public CustomPacketPayload.Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
