package dev.custom.portals.config;

import dev.isxander.yacl3.api.NameableEnum;
import dev.isxander.yacl3.api.Option;
import dev.isxander.yacl3.api.controller.ControllerBuilder;
import dev.isxander.yacl3.api.controller.StringControllerBuilder;
import dev.isxander.yacl3.config.v2.api.ConfigClassHandler;
import dev.isxander.yacl3.config.v2.api.ConfigField;
import dev.isxander.yacl3.config.v2.api.SerialEntry;
import dev.isxander.yacl3.config.v2.api.autogen.*;
import dev.isxander.yacl3.config.v2.api.autogen.Boolean;
import dev.isxander.yacl3.config.v2.api.serializer.GsonConfigSerializerBuilder;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import org.jetbrains.annotations.Nullable;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public final class CPSettings {

    public static final Path CONFIG_PATH = FabricLoader.getInstance().getConfigDir().resolve("customportals.json");

    public static final ConfigClassHandler<CPSettings> HANDLER = ConfigClassHandler.createBuilder(CPSettings.class)
            .id(Identifier.fromNamespaceAndPath("customportals", "config"))
            .serializer(config -> GsonConfigSerializerBuilder.create(config).setJson5(true)
                    .setPath(CONFIG_PATH).build())
            .build();

    public static void load() {
        HANDLER.load();
    }

    public static void save() {
        HANDLER.save();
    }

    public static CPSettings instance() {
        return HANDLER.instance();
    }

    @AutoGen(category = "general")
    @Boolean
    @SerialEntry
    public boolean unlimitedRange = false;
    @AutoGen(category = "general")
    @Boolean
    @SerialEntry
    public boolean alwaysInterdim = false;
    @AutoGen(category = "general")
    @EnumCycler()
    @SerialEntry
    public HasteEnum alwaysHaste = HasteEnum.CREATIVE;
    @AutoGen(category = "general")
    @Boolean
    @CustomDescription({
            "Only allows portals to link if they were each constructed by the same player. Useful for servers."
    })
    @SerialEntry(comment = "Only allows portals to link if they were each constructed by the same player. Useful for servers.")
    public boolean privatePortals = false;
    @AutoGen(category = "general")
    @EnumCycler()
    @CustomDescription({
            "Note: Setting this to 'Turns Portals On' means your portals will be off without redstone power!"
    })
    @SerialEntry(comment = "Note: Setting this to 'Turns Portals On' means your portals will be off without redstone power!")
    public RedstoneEnum redstone = RedstoneEnum.OFF;
    @AutoGen(category = "general")
    @Boolean
    @SerialEntry
    public boolean muteAmbientSounds = false;
    @AutoGen(category = "general")
    @Boolean
    @SerialEntry
    public boolean muteTeleportSounds = false;
    @AutoGen(category = "range_settings")
    @IntField(min = 0, max = Integer.MAX_VALUE)
    @SerialEntry
    public int defaultRange = 100;
    @AutoGen(category = "range_settings")
    @IntField(min = 0, max = Integer.MAX_VALUE)
    @SerialEntry
    public int rangeWithEnhancer = 1000;
    @AutoGen(category = "range_settings")
    @IntField(min = 0, max = Integer.MAX_VALUE)
    @SerialEntry
    public int rangeWithStrongEnhancer = 10000;
    @AutoGen(category = "block_filter_settings")
    @Boolean(formatter = Boolean.Formatter.CUSTOM)
    @CustomDescription({
            "Whether the below list is a whitelist or blacklist"
    })
    @SerialEntry(comment = "Whether the below list is a whitelist or blacklist")
    public boolean isWhitelist = false;
    @AutoGen(category = "block_filter_settings")
    @ListGroup(valueFactory = ListFactory.class, controllerFactory = ListFactory.class)
    @CustomDescription({
            "Allows customizing which blocks may be used as portal frames. Takes the block's id, e.g. \"minecraft:stone\" for stone block."
    })
    @SerialEntry(comment = "Allows customizing which blocks may be used as portal frames. Takes the block's id, e.g. \"minecraft:stone\" for stone block.")
    public List<String> filteredBlocks = new ArrayList<>();

    public enum HasteEnum implements NameableEnum {
        YES("True"), NO("False"), CREATIVE("Creative Mode Only");

        private final String displayName;
        HasteEnum(String displayName) {
            this.displayName = displayName;
        }

        @Override
        public Component getDisplayName() {
            return Component.nullToEmpty(displayName);
        }
    }

    public enum RedstoneEnum implements NameableEnum {
        OFF("Turns Portals Off"), ON("Turns Portals On"), NONE("Has No Effect");

        private final String displayName;
        RedstoneEnum(String displayName) {
            this.displayName = displayName;
        }

        @Override
        public Component getDisplayName() {
            return Component.nullToEmpty(displayName);
        }
    }

    public static Screen createScreen(@Nullable Screen parent) {
        return HANDLER.generateGui().generateScreen(parent);
    }

    public static class ListFactory implements ListGroup.ValueFactory<String>, ListGroup.ControllerFactory<String> {
        @Override
        public String provideNewValue() {
            return "";
        }

        @Override
        public ControllerBuilder<String> createController(ListGroup annotation, ConfigField<List<String>> field, OptionAccess storage, Option<String> option) {
            return StringControllerBuilder.create(option);
        }
    }
}
