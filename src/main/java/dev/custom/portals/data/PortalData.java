package dev.custom.portals.data;

import java.util.List;

import dev.custom.portals.network.SyncPortalsPayload;
import net.minecraft.core.BlockPos;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.network.PacketDistributor;
import org.jetbrains.annotations.Nullable;

/** Single access point for the portal registry on either side. Replaces the Cardinal Components level component. */
public final class PortalData {
    private PortalData() {}

    /** Server: the saved registry. Client: the last synced copy. */
    public static PortalRegistry get(Level level) {
        if (level instanceof ServerLevel serverLevel) {
            return PortalSavedData.get(serverLevel.getServer()).getRegistry();
        }
        return ClientPortalData.registry();
    }

    @Nullable
    public static CustomPortal getPortalFromPos(Level level, BlockPos pos) {
        return get(level).getPortalFromPos(level.dimension().identifier().toString(), pos);
    }

    /** Call after mutating the registry on the server: persists and pushes to all clients. No-op on the client. */
    public static void markChanged(Level level) {
        if (level instanceof ServerLevel serverLevel) {
            MinecraftServer server = serverLevel.getServer();
            PortalSavedData data = PortalSavedData.get(server);
            data.setDirty();
            PacketDistributor.sendToAllPlayers(snapshotPayload(data.getRegistry()));
        }
    }

    /** Send the full registry to one player (on login). */
    public static void syncTo(ServerPlayer player) {
        PortalSavedData data = PortalSavedData.get(player.level().getServer());
        PacketDistributor.sendToPlayer(player, snapshotPayload(data.getRegistry()));
    }

    /** Snapshots every portal so the packet never shares mutable state with the server registry. */
    private static SyncPortalsPayload snapshotPayload(PortalRegistry registry) {
        return new SyncPortalsPayload(registry.getPortals().stream().map(CustomPortal::snapshot).toList());
    }

    /** Client: replace the synced registry with the payload contents. */
    public static void applySync(List<CustomPortal> portals) {
        PortalRegistry registry = new PortalRegistry();
        for (CustomPortal portal : portals) {
            registry.register(portal);
        }
        ClientPortalData.set(registry);
    }
}
