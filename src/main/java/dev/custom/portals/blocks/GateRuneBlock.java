package dev.custom.portals.blocks;

import com.mojang.serialization.MapCodec;
import dev.custom.portals.data.CustomPortal;
import dev.custom.portals.data.PortalData;
import dev.custom.portals.data.PortalRegistry;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockBehaviour;

public class GateRuneBlock extends AbstractRuneBlock {

    public static final MapCodec<GateRuneBlock> CODEC = simpleCodec(GateRuneBlock::new);

    @Override
    public MapCodec<GateRuneBlock> codec() {
        return CODEC;
    }

    public GateRuneBlock(BlockBehaviour.Properties settings) {
        super(settings);
    }

    @Override
    protected void applyTo(CustomPortal portal, Level world) {
        portal.addGate();
        PortalData.get(world).tryWithAll(portal);
    }

    @Override
    protected void removeFrom(CustomPortal portal, Level world) {
        portal.removeGate();
        PortalRegistry registry = PortalData.get(world);
        if (portal.hasLinked())
            registry.tryWithAll(portal.getLinked());
        registry.tryWithAll(portal);
    }
}
