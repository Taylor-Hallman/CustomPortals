package dev.custom.portals.data;

import java.util.List;
import java.util.UUID;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import dev.custom.portals.config.CPSettings;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.network.chat.ClickEvent;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;

public class CustomPortal {

    public float offsetX;
    public float offsetZ;

    private final String frameId;
    private final String dimensionId;
    private final MapColor color;

    private BlockPos spawnPos;
    private List<BlockPos> portalBlocks;
    private int hasteRunes;
    private int gateRunes;
    private int weakEnhancerRunes;
    private int strongEnhancerRunes;
    private int infinityRunes;
    private final UUID creatorId;

    private boolean hasRedstoneSignal = false;
    
    private CustomPortal linked;

    public static final Codec<CustomPortal> CODEC = RecordCodecBuilder.create(instance -> instance.group(
        Codec.STRING.fieldOf("frameId").forGetter(CustomPortal::getFrameId),
        Codec.STRING.fieldOf("dimensionId").forGetter(CustomPortal::getDimensionId),
        Codec.INT.fieldOf("color").forGetter(CustomPortal::getColorId),
        BlockPos.CODEC.fieldOf("spawnPos").forGetter(CustomPortal::getSpawnPos),
        BlockPos.CODEC.listOf().fieldOf("portalBlocks").forGetter(CustomPortal::getPortalBlocks),
        Codec.FLOAT.fieldOf("offsetX").forGetter(CustomPortal::getOffsetX),
        Codec.FLOAT.fieldOf("offsetZ").forGetter(CustomPortal::getOffsetZ),
        Codec.STRING.fieldOf("creatorId").forGetter(CustomPortal::getCreatorIdAsString),
        Codec.INT.fieldOf("hasteRunes").forGetter(CustomPortal::getHasteRunes),
        Codec.INT.fieldOf("gateRunes").forGetter(CustomPortal::getGateRunes),
        Codec.INT.fieldOf("weakEnhancerRunes").forGetter(CustomPortal::getWeakEnhancerRunes),
        Codec.INT.fieldOf("strongEnhancerRunes").forGetter(CustomPortal::getStrongEnhancerRunes),
        Codec.INT.fieldOf("infinityRunes").forGetter(CustomPortal::getInfinityRunes)
    ).apply(instance, CustomPortal::new));

    public CustomPortal(final String frameId, final String dimensionId, final MapColor color, final BlockPos spawnPos,
                        final List<BlockPos> portalBlocks, final float offsetX, final float offsetZ, final UUID creatorId) {
        this.frameId = frameId;
        this.dimensionId = dimensionId;
        this.color = color;
        this.spawnPos = spawnPos;
        this.portalBlocks = portalBlocks;
        this.offsetX = offsetX;
        this.offsetZ = offsetZ;
        this.creatorId = creatorId;
        hasteRunes = 0;
        gateRunes = 0;
        weakEnhancerRunes = 0;
        strongEnhancerRunes = 0;
        infinityRunes = 0;
    }

    public float getOffsetX() {
        return offsetX;
    }

    public float getOffsetZ() {
        return offsetZ;
    }

    public CustomPortal(final String frameId, final String dimensionId, final MapColor color, final BlockPos spawnPos,
                        final List<BlockPos> portalBlocks, final float offsetX, final float offsetZ, final UUID creatorId, int hasteRunes, int gateRunes,
                        int weakEnhancerRunes, int strongEnhancerRunes, int infinityRunes) {
        this.frameId = frameId;
        this.dimensionId = dimensionId;
        this.color = color;
        this.spawnPos = spawnPos;
        this.portalBlocks = portalBlocks;
        this.offsetX = offsetX;
        this.offsetZ = offsetZ;
        this.creatorId = creatorId;
        this.hasteRunes = hasteRunes;
        this.gateRunes = gateRunes;
        this.weakEnhancerRunes = weakEnhancerRunes;
        this.strongEnhancerRunes = strongEnhancerRunes;
        this.infinityRunes = infinityRunes;
    }

    public CustomPortal(final String frameId, final String dimensionId, final int colorId, final BlockPos spawnPos,
                        final List<BlockPos> portalBlocks, final float offsetX, final float offsetZ, final String creatorId, int hasteRunes, int gateRunes,
                        int weakEnhancerRunes, int strongEnhancerRunes, int infinityRunes) {
        this.frameId = frameId;
        this.dimensionId = dimensionId;
        this.color = MapColor.byId(colorId);
        this.spawnPos = spawnPos;
        this.portalBlocks = portalBlocks;
        this.offsetX = offsetX;
        this.offsetZ = offsetZ;
        this.creatorId = UUID.fromString(creatorId);
        this.hasteRunes = hasteRunes;
        this.gateRunes = gateRunes;
        this.weakEnhancerRunes = weakEnhancerRunes;
        this.strongEnhancerRunes = strongEnhancerRunes;
        this.infinityRunes = infinityRunes;
    }

    public String getFrameId() {
        return frameId;
    }

    public String getDimensionId() {
        return dimensionId;
    }

    public MapColor getColor() {
        return color;
    }

    public int getColorId() { return color.id; }

    public CustomPortal getLinked() {
        return linked;
    }

    public BlockPos getSpawnPos() {
        return spawnPos;
    }

    public List<BlockPos> getPortalBlocks() {
        return portalBlocks;
    }

    public UUID getCreatorId() {
        return creatorId;
    }

