package dev.custom.portals.registry;

import java.util.function.ToIntFunction;

import dev.custom.portals.CustomPortals;
import dev.custom.portals.blocks.*;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.client.renderer.chunk.ChunkSectionLayer;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.resources.Identifier;

public class CPBlocks {

        private static ResourceKey<Block> getKey(String name) {
                Identifier id = Identifier.fromNamespaceAndPath(CustomPortals.MOD_ID, name);
                return ResourceKey.create(Registries.BLOCK, id);
        }

        private static final ToIntFunction<BlockState> STATE_TO_LUMINANCE = (state) -> {
                return (Boolean)state.getValue(BlockStateProperties.LIT) ? 11 : 0;
        };

        private static final ToIntFunction<BlockState> RUNE_LUMINANCE = (state) -> {
                return 2;
        };

        // Runes
        public static final Block HASTE_RUNE_BLOCK = new HasteRuneBlock(
                Block.Properties.of().setId(getKey("haste_rune_block")).sound(SoundType.AMETHYST_CLUSTER)
                .noOcclusion().lightLevel(RUNE_LUMINANCE).noCollision().strength(0.3F));
        public static final Block GATE_RUNE_BLOCK = new GateRuneBlock(
                Block.Properties.of().setId(getKey("gate_rune_block")).sound(SoundType.AMETHYST_CLUSTER)
                  .noOcclusion().lightLevel(RUNE_LUMINANCE).noCollision().strength(0.3F));
        public static final Block WEAK_ENHANCER_RUNE_BLOCK = new EnhancerRuneBlock(
                Block.Properties.of().setId(getKey("weak_enhancer_rune_block")).sound(SoundType.AMETHYST_CLUSTER)
                .noOcclusion().lightLevel(RUNE_LUMINANCE).noCollision().strength(0.3F));
        public static final Block STRONG_ENHANCER_RUNE_BLOCK = new StrongEnhancerRuneBlock(
                Block.Properties.of().setId(getKey("strong_enhancer_rune_block")).sound(SoundType.AMETHYST_CLUSTER)
                .noOcclusion().lightLevel(RUNE_LUMINANCE).noCollision().strength(0.3F));
        public static final Block INFINITY_RUNE_BLOCK = new InfinityRuneBlock(
                Block.Properties.of().setId(getKey("infinity_rune_block")).sound(SoundType.AMETHYST_CLUSTER)
                .noOcclusion().lightLevel(RUNE_LUMINANCE).noCollision().strength(0.3F));

