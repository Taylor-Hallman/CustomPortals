package dev.custom.portals.client;

import dev.custom.portals.CustomPortals;
import dev.custom.portals.data.ClientPortalData;
import dev.custom.portals.data.PortalRegistry;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.event.ClientPlayerNetworkEvent;
import net.neoforged.neoforge.client.gui.ConfigurationScreen;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;
import net.neoforged.neoforge.common.NeoForge;

/** Physical-client entry point. Nothing in this package is loaded on a dedicated server. */
@Mod(value = CustomPortals.MOD_ID, dist = Dist.CLIENT)
public class CustomPortalsClient {

    public CustomPortalsClient(IEventBus modBus, ModContainer container) {
        modBus.addListener(CPParticleProviders::register);
        modBus.addListener(ClientPayloadHandlers::register);
        container.registerExtensionPoint(IConfigScreenFactory.class, ConfigurationScreen::new);
        NeoForge.EVENT_BUS.addListener(CustomPortalsClient::onLoggedOut);
    }

    /** Drop the synced registry when leaving a server so a stale copy cannot leak into the next world. */
    private static void onLoggedOut(ClientPlayerNetworkEvent.LoggingOut event) {
        ClientPortalData.set(new PortalRegistry());
        ClientUtil.transitionBackgroundSpriteModel = null;
        ClientUtil.isTransitioning = false;
    }
}
