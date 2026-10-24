package dev.custom.portals.util;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Stack;
import java.util.UUID;

import dev.custom.portals.blocks.AbstractRuneBlock;
import dev.custom.portals.config.CPSettings;
import dev.custom.portals.data.CustomPortal;
import dev.custom.portals.data.PortalData;
import dev.custom.portals.registry.CPBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;

public class PortalHelper {

    private static BlockPos getUp(BlockPos pos, Direction.Axis axis) {
        return axis == Direction.Axis.Y ? pos.north() : pos.above();
    }

    private static BlockPos getDown(BlockPos pos, Direction.Axis axis) {
        return axis == Direction.Axis.Y ? pos.south() : pos.below();
    }

    private static BlockPos getLeft(BlockPos pos, Direction.Axis axis) {
        return axis == Direction.Axis.X ? pos.south() : pos.east();
    }

    private static BlockPos getRight(BlockPos pos, Direction.Axis axis) {
        return axis == Direction.Axis.X ? pos.north() : pos.west();
    }

    private static int getTranslatedX(BlockPos pos, Direction.Axis axis) {
        return axis == Direction.Axis.X ? pos.getZ() : pos.getX();
    }

    private static int getTranslatedY(BlockPos pos, Direction.Axis axis) {
        return axis == Direction.Axis.Y ? pos.getZ() : pos.getY();
    }

    private static List<BlockPos> getAdjacentsOnPlane(BlockPos blockPos, Direction.Axis axis) {
        List<BlockPos> adjacents = new ArrayList<>();
        adjacents.add(getUp(blockPos, axis));
        adjacents.add(getLeft(blockPos, axis));
        adjacents.add(getRight(blockPos, axis));
        adjacents.add(getDown(blockPos, axis));
        return adjacents;
    }

    private static List<BlockPos> getAdjacents(BlockPos blockPos) {
        List<BlockPos> adjacents = new ArrayList<>();
        adjacents.add(blockPos.above());
        adjacents.add(blockPos.below());
        adjacents.add(blockPos.north());
        adjacents.add(blockPos.south());
        adjacents.add(blockPos.east());
        adjacents.add(blockPos.west());
        return adjacents;
    }

    private static SpawnPosData determineSpawnPos(List<BlockPos> portalBlocks, Block portalBlock, Direction.Axis axis, Level world) {
        int leftmost = getTranslatedX(portalBlocks.get(0), axis), rightmost = getTranslatedX(portalBlocks.get(0), axis);
        for (BlockPos blockPos : portalBlocks) {
            int x = getTranslatedX(blockPos, axis);
            if (x < leftmost) leftmost = x;
            if (x > rightmost) rightmost = x;
        }
        int xSpawn = (rightmost + leftmost) / 2;
        int length = rightmost - leftmost + 1;
        if (length % 2 == 0 && leftmost >= 0) xSpawn++;
        List<BlockPos> midPortalBlocks = new ArrayList<>();
        for (BlockPos blockPos : portalBlocks) {
            int x = getTranslatedX(blockPos, axis);
            if (x == xSpawn) midPortalBlocks.add(blockPos);
        }
        BlockPos bottom = midPortalBlocks.get(0);
        for (BlockPos blockPos : midPortalBlocks) {
            if (getTranslatedY(blockPos, axis) < getTranslatedY(bottom, axis)) bottom = blockPos;
        }
        int ySpawn = getTranslatedY(bottom, axis);
        int height = 1; // unused unless portal is oriented along Y axis
        if (axis == Direction.Axis.Y) {
            BlockPos pos = bottom;
            while (world.getBlockState(getDown(pos, axis)).is(portalBlock)) {
                pos = getDown(pos, axis);
                height++;
            }
            ySpawn += height / 2;
        }
        int trueXSpawn = axis == Direction.Axis.X ? bottom.getX() : xSpawn;
        int trueYSpawn = axis == Direction.Axis.Y ? bottom.getY() : ySpawn;
        int trueZSpawn = axis == Direction.Axis.Z ? bottom.getZ() : axis == Direction.Axis.X ? xSpawn : ySpawn;
        float offsetX = 0.0f;
        float offsetZ = 0.0f;
        switch (axis) {
            case X -> {
                offsetX = 0.5f;
                if (length % 2 != 0) offsetZ = 0.5f;
            }
            case Y -> {
                if (length % 2 != 0) offsetX = 0.5f;
                if (height % 2 != 0) offsetZ = 0.5f;
            }
            case Z -> {
                offsetZ = 0.5f;
                if (length % 2 != 0) offsetX = 0.5f;
            }
        }
        BlockPos spawnPos = new BlockPos(new Vec3i(trueXSpawn, trueYSpawn, trueZSpawn));
        return new SpawnPosData(spawnPos, offsetX, offsetZ);
    }

    public static boolean buildPortal(BlockPos startPos, Block portalBlock, UUID creatorId, Level world) {
        if (buildPortal(startPos, Direction.Axis.X, portalBlock, creatorId, world)) return true;
        if (buildPortal(startPos, Direction.Axis.Y, portalBlock, creatorId, world)) return true;
        return buildPortal(startPos, Direction.Axis.Z, portalBlock, creatorId, world);
    }

