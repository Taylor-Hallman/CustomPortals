package dev.custom.portals.blocks;

import java.util.ArrayList;
import java.util.List;

import com.mojang.serialization.MapCodec;
import dev.custom.portals.data.PortalData;
import dev.custom.portals.data.CustomPortal;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.FaceAttachedHorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.AttachFace;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;

public class AbstractRuneBlock extends FaceAttachedHorizontalDirectionalBlock {
    public static final MapCodec<AbstractRuneBlock> CODEC = simpleCodec(AbstractRuneBlock::new);
    protected static final VoxelShape CEILING_SHAPE;
    protected static final VoxelShape FLOOR_SHAPE;
    protected static final VoxelShape NORTH_SHAPE;
    protected static final VoxelShape SOUTH_SHAPE;
    protected static final VoxelShape WEST_SHAPE;
    protected static final VoxelShape EAST_SHAPE;

    @Override
    public MapCodec<? extends AbstractRuneBlock> codec() {
        return CODEC;
    }

    protected AbstractRuneBlock(BlockBehaviour.Properties settings) {
        super(settings);
        this.registerDefaultState((BlockState)((BlockState)((BlockState)this.stateDefinition.any()).setValue(FACING, Direction.NORTH)).setValue(FACE, AttachFace.WALL));
    }

    /** Applies this rune's effect to the portal (counts, relinking). Does not sync; see {@link #registerOnPortal}. */
    protected void applyTo(CustomPortal portal, Level world) {}

    /** Reverses {@link #applyTo}. Does not sync; see {@link #unregisterOnPortal}. */
    protected void removeFrom(CustomPortal portal, Level world) {}

    /** Adds this rune to the portal and syncs the registry. */
    public final void registerOnPortal(CustomPortal portal, Level world) {
        if (world.isClientSide()) return;
        applyTo(portal, world);
        PortalData.markChanged(world);
    }

    /** Removes this rune from the portal and syncs the registry. */
    public final void unregisterOnPortal(CustomPortal portal, Level world) {
        if (world.isClientSide()) return;
        removeFrom(portal, world);
        PortalData.markChanged(world);
    }

