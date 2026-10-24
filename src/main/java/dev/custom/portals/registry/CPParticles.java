package dev.custom.portals.registry;

import dev.custom.portals.CustomPortals;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.Registries;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class CPParticles {

    public static final DeferredRegister<ParticleType<?>> PARTICLE_TYPES =
            DeferredRegister.create(Registries.PARTICLE_TYPE, CustomPortals.MOD_ID);

    private static DeferredHolder<ParticleType<?>, SimpleParticleType> simple(String name) {
        return PARTICLE_TYPES.register(name, () -> new SimpleParticleType(false));
    }

    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> BLACK_PORTAL_PARTICLE = simple("black_portal_particle");
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> BLUE_PORTAL_PARTICLE = simple("blue_portal_particle");
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> BROWN_PORTAL_PARTICLE = simple("brown_portal_particle");
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> CYAN_PORTAL_PARTICLE = simple("cyan_portal_particle");
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> GRAY_PORTAL_PARTICLE = simple("gray_portal_particle");
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> GREEN_PORTAL_PARTICLE = simple("green_portal_particle");
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> LIGHT_BLUE_PORTAL_PARTICLE = simple("light_blue_portal_particle");
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> LIGHT_GRAY_PORTAL_PARTICLE = simple("light_gray_portal_particle");
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> LIME_PORTAL_PARTICLE = simple("lime_portal_particle");
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> MAGENTA_PORTAL_PARTICLE = simple("magenta_portal_particle");
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> ORANGE_PORTAL_PARTICLE = simple("orange_portal_particle");
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> PINK_PORTAL_PARTICLE = simple("pink_portal_particle");
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> RED_PORTAL_PARTICLE = simple("red_portal_particle");
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> WHITE_PORTAL_PARTICLE = simple("white_portal_particle");
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> YELLOW_PORTAL_PARTICLE = simple("yellow_portal_particle");
}