    public static boolean buildPortal(BlockPos blockPos, Direction.Axis axis, Block portalBlock, UUID creatorId, Level world) {

        if (!world.getBlockState(blockPos).isAir()) return false;

        // figure out which block will make up the frame of the portal
        List<BlockPos> adjacents = getAdjacentsOnPlane(blockPos, axis);
        Block frameMaterial = Blocks.AIR;
        for (BlockPos pos : adjacents) {
            BlockState blockState = world.getBlockState(pos);
            if (!blockState.isAir()) {
                if (frameMaterial != Blocks.AIR) {
                    if (!frameMaterial.equals(blockState.getBlock())) return false;
                }
                else if (blockState.isCollisionShapeFullBlock(world, pos)) frameMaterial = blockState.getBlock();
                else return false;
            }
        }

        BlockPos startPos = blockPos;

        // if we haven't found a frame block, go down on the portal until we find one
        if (frameMaterial == Blocks.AIR) {
            for (int i = 0; world.getBlockState(getDown(startPos, axis)).isAir(); i++) {
                if (i > 30) return false;
                startPos = getDown(startPos, axis);
            }
            if (!world.getBlockState(getDown(startPos, axis)).isCollisionShapeFullBlock(world, getDown(startPos, axis))) return false;
            frameMaterial = world.getBlockState(getDown(startPos, axis)).getBlock();
        }

        // verify that the frame block is a valid portal frame block
        String frameId = BuiltInRegistries.BLOCK.getKey(frameMaterial).toString();
        List<? extends String> blockIds = CPSettings.filteredBlocks();
        if (CPSettings.isWhitelist()) {
            if (!blockIds.contains(frameId)) return false;
        } else {
            if (blockIds.contains(frameId)) return false;
        }

        List<BlockPos> portalBlocks = new ArrayList<>();

        // DFS algorithm to find the block positions of all the portal blocks
        Stack<BlockPos> s = new Stack<>();
        Set<BlockPos> visited = new HashSet<>();
        Set<BlockPos> frames = new HashSet<>();
        s.push(startPos);
        while (!s.isEmpty()) {
            BlockPos curPos = s.pop();
            if (!visited.add(curPos)) continue;
            if (visited.size() > 900) return false;
            List<BlockPos> curAdjacents = getAdjacentsOnPlane(curPos, axis);
            for (BlockPos pos : curAdjacents) {
                BlockState blockState = world.getBlockState(pos);
                if (blockState.isAir()) {
                    if (!visited.contains(pos))
                        s.push(pos);
                }
                else {
                    if (!blockState.is(frameMaterial))
                        return false;
                    frames.add(pos);
                }
            }
            portalBlocks.add(curPos);
        }

        // rotate blockState to ensure portal blocks are facing the proper direction
        BlockState portalState = switch (axis) {
            case X -> portalBlock.defaultBlockState().rotate(Rotation.CLOCKWISE_90);
            case Y -> portalBlock.defaultBlockState().rotate(Rotation.COUNTERCLOCKWISE_90);
            case Z -> portalBlock.defaultBlockState();
        };

        for (BlockPos pos : portalBlocks) {
            world.setBlockAndUpdate(pos, portalState);
        }

        // the registry is server-authoritative; clients receive the new portal through the sync payload
        if (!world.isClientSide()) {
            SpawnPosData spawnPosData = determineSpawnPos(portalBlocks, portalBlock, axis, world);

            CustomPortal portal = new CustomPortal(frameId, world.dimension().identifier().toString(),
                    portalBlock.defaultMapColor(), spawnPosData.blockPos, portalBlocks, spawnPosData.offsetX,
                    spawnPosData.offsetZ, creatorId);
            PortalData.get(world).register(portal);

            // register any runes that were already on the portal frames; sync once after all of them are applied
            for (BlockPos framePos : frames) {
                List<BlockPos> frameAdjacents = getAdjacents(framePos);
                for (BlockPos adj : frameAdjacents) {
                    Block block = world.getBlockState(adj).getBlock();
                    if (block instanceof AbstractRuneBlock)
                        if (((AbstractRuneBlock)block).getBlockMountedPos(adj, world.getBlockState(adj)).equals(framePos))
                            ((AbstractRuneBlock)block).attachWithoutSync(portal, world);
                }
            }
            PortalData.markChanged(world);
        }
        return true;
    }

    public static Block getPortalBlockFromColorId(int colorId) {
        return switch (colorId) {
            case 29 -> CPBlocks.BLACK_PORTAL.get();
            case 25 -> CPBlocks.BLUE_PORTAL.get();
            case 26 -> CPBlocks.BROWN_PORTAL.get();
            case 23 -> CPBlocks.CYAN_PORTAL.get();
            case 21 -> CPBlocks.GRAY_PORTAL.get();
            case 27 -> CPBlocks.GREEN_PORTAL.get();
            case 17 -> CPBlocks.LIGHT_BLUE_PORTAL.get();
            case 22 -> CPBlocks.LIGHT_GRAY_PORTAL.get();
            case 19 -> CPBlocks.LIME_PORTAL.get();
            case 16 -> CPBlocks.MAGENTA_PORTAL.get();
            case 15 -> CPBlocks.ORANGE_PORTAL.get();
            case 20 -> CPBlocks.PINK_PORTAL.get();
            case 24 -> CPBlocks.PURPLE_PORTAL.get();
            case 28 -> CPBlocks.RED_PORTAL.get();
            case 8 -> CPBlocks.WHITE_PORTAL.get();
            case 18 -> CPBlocks.YELLOW_PORTAL.get();
            default -> Blocks.NETHER_PORTAL;
        };
    }

    static class SpawnPosData {
        protected BlockPos blockPos;
        protected float offsetX;
        protected float offsetZ;

        protected SpawnPosData(BlockPos blockPos, float offsetX, float offsetZ) {
            this.blockPos = blockPos;
            this.offsetX = offsetX;
            this.offsetZ = offsetZ;
        }
    }
}