    /** Adds this rune to the portal without syncing; the caller must call {@link PortalData#markChanged} afterwards. */
    public final void attachWithoutSync(CustomPortal portal, Level world) {
        if (world.isClientSide()) return;
        applyTo(portal, world);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, FACE);
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader world, BlockPos pos) {
        BlockPos blockPos = pos.relative(getConnectedDirection(state).getOpposite());
        return world.getBlockState(blockPos).isCollisionShapeFullBlock(world, blockPos);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        Direction direction = (Direction)state.getValue(FACING);
        switch((AttachFace)state.getValue(FACE)) {
        case FLOOR:
            return FLOOR_SHAPE;
        case WALL:
            switch(direction) {
            case EAST:
               return EAST_SHAPE;
            case WEST:
               return WEST_SHAPE;
            case SOUTH:
               return SOUTH_SHAPE;
            case NORTH:
            default:
               return NORTH_SHAPE;
            }
        case CEILING:
        default:
          return CEILING_SHAPE;
        }
    }

    @Override
    public void setPlacedBy(Level world, BlockPos pos, BlockState state, LivingEntity placer, ItemStack itemStack) {
        super.setPlacedBy(world, pos, state, placer, itemStack);
        BlockPos blockPos = getBlockMountedPos(pos, state);
        CustomPortal portal;
        List<BlockPos> adjacents = new ArrayList<BlockPos>();
        adjacents.add(blockPos.below());
        adjacents.add(blockPos.east());
        adjacents.add(blockPos.west());
        adjacents.add(blockPos.south());
        adjacents.add(blockPos.north());
        adjacents.add(blockPos.above());
        for (BlockPos adjacent : adjacents) {
            portal = PortalData.getPortalFromPos(world, adjacent);
            if (portal != null) {
                BlockState adjacentState = world.getBlockState(adjacent);
                if ((adjacent.equals(blockPos.above()) || adjacent.equals(blockPos.below())) && adjacentState.hasProperty(BlockStateProperties.AXIS) && (Direction.Axis)adjacentState.getValue(BlockStateProperties.AXIS) != Direction.Axis.Y) {
                    registerOnPortal(portal, world);
                }
                if ((adjacent.equals(blockPos.east()) || adjacent.equals(blockPos.west())) && adjacentState.hasProperty(BlockStateProperties.AXIS) && (Direction.Axis)adjacentState.getValue(BlockStateProperties.AXIS) != Direction.Axis.Z) {
                    registerOnPortal(portal, world);
                }
                if ((adjacent.equals(blockPos.north()) || adjacent.equals(blockPos.south())) && adjacentState.hasProperty(BlockStateProperties.AXIS) && (Direction.Axis)adjacentState.getValue(BlockStateProperties.AXIS) != Direction.Axis.X) {
                    registerOnPortal(portal, world);
                }
            }
        }
    }

    @Override
    public BlockState playerWillDestroy(Level world, BlockPos pos, BlockState state, Player player) {
        super.playerWillDestroy(world, pos, state, player);
        BlockPos blockPos = getBlockMountedPos(pos, state);
        CustomPortal portal;
        List<BlockPos> adjacents = new ArrayList<BlockPos>();
        adjacents.add(blockPos.below());
        adjacents.add(blockPos.east());
        adjacents.add(blockPos.west());
        adjacents.add(blockPos.south());
        adjacents.add(blockPos.north());
        adjacents.add(blockPos.above());
        for (BlockPos adjacent : adjacents) {
            portal = PortalData.getPortalFromPos(world, adjacent);
            if (portal != null) {
                BlockState adjacentState = world.getBlockState(adjacent);
                if ((adjacent.equals(blockPos.above()) || adjacent.equals(blockPos.below())) && adjacentState.hasProperty(BlockStateProperties.AXIS) && (Direction.Axis)adjacentState.getValue(BlockStateProperties.AXIS) != Direction.Axis.Y) {
                    unregisterOnPortal(portal, world);
                }
                if ((adjacent.equals(blockPos.east()) || adjacent.equals(blockPos.west())) && adjacentState.hasProperty(BlockStateProperties.AXIS) && (Direction.Axis)adjacentState.getValue(BlockStateProperties.AXIS) != Direction.Axis.Z) {
                    unregisterOnPortal(portal, world);
                }
                if ((adjacent.equals(blockPos.north()) || adjacent.equals(blockPos.south())) && adjacentState.hasProperty(BlockStateProperties.AXIS) && (Direction.Axis)adjacentState.getValue(BlockStateProperties.AXIS) != Direction.Axis.X) {
                    unregisterOnPortal(portal, world);
                }
            }
        }
        return state;
    }

    public BlockPos getBlockMountedPos(BlockPos pos, BlockState state) {
        Direction direction = (Direction)state.getValue(FACING);
        AttachFace orientation = (AttachFace)state.getValue(FACE);
        switch(orientation) {
            case FLOOR: 
                return pos.below();
            case WALL:
                switch(direction) {
                    case EAST: return pos.west();
                    case WEST: return pos.east();
                    case SOUTH: return pos.north();
                    default:
                    case NORTH: return pos.south();
                }
            default:
            case CEILING: return pos.above();
        }
    }

    static {
        CEILING_SHAPE = Block.box(0.0D, 15.0D, 0.0D, 16.0D, 16.0D, 16.0D);
        FLOOR_SHAPE = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 1.0D, 16.0D);
        NORTH_SHAPE = Block.box(0.0D, 0.0D, 15.0D, 16.0D, 16.0D, 16.0D);
        SOUTH_SHAPE = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 1.0D);
        WEST_SHAPE = Block.box(15.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D);
        EAST_SHAPE = Block.box(0.0D, 0.0D, 0.0D, 1.0D, 16.0D, 16.0D);
    }
    
}
