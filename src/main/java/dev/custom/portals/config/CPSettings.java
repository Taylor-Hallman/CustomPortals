package dev.custom.portals.config;

import java.util.List;

import net.minecraft.network.chat.Component;
import net.neoforged.neoforge.common.TranslatableEnum;

/** Static facade over {@link CPConfig}. Call sites use e.g. {@code CPSettings.unlimitedRange()}. */
public final class CPSettings {
    private CPSettings() {}

    public static boolean unlimitedRange() { return CPConfig.UNLIMITED_RANGE.get(); }
    public static boolean alwaysInterdim() { return CPConfig.ALWAYS_INTERDIM.get(); }
    public static HasteEnum alwaysHaste() { return CPConfig.ALWAYS_HASTE.get(); }
    public static boolean privatePortals() { return CPConfig.PRIVATE_PORTALS.get(); }
    public static RedstoneEnum redstone() { return CPConfig.REDSTONE.get(); }
    public static boolean muteAmbientSounds() { return CPConfig.MUTE_AMBIENT_SOUNDS.get(); }
    public static boolean muteTeleportSounds() { return CPConfig.MUTE_TELEPORT_SOUNDS.get(); }
    public static int defaultRange() { return CPConfig.DEFAULT_RANGE.get(); }
    public static int rangeWithEnhancer() { return CPConfig.RANGE_WITH_ENHANCER.get(); }
    public static int rangeWithStrongEnhancer() { return CPConfig.RANGE_WITH_STRONG_ENHANCER.get(); }
    public static boolean isWhitelist() { return CPConfig.IS_WHITELIST.get(); }
    public static List<? extends String> filteredBlocks() { return CPConfig.FILTERED_BLOCKS.get(); }

    public enum HasteEnum implements TranslatableEnum {
        YES("True"), NO("False"), CREATIVE("Creative Mode Only");

        private final String displayName;
        HasteEnum(String displayName) { this.displayName = displayName; }

        @Override
        public Component getTranslatedName() { return Component.literal(displayName); }
    }

    public enum RedstoneEnum implements TranslatableEnum {
        OFF("Turns Portals Off"), ON("Turns Portals On"), NONE("Has No Effect");

        private final String displayName;
        RedstoneEnum(String displayName) { this.displayName = displayName; }

        @Override
        public Component getTranslatedName() { return Component.literal(displayName); }
    }
}
