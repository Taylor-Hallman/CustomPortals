package dev.custom.portals.mixin;

import dev.custom.portals.util.PortalHelper;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.util.ARGB;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.At;
import dev.custom.portals.util.EntityMixinAccess;

import org.spongepowered.asm.mixin.Final;

import net.minecraft.client.gui.Gui;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.resources.model.sprite.SpriteId;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.world.level.block.Block;

@Mixin(Gui.class)
public abstract class GuiMixin {

    @Final
    @Shadow
    private Minecraft minecraft;

    @Inject(method = "extractPortalOverlay", at = @At("HEAD"), cancellable = true)
    private void extractPortalOverlay(GuiGraphicsExtractor drawContext, float f, CallbackInfo ci) {
        int color = ((EntityMixinAccess)this.minecraft.player).getPortalColor();
        if(color != 0 && !((EntityMixinAccess)this.minecraft.player).isInNetherPortal()) {
            Block spriteModel = PortalHelper.getPortalBlockFromColorId(color);
            if (f < 1.0F) {
                f *= f;
                f *= f;
                f = f * 0.8F + 0.2F;
            }

            int i = ARGB.white(f);
            Identifier blockId = BuiltInRegistries.BLOCK.getKey(spriteModel);
            Identifier textureId = Identifier.fromNamespaceAndPath(blockId.getNamespace(), "block/" + blockId.getPath());
            TextureAtlasSprite sprite = this.minecraft.getAtlasManager().get(new SpriteId(TextureAtlas.LOCATION_BLOCKS, textureId));
            drawContext.blitSprite(RenderPipelines.GUI_TEXTURED, sprite, 0, 0, drawContext.guiWidth(), drawContext.guiHeight(), i);
            ci.cancel();
        }
    }
}
