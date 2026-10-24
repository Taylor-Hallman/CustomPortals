package dev.custom.portals.blocks;

import com.mojang.serialization.MapCodec;
import dev.custom.portals.data.CustomPortal;
import dev.custom.portals.data.PortalData;
import dev.custom.portals.data.PortalRegistry;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockBehaviour;

public class StrongEnhancerRuneBlock extends AbstractRuneBlock {

    public static final MapCodec<StrongEnhancerRuneBlock> CODEC = simpleCodec(StrongEnhancerRuneBlock::new);

    @Override
    public MapCodec<StrongEnhancerRuneBlock> codec() {
        return CODEC;
    }

    public StrongEnhancerRuneBlock(BlockBehaviour.Properties settings) {
        super(settings);
    }

    @Override
    protected void applyTo(CustomPortal portal, Level world) {
        portal.addStrongEnhancer();
        PortalData.get(world).tryWithAll(portal);
    }

    @Override
    protected void removeFrom(CustomPortal portal, Level world) {
        portal.removeStrongEnhancer();
        PortalRegistry registry = PortalData.get(world);
        if (portal.hasLinked())
            registry.tryWithAll(portal.getLinked());
        registry.tryWithAll(portal);
    }
}
