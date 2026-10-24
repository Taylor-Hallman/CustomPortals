package dev.custom.portals.blocks;

import dev.custom.portals.config.CPSettings;
import dev.custom.portals.data.CustomPortal;
import dev.custom.portals.data.PortalData;
import dev.custom.portals.registry.CPBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class PortalBlockEntity extends BlockEntity {

    public PortalBlockEntity(BlockPos pos, BlockState state) {
        super(CPBlocks.PORTAL_BLOCK_ENTITY.get(), pos, state);
    }

    public static <T extends BlockEntity> void tick(Level world, BlockPos pos, BlockState state, BlockEntity be) {
        if (world.isClientSide())
            return;
        CustomPortal portal = PortalData.getPortalFromPos(world, pos);
        if (portal != null) {
            if (CPSettings.redstone() == CPSettings.RedstoneEnum.OFF) {
                if (portal.hasLinked() && !state.getValue(PortalBlock.LIT) && !portal.hasRedstoneSignal()) {
                    world.setBlock(pos, state.setValue(PortalBlock.LIT, true), Block.UPDATE_ALL);
                } else if ((!portal.hasLinked() || portal.hasRedstoneSignal()) && state.getValue(PortalBlock.LIT)) {
                    world.setBlock(pos, state.setValue(PortalBlock.LIT, false), Block.UPDATE_ALL);
                }
            } else if (CPSettings.redstone() == CPSettings.RedstoneEnum.ON) {
                if (portal.hasLinked() && !state.getValue(PortalBlock.LIT) && portal.hasRedstoneSignal()) {
                    world.setBlock(pos, state.setValue(PortalBlock.LIT, true), Block.UPDATE_ALL);
                } else if ((!portal.hasLinked() || !portal.hasRedstoneSignal()) && state.getValue(PortalBlock.LIT)) {
                    world.setBlock(pos, state.setValue(PortalBlock.LIT, false), Block.UPDATE_ALL);
                }
            } else {
                if (portal.hasLinked() && !state.getValue(PortalBlock.LIT))
                    world.setBlock(pos, state.setValue(PortalBlock.LIT, true), Block.UPDATE_ALL);
                if (!portal.hasLinked() && state.getValue(PortalBlock.LIT))
                    world.setBlock(pos, state.setValue(PortalBlock.LIT, false), Block.UPDATE_ALL);
            }
        }
    }
}
