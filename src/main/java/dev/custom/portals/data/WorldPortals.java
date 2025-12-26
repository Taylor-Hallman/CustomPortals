package dev.custom.portals.data;

import dev.custom.portals.CustomPortals;
import dev.custom.portals.blocks.PortalBlock;
import dev.onyxstudios.cca.api.v3.component.sync.AutoSyncedComponent;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class WorldPortals extends PortalComponent implements AutoSyncedComponent {
    private World world;

    public WorldPortals(World world) { this.world = world; }

    public World getWorld() { return world; }

    @Override
    public void verifyPortals() {
        List<Portal> portals = this.getPortalRegistry().getPortals();
        List<Portal> buggedPortals = new ArrayList<>();
        for (Portal portal : portals) {
            if (!world.getRegistryKey().getValue().toString().equals(portal.getDimensionId()))
                continue;
            for (BlockPos pos : portal.getPortalBlocks()) {
                if (!(world.getBlockState(pos).getBlock() instanceof PortalBlock)) {
                    Logger logger = LoggerFactory.getLogger(CustomPortals.MOD_ID);
                    logger.error("Found portal registered at a location with missing portal block instances! This is a bugged portal that should not exist, so it will be deleted.");
                    buggedPortals.add(portal);
                    break;
                }
            }
        }
        for (Portal portal : buggedPortals) {
            this.unregisterPortal(portal);
        }
    }

    @Override
    public void syncWithAll(MinecraftServer server) {
        Iterable<ServerWorld> worlds = server.getWorlds();
        for (ServerWorld serverWorld : worlds) {
            CustomPortals.PORTALS.get(serverWorld).setPortalRegistry(this.getPortalRegistry());
        }
    }
}
