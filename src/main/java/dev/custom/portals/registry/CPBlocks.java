package dev.custom.portals.registry;

import java.util.function.ToIntFunction;

import dev.custom.portals.CustomPortals;
import dev.custom.portals.blocks.*;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.MapColor;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.client.render.BlockRenderLayer;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.state.property.Properties;
import net.minecraft.util.Identifier;

public class CPBlocks {

        private static RegistryKey<Block> getKey(String name) {
                Identifier id = Identifier.of(CustomPortals.MOD_ID, name);
                return RegistryKey.of(RegistryKeys.BLOCK, id);
        }

        private static final ToIntFunction<BlockState> STATE_TO_LUMINANCE = (state) -> {
                return (Boolean)state.get(Properties.LIT) ? 11 : 0;
        };

        private static final ToIntFunction<BlockState> RUNE_LUMINANCE = (state) -> {
                return 2;
        };

        // Runes
        public static final Block HASTE_RUNE_BLOCK = new HasteRuneBlock(
                Block.Settings.create().registryKey(getKey("haste_rune_block")).sounds(BlockSoundGroup.AMETHYST_CLUSTER)
                .nonOpaque().luminance(RUNE_LUMINANCE).noCollision().strength(0.3F));
        public static final Block GATE_RUNE_BLOCK = new GateRuneBlock(
                Block.Settings.create().registryKey(getKey("gate_rune_block")).sounds(BlockSoundGroup.AMETHYST_CLUSTER)
                  .nonOpaque().luminance(RUNE_LUMINANCE).noCollision().strength(0.3F));
        public static final Block WEAK_ENHANCER_RUNE_BLOCK = new EnhancerRuneBlock(
                Block.Settings.create().registryKey(getKey("weak_enhancer_rune_block")).sounds(BlockSoundGroup.AMETHYST_CLUSTER)
                .nonOpaque().luminance(RUNE_LUMINANCE).noCollision().strength(0.3F));
        public static final Block STRONG_ENHANCER_RUNE_BLOCK = new StrongEnhancerRuneBlock(
                Block.Settings.create().registryKey(getKey("strong_enhancer_rune_block")).sounds(BlockSoundGroup.AMETHYST_CLUSTER)
                .nonOpaque().luminance(RUNE_LUMINANCE).noCollision().strength(0.3F));
        public static final Block INFINITY_RUNE_BLOCK = new InfinityRuneBlock(
                Block.Settings.create().registryKey(getKey("infinity_rune_block")).sounds(BlockSoundGroup.AMETHYST_CLUSTER)
                .nonOpaque().luminance(RUNE_LUMINANCE).noCollision().strength(0.3F));

