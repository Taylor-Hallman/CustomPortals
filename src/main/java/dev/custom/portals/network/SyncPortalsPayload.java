package dev.custom.portals.network;

import java.util.List;

import dev.custom.portals.CustomPortals;
import dev.custom.portals.data.CustomPortal;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;

/** Server -> client: full portal registry. */
public record SyncPortalsPayload(List<CustomPortal> portals) implements CustomPacketPayload {
    public static final CustomPacketPayload.Type<SyncPortalsPayload> TYPE =
            new CustomPacketPayload.Type<>(Identifier.fromNamespaceAndPath(CustomPortals.MOD_ID, "sync_portals"));

    public static final StreamCodec<RegistryFriendlyByteBuf, SyncPortalsPayload> STREAM_CODEC = StreamCodec.composite(
            CustomPortal.STREAM_CODEC.apply(ByteBufCodecs.list()), SyncPortalsPayload::portals,
            SyncPortalsPayload::new);

    @Override
    public CustomPacketPayload.Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
