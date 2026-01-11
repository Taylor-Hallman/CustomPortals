package dev.custom.portals.mixin;

import java.util.Iterator;

import com.mojang.authlib.GameProfile;

import dev.custom.portals.config.CPSettings;
import net.minecraft.network.packet.s2c.play.*;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import dev.custom.portals.util.EntityMixinAccess;
import net.minecraft.entity.Entity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.PlayerManager;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.network.ServerPlayerInteractionManager;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.TeleportTarget;
import net.minecraft.world.World;
import net.minecraft.world.WorldProperties;
import net.minecraft.world.biome.source.BiomeAccess;

@Mixin(ServerPlayerEntity.class)
public abstract class ServerPlayerEntityMixin extends PlayerEntity {

    @Shadow
    private boolean inTeleportationState;
    @Shadow
    public ServerPlayNetworkHandler networkHandler;
    @Final
    @Shadow
    public ServerPlayerInteractionManager interactionManager;
    @Final
    @Shadow
    public MinecraftServer server;
    @Shadow
    private float syncedHealth;
    @Shadow
    private int syncedExperience;
    @Shadow
    private int syncedFoodLevel;

    @Shadow
    private Vec3d enteredNetherPos;

    @Shadow
    public abstract ServerWorld getServerWorld();
    @Shadow
    protected abstract void worldChanged(ServerWorld serverWorld);
    @Shadow
    public abstract void setServerWorld(ServerWorld world);

    public ServerPlayerEntityMixin(MinecraftServer minecraftServer, ServerWorld serverWorld, GameProfile gameProfile) {
        super(serverWorld, serverWorld.getSpawnPos(), serverWorld.getSpawnAngle(), gameProfile);
    }

    /* This is necessary to avoid unwanted side effects from using the normal moveToWorld(),
     * such as how moving from the end to the overworld causes the credits to play and
     * resets the player's position to the world spawn. Most of this is simply copied from
     * vanilla code.
     */
    @Inject(method = "moveToWorld", at = @At("HEAD"), cancellable = true)
    public void moveToWorld(ServerWorld serverWorld, CallbackInfoReturnable<Entity> ci) {
        this.inTeleportationState = true;
        ServerWorld serverWorld2 = this.getServerWorld();
        RegistryKey<World> registryKey = serverWorld2.getRegistryKey();
        if (((EntityMixinAccess)this).isInCustomPortal()) {
            WorldProperties worldProperties = serverWorld.getLevelProperties();
            this.networkHandler.sendPacket(new PlayerRespawnS2CPacket(serverWorld.getDimensionKey(), serverWorld.getRegistryKey(), BiomeAccess.hashSeed(serverWorld.getSeed()), this.interactionManager.getGameMode(), this.interactionManager.getPreviousGameMode(), serverWorld.isDebugWorld(), serverWorld.isFlat(), (byte)3, this.getLastDeathPos(), this.getPortalCooldown()));
            this.networkHandler.sendPacket(new DifficultyS2CPacket(worldProperties.getDifficulty(), worldProperties.isDifficultyLocked()));
            PlayerManager playerManager = this.server.getPlayerManager();
            playerManager.sendCommandTree((ServerPlayerEntity)(Object)this);
            serverWorld2.removePlayer((ServerPlayerEntity)(Object)this, RemovalReason.CHANGED_DIMENSION);
            this.unsetRemoved();
            TeleportTarget teleportTarget = this.getTeleportTarget(serverWorld);
            if (teleportTarget != null) {
                serverWorld2.getProfiler().push("moving");
                serverWorld2.getProfiler().pop();
                serverWorld2.getProfiler().push("placing");
                this.setServerWorld(serverWorld);
                this.networkHandler.requestTeleport(teleportTarget.position.x, teleportTarget.position.y, teleportTarget.position.z, teleportTarget.yaw, teleportTarget.pitch);
                this.networkHandler.syncWithPlayerPosition();
                serverWorld.onPlayerChangeDimension((ServerPlayerEntity)(Object)this);
                serverWorld2.getProfiler().pop();
                this.worldChanged(serverWorld2);
                this.networkHandler.sendPacket(new PlayerAbilitiesS2CPacket(this.getAbilities()));
                playerManager.sendWorldInfo((ServerPlayerEntity)(Object)this, serverWorld);
                playerManager.sendPlayerStatus((ServerPlayerEntity)(Object)this);
                Iterator var7 = this.getStatusEffects().iterator();

                while(var7.hasNext()) {
                    StatusEffectInstance statusEffectInstance = (StatusEffectInstance)var7.next();
                    this.networkHandler.sendPacket(new EntityStatusEffectS2CPacket(this.getId(), statusEffectInstance));
                }
                if (!CPSettings.instance().muteTeleportSounds)
                    this.networkHandler.sendPacket(new WorldEventS2CPacket(1032, BlockPos.ORIGIN, 0, false));
                this.syncedExperience = -1;
                this.syncedHealth = -1.0F;
                this.syncedFoodLevel = -1;
            }
            ci.setReturnValue((ServerPlayerEntity)(Object)this);
        }
    }
}
