package dev.custom.portals.registry;

import dev.custom.portals.CustomPortals;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class CPCreativeTabs {

    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, CustomPortals.MOD_ID);

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> GENERAL = CREATIVE_MODE_TABS.register("general",
            () -> CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup.customportals.general"))
                    .icon(() -> new ItemStack(CPItems.PURPLE_PORTAL_CATALYST.get()))
                    .displayItems((parameters, output) -> {
                        output.accept(CPItems.WHITE_PORTAL_CATALYST.get());
                        output.accept(CPItems.LIGHT_GRAY_PORTAL_CATALYST.get());
                        output.accept(CPItems.GRAY_PORTAL_CATALYST.get());
                        output.accept(CPItems.BLACK_PORTAL_CATALYST.get());
                        output.accept(CPItems.BROWN_PORTAL_CATALYST.get());
                        output.accept(CPItems.RED_PORTAL_CATALYST.get());
                        output.accept(CPItems.ORANGE_PORTAL_CATALYST.get());
                        output.accept(CPItems.YELLOW_PORTAL_CATALYST.get());
                        output.accept(CPItems.LIME_PORTAL_CATALYST.get());
                        output.accept(CPItems.GREEN_PORTAL_CATALYST.get());
                        output.accept(CPItems.CYAN_PORTAL_CATALYST.get());
                        output.accept(CPItems.LIGHT_BLUE_PORTAL_CATALYST.get());
                        output.accept(CPItems.BLUE_PORTAL_CATALYST.get());
                        output.accept(CPItems.PURPLE_PORTAL_CATALYST.get());
                        output.accept(CPItems.MAGENTA_PORTAL_CATALYST.get());
                        output.accept(CPItems.PINK_PORTAL_CATALYST.get());
                        output.accept(CPItems.HASTE_RUNE.get());
                        output.accept(CPItems.GATE_RUNE.get());
                        output.accept(CPItems.WEAK_ENHANCER_RUNE.get());
                        output.accept(CPItems.STRONG_ENHANCER_RUNE.get());
                        output.accept(CPItems.INFINITY_RUNE.get());
                    })
                    .build());
}
