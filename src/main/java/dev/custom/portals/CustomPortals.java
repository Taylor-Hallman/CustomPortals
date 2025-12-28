package dev.custom.portals;

import dev.custom.portals.config.CPSettings;
import dev.custom.portals.data.BasePortalComponent;
import dev.custom.portals.data.WorldPortals;
import dev.custom.portals.registry.CPBlocks;
import dev.custom.portals.registry.CPItems;
import dev.custom.portals.registry.CPParticles;
import dev.custom.portals.util.DrawSpritePayload;
import dev.custom.portals.util.EntityMixinAccess;
import dev.custom.portals.util.ScreenTransitionPayload;
import net.fabricmc.fabric.api.entity.event.v1.ServerEntityWorldChangeEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerWorldEvents;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.server.world.ServerWorld;
import org.ladysnake.cca.api.v3.world.WorldComponentFactoryRegistry;
import org.ladysnake.cca.api.v3.world.WorldComponentInitializer;
import org.ladysnake.cca.api.v3.component.ComponentRegistryV3;
import org.ladysnake.cca.api.v3.component.ComponentKey;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;

public class CustomPortals implements ModInitializer, WorldComponentInitializer {

        public static final String MOD_ID = "customportals";

        public static final ComponentKey<BasePortalComponent> PORTALS = ComponentRegistryV3.INSTANCE
                .getOrCreate(Identifier.of("customportals:portals"), BasePortalComponent.class);

        public static final RegistryKey<ItemGroup> PORTALS_ITEM_GROUP = RegistryKey.of(RegistryKeys.ITEM_GROUP,
                Identifier.of(CustomPortals.MOD_ID, "general"));

        @Override
        public void onInitialize() {
                Registry.register(Registries.ITEM_GROUP, PORTALS_ITEM_GROUP, FabricItemGroup.builder().icon(()
                        -> new ItemStack(CPItems.PURPLE_PORTAL_CATALYST))
                        .displayName(Text.translatable("itemGroup.customportals.general")).build());
                CPSettings.load();
                CPBlocks.registerBlocks();
                CPItems.registerItems();
                CPParticles.registerParticles();
                PayloadTypeRegistry.playS2C().register(DrawSpritePayload.ID, DrawSpritePayload.CODEC);
                PayloadTypeRegistry.playC2S().register(ScreenTransitionPayload.ID, ScreenTransitionPayload.CODEC);
                ServerPlayNetworking.registerGlobalReceiver(ScreenTransitionPayload.ID, (payload, context) -> {
                        context.server().execute(() -> {
                                ((EntityMixinAccess)context.player()).setInTransition(payload.isTransitioning());
                        });
                });
                ServerWorldEvents.LOAD.register((minecraftServer, serverWorld) -> {
                        PORTALS.get(serverWorld).syncWithAll(minecraftServer);
                        PORTALS.get(serverWorld).verifyPortals();
                });
                ServerEntityWorldChangeEvents.AFTER_PLAYER_CHANGE_WORLD.register((player, origin, destination) -> {
                        PORTALS.get(destination).verifyPortals();
                });
        }

        @Override
        public void registerWorldComponentFactories(WorldComponentFactoryRegistry registry) {
                registry.register(PORTALS, WorldPortals.class, WorldPortals::new);
        }

        // for debugging purposes
        public static String blockPosToString(BlockPos pos) {
                return "(" + pos.getX() + ", " + pos.getY() + ", " + pos.getZ() + ")";
        }

}