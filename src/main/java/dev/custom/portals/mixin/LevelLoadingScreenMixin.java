package dev.custom.portals.mixin;

import dev.custom.portals.blocks.PortalBlock;
import dev.custom.portals.util.*;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.LevelLoadingScreen;
import net.minecraft.client.GameNarrator;
import net.minecraft.client.multiplayer.LevelLoadTracker;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.BooleanSupplier;

@Mixin(LevelLoadingScreen.class)
public abstract class LevelLoadingScreenMixin extends Screen {
    @Shadow
    private LevelLoadingScreen.Reason reason;
    @Shadow
    private LevelLoadTracker loadTracker;

    @Unique
    private boolean packetSent = false;

    public LevelLoadingScreenMixin(LevelLoadTracker clientChunkLoadProgress, LevelLoadingScreen.Reason worldEntryReason) {
        super(GameNarrator.NO_TITLE);
        this.loadTracker = clientChunkLoadProgress;
        this.reason = worldEntryReason;
    }

    @Inject(method = "renderBackground", at = @At("HEAD"), cancellable = true)
    public void renderBackground(GuiGraphics drawContext, int i, int j, float f, CallbackInfo ci) {
        if (ClientUtil.transitionBackgroundSpriteModel != null) {
            ClientUtil.isTransitioning = true;
            if (!packetSent) {
                ClientPlayNetworking.send(new ScreenTransitionPayload(true));
                packetSent = true;
            }
            drawContext.blitSprite(RenderPipelines.GUI_OPAQUE_TEXTURED_BACKGROUND, this.minecraft.getBlockRenderer().getBlockModelShaper().getParticleIcon(ClientUtil.transitionBackgroundSpriteModel.defaultBlockState().setValue(PortalBlock.LIT, true)), 0, 0, drawContext.guiWidth(), drawContext.guiHeight());
            ci.cancel();
        }
    }
    @Inject(method = "onClose", at = @At("HEAD"))
    public void close(CallbackInfo ci) {
        ClientUtil.isTransitioning = false;
        ClientPlayNetworking.send(new ScreenTransitionPayload(false));
        ClientUtil.transitionBackgroundSpriteModel = null;
        packetSent = false;
    }
}
