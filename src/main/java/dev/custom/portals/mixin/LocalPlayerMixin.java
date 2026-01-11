package dev.custom.portals.mixin;

import dev.custom.portals.config.CPSettings;
import dev.custom.portals.data.CustomPortal;
import net.minecraft.client.gui.screens.DeathScreen;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.client.ClientRecipeBook;
import net.minecraft.stats.StatsCounter;
import net.minecraft.util.Mth;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.sounds.SoundEvents;

import dev.custom.portals.util.EntityMixinAccess;

@Mixin(LocalPlayer.class)
public abstract class LocalPlayerMixin extends AbstractClientPlayer {

    @Shadow
    private Minecraft minecraft;
    @Shadow
    public float oPortalEffectIntensity;
    @Shadow
    public float portalEffectIntensity;

    public LocalPlayerMixin(Minecraft minecraftClient, ClientLevel clientWorld, ClientPacketListener clientPlayNetworkHandler, StatsCounter statHandler, ClientRecipeBook clientRecipeBook, boolean bl, boolean bl2) {
        super(clientWorld, clientPlayNetworkHandler.getLocalGameProfile());
    }

    @Inject(method = "handlePortalTransitionEffect", at = @At("HEAD"), cancellable = true)
    private void tickNausea(CallbackInfo ci) {
        CustomPortal portal = ((EntityMixinAccess)this).getDestPortal();
        float f = 0.0F;
        if(((EntityMixinAccess)this).isInCustomPortal() && portal != null && this.portalProcess != null) {
            this.oPortalEffectIntensity = this.portalEffectIntensity;
            if (this.minecraft.screen != null && !this.minecraft.screen.isPauseScreen() && !(this.minecraft.screen instanceof DeathScreen)) {
                if (this.minecraft.screen instanceof AbstractContainerScreen) {
                    this.closeContainer();
                }

                this.minecraft.setScreen((Screen)null);
            }

            if (!CPSettings.instance().muteTeleportSounds && this.portalEffectIntensity == 0.0F && portal.getPlayerTeleportDelay() > 1) {
                this.minecraft.getSoundManager().play(SimpleSoundInstance.forLocalAmbience(SoundEvents.PORTAL_TRIGGER, this.random.nextFloat() * 0.4F + 0.8F, 0.25F));
            }

            f = 0.0125F;
            this.portalProcess.setAsInsidePortalThisTick(false);
            ((EntityMixinAccess)this).notInCustomPortal();
            this.portalEffectIntensity = Mth.clamp(this.portalEffectIntensity + f, 0.0F, 1.0F);
            this.processPortalCooldown();
            ci.cancel();
        }
        if(this.oPortalEffectIntensity == 0.0F && ((EntityMixinAccess)this).getPortalColor() != 0) {
            ((EntityMixinAccess) this).setPortalColor(0);
        }
    }
}