        // Portal Blocks
        public static final Block BLACK_PORTAL = new PortalBlock(
                Block.Settings.create().registryKey(getKey("black_portal")).mapColor(MapColor.BLACK).nonOpaque().noCollision().ticksRandomly()
                .strength(-1.0F).sounds(BlockSoundGroup.GLASS));
        public static final Block BLUE_PORTAL = new PortalBlock(
                Block.Settings.create().registryKey(getKey("blue_portal")).mapColor(MapColor.BLUE).nonOpaque().noCollision().ticksRandomly()
                .strength(-1.0F).sounds(BlockSoundGroup.GLASS).luminance(STATE_TO_LUMINANCE));
        public static final Block BROWN_PORTAL = new PortalBlock(
                Block.Settings.create().registryKey(getKey("brown_portal")).mapColor(MapColor.BROWN).nonOpaque().noCollision().ticksRandomly()
                .strength(-1.0F).sounds(BlockSoundGroup.GLASS).luminance(STATE_TO_LUMINANCE));
        public static final Block CYAN_PORTAL = new PortalBlock(
                Block.Settings.create().registryKey(getKey("cyan_portal")).mapColor(MapColor.CYAN).nonOpaque().noCollision().ticksRandomly()
                .strength(-1.0F).sounds(BlockSoundGroup.GLASS).luminance(STATE_TO_LUMINANCE));
        public static final Block GRAY_PORTAL = new PortalBlock(
                Block.Settings.create().registryKey(getKey("gray_portal")).mapColor(MapColor.GRAY).nonOpaque().noCollision().ticksRandomly()
                .strength(-1.0F).sounds(BlockSoundGroup.GLASS).luminance(STATE_TO_LUMINANCE));
        public static final Block GREEN_PORTAL = new PortalBlock(
                Block.Settings.create().registryKey(getKey("green_portal")).mapColor(MapColor.GREEN).nonOpaque().noCollision().ticksRandomly()
                .strength(-1.0F).sounds(BlockSoundGroup.GLASS).luminance(STATE_TO_LUMINANCE));
        public static final Block LIGHT_BLUE_PORTAL = new PortalBlock(
                Block.Settings.create().registryKey(getKey("light_blue_portal")).mapColor(MapColor.LIGHT_BLUE).nonOpaque().noCollision()
                .ticksRandomly().strength(-1.0F).sounds(BlockSoundGroup.GLASS).luminance(STATE_TO_LUMINANCE));
        public static final Block LIGHT_GRAY_PORTAL = new PortalBlock(
                Block.Settings.create().registryKey(getKey("light_gray_portal")).mapColor(MapColor.LIGHT_GRAY).nonOpaque().noCollision()
                .ticksRandomly().strength(-1.0F).sounds(BlockSoundGroup.GLASS).luminance(STATE_TO_LUMINANCE));
        public static final Block LIME_PORTAL = new PortalBlock(
                Block.Settings.create().registryKey(getKey("lime_portal")).mapColor(MapColor.LIME).nonOpaque().noCollision().ticksRandomly()
                .strength(-1.0F).sounds(BlockSoundGroup.GLASS).luminance(STATE_TO_LUMINANCE));
        public static final Block MAGENTA_PORTAL = new PortalBlock(
                Block.Settings.create().registryKey(getKey("magenta_portal")).mapColor(MapColor.MAGENTA).nonOpaque().noCollision().ticksRandomly()
                .strength(-1.0F).sounds(BlockSoundGroup.GLASS).luminance(STATE_TO_LUMINANCE));
        public static final Block ORANGE_PORTAL = new PortalBlock(
                Block.Settings.create().registryKey(getKey("orange_portal")).mapColor(MapColor.ORANGE).nonOpaque().noCollision().ticksRandomly()
                .strength(-1.0F).sounds(BlockSoundGroup.GLASS).luminance(STATE_TO_LUMINANCE));
        public static final Block PINK_PORTAL = new PortalBlock(
                Block.Settings.create().registryKey(getKey("pink_portal")).mapColor(MapColor.PINK).nonOpaque().noCollision().ticksRandomly()
                .strength(-1.0F).sounds(BlockSoundGroup.GLASS).luminance(STATE_TO_LUMINANCE));
        public static final Block PURPLE_PORTAL = new PortalBlock(
                Block.Settings.create().registryKey(getKey("purple_portal")).mapColor(MapColor.PURPLE).nonOpaque().noCollision().ticksRandomly()
                .strength(-1.0F).sounds(BlockSoundGroup.GLASS).luminance(STATE_TO_LUMINANCE));
        public static final Block RED_PORTAL = new PortalBlock(
                Block.Settings.create().registryKey(getKey("red_portal")).mapColor(MapColor.RED).nonOpaque().noCollision().ticksRandomly()
                .strength(-1.0F).sounds(BlockSoundGroup.GLASS).luminance(STATE_TO_LUMINANCE));
        public static final Block WHITE_PORTAL = new PortalBlock(
                Block.Settings.create().registryKey(getKey("white_portal")).mapColor(MapColor.WHITE).nonOpaque().noCollision().ticksRandomly()
                .strength(-1.0F).sounds(BlockSoundGroup.GLASS).luminance(STATE_TO_LUMINANCE));
        public static final Block YELLOW_PORTAL = new PortalBlock(
                Block.Settings.create().registryKey(getKey("yellow_portal")).mapColor(MapColor.YELLOW).nonOpaque().noCollision().ticksRandomly()
                .strength(-1.0F).sounds(BlockSoundGroup.GLASS).luminance(STATE_TO_LUMINANCE));

        // BlockEntities
        public static BlockEntityType<PortalBlockEntity> PORTAL_BLOCK_ENTITY;

