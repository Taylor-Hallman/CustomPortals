package dev.custom.portals.data;

import java.util.List;

import com.mojang.serialization.codecs.RecordCodecBuilder;
import dev.custom.portals.CustomPortals;
import net.minecraft.resources.Identifier;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.level.saveddata.SavedData;
import net.minecraft.world.level.saveddata.SavedDataType;

/**
 * Server-wide portal registry. Stored once, in the overworld's data storage, because portals link across
 * dimensions and the Fabric build also kept a single shared registry.
 */
public class PortalSavedData extends SavedData {

    public static final SavedDataType<PortalSavedData> TYPE = new SavedDataType<>(
            Identifier.fromNamespaceAndPath(CustomPortals.MOD_ID, "portals"),
            PortalSavedData::new,
            RecordCodecBuilder.create(instance -> instance.group(
                    CustomPortal.CODEC.listOf().fieldOf("portals").forGetter(data -> data.registry.getPortals())
            ).apply(instance, PortalSavedData::new))
    );

    private final PortalRegistry registry = new PortalRegistry();

    public PortalSavedData() {}

    private PortalSavedData(List<CustomPortal> portals) {
        // Registering in order recomputes links, exactly as the Fabric readData did.
        for (CustomPortal portal : portals) {
            registry.register(portal);
        }
    }

    public PortalRegistry getRegistry() {
        return registry;
    }

    public static PortalSavedData get(MinecraftServer server) {
        return server.overworld().getDataStorage().computeIfAbsent(TYPE);
    }
}
