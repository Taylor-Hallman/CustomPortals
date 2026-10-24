package dev.custom.portals.client;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.PortalParticle;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.RandomSource;

/** Vanilla portal particle with a fixed tint. Replaces the 15 copy-pasted particle classes of the Fabric build. */
public class ColoredPortalParticle extends PortalParticle {

    protected ColoredPortalParticle(ClientLevel level, double x, double y, double z, double dx, double dy, double dz,
                                    TextureAtlasSprite sprite, float red, float green, float blue, boolean randomizeBrightness) {
        super(level, x, y, z, dx, dy, dz, sprite);
        float brightness = randomizeBrightness ? this.random.nextFloat() * 0.6F + 0.4F : 1.0F;
        this.rCol = red * brightness;
        this.gCol = green * brightness;
        this.bCol = blue * brightness;
    }

    public record Provider(SpriteSet sprites, float red, float green, float blue, boolean randomizeBrightness)
            implements ParticleProvider<SimpleParticleType> {

        @Override
        public Particle createParticle(SimpleParticleType type, ClientLevel level, double x, double y, double z,
                                       double dx, double dy, double dz, RandomSource random) {
            return new ColoredPortalParticle(level, x, y, z, dx, dy, dz, sprites.get(random), red, green, blue, randomizeBrightness);
        }
    }
}
