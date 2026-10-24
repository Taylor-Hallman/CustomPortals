package dev.custom.portals.client;

import dev.custom.portals.data.PortalData;
import dev.custom.portals.network.DrawSpritePayload;
import dev.custom.portals.network.SyncPortalsPayload;
import dev.custom.portals.util.PortalHelper;
import net.neoforged.neoforge.client.network.event.RegisterClientPayloadHandlersEvent;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public final class ClientPayloadHandlers {
    private ClientPayloadHandlers() {}

    public static void register(RegisterClientPayloadHandlersEvent event) {
        event.register(DrawSpritePayload.TYPE, ClientPayloadHandlers::handleDrawSprite);
        event.register(SyncPortalsPayload.TYPE, ClientPayloadHandlers::handleSyncPortals);
    }

    /** Client main thread. */
    private static void handleDrawSprite(DrawSpritePayload payload, IPayloadContext context) {
        ClientUtil.transitionBackgroundSpriteModel = payload.colorId() == 0 ? null : PortalHelper.getPortalBlockFromColorId(payload.colorId());
    }

    private static void handleSyncPortals(SyncPortalsPayload payload, IPayloadContext context) {
        PortalData.applySync(payload.portals());
    }
}
