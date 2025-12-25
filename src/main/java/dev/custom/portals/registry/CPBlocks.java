package dev.custom.portals.registry;

import java.util.function.ToIntFunction;

import dev.custom.portals.CustomPortals;
import dev.custom.portals.blocks.*;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.MapColor;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.state.property.Properties;
import net.minecraft.util.Identifier;

public class CPBlocks {

        // Runes
        public static final Block HASTE_RUNE_BLOCK = new HasteRuneBlock(
                FabricBlockSettings.create().sounds(BlockSoundGroup.AMETHYST_CLUSTER)
                .nonOpaque().luminance(2).noCollision().strength(0.3F));
        public static final Block GATE_RUNE_BLOCK = new GateRuneBlock(
                FabricBlockSettings.create().sounds(BlockSoundGroup.AMETHYST_CLUSTER)
                .nonOpaque().luminance(2).noCollision().strength(0.3F));
        public static final Block WEAK_ENHANCER_RUNE_BLOCK = new EnhancerRuneBlock(
                FabricBlockSettings.create().sounds(BlockSoundGroup.AMETHYST_CLUSTER)
                .nonOpaque().luminance(2).noCollision().strength(0.3F));
        public static final Block STRONG_ENHANCER_RUNE_BLOCK = new StrongEnhancerRuneBlock(
                FabricBlockSettings.create().sounds(BlockSoundGroup.AMETHYST_CLUSTER)
                .nonOpaque().luminance(2).noCollision().strength(0.3F));
        public static final Block INFINITY_RUNE_BLOCK = new InfinityRuneBlock(
                FabricBlockSettings.create().sounds(BlockSoundGroup.AMETHYST_CLUSTER)
                .nonOpaque().luminance(2).noCollision().strength(0.3F));

        // Portal Blocks
        public static final Block BLACK_PORTAL = new PortalBlock(
                FabricBlockSettings.create().mapColor(MapColor.BLACK).nonOpaque().noCollision().ticksRandomly()
                .strength(-1.0F).sounds(BlockSoundGroup.GLASS));
        public static final Block BLUE_PORTAL = new PortalBlock(
                FabricBlockSettings.create().mapColor(MapColor.BLUE).nonOpaque().noCollision().ticksRandomly()
                .strength(-1.0F).sounds(BlockSoundGroup.GLASS).luminance(createLightLevelFromLitBlockState(11)));
        public static final Block BROWN_PORTAL = new PortalBlock(
                FabricBlockSettings.create().mapColor(MapColor.BROWN).nonOpaque().noCollision().ticksRandomly()
                .strength(-1.0F).sounds(BlockSoundGroup.GLASS).luminance(createLightLevelFromLitBlockState(11)));
        public static final Block CYAN_PORTAL = new PortalBlock(
                FabricBlockSettings.create().mapColor(MapColor.CYAN).nonOpaque().noCollision().ticksRandomly()
                .strength(-1.0F).sounds(BlockSoundGroup.GLASS).luminance(createLightLevelFromLitBlockState(11)));
        public static final Block GRAY_PORTAL = new PortalBlock(
                FabricBlockSettings.create().mapColor(MapColor.GRAY).nonOpaque().noCollision().ticksRandomly()
                .strength(-1.0F).sounds(BlockSoundGroup.GLASS).luminance(createLightLevelFromLitBlockState(11)));
        public static final Block GREEN_PORTAL = new PortalBlock(
                FabricBlockSettings.create().mapColor(MapColor.GREEN).nonOpaque().noCollision().ticksRandomly()
                .strength(-1.0F).sounds(BlockSoundGroup.GLASS).luminance(createLightLevelFromLitBlockState(11)));
        public static final Block LIGHT_BLUE_PORTAL = new PortalBlock(
                FabricBlockSettings.create().mapColor(MapColor.LIGHT_BLUE).nonOpaque().noCollision()
                .ticksRandomly().strength(-1.0F).sounds(BlockSoundGroup.GLASS).luminance(createLightLevelFromLitBlockState(11)));
        public static final Block LIGHT_GRAY_PORTAL = new PortalBlock(
                FabricBlockSettings.create().mapColor(MapColor.LIGHT_GRAY).nonOpaque().noCollision()
                .ticksRandomly().strength(-1.0F).sounds(BlockSoundGroup.GLASS).luminance(createLightLevelFromLitBlockState(11)));
        public static final Block LIME_PORTAL = new PortalBlock(
                FabricBlockSettings.create().mapColor(MapColor.LIME).nonOpaque().noCollision().ticksRandomly()
                .strength(-1.0F).sounds(BlockSoundGroup.GLASS).luminance(createLightLevelFromLitBlockState(11)));
        public static final Block MAGENTA_PORTAL = new PortalBlock(
                FabricBlockSettings.create().mapColor(MapColor.MAGENTA).nonOpaque().noCollision().ticksRandomly()
                .strength(-1.0F).sounds(BlockSoundGroup.GLASS).luminance(createLightLevelFromLitBlockState(11)));
        public static final Block ORANGE_PORTAL = new PortalBlock(
                FabricBlockSettings.create().mapColor(MapColor.ORANGE).nonOpaque().noCollision().ticksRandomly()
                .strength(-1.0F).sounds(BlockSoundGroup.GLASS).luminance(createLightLevelFromLitBlockState(11)));
        public static final Block PINK_PORTAL = new PortalBlock(
                FabricBlockSettings.create().mapColor(MapColor.PINK).nonOpaque().noCollision().ticksRandomly()
                .strength(-1.0F).sounds(BlockSoundGroup.GLASS).luminance(createLightLevelFromLitBlockState(11)));
        public static final Block PURPLE_PORTAL = new PortalBlock(
                FabricBlockSettings.create().mapColor(MapColor.PURPLE).nonOpaque().noCollision().ticksRandomly()
                .strength(-1.0F).sounds(BlockSoundGroup.GLASS).luminance(createLightLevelFromLitBlockState(11)));
        public static final Block RED_PORTAL = new PortalBlock(
                FabricBlockSettings.create().mapColor(MapColor.RED).nonOpaque().noCollision().ticksRandomly()
                .strength(-1.0F).sounds(BlockSoundGroup.GLASS).luminance(createLightLevelFromLitBlockState(11)));
        public static final Block WHITE_PORTAL = new PortalBlock(
                FabricBlockSettings.create().mapColor(MapColor.WHITE).nonOpaque().noCollision().ticksRandomly()
                .strength(-1.0F).sounds(BlockSoundGroup.GLASS).luminance(createLightLevelFromLitBlockState(11)));
        public static final Block YELLOW_PORTAL = new PortalBlock(
                FabricBlockSettings.create().mapColor(MapColor.YELLOW).nonOpaque().noCollision().ticksRandomly()
                .strength(-1.0F).sounds(BlockSoundGroup.GLASS).luminance(createLightLevelFromLitBlockState(11)));

