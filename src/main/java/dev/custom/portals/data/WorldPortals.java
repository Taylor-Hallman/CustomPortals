package dev.custom.portals.data;

import dev.custom.portals.CustomPortals;
import dev.custom.portals.blocks.PortalBlock;
import org.ladysnake.cca.api.v3.component.sync.AutoSyncedComponent;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.world.ServerWorld;
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
        List<CustomPortal> portals = this.getPortalRegistry().getPortals();
        List<CustomPortal> buggedPortals = new ArrayList<>();
        for (CustomPortal portal : portals) {
            if (!world.getRegistryKey().getValue().toString().equals(portal.getDimensionId()))
                continue;
            if (!(world.getBlockState(portal.getSpawnPos()).getBlock() instanceof PortalBlock)) {
                Logger logger = LoggerFactory.getLogger(CustomPortals.MOD_ID);
                logger.error("Found portal registered at a location where no portal should be; this should never happen! The glitched portal will be deleted.");
                buggedPortals.add(portal);
            }
        }
        for (CustomPortal portal : buggedPortals) {
            this.unregisterPortal(portal);
        }
        /*System.out.println("Portals registerd on this server: ");
        for (CustomPortal portal : portals) {
            System.out.println(portal.getDimensionId() + ": " + CustomPortals.blockPosToString(portal.getSpawnPos()));
        }*/
    }

    @Override
    public void syncWithAll(MinecraftServer server) {
        Iterable<ServerWorld> worlds = server.getWorlds();
        for (ServerWorld serverWorld : worlds) {
            CustomPortals.PORTALS.get(serverWorld).setPortalRegistry(this.getPortalRegistry());
        }
    }
}
