package dev.custom.portals.network;

import dev.custom.portals.CustomPortals;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;

/** Server -> client: which portal color to use for the loading-screen background (0 = none). */
public record DrawSpritePayload(int colorId) implements CustomPacketPayload {
    public static final CustomPacketPayload.Type<DrawSpritePayload> TYPE =
            new CustomPacketPayload.Type<>(Identifier.fromNamespaceAndPath(CustomPortals.MOD_ID, "draw_sprite"));
    public static final StreamCodec<RegistryFriendlyByteBuf, DrawSpritePayload> STREAM_CODEC =
            StreamCodec.composite(ByteBufCodecs.INT, DrawSpritePayload::colorId, DrawSpritePayload::new);

    @Override
    public CustomPacketPayload.Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
