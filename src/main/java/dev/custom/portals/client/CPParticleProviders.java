package dev.custom.portals.client;

import dev.custom.portals.registry.CPParticles;
import net.neoforged.neoforge.client.event.RegisterParticleProvidersEvent;

public final class CPParticleProviders {
    private CPParticleProviders() {}

    public static void register(RegisterParticleProvidersEvent event) {
        event.registerSpriteSet(CPParticles.BLACK_PORTAL_PARTICLE.get(), sprites -> new ColoredPortalParticle.Provider(sprites, 0.0F, 0.0F, 0.0F, false));
        event.registerSpriteSet(CPParticles.BLUE_PORTAL_PARTICLE.get(), sprites -> new ColoredPortalParticle.Provider(sprites, 0.2F, 0.2F, 1.0F, true));
        event.registerSpriteSet(CPParticles.BROWN_PORTAL_PARTICLE.get(), sprites -> new ColoredPortalParticle.Provider(sprites, 0.575F, 0.45F, 0.325F, true));
        event.registerSpriteSet(CPParticles.CYAN_PORTAL_PARTICLE.get(), sprites -> new ColoredPortalParticle.Provider(sprites, 0.2F, 0.65F, 0.8F, true));
        event.registerSpriteSet(CPParticles.GRAY_PORTAL_PARTICLE.get(), sprites -> new ColoredPortalParticle.Provider(sprites, 0.5F, 0.5F, 0.5F, true));
        event.registerSpriteSet(CPParticles.GREEN_PORTAL_PARTICLE.get(), sprites -> new ColoredPortalParticle.Provider(sprites, 0.2F, 0.5F, 0.2F, true));
        event.registerSpriteSet(CPParticles.LIGHT_BLUE_PORTAL_PARTICLE.get(), sprites -> new ColoredPortalParticle.Provider(sprites, 0.6F, 0.7F, 1.0F, true));
        event.registerSpriteSet(CPParticles.LIGHT_GRAY_PORTAL_PARTICLE.get(), sprites -> new ColoredPortalParticle.Provider(sprites, 0.7F, 0.7F, 0.7F, true));
        event.registerSpriteSet(CPParticles.LIME_PORTAL_PARTICLE.get(), sprites -> new ColoredPortalParticle.Provider(sprites, 0.5F, 1.0F, 0.3F, true));
        event.registerSpriteSet(CPParticles.MAGENTA_PORTAL_PARTICLE.get(), sprites -> new ColoredPortalParticle.Provider(sprites, 1.0F, 0.4F, 1.0F, true));
        event.registerSpriteSet(CPParticles.ORANGE_PORTAL_PARTICLE.get(), sprites -> new ColoredPortalParticle.Provider(sprites, 1.0F, 0.7F, 0.2F, true));
        event.registerSpriteSet(CPParticles.PINK_PORTAL_PARTICLE.get(), sprites -> new ColoredPortalParticle.Provider(sprites, 1.0F, 0.6F, 0.9F, true));
        event.registerSpriteSet(CPParticles.RED_PORTAL_PARTICLE.get(), sprites -> new ColoredPortalParticle.Provider(sprites, 1.0F, 0.2F, 0.2F, true));
        event.registerSpriteSet(CPParticles.WHITE_PORTAL_PARTICLE.get(), sprites -> new ColoredPortalParticle.Provider(sprites, 1.0F, 1.0F, 1.0F, true));
        event.registerSpriteSet(CPParticles.YELLOW_PORTAL_PARTICLE.get(), sprites -> new ColoredPortalParticle.Provider(sprites, 1.0F, 1.0F, 0.2F, true));
    }
}
