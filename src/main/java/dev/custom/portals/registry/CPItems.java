package dev.custom.portals.registry;

import java.util.function.IntSupplier;

import dev.custom.portals.CustomPortals;
import dev.custom.portals.blocks.AbstractRuneBlock;
import dev.custom.portals.blocks.PortalBlock;
import dev.custom.portals.config.CPSettings;
import dev.custom.portals.items.PortalCatalyst;
import dev.custom.portals.items.RuneBlockItem;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.jetbrains.annotations.Nullable;

public class CPItems {

    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(CustomPortals.MOD_ID);

    private static DeferredItem<PortalCatalyst> catalyst(String name, DeferredBlock<PortalBlock> portal) {
        return ITEMS.registerItem(name, properties -> new PortalCatalyst(properties, portal), properties -> properties.stacksTo(16));
    }

    private static DeferredItem<RuneBlockItem> rune(String name, DeferredBlock<? extends AbstractRuneBlock> block,
                                                   @Nullable IntSupplier rangeArgument) {
        String tooltipKey = "item." + CustomPortals.MOD_ID + "." + name + ".tooltip";
        return ITEMS.registerItem(name, properties -> new RuneBlockItem(block.get(), properties, tooltipKey, rangeArgument),
                properties -> properties.stacksTo(1));
    }

    // Catalysts
    public static final DeferredItem<PortalCatalyst> WHITE_PORTAL_CATALYST = catalyst("white_portal_catalyst", CPBlocks.WHITE_PORTAL);
    public static final DeferredItem<PortalCatalyst> ORANGE_PORTAL_CATALYST = catalyst("orange_portal_catalyst", CPBlocks.ORANGE_PORTAL);
    public static final DeferredItem<PortalCatalyst> MAGENTA_PORTAL_CATALYST = catalyst("magenta_portal_catalyst", CPBlocks.MAGENTA_PORTAL);
    public static final DeferredItem<PortalCatalyst> LIGHT_BLUE_PORTAL_CATALYST = catalyst("light_blue_portal_catalyst", CPBlocks.LIGHT_BLUE_PORTAL);
    public static final DeferredItem<PortalCatalyst> YELLOW_PORTAL_CATALYST = catalyst("yellow_portal_catalyst", CPBlocks.YELLOW_PORTAL);
    public static final DeferredItem<PortalCatalyst> LIME_PORTAL_CATALYST = catalyst("lime_portal_catalyst", CPBlocks.LIME_PORTAL);
    public static final DeferredItem<PortalCatalyst> PINK_PORTAL_CATALYST = catalyst("pink_portal_catalyst", CPBlocks.PINK_PORTAL);
    public static final DeferredItem<PortalCatalyst> GRAY_PORTAL_CATALYST = catalyst("gray_portal_catalyst", CPBlocks.GRAY_PORTAL);
    public static final DeferredItem<PortalCatalyst> LIGHT_GRAY_PORTAL_CATALYST = catalyst("light_gray_portal_catalyst", CPBlocks.LIGHT_GRAY_PORTAL);
    public static final DeferredItem<PortalCatalyst> CYAN_PORTAL_CATALYST = catalyst("cyan_portal_catalyst", CPBlocks.CYAN_PORTAL);
    public static final DeferredItem<PortalCatalyst> PURPLE_PORTAL_CATALYST = catalyst("purple_portal_catalyst", CPBlocks.PURPLE_PORTAL);
    public static final DeferredItem<PortalCatalyst> BLUE_PORTAL_CATALYST = catalyst("blue_portal_catalyst", CPBlocks.BLUE_PORTAL);
    public static final DeferredItem<PortalCatalyst> BROWN_PORTAL_CATALYST = catalyst("brown_portal_catalyst", CPBlocks.BROWN_PORTAL);
    public static final DeferredItem<PortalCatalyst> GREEN_PORTAL_CATALYST = catalyst("green_portal_catalyst", CPBlocks.GREEN_PORTAL);
    public static final DeferredItem<PortalCatalyst> RED_PORTAL_CATALYST = catalyst("red_portal_catalyst", CPBlocks.RED_PORTAL);
    public static final DeferredItem<PortalCatalyst> BLACK_PORTAL_CATALYST = catalyst("black_portal_catalyst", CPBlocks.BLACK_PORTAL);

    // Rune block items (item ids differ from block ids, as in the Fabric build)
    public static final DeferredItem<RuneBlockItem> HASTE_RUNE = rune("haste_rune", CPBlocks.HASTE_RUNE_BLOCK, null);
    public static final DeferredItem<RuneBlockItem> GATE_RUNE = rune("gate_rune", CPBlocks.GATE_RUNE_BLOCK, null);
    public static final DeferredItem<RuneBlockItem> WEAK_ENHANCER_RUNE = rune("weak_enhancer_rune", CPBlocks.WEAK_ENHANCER_RUNE_BLOCK, CPSettings::rangeWithEnhancer);
    public static final DeferredItem<RuneBlockItem> STRONG_ENHANCER_RUNE = rune("strong_enhancer_rune", CPBlocks.STRONG_ENHANCER_RUNE_BLOCK, CPSettings::rangeWithStrongEnhancer);
    public static final DeferredItem<RuneBlockItem> INFINITY_RUNE = rune("infinity_rune", CPBlocks.INFINITY_RUNE_BLOCK, null);
}
