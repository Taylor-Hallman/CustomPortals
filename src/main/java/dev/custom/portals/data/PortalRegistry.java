package dev.custom.portals.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

import net.minecraft.core.BlockPos;

public class PortalRegistry {

    private List<CustomPortal> portals;
    private Map<BlockPos, CustomPortal> portalPositions;

    public PortalRegistry() {
        portals = new ArrayList<CustomPortal>();
        portalPositions = new HashMap<BlockPos, CustomPortal>();
    }
    
    public void register(CustomPortal portal) {
        tryWithAll(portal);
        for (BlockPos blockPos : portal.getPortalBlocks()) {
            portalPositions.put(blockPos, portal);
        }
        portals.add(portal);
    }

    public void unregister(CustomPortal portal) {
        portals.remove(portal);
        if (portal.hasLinked()) {
            tryWithAll(portal.getLinked());
        }
        for (BlockPos blockPos : portal.getPortalBlocks()) {
            portalPositions.remove(blockPos);
        }
    }

    public void tryWithAll(CustomPortal portal) {
        portal.setLinked(null);
        for (CustomPortal p : portals) {
            portal.tryLink(p);
        }
    }

    public void refreshPortals() {
        for (CustomPortal portal : portals) {
            tryWithAll(portal);
        }
    }

    public List<CustomPortal> getPortals() { return portals; }

    //public void clear() { portals.clear(); }

    public CustomPortal getPortalFromPos(BlockPos pos) {
        return portalPositions.get(pos);
    }
}
