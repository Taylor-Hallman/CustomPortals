package dev.custom.portals.mixin;

import dev.custom.portals.data.PortalData;
import dev.custom.portals.data.CustomPortal;
import dev.custom.portals.network.DrawSpritePayload;
import net.neoforged.neoforge.network.PacketDistributor;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.PortalProcessor;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import dev.custom.portals.util.EntityMixinAccess;
import net.minecraft.world.entity.Entity;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;

@Mixin(Entity.class)
public abstract class EntityMixin implements EntityMixinAccess {

    @Unique
    private int portalColor;
    @Unique
    private boolean inCustomPortal;
    @Unique
    private CustomPortal destPortal;
    @Unique
    private int customPortalTime;
    @Unique
    private boolean packetSent = false;
    @Unique
    boolean inTransition;

    @Shadow
    @Nullable
    public PortalProcessor portalProcess;
    @Shadow
    private Level level;
    @Shadow
    private BlockPos blockPosition;

    @Inject(method = "baseTick", at = @At("TAIL"))
    public void baseTick(CallbackInfo ci) {
        if (!inCustomPortal && packetSent && ((Entity)(Object)this) instanceof ServerPlayer && !inTransition) {
            PacketDistributor.sendToPlayer(((ServerPlayer)(Object)this), new DrawSpritePayload(0));
            packetSent = false;
        }
    }

    @Inject(method = "handlePortal", at = @At("TAIL"))
    protected void tickPortalTeleportation(CallbackInfo ci) {
        if (level instanceof ServerLevel) {
            if (this.inCustomPortal) {
                if (((Entity) (Object) this) instanceof ServerPlayer && !packetSent) {
                    PacketDistributor.sendToPlayer(((ServerPlayer) (Object) this), new DrawSpritePayload(this.portalColor));
                    packetSent = true;
                }
                if (this.portalProcess == null) {
                    this.destPortal = null;
                    this.inCustomPortal = false;
                    this.portalColor = 0;
                }
            }
        }
    }

    @Unique
    public void setInCustomPortal(CustomPortal customPortal) {
        if (this.portalProcess != null) {
            this.destPortal = customPortal.getLinked();
            this.inCustomPortal = true;
            this.portalColor = customPortal.getColor().id;
        }
    }

    @Unique
    public CustomPortal getDestPortal() { return destPortal; }

    @Unique
    public boolean isInCustomPortal() { return inCustomPortal; }

    @Unique
    public boolean isInNetherPortal() { return false; }
    
    @Unique
    public void notInCustomPortal() { inCustomPortal = false; }

    @Unique
    public int getPortalColor() {
        if (portalColor == 0) {
            CustomPortal portal = PortalData.getPortalFromPos(level, blockPosition);
            portalColor = portal == null ? 0 : portal.getColor().id;
        }
        return portalColor;
    }

    @Unique
    public void setPortalColor(int color) { this.portalColor = color; }

    @Unique
    public void setInTransition(boolean bl) { this.inTransition = bl; }

}
