package dev.custom.portals.mixin;

import com.mojang.authlib.GameProfile;
import net.minecraft.network.protocol.game.ClientboundChangeDifficultyPacket;
import net.minecraft.network.protocol.game.ClientboundLevelEventPacket;
import net.minecraft.network.protocol.game.ClientboundPlayerAbilitiesPacket;
import net.minecraft.network.protocol.game.ClientboundRespawnPacket;
import net.minecraft.network.protocol.game.CommonPlayerSpawnInfo;
import net.minecraft.resources.ResourceKey;
import net.minecraft.core.BlockPos;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.util.profiling.Profiler;
import net.minecraft.world.entity.Entity.RemovalReason;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import dev.custom.portals.util.EntityMixinAccess;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.PositionMoveRotation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.players.PlayerList;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.portal.TeleportTransition;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.LevelData;

@Mixin(ServerPlayer.class)
public abstract class ServerPlayerMixin extends Player {

    @Shadow
    private boolean isChangingDimension;
    @Shadow
    public ServerGamePacketListenerImpl connection;
    @Final
    @Shadow
    public MinecraftServer server;
    @Shadow
    private float lastSentHealth;
    @Shadow
    private int lastSentExp;
    @Shadow
    private int lastSentFood;

    @Shadow
    public abstract ServerLevel level();
    @Shadow
    protected abstract void triggerDimensionChangeTriggers(ServerLevel serverWorld);
    @Shadow
    public abstract void setServerLevel(ServerLevel world);
    @Shadow
    public abstract CommonPlayerSpawnInfo createCommonSpawnInfo(ServerLevel serverWorld);

    public ServerPlayerMixin(MinecraftServer minecraftServer, ServerLevel serverWorld, GameProfile gameProfile) {
        super(serverWorld, gameProfile);
    }

    /* This is necessary to avoid unwanted side effects from using the normal teleportTo(),
     * such as how moving from the end to the overworld causes the credits to play and
     * resets the player's position to the world spawn. Most of this is simply copied from
     * vanilla code.
     */
    @Inject(method = "teleport", at = @At("HEAD"), cancellable = true)
    public void telportTo(TeleportTransition teleportTransition, CallbackInfoReturnable<Entity> cir) {
        if (this.isRemoved())
            cir.setReturnValue(null);
        ServerLevel serverWorld = teleportTransition.newLevel();
        ServerLevel serverWorld2 = this.level();
        ResourceKey<Level> registryKey = serverWorld2.dimension();
        if (((EntityMixinAccess)this).isInCustomPortal()) {
            ServerPlayer thisPlayer = (ServerPlayer)(Object)this;
            this.isChangingDimension = true;
            LevelData worldProperties = serverWorld.getLevelData();
            this.connection.send(new ClientboundRespawnPacket(this.createCommonSpawnInfo(serverWorld), (byte)3));
            this.connection.send(new ClientboundChangeDifficultyPacket(worldProperties.getDifficulty(), worldProperties.isDifficultyLocked()));
            PlayerList playerManager = this.server.getPlayerList();
            playerManager.sendPlayerPermissionLevel(thisPlayer);
            serverWorld2.removePlayerImmediately(thisPlayer, RemovalReason.CHANGED_DIMENSION);
            this.unsetRemoved();
            ProfilerFiller profiler = Profiler.get();
            profiler.push("moving");
            profiler.pop();
            profiler.push("placing");
            this.setServerLevel(serverWorld);
            this.connection.teleport(PositionMoveRotation.of(teleportTransition), teleportTransition.relatives());
            this.connection.resetPosition();
            serverWorld.addDuringTeleport(thisPlayer);
            profiler.pop();
            this.triggerDimensionChangeTriggers(serverWorld2);
            this.connection.send(new ClientboundPlayerAbilitiesPacket(this.getAbilities()));
            playerManager.sendLevelInfo(thisPlayer, serverWorld);
            playerManager.sendAllPlayerInfo(thisPlayer);
            playerManager.sendActivePlayerEffects(thisPlayer);
            // Apparently this line means "play the teleport sound effect." Minecraft try to have readable code challenge (impossible)
            this.connection.send(new ClientboundLevelEventPacket(1032, BlockPos.ZERO, 0, false));
            this.lastSentExp = -1;
            this.lastSentHealth = -1.0F;
            this.lastSentFood = -1;
            cir.setReturnValue(thisPlayer);
        }
    }
}
