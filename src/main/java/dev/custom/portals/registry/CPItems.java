package dev.custom.portals.registry;

import dev.custom.portals.CustomPortals;
import dev.custom.portals.config.CPSettings;
import dev.custom.portals.items.PortalCatalyst;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.item.v1.ItemTooltipCallback;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.ChatFormatting;
import net.minecraft.resources.Identifier;

public class CPItems {

    private static ResourceKey<Item> getKey(String name) {
        Identifier id = Identifier.fromNamespaceAndPath(CustomPortals.MOD_ID, name);
        return ResourceKey.create(Registries.ITEM, id);
    }
    
    // Items
    public static final Item WHITE_PORTAL_CATALYST = new PortalCatalyst(
        new Item.Properties().stacksTo(16).setId(getKey("white_portal_catalyst")), CPBlocks.WHITE_PORTAL);
    public static final Item ORANGE_PORTAL_CATALYST = new PortalCatalyst(
        new Item.Properties().stacksTo(16).setId(getKey("orange_portal_catalyst")), CPBlocks.ORANGE_PORTAL);
    public static final Item MAGENTA_PORTAL_CATALYST = new PortalCatalyst(
        new Item.Properties().stacksTo(16).setId(getKey("magenta_portal_catalyst")), CPBlocks.MAGENTA_PORTAL);
    public static final Item LIGHT_BLUE_PORTAL_CATALYST = new PortalCatalyst(
        new Item.Properties().stacksTo(16).setId(getKey("light_blue_portal_catalyst")), CPBlocks.LIGHT_BLUE_PORTAL);
    public static final Item YELLOW_PORTAL_CATALYST = new PortalCatalyst(
        new Item.Properties().stacksTo(16).setId(getKey("yellow_portal_catalyst")), CPBlocks.YELLOW_PORTAL);
    public static final Item LIME_PORTAL_CATALYST = new PortalCatalyst(
        new Item.Properties().stacksTo(16).setId(getKey("lime_portal_catalyst")), CPBlocks.LIME_PORTAL);
    public static final Item PINK_PORTAL_CATALYST = new PortalCatalyst(
        new Item.Properties().stacksTo(16).setId(getKey("pink_portal_catalyst")), CPBlocks.PINK_PORTAL);
    public static final Item GRAY_PORTAL_CATALYST = new PortalCatalyst(
        new Item.Properties().stacksTo(16).setId(getKey("gray_portal_catalyst")), CPBlocks.GRAY_PORTAL);
    public static final Item LIGHT_GRAY_PORTAL_CATALYST = new PortalCatalyst(
        new Item.Properties().stacksTo(16).setId(getKey("light_gray_portal_catalyst")), CPBlocks.LIGHT_GRAY_PORTAL);
    public static final Item CYAN_PORTAL_CATALYST = new PortalCatalyst(
        new Item.Properties().stacksTo(16).setId(getKey("cyan_portal_catalyst")), CPBlocks.CYAN_PORTAL);
    public static final Item PURPLE_PORTAL_CATALYST = new PortalCatalyst(
        new Item.Properties().stacksTo(16).setId(getKey("purple_portal_catalyst")), CPBlocks.PURPLE_PORTAL);
    public static final Item BLUE_PORTAL_CATALYST = new PortalCatalyst(
        new Item.Properties().stacksTo(16).setId(getKey("blue_portal_catalyst")), CPBlocks.BLUE_PORTAL);
    public static final Item BROWN_PORTAL_CATALYST = new PortalCatalyst(
        new Item.Properties().stacksTo(16).setId(getKey("brown_portal_catalyst")), CPBlocks.BROWN_PORTAL);
    public static final Item GREEN_PORTAL_CATALYST = new PortalCatalyst(
        new Item.Properties().stacksTo(16).setId(getKey("green_portal_catalyst")), CPBlocks.GREEN_PORTAL);
    public static final Item RED_PORTAL_CATALYST = new PortalCatalyst(
        new Item.Properties().stacksTo(16).setId(getKey("red_portal_catalyst")), CPBlocks.RED_PORTAL);
    public static final Item BLACK_PORTAL_CATALYST = new PortalCatalyst(
        new Item.Properties().stacksTo(16).setId(getKey("black_portal_catalyst")), CPBlocks.BLACK_PORTAL);