        // BlockEntities
        public static BlockEntityType<PortalBlockEntity> PORTAL_BLOCK_ENTITY;

        public static void registerBlocks() {
                Registry.register(Registries.BLOCK, new Identifier(CustomPortals.MOD_ID, "haste_rune_block"), HASTE_RUNE_BLOCK);
                Registry.register(Registries.BLOCK, new Identifier(CustomPortals.MOD_ID, "gate_rune_block"), GATE_RUNE_BLOCK);
                Registry.register(Registries.BLOCK, new Identifier(CustomPortals.MOD_ID, "weak_enhancer_rune_block"), WEAK_ENHANCER_RUNE_BLOCK);
                Registry.register(Registries.BLOCK, new Identifier(CustomPortals.MOD_ID, "strong_enhancer_rune_block"), STRONG_ENHANCER_RUNE_BLOCK);
                Registry.register(Registries.BLOCK, new Identifier(CustomPortals.MOD_ID, "infinity_rune_block"), INFINITY_RUNE_BLOCK);
                Registry.register(Registries.BLOCK, new Identifier(CustomPortals.MOD_ID, "black_portal"), BLACK_PORTAL);
                Registry.register(Registries.BLOCK, new Identifier(CustomPortals.MOD_ID, "blue_portal"), BLUE_PORTAL);
                Registry.register(Registries.BLOCK, new Identifier(CustomPortals.MOD_ID, "brown_portal"), BROWN_PORTAL);
                Registry.register(Registries.BLOCK, new Identifier(CustomPortals.MOD_ID, "cyan_portal"), CYAN_PORTAL);
                Registry.register(Registries.BLOCK, new Identifier(CustomPortals.MOD_ID, "gray_portal"), GRAY_PORTAL);
                Registry.register(Registries.BLOCK, new Identifier(CustomPortals.MOD_ID, "green_portal"), GREEN_PORTAL);
                Registry.register(Registries.BLOCK, new Identifier(CustomPortals.MOD_ID, "light_blue_portal"), LIGHT_BLUE_PORTAL);
                Registry.register(Registries.BLOCK, new Identifier(CustomPortals.MOD_ID, "light_gray_portal"), LIGHT_GRAY_PORTAL);
                Registry.register(Registries.BLOCK, new Identifier(CustomPortals.MOD_ID, "lime_portal"), LIME_PORTAL);
                Registry.register(Registries.BLOCK, new Identifier(CustomPortals.MOD_ID, "magenta_portal"), MAGENTA_PORTAL);
                Registry.register(Registries.BLOCK, new Identifier(CustomPortals.MOD_ID, "orange_portal"), ORANGE_PORTAL);
                Registry.register(Registries.BLOCK, new Identifier(CustomPortals.MOD_ID, "pink_portal"), PINK_PORTAL);
                Registry.register(Registries.BLOCK, new Identifier(CustomPortals.MOD_ID, "purple_portal"), PURPLE_PORTAL);
                Registry.register(Registries.BLOCK, new Identifier(CustomPortals.MOD_ID, "red_portal"), RED_PORTAL);
                Registry.register(Registries.BLOCK, new Identifier(CustomPortals.MOD_ID, "white_portal"), WHITE_PORTAL);
                Registry.register(Registries.BLOCK, new Identifier(CustomPortals.MOD_ID, "yellow_portal"), YELLOW_PORTAL);
                PORTAL_BLOCK_ENTITY = Registry.register(Registries.BLOCK_ENTITY_TYPE, "customportals:portal_block_entity", FabricBlockEntityTypeBuilder.create(
                        PortalBlockEntity::new, BLACK_PORTAL, BLUE_PORTAL, BROWN_PORTAL, CYAN_PORTAL, GRAY_PORTAL, GREEN_PORTAL, LIGHT_BLUE_PORTAL, 
                        LIGHT_GRAY_PORTAL, LIME_PORTAL, MAGENTA_PORTAL, ORANGE_PORTAL, PINK_PORTAL, PURPLE_PORTAL, RED_PORTAL, WHITE_PORTAL, YELLOW_PORTAL).build(null));
        }

