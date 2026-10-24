package dev.custom.portals.registry;

import java.util.function.ToIntFunction;

import dev.custom.portals.CustomPortals;
import dev.custom.portals.blocks.EnhancerRuneBlock;
import dev.custom.portals.blocks.GateRuneBlock;
import dev.custom.portals.blocks.HasteRuneBlock;
import dev.custom.portals.blocks.InfinityRuneBlock;
import dev.custom.portals.blocks.PortalBlock;
import dev.custom.portals.blocks.PortalBlockEntity;
import dev.custom.portals.blocks.StrongEnhancerRuneBlock;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class CPBlocks {

    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(CustomPortals.MOD_ID);
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPES =
            DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, CustomPortals.MOD_ID);

    private static final ToIntFunction<BlockState> STATE_TO_LUMINANCE = state -> state.getValue(BlockStateProperties.LIT) ? 11 : 0;
    private static final ToIntFunction<BlockState> RUNE_LUMINANCE = state -> 2;

    /** Fresh instance per block: BlockBehaviour.Properties must not be shared. */
    private static BlockBehaviour.Properties runeProperties() {
        return BlockBehaviour.Properties.of().sound(SoundType.AMETHYST_CLUSTER)
                .noOcclusion().lightLevel(RUNE_LUMINANCE).noCollision().strength(0.3F);
    }

    private static BlockBehaviour.Properties portalProperties(MapColor color, boolean emitsLight) {
        BlockBehaviour.Properties properties = BlockBehaviour.Properties.of().mapColor(color)
                .noOcclusion().noCollision().randomTicks().strength(-1.0F).sound(SoundType.GLASS);
        return emitsLight ? properties.lightLevel(STATE_TO_LUMINANCE) : properties;
    }

    // Properties are passed as Suppliers: DeferredRegister.Blocks#registerBlock calls setId on the supplied instance.

    // Runes
    public static final DeferredBlock<HasteRuneBlock> HASTE_RUNE_BLOCK =
            BLOCKS.registerBlock("haste_rune_block", HasteRuneBlock::new, CPBlocks::runeProperties);
    public static final DeferredBlock<GateRuneBlock> GATE_RUNE_BLOCK =
            BLOCKS.registerBlock("gate_rune_block", GateRuneBlock::new, CPBlocks::runeProperties);
    public static final DeferredBlock<EnhancerRuneBlock> WEAK_ENHANCER_RUNE_BLOCK =
            BLOCKS.registerBlock("weak_enhancer_rune_block", EnhancerRuneBlock::new, CPBlocks::runeProperties);
    public static final DeferredBlock<StrongEnhancerRuneBlock> STRONG_ENHANCER_RUNE_BLOCK =
            BLOCKS.registerBlock("strong_enhancer_rune_block", StrongEnhancerRuneBlock::new, CPBlocks::runeProperties);
    public static final DeferredBlock<InfinityRuneBlock> INFINITY_RUNE_BLOCK =
            BLOCKS.registerBlock("infinity_rune_block", InfinityRuneBlock::new, CPBlocks::runeProperties);

    // Portal blocks (black portals do not emit light, matching the Fabric build)
    public static final DeferredBlock<PortalBlock> BLACK_PORTAL = BLOCKS.registerBlock("black_portal", PortalBlock::new, () -> portalProperties(MapColor.COLOR_BLACK, false));
    public static final DeferredBlock<PortalBlock> BLUE_PORTAL = BLOCKS.registerBlock("blue_portal", PortalBlock::new, () -> portalProperties(MapColor.COLOR_BLUE, true));
    public static final DeferredBlock<PortalBlock> BROWN_PORTAL = BLOCKS.registerBlock("brown_portal", PortalBlock::new, () -> portalProperties(MapColor.COLOR_BROWN, true));
    public static final DeferredBlock<PortalBlock> CYAN_PORTAL = BLOCKS.registerBlock("cyan_portal", PortalBlock::new, () -> portalProperties(MapColor.COLOR_CYAN, true));
    public static final DeferredBlock<PortalBlock> GRAY_PORTAL = BLOCKS.registerBlock("gray_portal", PortalBlock::new, () -> portalProperties(MapColor.COLOR_GRAY, true));
    public static final DeferredBlock<PortalBlock> GREEN_PORTAL = BLOCKS.registerBlock("green_portal", PortalBlock::new, () -> portalProperties(MapColor.COLOR_GREEN, true));
    public static final DeferredBlock<PortalBlock> LIGHT_BLUE_PORTAL = BLOCKS.registerBlock("light_blue_portal", PortalBlock::new, () -> portalProperties(MapColor.COLOR_LIGHT_BLUE, true));
    public static final DeferredBlock<PortalBlock> LIGHT_GRAY_PORTAL = BLOCKS.registerBlock("light_gray_portal", PortalBlock::new, () -> portalProperties(MapColor.COLOR_LIGHT_GRAY, true));
    public static final DeferredBlock<PortalBlock> LIME_PORTAL = BLOCKS.registerBlock("lime_portal", PortalBlock::new, () -> portalProperties(MapColor.COLOR_LIGHT_GREEN, true));
    public static final DeferredBlock<PortalBlock> MAGENTA_PORTAL = BLOCKS.registerBlock("magenta_portal", PortalBlock::new, () -> portalProperties(MapColor.COLOR_MAGENTA, true));
    public static final DeferredBlock<PortalBlock> ORANGE_PORTAL = BLOCKS.registerBlock("orange_portal", PortalBlock::new, () -> portalProperties(MapColor.COLOR_ORANGE, true));
    public static final DeferredBlock<PortalBlock> PINK_PORTAL = BLOCKS.registerBlock("pink_portal", PortalBlock::new, () -> portalProperties(MapColor.COLOR_PINK, true));
    public static final DeferredBlock<PortalBlock> PURPLE_PORTAL = BLOCKS.registerBlock("purple_portal", PortalBlock::new, () -> portalProperties(MapColor.COLOR_PURPLE, true));
    public static final DeferredBlock<PortalBlock> RED_PORTAL = BLOCKS.registerBlock("red_portal", PortalBlock::new, () -> portalProperties(MapColor.COLOR_RED, true));
    public static final DeferredBlock<PortalBlock> WHITE_PORTAL = BLOCKS.registerBlock("white_portal", PortalBlock::new, () -> portalProperties(MapColor.SNOW, true));
    public static final DeferredBlock<PortalBlock> YELLOW_PORTAL = BLOCKS.registerBlock("yellow_portal", PortalBlock::new, () -> portalProperties(MapColor.COLOR_YELLOW, true));

    // Block entity
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<PortalBlockEntity>> PORTAL_BLOCK_ENTITY =
            BLOCK_ENTITY_TYPES.register("portal_block_entity", () -> new BlockEntityType<>(
                    PortalBlockEntity::new,
                    false,
                    BLACK_PORTAL.get(), BLUE_PORTAL.get(), BROWN_PORTAL.get(), CYAN_PORTAL.get(), GRAY_PORTAL.get(),
                    GREEN_PORTAL.get(), LIGHT_BLUE_PORTAL.get(), LIGHT_GRAY_PORTAL.get(), LIME_PORTAL.get(),
                    MAGENTA_PORTAL.get(), ORANGE_PORTAL.get(), PINK_PORTAL.get(), PURPLE_PORTAL.get(), RED_PORTAL.get(),
                    WHITE_PORTAL.get(), YELLOW_PORTAL.get()));
}
