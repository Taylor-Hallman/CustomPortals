package dev.custom.portals;

import dev.custom.portals.config.CPConfig;
import dev.custom.portals.data.PortalData;
import dev.custom.portals.network.CPNetwork;
import dev.custom.portals.registry.CPBlocks;
import dev.custom.portals.registry.CPCreativeTabs;
import dev.custom.portals.registry.CPItems;
import dev.custom.portals.registry.CPParticles;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;

@Mod(CustomPortals.MOD_ID)
public class CustomPortals {

    public static final String MOD_ID = "customportals";

    public CustomPortals(IEventBus modBus, ModContainer container) {
        CPBlocks.BLOCKS.register(modBus);
        CPBlocks.BLOCK_ENTITY_TYPES.register(modBus);
        CPItems.ITEMS.register(modBus);
        CPParticles.PARTICLE_TYPES.register(modBus);
        CPCreativeTabs.CREATIVE_MODE_TABS.register(modBus);
        modBus.addListener(CPNetwork::registerPayloads);
        container.registerConfig(ModConfig.Type.COMMON, CPConfig.SPEC);
        NeoForge.EVENT_BUS.addListener(CustomPortals::onPlayerLoggedIn);
    }

    /** New players need the full registry before they can see portal overlays or predict portal behavior. */
    private static void onPlayerLoggedIn(PlayerEvent.PlayerLoggedInEvent event) {
        if (event.getEntity() instanceof ServerPlayer player) {
            PortalData.syncTo(player);
        }
    }

    // for debugging purposes
    public static String blockPosToString(BlockPos pos) {
        return "(" + pos.getX() + ", " + pos.getY() + ", " + pos.getZ() + ")";
    }
}