    public String getCreatorIdAsString() { return creatorId.toString(); }

    public boolean hasLinked() {
        return linked != null;
    }

    public boolean isInterdimensional() {
        return hasLinked() && !dimensionId.equals(linked.getDimensionId());
    }

    public boolean hasRedstoneSignal() {
        return hasRedstoneSignal;
    }

    public void setHasRedstoneSignal(boolean hasRedstoneSignal) {
        this.hasRedstoneSignal = hasRedstoneSignal;
    }

    public void setLinked(final CustomPortal portal) {
        linked = portal;
    }

    public int getPlayerTeleportDelay() {
        if (CPSettings.instance().alwaysHaste == CPSettings.HasteEnum.YES)
            return 1;
        return this.hasHaste() ? 1 : 80;
    }

    public void setSpawnPos(BlockPos newSpawn) {
        spawnPos = newSpawn;
        offsetX = 0.5f;
        offsetZ = 0.5f;
    }

    public int getHasteRunes() { return hasteRunes; }
    public int getGateRunes() { return gateRunes; }
    public int getWeakEnhancerRunes() { return weakEnhancerRunes; }
    public int getStrongEnhancerRunes() { return strongEnhancerRunes; }
    public int getInfinityRunes() { return infinityRunes; }

    public void addHaste() { hasteRunes++; }
    public void addGate() { gateRunes++; }
    public void addWeakEnhancer() { weakEnhancerRunes++; }
    public void addStrongEnhancer() { strongEnhancerRunes++; }
    public void addInfinity() { infinityRunes++; }

    public void removeHaste() { hasteRunes--; }
    public void removeGate() { gateRunes--; }
    public void removeWeakEnhancer() { weakEnhancerRunes--; }
    public void removeStrongEnhancer() { strongEnhancerRunes--; }
    public void removeInfinity() { infinityRunes--; }

    public boolean hasHaste() {
        int i = this.hasLinked() ? linked.getHasteRunes() : 0; 
        return (hasteRunes + i) > 0;
    }

    public boolean hasGate() {
        int i = this.hasLinked() ? linked.getGateRunes() : 0; 
        return (gateRunes + i) > 0;
    }

    public int getEnhanceTier() {
        int i = this.hasLinked() ? linked.getInfinityRunes() : 0;
        int j = this.hasLinked() ? linked.getStrongEnhancerRunes() : 0;
        int k = this.hasLinked() ? linked.getWeakEnhancerRunes() : 0; 
        if ((infinityRunes + i) > 0)
            return 3;
        if ((strongEnhancerRunes + j) > 0)
            return 2;
        if ((weakEnhancerRunes + k) > 0)
            return 1;
        return 0;
    }

    public void tryLink(final CustomPortal portal) {
        if (CPSettings.instance().privatePortals && this.creatorId != null && portal.creatorId != null &&
                !this.creatorId.equals(portal.getCreatorId())) return;
        if (portal.getColor() == color && portal.getFrameId().equals(frameId) && portal != this) {
            if (portal.hasLinked()) {
                if (portal.getLinked() != this)
                    return;
            }
            if (!CPSettings.instance().unlimitedRange || !CPSettings.instance().alwaysInterdim) {
                int distance;
                if (!portal.getDimensionId().equals(dimensionId)) {
                    if (!this.hasGate() && !portal.hasGate() && !CPSettings.instance().alwaysInterdim)
                        return;
                    if (portal.getDimensionId().equals("minecraft:the_nether")) {
                        int translatedX = portal.getSpawnPos().getX() * 8;
                        int translatedZ = portal.getSpawnPos().getZ() * 8;
                        BlockPos translatedSpawnPos = new BlockPos(new Vec3i(
                                translatedX, portal.getSpawnPos().getY(), translatedZ));
                        distance = distance(spawnPos, translatedSpawnPos);
                    } else if (this.dimensionId.equals("minecraft:the_nether")) {
                        int translatedX = spawnPos.getX() * 8;
                        int translatedZ = spawnPos.getZ() * 8;
                        BlockPos translatedSpawnPos = new BlockPos(new Vec3i(
                                translatedX, spawnPos.getY(), translatedZ));
                        distance = distance(translatedSpawnPos, portal.getSpawnPos());
                    } else distance = distance(spawnPos, portal.getSpawnPos());
                } else distance = distance(spawnPos, portal.getSpawnPos());
                if (!CPSettings.instance().unlimitedRange) {
                    int tier = Math.max(portal.getEnhanceTier(), this.getEnhanceTier());
                    switch (tier) {
                        case 0 -> {
                            if (distance > CPSettings.instance().defaultRange)
                                return;
                        }
                        case 1 -> {
                            if (distance > CPSettings.instance().rangeWithEnhancer)
                                return;
                        }
                        case 2 -> {
                            if (distance > CPSettings.instance().rangeWithStrongEnhancer)
                                return;
                        }
                    }
                }
            }
            linked = portal;
            portal.setLinked(this);
        }
    }

    private int distance(BlockPos pos1, BlockPos pos2) {
        int x1 = pos1.getX();
        int x2 = pos2.getX();
        int y1 = pos1.getY();
        int y2 = pos2.getY();
        int z1 = pos1.getZ();
        int z2 = pos2.getZ();
        double d = Math.sqrt(Math.pow((x2 - x1), 2) + Math.pow((y2 - y1), 2) + Math.pow((z2 - z1), 2));
        return (int)d;
    }
}
