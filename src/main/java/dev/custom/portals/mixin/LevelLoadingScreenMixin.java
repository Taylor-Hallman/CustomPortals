package dev.custom.portals.mixin;
import dev.custom.portals.client.ClientUtil;
import dev.custom.portals.network.ScreenTransitionPayload;
import net.neoforged.neoforge.client.network.ClientPacketDistributor;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.LevelLoadingScreen;
import net.minecraft.client.GameNarrator;
import net.minecraft.client.multiplayer.LevelLoadTracker;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.sprite.SpriteId;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

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

    @Inject(method = "extractBackground", at = @At("HEAD"), cancellable = true)
    public void extractBackground(GuiGraphicsExtractor drawContext, int i, int j, float f, CallbackInfo ci) {
        if (ClientUtil.transitionBackgroundSpriteModel != null) {
            ClientUtil.isTransitioning = true;
            if (!packetSent) {
                ClientPacketDistributor.sendToServer(new ScreenTransitionPayload(true));
                packetSent = true;
            }
            Identifier blockId = BuiltInRegistries.BLOCK.getKey(ClientUtil.transitionBackgroundSpriteModel);
            Identifier textureId = Identifier.fromNamespaceAndPath(blockId.getNamespace(), "block/" + blockId.getPath());
            TextureAtlasSprite sprite = this.minecraft.getAtlasManager().get(new SpriteId(TextureAtlas.LOCATION_BLOCKS, textureId));
            drawContext.blitSprite(RenderPipelines.GUI_OPAQUE_TEXTURED_BACKGROUND, sprite, 0, 0, drawContext.guiWidth(), drawContext.guiHeight());
            ci.cancel();
        }
    }
    @Inject(method = "onClose", at = @At("HEAD"))
    public void close(CallbackInfo ci) {
        ClientUtil.isTransitioning = false;
        ClientPacketDistributor.sendToServer(new ScreenTransitionPayload(false));
        ClientUtil.transitionBackgroundSpriteModel = null;
        packetSent = false;
    }
}
