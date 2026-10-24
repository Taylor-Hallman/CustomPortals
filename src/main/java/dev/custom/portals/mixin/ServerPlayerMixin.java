package dev.custom.portals.mixin;

import com.mojang.authlib.GameProfile;
import dev.custom.portals.config.CPSettings;
import dev.custom.portals.util.EntityMixinAccess;
import net.minecraft.core.BlockPos;
import net.minecraft.network.protocol.game.ClientboundChangeDifficultyPacket;
import net.minecraft.network.protocol.game.ClientboundGameEventPacket;
import net.minecraft.network.protocol.game.ClientboundLevelEventPacket;
import net.minecraft.network.protocol.game.ClientboundPlayerAbilitiesPacket;
import net.minecraft.network.protocol.game.ClientboundRespawnPacket;
import net.minecraft.network.protocol.game.CommonPlayerSpawnInfo;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import net.minecraft.server.players.PlayerList;
import net.minecraft.util.profiling.Profiler;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.PositionMoveRotation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.portal.TeleportTransition;
import net.minecraft.world.level.storage.LevelData;
import net.neoforged.neoforge.attachment.AttachmentSync;
import net.neoforged.neoforge.common.CommonHooks;
import net.neoforged.neoforge.event.EventHooks;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ServerPlayer.class)
public abstract class ServerPlayerMixin extends Player {

    @Shadow
    private boolean isChangingDimension;
    @Shadow
    public ServerGamePacketListenerImpl connection;
    @Final
    @Shadow
    private MinecraftServer server;
    @Shadow
    private float lastSentHealth;
    @Shadow
    private int lastSentExp;
    @Shadow
    private int lastSentFood;

    @Shadow
    public abstract ServerLevel level();
    @Shadow
    protected abstract void triggerDimensionChangeTriggers(ServerLevel oldLevel);
    @Shadow
    public abstract void setServerLevel(ServerLevel level);
    @Shadow
    public abstract CommonPlayerSpawnInfo createCommonSpawnInfo(ServerLevel level);

    public ServerPlayerMixin(MinecraftServer minecraftServer, ServerLevel serverWorld, GameProfile gameProfile) {
        super(serverWorld, gameProfile);
    }

    /*
     * Custom-portal teleports always go through the dimension-change flow (respawn packet, loading screen),
     * even within one dimension, and play the travel sound unless muted. The body mirrors NeoForge's patched
     * ServerPlayer.teleport for the cross-dimension branch, including NeoForge's travel event, revive(),
     * attachment re-sync and changed-dimension event. Like the Fabric version, it skips the transition's
     * post-teleport callback, spectator teleporting, and the nether-entry bookkeeping.
     *
     * The full descriptor targets the real method, not the synthetic bridge that returns Entity.
     */
    @Inject(
            method = "teleport(Lnet/minecraft/world/level/portal/TeleportTransition;)Lnet/minecraft/server/level/ServerPlayer;",
            at = @At("HEAD"),
            cancellable = true
    )
    private void customportals$teleport(TeleportTransition transition, CallbackInfoReturnable<ServerPlayer> cir) {
        if (!((EntityMixinAccess) this).isInCustomPortal()) {
            return;
        }
        ServerPlayer thisPlayer = (ServerPlayer) (Object) this;

        if (!CommonHooks.onTravelToDimension(thisPlayer, transition.newLevel().dimension())) {
            cir.setReturnValue(null);
            return;
        }
        if (this.isRemoved()) {
            cir.setReturnValue(null);
            return;
        }

        if (transition.missingRespawnBlock()) {
            this.connection.send(new ClientboundGameEventPacket(ClientboundGameEventPacket.NO_RESPAWN_BLOCK_AVAILABLE, 0.0F));
        }

        ServerLevel newLevel = transition.newLevel();
        ServerLevel oldLevel = this.level();
        ResourceKey<Level> lastDimension = oldLevel.dimension();
        if (!transition.asPassenger()) {
            this.removeVehicle();
        }

        this.isChangingDimension = true;
        LevelData levelData = newLevel.getLevelData();
        this.connection.send(new ClientboundRespawnPacket(this.createCommonSpawnInfo(newLevel), (byte) 3));
        this.connection.send(new ClientboundChangeDifficultyPacket(levelData.getDifficulty(), levelData.isDifficultyLocked()));
        PlayerList playerList = this.server.getPlayerList();
        playerList.sendPlayerPermissionLevel(thisPlayer);
        oldLevel.removePlayerImmediately(thisPlayer, Entity.RemovalReason.CHANGED_DIMENSION);
        this.revive();
        ProfilerFiller profiler = Profiler.get();
        profiler.push("moving");
        profiler.pop();
        profiler.push("placing");
        this.setServerLevel(newLevel);
        this.connection.teleport(PositionMoveRotation.of(transition), transition.relatives());
        this.connection.resetPosition();
        newLevel.addDuringTeleport(thisPlayer);
        profiler.pop();
        this.triggerDimensionChangeTriggers(oldLevel);
        this.stopUsingItem();
        this.connection.send(new ClientboundPlayerAbilitiesPacket(this.getAbilities()));
        playerList.sendLevelInfo(thisPlayer, newLevel);
        playerList.sendAllPlayerInfo(thisPlayer);
        playerList.sendActivePlayerEffects(thisPlayer);
        if (!CPSettings.muteTeleportSounds()) {
            this.connection.send(new ClientboundLevelEventPacket(1032, BlockPos.ZERO, 0, false));
        }
        this.lastSentExp = -1;
        this.lastSentHealth = -1.0F;
        this.lastSentFood = -1;
        // The respawn packet makes the client drop all attachments, so re-send them even within one dimension.
        AttachmentSync.syncInitialPlayerAttachments(thisPlayer);
        if (newLevel.dimension() != lastDimension) {
            EventHooks.firePlayerChangedDimensionEvent(thisPlayer, lastDimension, newLevel.dimension());
        }
        cir.setReturnValue(thisPlayer);
    }
}
