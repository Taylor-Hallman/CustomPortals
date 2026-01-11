package dev.custom.portals.mixin;

import dev.custom.portals.CustomPortals;
import dev.custom.portals.config.CPSettings;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.network.packet.s2c.play.*;
import net.minecraft.server.PlayerManager;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.world.WorldProperties;
import net.minecraft.world.biome.source.BiomeAccess;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import dev.custom.portals.util.EntityMixinAccess;
import dev.custom.portals.data.Portal;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.registry.RegistryKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.TeleportTarget;
import net.minecraft.world.World;

import java.util.Collections;
import java.util.Set;

@Mixin(Entity.class)
public abstract class EntityMixin implements EntityMixinAccess {

    @Unique
    private int portalColor;
    @Unique
    private boolean inCustomPortal;
    @Unique
    private Portal destPortal;
    @Unique
    private int customPortalTime;

    @Shadow
    protected boolean inNetherPortal;
    @Shadow
    private World world;
    @Shadow
    private float yaw;
    @Shadow
    private float pitch;
    @Shadow
    private BlockPos blockPos;

    @Shadow
    public abstract boolean hasPortalCooldown();
    @Shadow
    public abstract void resetPortalCooldown();
    @Shadow
    public abstract int getMaxNetherPortalTime();
    @Shadow
    protected abstract void tickPortalCooldown();
    @Shadow
    public abstract boolean teleport(ServerWorld serverWorld, double d, double e, double f, Set<PositionFlag> set, float g, float h);
    @Shadow
    public abstract boolean hasVehicle();
    @Shadow
    public abstract Entity moveToWorld(ServerWorld destination);
    @Shadow 
    public abstract Vec3d getVelocity();

    @Inject(method = "baseTick", at = @At("TAIL"))
    public void baseTick(CallbackInfo ci) {
        this.tickCustomPortal();
    }

    @Inject(method = "getTeleportTarget", at = @At("HEAD"), cancellable = true)
    protected void getTeleportTarget(ServerWorld destination, CallbackInfoReturnable<TeleportTarget> ci) {
        if(this.inCustomPortal) {
            BlockPos dest = destPortal.getSpawnPos();
            double destX = dest.getX();
            double destY = dest.getY();
            double destZ = dest.getZ();
            destX += destPortal.offsetX;
            destZ += destPortal.offsetZ;
            /* For some reason, when the player is going from the Overworld to the End, the Y coordinate somehow gets
             * decreased by 1. I have no idea why this happens or how to fix it directly, so this is here to correct it.
             */
            if(destPortal.getDimensionId().equals("minecraft:the_end") && this.world.getRegistryKey() == World.OVERWORLD)
                destY += 1.0;
            ci.setReturnValue(new TeleportTarget(new Vec3d(destX, destY, destZ), this.getVelocity(), this.yaw, this.pitch));
        }
    }

    @Unique
    public void setInCustomPortal(Portal portal) {
        if (this.hasPortalCooldown()) {
            this.resetPortalCooldown();
        } else {
            this.destPortal = portal.getLinked();
            this.inCustomPortal = true;
            this.portalColor = portal.getColor().id;
        }
    }

    @Unique
    public void tickCustomPortal() {
        if (this.world instanceof ServerWorld) {
            int i = this.getMaxCustomPortalTime();
            ServerWorld serverWorld = (ServerWorld)this.world;
            if (this.inCustomPortal) {
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
                            thisPlayer.networkHandler.sendPacket(new PlayerRespawnS2CPacket(serverWorld.getDimensionKey(),
                                    serverWorld.getRegistryKey(), BiomeAccess.hashSeed(serverWorld.getSeed()),
                                    thisPlayer.interactionManager.getGameMode(), thisPlayer.interactionManager.getPreviousGameMode(),
                                    serverWorld.isDebugWorld(), serverWorld.isFlat(), (byte)3, thisPlayer.getLastDeathPos(),
                                    thisPlayer.getPortalCooldown()));
                            thisPlayer.networkHandler.sendPacket(new DifficultyS2CPacket(worldProperties.getDifficulty(), worldProperties.isDifficultyLocked()));
                            thisPlayer.networkHandler.requestTeleport(destX, destY, destZ, this.yaw, this.pitch);
                            thisPlayer.networkHandler.syncWithPlayerPosition();
                            thisPlayer.networkHandler.sendPacket(new PlayerAbilitiesS2CPacket(thisPlayer.getAbilities()));
                            playerManager.sendWorldInfo(thisPlayer, serverWorld);
                            playerManager.sendPlayerStatus(thisPlayer);

                            for (StatusEffectInstance statusEffectInstance : thisPlayer.getStatusEffects()) {
                                thisPlayer.networkHandler.sendPacket(new EntityStatusEffectS2CPacket(((ServerPlayerEntity) (Object) this).getId(), statusEffectInstance));
                            }
                            if (!CPSettings.instance().muteTeleportSounds)
                                thisPlayer.networkHandler.sendPacket(new WorldEventS2CPacket(1032, BlockPos.ORIGIN, 0, false));
                            playerManager.sendCommandTree(thisPlayer);
                        }
                        else this.teleport(serverWorld, destX, destY, destZ, Collections.emptySet(), this.yaw, this.pitch);
                    }
                    this.world.getProfiler().pop();
                }
                this.inCustomPortal = false;
                this.portalColor = 0;
                this.destPortal = null;
            } else {
                if (this.customPortalTime > 0) {
                    this.customPortalTime -= 4;
                }
  
                if (this.customPortalTime < 0) {
                    this.customPortalTime = 0;
                }
            }
  
           this.tickPortalCooldown();
        }
    }

    @Unique
    public int getMaxCustomPortalTime() {
        if (((Entity)(Object)this) instanceof PlayerEntity && destPortal != null) {
            if (CPSettings.instance().alwaysHaste == CPSettings.HasteEnum.CREATIVE)
                return destPortal.hasHaste() ? 1 : this.getMaxNetherPortalTime();
            else if (CPSettings.instance().alwaysHaste == CPSettings.HasteEnum.NO)
                return destPortal.hasHaste() ? 1 : 80;
            else return 1;
        }
        return this.getMaxNetherPortalTime();
    }

    @Unique
    public Portal getDestPortal() { return destPortal; }

    @Unique
    public boolean isInCustomPortal() { return inCustomPortal; }

    @Unique
    public boolean isInNetherPortal() { return inNetherPortal; }
    
    @Unique
    public void notInCustomPortal() { inCustomPortal = false; }

    @Unique
    public int getPortalColor() {
        if (portalColor == 0) {
            Portal portal = CustomPortals.PORTALS.get(world).getPortalFromPos(blockPos);
            portalColor = portal == null ? 0 : portal.getColor().id;
        }
        return portalColor;
    }

    @Unique
    public void setPortalColor(int color) { this.portalColor = color; }

}
