package dev.custom.portals.items;

import java.util.function.Consumer;
import java.util.function.IntSupplier;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.Nullable;

/** Block item for runes; adds the gray description line the Fabric build added via ItemTooltipCallback. */
public class RuneBlockItem extends BlockItem {
    private final String tooltipKey;
    @Nullable
    private final IntSupplier rangeArgument;

    public RuneBlockItem(Block block, Item.Properties properties, String tooltipKey, @Nullable IntSupplier rangeArgument) {
        super(block, properties);
        this.tooltipKey = tooltipKey;
        this.rangeArgument = rangeArgument;
    }

    @Override
    public void appendHoverText(ItemStack stack, Item.TooltipContext context, TooltipDisplay display,
                                Consumer<Component> tooltip, TooltipFlag flag) {
        super.appendHoverText(stack, context, display, tooltip, flag);
        MutableComponent line = rangeArgument == null
                ? Component.translatable(tooltipKey)
                : Component.translatable(tooltipKey, rangeArgument.getAsInt());
        tooltip.accept(line.withStyle(ChatFormatting.GRAY));
    }
}