    // BlockItems
    public static final BlockItem HASTE_RUNE = new BlockItem(
        CPBlocks.HASTE_RUNE_BLOCK, new Item.Properties().stacksTo(1).setId(getKey("haste_rune")));
    public static final BlockItem GATE_RUNE = new BlockItem(
        CPBlocks.GATE_RUNE_BLOCK, new Item.Properties().stacksTo(1).setId(getKey("gate_rune")));
    public static final BlockItem WEAK_ENHANCER_RUNE = new BlockItem(
        CPBlocks.WEAK_ENHANCER_RUNE_BLOCK, new Item.Properties().stacksTo(1).setId(getKey("weak_enhancer_rune")));
    public static final BlockItem STRONG_ENHANCER_RUNE = new BlockItem(
        CPBlocks.STRONG_ENHANCER_RUNE_BLOCK, new Item.Properties().stacksTo(1).setId(getKey("strong_enhancer_rune")));
    public static final BlockItem INFINITY_RUNE = new BlockItem(
        CPBlocks.INFINITY_RUNE_BLOCK, new Item.Properties().stacksTo(1).setId(getKey("infinity_rune")));

    public static void registerItems() {
        Registry.register(BuiltInRegistries.ITEM, Identifier.fromNamespaceAndPath(CustomPortals.MOD_ID, "white_portal_catalyst"),
            WHITE_PORTAL_CATALYST);
        Registry.register(BuiltInRegistries.ITEM, Identifier.fromNamespaceAndPath(CustomPortals.MOD_ID, "orange_portal_catalyst"),
            ORANGE_PORTAL_CATALYST);
        Registry.register(BuiltInRegistries.ITEM, Identifier.fromNamespaceAndPath(CustomPortals.MOD_ID, "magenta_portal_catalyst"),
            MAGENTA_PORTAL_CATALYST);
        Registry.register(BuiltInRegistries.ITEM, Identifier.fromNamespaceAndPath(CustomPortals.MOD_ID, "light_blue_portal_catalyst"),
            LIGHT_BLUE_PORTAL_CATALYST);
        Registry.register(BuiltInRegistries.ITEM, Identifier.fromNamespaceAndPath(CustomPortals.MOD_ID, "yellow_portal_catalyst"),
            YELLOW_PORTAL_CATALYST);
        Registry.register(BuiltInRegistries.ITEM, Identifier.fromNamespaceAndPath(CustomPortals.MOD_ID, "lime_portal_catalyst"), LIME_PORTAL_CATALYST);
        Registry.register(BuiltInRegistries.ITEM, Identifier.fromNamespaceAndPath(CustomPortals.MOD_ID, "pink_portal_catalyst"), PINK_PORTAL_CATALYST);
        Registry.register(BuiltInRegistries.ITEM, Identifier.fromNamespaceAndPath(CustomPortals.MOD_ID, "gray_portal_catalyst"), GRAY_PORTAL_CATALYST);
        Registry.register(BuiltInRegistries.ITEM, Identifier.fromNamespaceAndPath(CustomPortals.MOD_ID, "light_gray_portal_catalyst"),
            LIGHT_GRAY_PORTAL_CATALYST);
        Registry.register(BuiltInRegistries.ITEM, Identifier.fromNamespaceAndPath(CustomPortals.MOD_ID, "cyan_portal_catalyst"), CYAN_PORTAL_CATALYST);
        Registry.register(BuiltInRegistries.ITEM, Identifier.fromNamespaceAndPath(CustomPortals.MOD_ID, "purple_portal_catalyst"),
            PURPLE_PORTAL_CATALYST);
        Registry.register(BuiltInRegistries.ITEM, Identifier.fromNamespaceAndPath(CustomPortals.MOD_ID, "blue_portal_catalyst"), BLUE_PORTAL_CATALYST);
        Registry.register(BuiltInRegistries.ITEM, Identifier.fromNamespaceAndPath(CustomPortals.MOD_ID, "brown_portal_catalyst"),
            BROWN_PORTAL_CATALYST);
        Registry.register(BuiltInRegistries.ITEM, Identifier.fromNamespaceAndPath(CustomPortals.MOD_ID, "green_portal_catalyst"),
            GREEN_PORTAL_CATALYST);
        Registry.register(BuiltInRegistries.ITEM, Identifier.fromNamespaceAndPath(CustomPortals.MOD_ID, "red_portal_catalyst"), RED_PORTAL_CATALYST);
        Registry.register(BuiltInRegistries.ITEM, Identifier.fromNamespaceAndPath(CustomPortals.MOD_ID, "black_portal_catalyst"),
            BLACK_PORTAL_CATALYST);
        Registry.register(BuiltInRegistries.ITEM, Identifier.fromNamespaceAndPath(CustomPortals.MOD_ID, "haste_rune"), HASTE_RUNE);
        Registry.register(BuiltInRegistries.ITEM, Identifier.fromNamespaceAndPath(CustomPortals.MOD_ID, "gate_rune"), GATE_RUNE);
        Registry.register(BuiltInRegistries.ITEM, Identifier.fromNamespaceAndPath(CustomPortals.MOD_ID, "weak_enhancer_rune"), WEAK_ENHANCER_RUNE);
        Registry.register(BuiltInRegistries.ITEM, Identifier.fromNamespaceAndPath(CustomPortals.MOD_ID, "strong_enhancer_rune"), STRONG_ENHANCER_RUNE);
        Registry.register(BuiltInRegistries.ITEM, Identifier.fromNamespaceAndPath(CustomPortals.MOD_ID, "infinity_rune"), INFINITY_RUNE);

        ItemGroupEvents.modifyEntriesEvent(CustomPortals.PORTALS_ITEM_GROUP).register(content -> {
            content.accept(WHITE_PORTAL_CATALYST);
            content.accept(LIGHT_GRAY_PORTAL_CATALYST);
            content.accept(GRAY_PORTAL_CATALYST);
            content.accept(BLACK_PORTAL_CATALYST);
            content.accept(BROWN_PORTAL_CATALYST);
            content.accept(RED_PORTAL_CATALYST);
            content.accept(ORANGE_PORTAL_CATALYST);
            content.accept(YELLOW_PORTAL_CATALYST);
            content.accept(LIME_PORTAL_CATALYST);
            content.accept(GREEN_PORTAL_CATALYST);
            content.accept(CYAN_PORTAL_CATALYST);
            content.accept(LIGHT_BLUE_PORTAL_CATALYST);
            content.accept(BLUE_PORTAL_CATALYST);
            content.accept(PURPLE_PORTAL_CATALYST);
            content.accept(MAGENTA_PORTAL_CATALYST);
            content.accept(PINK_PORTAL_CATALYST);
            content.accept(HASTE_RUNE);
            content.accept(GATE_RUNE);
            content.accept(WEAK_ENHANCER_RUNE);
            content.accept(STRONG_ENHANCER_RUNE);
            content.accept(INFINITY_RUNE);
        });
    }