        public static void registerBlocks() {
                Registry.register(Registries.BLOCK, Identifier.of(CustomPortals.MOD_ID, "haste_rune_block"), HASTE_RUNE_BLOCK);
                Registry.register(Registries.BLOCK, Identifier.of(CustomPortals.MOD_ID, "gate_rune_block"), GATE_RUNE_BLOCK);
                Registry.register(Registries.BLOCK, Identifier.of(CustomPortals.MOD_ID, "weak_enhancer_rune_block"), WEAK_ENHANCER_RUNE_BLOCK);
                Registry.register(Registries.BLOCK, Identifier.of(CustomPortals.MOD_ID, "strong_enhancer_rune_block"), STRONG_ENHANCER_RUNE_BLOCK);
                Registry.register(Registries.BLOCK, Identifier.of(CustomPortals.MOD_ID, "infinity_rune_block"), INFINITY_RUNE_BLOCK);
                Registry.register(Registries.BLOCK, Identifier.of(CustomPortals.MOD_ID, "black_portal"), BLACK_PORTAL);
                Registry.register(Registries.BLOCK, Identifier.of(CustomPortals.MOD_ID, "blue_portal"), BLUE_PORTAL);
                Registry.register(Registries.BLOCK, Identifier.of(CustomPortals.MOD_ID, "brown_portal"), BROWN_PORTAL);
                Registry.register(Registries.BLOCK, Identifier.of(CustomPortals.MOD_ID, "cyan_portal"), CYAN_PORTAL);
                Registry.register(Registries.BLOCK, Identifier.of(CustomPortals.MOD_ID, "gray_portal"), GRAY_PORTAL);
                Registry.register(Registries.BLOCK, Identifier.of(CustomPortals.MOD_ID, "green_portal"), GREEN_PORTAL);
                Registry.register(Registries.BLOCK, Identifier.of(CustomPortals.MOD_ID, "light_blue_portal"), LIGHT_BLUE_PORTAL);
                Registry.register(Registries.BLOCK, Identifier.of(CustomPortals.MOD_ID, "light_gray_portal"), LIGHT_GRAY_PORTAL);
                Registry.register(Registries.BLOCK, Identifier.of(CustomPortals.MOD_ID, "lime_portal"), LIME_PORTAL);
                Registry.register(Registries.BLOCK, Identifier.of(CustomPortals.MOD_ID, "magenta_portal"), MAGENTA_PORTAL);
                Registry.register(Registries.BLOCK, Identifier.of(CustomPortals.MOD_ID, "orange_portal"), ORANGE_PORTAL);
                Registry.register(Registries.BLOCK, Identifier.of(CustomPortals.MOD_ID, "pink_portal"), PINK_PORTAL);
                Registry.register(Registries.BLOCK, Identifier.of(CustomPortals.MOD_ID, "purple_portal"), PURPLE_PORTAL);
                Registry.register(Registries.BLOCK, Identifier.of(CustomPortals.MOD_ID, "red_portal"), RED_PORTAL);
                Registry.register(Registries.BLOCK, Identifier.of(CustomPortals.MOD_ID, "white_portal"), WHITE_PORTAL);
                Registry.register(Registries.BLOCK, Identifier.of(CustomPortals.MOD_ID, "yellow_portal"), YELLOW_PORTAL);
                PORTAL_BLOCK_ENTITY = Registry.register(Registries.BLOCK_ENTITY_TYPE, "customportals:portal_block_entity", FabricBlockEntityTypeBuilder.create(
                        PortalBlockEntity::new, BLACK_PORTAL, BLUE_PORTAL, BROWN_PORTAL, CYAN_PORTAL, GRAY_PORTAL, GREEN_PORTAL, LIGHT_BLUE_PORTAL, 
                        LIGHT_GRAY_PORTAL, LIME_PORTAL, MAGENTA_PORTAL, ORANGE_PORTAL, PINK_PORTAL, PURPLE_PORTAL, RED_PORTAL, WHITE_PORTAL, YELLOW_PORTAL).build(null));
        }

        @Environment(EnvType.CLIENT)
        public static void setBlockRenderLayers() {
                BlockRenderLayerMap.putBlock(HASTE_RUNE_BLOCK, BlockRenderLayer.CUTOUT);
                BlockRenderLayerMap.putBlock(GATE_RUNE_BLOCK, BlockRenderLayer.CUTOUT);
                BlockRenderLayerMap.putBlock(WEAK_ENHANCER_RUNE_BLOCK, BlockRenderLayer.CUTOUT);
                BlockRenderLayerMap.putBlock(STRONG_ENHANCER_RUNE_BLOCK, BlockRenderLayer.CUTOUT);
                BlockRenderLayerMap.putBlock(INFINITY_RUNE_BLOCK, BlockRenderLayer.CUTOUT);
                BlockRenderLayerMap.putBlock(BLACK_PORTAL, BlockRenderLayer.TRANSLUCENT);
                BlockRenderLayerMap.putBlock(BLUE_PORTAL, BlockRenderLayer.TRANSLUCENT);
                BlockRenderLayerMap.putBlock(BROWN_PORTAL, BlockRenderLayer.TRANSLUCENT);
                BlockRenderLayerMap.putBlock(CYAN_PORTAL, BlockRenderLayer.TRANSLUCENT);
                BlockRenderLayerMap.putBlock(GRAY_PORTAL, BlockRenderLayer.TRANSLUCENT);
                BlockRenderLayerMap.putBlock(GREEN_PORTAL, BlockRenderLayer.TRANSLUCENT);
                BlockRenderLayerMap.putBlock(LIGHT_BLUE_PORTAL, BlockRenderLayer.TRANSLUCENT);
                BlockRenderLayerMap.putBlock(LIGHT_GRAY_PORTAL, BlockRenderLayer.TRANSLUCENT);
                BlockRenderLayerMap.putBlock(LIME_PORTAL, BlockRenderLayer.TRANSLUCENT);
                BlockRenderLayerMap.putBlock(MAGENTA_PORTAL, BlockRenderLayer.TRANSLUCENT);
                BlockRenderLayerMap.putBlock(ORANGE_PORTAL, BlockRenderLayer.TRANSLUCENT);
                BlockRenderLayerMap.putBlock(PINK_PORTAL, BlockRenderLayer.TRANSLUCENT);
                BlockRenderLayerMap.putBlock(PURPLE_PORTAL, BlockRenderLayer.TRANSLUCENT);
                BlockRenderLayerMap.putBlock(RED_PORTAL, BlockRenderLayer.TRANSLUCENT);
                BlockRenderLayerMap.putBlock(WHITE_PORTAL, BlockRenderLayer.TRANSLUCENT);
                BlockRenderLayerMap.putBlock(YELLOW_PORTAL, BlockRenderLayer.TRANSLUCENT);
        }
}
