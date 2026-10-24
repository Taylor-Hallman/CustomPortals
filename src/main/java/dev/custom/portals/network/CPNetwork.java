package dev.custom.portals.network;

import dev.custom.portals.util.EntityMixinAccess;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

/** Payload registration (mod bus) and the server-side handler. Client handlers live in client.ClientPayloadHandlers. */
public final class CPNetwork {
    private CPNetwork() {}

    public static void registerPayloads(RegisterPayloadHandlersEvent event) {
        PayloadRegistrar registrar = event.registrar("1");
        registrar.playToClient(DrawSpritePayload.TYPE, DrawSpritePayload.STREAM_CODEC);
        registrar.playToClient(SyncPortalsPayload.TYPE, SyncPortalsPayload.STREAM_CODEC);
        registrar.playToServer(ScreenTransitionPayload.TYPE, ScreenTransitionPayload.STREAM_CODEC, CPNetwork::handleScreenTransition);
    }

    /** Runs on the server main thread (NeoForge default). */
    private static void handleScreenTransition(ScreenTransitionPayload payload, IPayloadContext context) {
        ((EntityMixinAccess) context.player()).setInTransition(payload.isTransitioning());
    }
}