        @Environment(EnvType.CLIENT)
        public static void setBlockRenderLayers() {
                BlockRenderLayerMap.INSTANCE.putBlock(HASTE_RUNE_BLOCK, RenderLayer.getCutout());
                BlockRenderLayerMap.INSTANCE.putBlock(GATE_RUNE_BLOCK, RenderLayer.getCutout());
                BlockRenderLayerMap.INSTANCE.putBlock(WEAK_ENHANCER_RUNE_BLOCK, RenderLayer.getCutout());
                BlockRenderLayerMap.INSTANCE.putBlock(STRONG_ENHANCER_RUNE_BLOCK, RenderLayer.getCutout());
                BlockRenderLayerMap.INSTANCE.putBlock(INFINITY_RUNE_BLOCK, RenderLayer.getCutout());
                BlockRenderLayerMap.INSTANCE.putBlock(BLACK_PORTAL, RenderLayer.getTranslucent());
                BlockRenderLayerMap.INSTANCE.putBlock(BLUE_PORTAL, RenderLayer.getTranslucent());
                BlockRenderLayerMap.INSTANCE.putBlock(BROWN_PORTAL, RenderLayer.getTranslucent());
                BlockRenderLayerMap.INSTANCE.putBlock(CYAN_PORTAL, RenderLayer.getTranslucent());
                BlockRenderLayerMap.INSTANCE.putBlock(GRAY_PORTAL, RenderLayer.getTranslucent());
                BlockRenderLayerMap.INSTANCE.putBlock(GREEN_PORTAL, RenderLayer.getTranslucent());
                BlockRenderLayerMap.INSTANCE.putBlock(LIGHT_BLUE_PORTAL, RenderLayer.getTranslucent());
                BlockRenderLayerMap.INSTANCE.putBlock(LIGHT_GRAY_PORTAL, RenderLayer.getTranslucent());
                BlockRenderLayerMap.INSTANCE.putBlock(LIME_PORTAL, RenderLayer.getTranslucent());
                BlockRenderLayerMap.INSTANCE.putBlock(MAGENTA_PORTAL, RenderLayer.getTranslucent());
                BlockRenderLayerMap.INSTANCE.putBlock(ORANGE_PORTAL, RenderLayer.getTranslucent());
                BlockRenderLayerMap.INSTANCE.putBlock(PINK_PORTAL, RenderLayer.getTranslucent());
                BlockRenderLayerMap.INSTANCE.putBlock(PURPLE_PORTAL, RenderLayer.getTranslucent());
                BlockRenderLayerMap.INSTANCE.putBlock(RED_PORTAL, RenderLayer.getTranslucent());
                BlockRenderLayerMap.INSTANCE.putBlock(WHITE_PORTAL, RenderLayer.getTranslucent());
                BlockRenderLayerMap.INSTANCE.putBlock(YELLOW_PORTAL, RenderLayer.getTranslucent());
        }

        private static ToIntFunction<BlockState> createLightLevelFromLitBlockState(int litLevel) {
                return (state) -> {
                   return (Boolean)state.get(Properties.LIT) ? litLevel : 0;
                };
        }
}
