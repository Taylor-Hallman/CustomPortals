package dev.custom.portals.util;

import net.minecraft.network.protocol.common.custom.CustomPacketPayload.Type;
import net.minecraft.world.level.block.Block;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;

public record DrawSpritePayload(Integer colorId) implements CustomPacketPayload {
    public static final CustomPacketPayload.Type<DrawSpritePayload> ID = new CustomPacketPayload.Type<>(PortalHelper.DRAW_SPRITE_PACKET_ID);
    public static final StreamCodec<RegistryFriendlyByteBuf, DrawSpritePayload> CODEC = StreamCodec.composite(ByteBufCodecs.INT, DrawSpritePayload::colorId, DrawSpritePayload::new);
    @Override
    public Type<? extends CustomPacketPayload> type() {
        return ID;
    }
}
