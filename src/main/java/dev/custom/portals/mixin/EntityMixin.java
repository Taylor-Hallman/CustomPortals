package dev.custom.portals.mixin;

import dev.custom.portals.CustomPortals;
import dev.custom.portals.data.CustomPortal;
import dev.custom.portals.util.DrawSpritePayload;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
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
            ServerPlayNetworking.send(((ServerPlayer)(Object)this), new DrawSpritePayload(0));
            packetSent = false;
        }
    }

    @Inject(method = "handlePortal", at = @At("TAIL"))
    protected void tickPortalTeleportation(CallbackInfo ci) {
        if (level instanceof ServerLevel) {
            if (this.inCustomPortal) {
                if (((Entity) (Object) this) instanceof ServerPlayer && !packetSent) {
                    ServerPlayNetworking.send(((ServerPlayer) (Object) this), new DrawSpritePayload(this.portalColor));
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

    /*@Unique
    public void tickCustomPortal() {
        if (this.world instanceof ServerWorld serverWorld) {
            int i = this.getMaxCustomPortalTime();
            if (this.inCustomPortal) {
                if (((Entity)(Object)this) instanceof ServerPlayerEntity && !packetSent) {
                    ServerPlayNetworking.send(((ServerPlayerEntity) (Object) this), new DrawSpritePayload(this.portalColor));
                    packetSent = true;
                }
                MinecraftServer minecraftServer = serverWorld.getServer();
                if (!this.hasVehicle() && this.customPortalTime++ >= i && this.destPortal != null) {
                    this.world.getProfiler().push("portal");
                    this.customPortalTime = i;
                    this.resetPortalCooldown();
                    String destDimensionId = destPortal.getDimensionId();
                    if(!destDimensionId.equals(this.world.getRegistryKey().getValue().toString())) {
                        for(RegistryKey<World> registryKey : minecraftServer.getWorldRegistryKeys()) {
                            if(registryKey.getValue().toString().equals(destDimensionId)) {
                                ServerWorld serverWorld2 = minecraftServer.getWorld(registryKey);
                                if(serverWorld2 != null) {
                                    this.moveToWorld(serverWorld2);
                                }
                            }
                        }
                    }
                    else {
                        BlockPos dest = destPortal.getSpawnPos();
                        double destX = dest.getX();
                        double destY = dest.getY();
                        double destZ = dest.getZ();
                        destX += destPortal.offsetX;
                        destZ += destPortal.offsetZ;

                        // Since this is a same-dimension teleport and we're not using moveToWorld(), need to do some
                        // stuff that's normally handled there
                        if (((Entity)(Object)this) instanceof ServerPlayerEntity) {
                            ServerPlayerEntity thisPlayer = ((ServerPlayerEntity)(Object)this);
                            WorldProperties worldProperties = serverWorld.getLevelProperties();
                            PlayerManager playerManager = thisPlayer.server.getPlayerManager();
                            thisPlayer.networkHandler.sendPacket(new PlayerRespawnS2CPacket(thisPlayer.createCommonPlayerSpawnInfo(serverWorld), (byte)3));
                            thisPlayer.networkHandler.sendPacket(new DifficultyS2CPacket(worldProperties.getDifficulty(), worldProperties.isDifficultyLocked()));
                            thisPlayer.networkHandler.requestTeleport(destX, destY, destZ, this.yaw, this.pitch);
                            thisPlayer.networkHandler.syncWithPlayerPosition();
                            thisPlayer.networkHandler.sendPacket(new PlayerAbilitiesS2CPacket(thisPlayer.getAbilities()));
                            playerManager.sendWorldInfo(thisPlayer, serverWorld);
                            playerManager.sendPlayerStatus(thisPlayer);

                            for (StatusEffectInstance statusEffectInstance : thisPlayer.getStatusEffects()) {
                                thisPlayer.networkHandler.sendPacket(new EntityStatusEffectS2CPacket(((ServerPlayerEntity) (Object) this).getId(), statusEffectInstance, false));
                            }
                            thisPlayer.networkHandler.sendPacket(new WorldEventS2CPacket(1032, BlockPos.ORIGIN, 0, false));
                        }
                        else this.teleport(serverWorld, destX, destY, destZ, Collections.emptySet(), this.yaw, this.pitch);
                    }
                    this.world.getProfiler().pop();
                }
                this.inCustomPortal = false;
                this.portalColor = 0;
                this.destPortal = null;
            } else {
                if (packetSent && ((Entity)(Object)this) instanceof ServerPlayerEntity && !inTransition) {
                    ServerPlayNetworking.send(((ServerPlayerEntity)(Object)this), new DrawSpritePayload(0));
                    packetSent = false;
                }
                if (this.customPortalTime > 0) {
                    this.customPortalTime -= 4;
                }

                if (this.customPortalTime < 0) {
                    this.customPortalTime = 0;
                }
            }

           this.tickPortalCooldown();
        }
    }*/

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
            CustomPortal portal = CustomPortals.PORTALS.get(level).getPortalFromPos(blockPosition);
            portalColor = portal == null ? 0 : portal.getColor().id;
        }
        return portalColor;
    }

    @Unique
    public void setPortalColor(int color) { this.portalColor = color; }

    @Unique
    public void setInTransition(boolean bl) { this.inTransition = bl; }

}
