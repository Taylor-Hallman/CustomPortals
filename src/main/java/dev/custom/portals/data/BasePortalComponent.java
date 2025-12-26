package dev.custom.portals.data;

import dev.onyxstudios.cca.api.v3.component.ComponentV3;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;

public interface BasePortalComponent extends ComponentV3 {
    PortalRegistry getPortalRegistry();
    Portal getPortalFromPos(BlockPos pos);
    //boolean settingsChanged();
    void setPortalRegistry(PortalRegistry portalRegistry);
    void registerPortal(Portal portal);
    void unregisterPortal(Portal portal);
    void verifyPortals();
    void tryWithAll(Portal portal);
    void refreshPortals();
    //void clearPortals();
    void syncWithAll(MinecraftServer server);
}
