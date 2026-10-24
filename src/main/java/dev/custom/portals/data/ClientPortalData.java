package dev.custom.portals.data;

/** Client-side copy of the registry, replaced wholesale by each SyncPortalsPayload. No client-only imports. */
public final class ClientPortalData {
    private static PortalRegistry registry = new PortalRegistry();

    private ClientPortalData() {}

    public static PortalRegistry registry() {
        return registry;
    }

    public static void set(PortalRegistry newRegistry) {
        registry = newRegistry;
    }
}