        // Portal Blocks
        public static final Block BLACK_PORTAL = new PortalBlock(
                Block.Properties.of().setId(getKey("black_portal")).mapColor(MapColor.COLOR_BLACK).noOcclusion().noCollision().randomTicks()
                .strength(-1.0F).sound(SoundType.GLASS));
        public static final Block BLUE_PORTAL = new PortalBlock(
                Block.Properties.of().setId(getKey("blue_portal")).mapColor(MapColor.COLOR_BLUE).noOcclusion().noCollision().randomTicks()
                .strength(-1.0F).sound(SoundType.GLASS).lightLevel(STATE_TO_LUMINANCE));
        public static final Block BROWN_PORTAL = new PortalBlock(
                Block.Properties.of().setId(getKey("brown_portal")).mapColor(MapColor.COLOR_BROWN).noOcclusion().noCollision().randomTicks()
                .strength(-1.0F).sound(SoundType.GLASS).lightLevel(STATE_TO_LUMINANCE));
        public static final Block CYAN_PORTAL = new PortalBlock(
                Block.Properties.of().setId(getKey("cyan_portal")).mapColor(MapColor.COLOR_CYAN).noOcclusion().noCollision().randomTicks()
                .strength(-1.0F).sound(SoundType.GLASS).lightLevel(STATE_TO_LUMINANCE));
        public static final Block GRAY_PORTAL = new PortalBlock(
                Block.Properties.of().setId(getKey("gray_portal")).mapColor(MapColor.COLOR_GRAY).noOcclusion().noCollision().randomTicks()
                .strength(-1.0F).sound(SoundType.GLASS).lightLevel(STATE_TO_LUMINANCE));
        public static final Block GREEN_PORTAL = new PortalBlock(
                Block.Properties.of().setId(getKey("green_portal")).mapColor(MapColor.COLOR_GREEN).noOcclusion().noCollision().randomTicks()
                .strength(-1.0F).sound(SoundType.GLASS).lightLevel(STATE_TO_LUMINANCE));
        public static final Block LIGHT_BLUE_PORTAL = new PortalBlock(
                Block.Properties.of().setId(getKey("light_blue_portal")).mapColor(MapColor.COLOR_LIGHT_BLUE).noOcclusion().noCollision()
                .randomTicks().strength(-1.0F).sound(SoundType.GLASS).lightLevel(STATE_TO_LUMINANCE));
        public static final Block LIGHT_GRAY_PORTAL = new PortalBlock(
                Block.Properties.of().setId(getKey("light_gray_portal")).mapColor(MapColor.COLOR_LIGHT_GRAY).noOcclusion().noCollision()
                .randomTicks().strength(-1.0F).sound(SoundType.GLASS).lightLevel(STATE_TO_LUMINANCE));
        public static final Block LIME_PORTAL = new PortalBlock(
                Block.Properties.of().setId(getKey("lime_portal")).mapColor(MapColor.COLOR_LIGHT_GREEN).noOcclusion().noCollision().randomTicks()
                .strength(-1.0F).sound(SoundType.GLASS).lightLevel(STATE_TO_LUMINANCE));
        public static final Block MAGENTA_PORTAL = new PortalBlock(
                Block.Properties.of().setId(getKey("magenta_portal")).mapColor(MapColor.COLOR_MAGENTA).noOcclusion().noCollision().randomTicks()
                .strength(-1.0F).sound(SoundType.GLASS).lightLevel(STATE_TO_LUMINANCE));
        public static final Block ORANGE_PORTAL = new PortalBlock(
                Block.Properties.of().setId(getKey("orange_portal")).mapColor(MapColor.COLOR_ORANGE).noOcclusion().noCollision().randomTicks()
                .strength(-1.0F).sound(SoundType.GLASS).lightLevel(STATE_TO_LUMINANCE));
        public static final Block PINK_PORTAL = new PortalBlock(
                Block.Properties.of().setId(getKey("pink_portal")).mapColor(MapColor.COLOR_PINK).noOcclusion().noCollision().randomTicks()
                .strength(-1.0F).sound(SoundType.GLASS).lightLevel(STATE_TO_LUMINANCE));
        public static final Block PURPLE_PORTAL = new PortalBlock(
                Block.Properties.of().setId(getKey("purple_portal")).mapColor(MapColor.COLOR_PURPLE).noOcclusion().noCollision().randomTicks()
                .strength(-1.0F).sound(SoundType.GLASS).lightLevel(STATE_TO_LUMINANCE));
        public static final Block RED_PORTAL = new PortalBlock(
                Block.Properties.of().setId(getKey("red_portal")).mapColor(MapColor.COLOR_RED).noOcclusion().noCollision().randomTicks()
                .strength(-1.0F).sound(SoundType.GLASS).lightLevel(STATE_TO_LUMINANCE));
        public static final Block WHITE_PORTAL = new PortalBlock(
                Block.Properties.of().setId(getKey("white_portal")).mapColor(MapColor.SNOW).noOcclusion().noCollision().randomTicks()
                .strength(-1.0F).sound(SoundType.GLASS).lightLevel(STATE_TO_LUMINANCE));
        public static final Block YELLOW_PORTAL = new PortalBlock(
                Block.Properties.of().setId(getKey("yellow_portal")).mapColor(MapColor.COLOR_YELLOW).noOcclusion().noCollision().randomTicks()
                .strength(-1.0F).sound(SoundType.GLASS).lightLevel(STATE_TO_LUMINANCE));

