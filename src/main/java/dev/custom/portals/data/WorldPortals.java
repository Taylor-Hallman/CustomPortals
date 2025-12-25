package dev.custom.portals.data;

import dev.custom.portals.CustomPortals;
import org.ladysnake.cca.api.v3.component.sync.AutoSyncedComponent;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;

public class WorldPortals extends PortalComponent implements AutoSyncedComponent {
    private Level world;

    public WorldPortals(Level world) { this.world = world; }

    public Level getWorld() { return world; }

    @Override
    public void syncWithAll(MinecraftServer server) {
        Iterable<ServerLevel> worlds = server.getAllLevels();
        for (ServerLevel serverWorld : worlds) {
            CustomPortals.PORTALS.get(serverWorld).setPortalRegistry(this.getPortalRegistry());
        }
    }
}
