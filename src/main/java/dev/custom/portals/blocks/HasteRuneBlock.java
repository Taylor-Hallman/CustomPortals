package dev.custom.portals.blocks;

import com.mojang.serialization.MapCodec;
import dev.custom.portals.data.CustomPortal;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockBehaviour;

public class HasteRuneBlock extends AbstractRuneBlock {

    public static final MapCodec<HasteRuneBlock> CODEC = simpleCodec(HasteRuneBlock::new);

    @Override
    public MapCodec<HasteRuneBlock> codec() {
        return CODEC;
    }

    public HasteRuneBlock(BlockBehaviour.Properties settings) {
        super(settings);
    }

    @Override
    protected void applyTo(CustomPortal portal, Level world) {
        portal.addHaste();
    }

    @Override
    protected void removeFrom(CustomPortal portal, Level world) {
        portal.removeHaste();
    }
}