        // BlockEntities
        public static BlockEntityType<PortalBlockEntity> PORTAL_BLOCK_ENTITY;

        public static void registerBlocks() {
                Registry.register(BuiltInRegistries.BLOCK, Identifier.fromNamespaceAndPath(CustomPortals.MOD_ID, "haste_rune_block"), HASTE_RUNE_BLOCK);
                Registry.register(BuiltInRegistries.BLOCK, Identifier.fromNamespaceAndPath(CustomPortals.MOD_ID, "gate_rune_block"), GATE_RUNE_BLOCK);
                Registry.register(BuiltInRegistries.BLOCK, Identifier.fromNamespaceAndPath(CustomPortals.MOD_ID, "weak_enhancer_rune_block"), WEAK_ENHANCER_RUNE_BLOCK);
                Registry.register(BuiltInRegistries.BLOCK, Identifier.fromNamespaceAndPath(CustomPortals.MOD_ID, "strong_enhancer_rune_block"), STRONG_ENHANCER_RUNE_BLOCK);
                Registry.register(BuiltInRegistries.BLOCK, Identifier.fromNamespaceAndPath(CustomPortals.MOD_ID, "infinity_rune_block"), INFINITY_RUNE_BLOCK);
                Registry.register(BuiltInRegistries.BLOCK, Identifier.fromNamespaceAndPath(CustomPortals.MOD_ID, "black_portal"), BLACK_PORTAL);
                Registry.register(BuiltInRegistries.BLOCK, Identifier.fromNamespaceAndPath(CustomPortals.MOD_ID, "blue_portal"), BLUE_PORTAL);
                Registry.register(BuiltInRegistries.BLOCK, Identifier.fromNamespaceAndPath(CustomPortals.MOD_ID, "brown_portal"), BROWN_PORTAL);
                Registry.register(BuiltInRegistries.BLOCK, Identifier.fromNamespaceAndPath(CustomPortals.MOD_ID, "cyan_portal"), CYAN_PORTAL);
                Registry.register(BuiltInRegistries.BLOCK, Identifier.fromNamespaceAndPath(CustomPortals.MOD_ID, "gray_portal"), GRAY_PORTAL);
                Registry.register(BuiltInRegistries.BLOCK, Identifier.fromNamespaceAndPath(CustomPortals.MOD_ID, "green_portal"), GREEN_PORTAL);
                Registry.register(BuiltInRegistries.BLOCK, Identifier.fromNamespaceAndPath(CustomPortals.MOD_ID, "light_blue_portal"), LIGHT_BLUE_PORTAL);
                Registry.register(BuiltInRegistries.BLOCK, Identifier.fromNamespaceAndPath(CustomPortals.MOD_ID, "light_gray_portal"), LIGHT_GRAY_PORTAL);
                Registry.register(BuiltInRegistries.BLOCK, Identifier.fromNamespaceAndPath(CustomPortals.MOD_ID, "lime_portal"), LIME_PORTAL);
                Registry.register(BuiltInRegistries.BLOCK, Identifier.fromNamespaceAndPath(CustomPortals.MOD_ID, "magenta_portal"), MAGENTA_PORTAL);
                Registry.register(BuiltInRegistries.BLOCK, Identifier.fromNamespaceAndPath(CustomPortals.MOD_ID, "orange_portal"), ORANGE_PORTAL);
                Registry.register(BuiltInRegistries.BLOCK, Identifier.fromNamespaceAndPath(CustomPortals.MOD_ID, "pink_portal"), PINK_PORTAL);
                Registry.register(BuiltInRegistries.BLOCK, Identifier.fromNamespaceAndPath(CustomPortals.MOD_ID, "purple_portal"), PURPLE_PORTAL);
                Registry.register(BuiltInRegistries.BLOCK, Identifier.fromNamespaceAndPath(CustomPortals.MOD_ID, "red_portal"), RED_PORTAL);
                Registry.register(BuiltInRegistries.BLOCK, Identifier.fromNamespaceAndPath(CustomPortals.MOD_ID, "white_portal"), WHITE_PORTAL);
                Registry.register(BuiltInRegistries.BLOCK, Identifier.fromNamespaceAndPath(CustomPortals.MOD_ID, "yellow_portal"), YELLOW_PORTAL);
                PORTAL_BLOCK_ENTITY = Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE, "customportals:portal_block_entity", FabricBlockEntityTypeBuilder.create(
                        PortalBlockEntity::new, BLACK_PORTAL, BLUE_PORTAL, BROWN_PORTAL, CYAN_PORTAL, GRAY_PORTAL, GREEN_PORTAL, LIGHT_BLUE_PORTAL, 
                        LIGHT_GRAY_PORTAL, LIME_PORTAL, MAGENTA_PORTAL, ORANGE_PORTAL, PINK_PORTAL, PURPLE_PORTAL, RED_PORTAL, WHITE_PORTAL, YELLOW_PORTAL).build(null));
        }

        @Environment(EnvType.CLIENT)
        public static void setBlockRenderLayers() {
                BlockRenderLayerMap.putBlock(HASTE_RUNE_BLOCK, ChunkSectionLayer.CUTOUT);
                BlockRenderLayerMap.putBlock(GATE_RUNE_BLOCK, ChunkSectionLayer.CUTOUT);
                BlockRenderLayerMap.putBlock(WEAK_ENHANCER_RUNE_BLOCK, ChunkSectionLayer.CUTOUT);
                BlockRenderLayerMap.putBlock(STRONG_ENHANCER_RUNE_BLOCK, ChunkSectionLayer.CUTOUT);
                BlockRenderLayerMap.putBlock(INFINITY_RUNE_BLOCK, ChunkSectionLayer.CUTOUT);
                BlockRenderLayerMap.putBlock(BLACK_PORTAL, ChunkSectionLayer.TRANSLUCENT);
                BlockRenderLayerMap.putBlock(BLUE_PORTAL, ChunkSectionLayer.TRANSLUCENT);
                BlockRenderLayerMap.putBlock(BROWN_PORTAL, ChunkSectionLayer.TRANSLUCENT);
                BlockRenderLayerMap.putBlock(CYAN_PORTAL, ChunkSectionLayer.TRANSLUCENT);
                BlockRenderLayerMap.putBlock(GRAY_PORTAL, ChunkSectionLayer.TRANSLUCENT);
                BlockRenderLayerMap.putBlock(GREEN_PORTAL, ChunkSectionLayer.TRANSLUCENT);
                BlockRenderLayerMap.putBlock(LIGHT_BLUE_PORTAL, ChunkSectionLayer.TRANSLUCENT);
                BlockRenderLayerMap.putBlock(LIGHT_GRAY_PORTAL, ChunkSectionLayer.TRANSLUCENT);
                BlockRenderLayerMap.putBlock(LIME_PORTAL, ChunkSectionLayer.TRANSLUCENT);
                BlockRenderLayerMap.putBlock(MAGENTA_PORTAL, ChunkSectionLayer.TRANSLUCENT);
                BlockRenderLayerMap.putBlock(ORANGE_PORTAL, ChunkSectionLayer.TRANSLUCENT);
                BlockRenderLayerMap.putBlock(PINK_PORTAL, ChunkSectionLayer.TRANSLUCENT);
                BlockRenderLayerMap.putBlock(PURPLE_PORTAL, ChunkSectionLayer.TRANSLUCENT);
                BlockRenderLayerMap.putBlock(RED_PORTAL, ChunkSectionLayer.TRANSLUCENT);
                BlockRenderLayerMap.putBlock(WHITE_PORTAL, ChunkSectionLayer.TRANSLUCENT);
                BlockRenderLayerMap.putBlock(YELLOW_PORTAL, ChunkSectionLayer.TRANSLUCENT);
        }
}
