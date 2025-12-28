package dev.custom.portals.data;

import net.minecraft.server.MinecraftServer;
import net.minecraft.storage.ReadView;
import net.minecraft.storage.WriteView;
import net.minecraft.util.math.BlockPos;

public class PortalComponent implements BasePortalComponent {

    private PortalRegistry portalRegistry;

    public PortalComponent() {
        portalRegistry = new PortalRegistry();
    }

    @Override
    public PortalRegistry getPortalRegistry() { return portalRegistry; }

    @Override
    public CustomPortal getPortalFromPos(BlockPos pos) {
        return portalRegistry.getPortalFromPos(pos);
    }

    @Override
    public void setPortalRegistry(PortalRegistry portalRegistry) { this.portalRegistry = portalRegistry; }

    @Override
    public void registerPortal(CustomPortal portal) {
        portalRegistry.register(portal);
    }

    @Override
    public void unregisterPortal(CustomPortal portal) {
        portalRegistry.unregister(portal);
    }

    @Override
    public void verifyPortals() {}

    @Override
    public void tryWithAll(CustomPortal portal) {
        portalRegistry.tryWithAll(portal);
    }

    @Override
    public void refreshPortals() {
        portalRegistry.refreshPortals();
    }

    /*@Override
    public void clearPortals() {
        portalRegistry.clear();
    }*/

    @Override
    public void readData(ReadView readView) {
        var portals = readView.read("portals", CustomPortal.CODEC.listOf());
        if (portals.isEmpty()) {
            return;
        }
        for (CustomPortal portal : portals.get()) {
            registerPortal(portal);
        }
    }

    @Override
    public void writeData(WriteView writeView) {
        writeView.put("portals", CustomPortal.CODEC.listOf(), portalRegistry.getPortals());
    }
    
    @Override
    public void syncWithAll(MinecraftServer server) {}
}