    @Environment(EnvType.CLIENT)
    public static void registerItemTooltips() {
        ItemTooltipCallback.EVENT.register((itemStack, tooltipContext, tooltipType, list) -> {
            if (itemStack.is(HASTE_RUNE)) {
                list.add(Component.translatable("item.customportals.haste_rune.tooltip").withStyle(ChatFormatting.GRAY));
            }
            else if (itemStack.is(GATE_RUNE)) {
                list.add(Component.translatable("item.customportals.gate_rune.tooltip").withStyle(ChatFormatting.GRAY));
            }
            else if (itemStack.is(WEAK_ENHANCER_RUNE)) {
                list.add(Component.translatable("item.customportals.weak_enhancer_rune.tooltip", CPSettings.instance().rangeWithEnhancer).withStyle(ChatFormatting.GRAY));
            }
            else if (itemStack.is(STRONG_ENHANCER_RUNE)) {
                list.add(Component.translatable("item.customportals.weak_enhancer_rune.tooltip", CPSettings.instance().rangeWithStrongEnhancer).withStyle(ChatFormatting.GRAY));
            }
            else if (itemStack.is(INFINITY_RUNE)) {
                list.add(Component.translatable("item.customportals.infinity_rune.tooltip").withStyle(ChatFormatting.GRAY));
            }
        });
    }
}
