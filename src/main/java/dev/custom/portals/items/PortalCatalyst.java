package dev.custom.portals.items;
import dev.custom.portals.util.PortalHelper;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionResult;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;

import java.util.UUID;

public class PortalCatalyst extends Item {

    private Block portalBlock;

    public PortalCatalyst(net.minecraft.world.item.Item.Properties settings, Block portal) {
        super(settings);
        this.portalBlock = portal;
    }
    
    @Override
    public InteractionResult useOn(UseOnContext ctx) {
        Player playerEntity = ctx.getPlayer();
        Level world = ctx.getLevel();
        BlockPos pos = new BlockPos(ctx.getClickedPos());
        Direction dir = ctx.getClickedFace();
        pos = switch (dir) {
            case NORTH -> pos.north();
            case SOUTH -> pos.south();
            case EAST -> pos.east();
            case WEST -> pos.west();
            case UP -> pos.above();
            case DOWN -> pos.below();
        };
        if (PortalHelper.buildPortal(pos, portalBlock, playerEntity.getUUID(), world)) {
            if (playerEntity != null) {
                playerEntity.playSound(SoundEvents.FLINTANDSTEEL_USE, 1.0F, 1.0F);
            }
            ctx.getItemInHand().shrink(1);
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.PASS;
    }
}