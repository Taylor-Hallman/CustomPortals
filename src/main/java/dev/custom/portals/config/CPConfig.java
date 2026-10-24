package dev.custom.portals.config;

import java.util.List;

import net.neoforged.neoforge.common.ModConfigSpec;

/** NeoForge config spec. Read values through {@link CPSettings}. */
public final class CPConfig {
    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

    private static final String LANG = "customportals.configuration.";

    public static final ModConfigSpec.BooleanValue UNLIMITED_RANGE = BUILDER
            .comment("Portals always link regardless of distance.")
            .translation(LANG + "unlimitedRange")
            .define("unlimitedRange", false);

    public static final ModConfigSpec.BooleanValue ALWAYS_INTERDIM = BUILDER
            .comment("Portals always link across dimensions, even without a Gate Rune.")
            .translation(LANG + "alwaysInterdim")
            .define("alwaysInterdim", false);

    public static final ModConfigSpec.EnumValue<CPSettings.HasteEnum> ALWAYS_HASTE = BUILDER
            .comment("Whether portals always teleport instantly: YES, NO, or CREATIVE (creative mode only).")
            .translation(LANG + "alwaysHaste")
            .defineEnum("alwaysHaste", CPSettings.HasteEnum.CREATIVE);

    public static final ModConfigSpec.BooleanValue PRIVATE_PORTALS = BUILDER
            .comment("Only allows portals to link if they were each constructed by the same player. Useful for servers.")
            .translation(LANG + "privatePortals")
            .define("privatePortals", false);

    public static final ModConfigSpec.EnumValue<CPSettings.RedstoneEnum> REDSTONE = BUILDER
            .comment("What a redstone signal does to a portal: OFF turns portals off, ON turns portals on, NONE has no effect.",
                    "Note: Setting this to ON means your portals will be off without redstone power!")
            .translation(LANG + "redstone")
            .defineEnum("redstone", CPSettings.RedstoneEnum.OFF);

    public static final ModConfigSpec.BooleanValue MUTE_AMBIENT_SOUNDS = BUILDER
            .comment("Mute the ambient portal sound effects.")
            .translation(LANG + "muteAmbientSounds")
            .define("muteAmbientSounds", false);

    public static final ModConfigSpec.BooleanValue MUTE_TELEPORT_SOUNDS = BUILDER
            .comment("Mute the player teleport sound effects.")
            .translation(LANG + "muteTeleportSounds")
            .define("muteTeleportSounds", false);

    public static final ModConfigSpec.IntValue DEFAULT_RANGE = BUILDER
            .comment("Default portal range, in blocks.")
            .translation(LANG + "defaultRange")
            .defineInRange("defaultRange", 100, 0, Integer.MAX_VALUE);

    public static final ModConfigSpec.IntValue RANGE_WITH_ENHANCER = BUILDER
            .comment("Portal range with an Enhancer Rune, in blocks.")
            .translation(LANG + "rangeWithEnhancer")
            .defineInRange("rangeWithEnhancer", 1000, 0, Integer.MAX_VALUE);

    public static final ModConfigSpec.IntValue RANGE_WITH_STRONG_ENHANCER = BUILDER
            .comment("Portal range with a Strong Enhancer Rune, in blocks.")
            .translation(LANG + "rangeWithStrongEnhancer")
            .defineInRange("rangeWithStrongEnhancer", 10000, 0, Integer.MAX_VALUE);

    public static final ModConfigSpec.BooleanValue IS_WHITELIST = BUILDER
            .comment("Whether filteredBlocks is a whitelist (true) or a blacklist (false).")
            .translation(LANG + "isWhitelist")
            .define("isWhitelist", false);

    public static final ModConfigSpec.ConfigValue<List<? extends String>> FILTERED_BLOCKS = BUILDER
            .comment("Allows customizing which blocks may be used as portal frames. Takes the block's id, e.g. \"minecraft:stone\" for stone block.")
            .translation(LANG + "filteredBlocks")
            .defineListAllowEmpty("filteredBlocks", List.of(), () -> "", obj -> obj instanceof String);

    public static final ModConfigSpec SPEC = BUILDER.build();

    private CPConfig() {}
}
