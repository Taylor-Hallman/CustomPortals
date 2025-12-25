package dev.custom.portals.registry;

import dev.custom.portals.CustomPortals;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.particle.v1.FabricSpriteProvider;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.PortalParticle;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;

public class CPParticles {

    public static final SimpleParticleType BLACK_PORTAL_PARTICLE = FabricParticleTypes.simple();
    public static final SimpleParticleType BLUE_PORTAL_PARTICLE = FabricParticleTypes.simple();
    public static final SimpleParticleType BROWN_PORTAL_PARTICLE = FabricParticleTypes.simple();
    public static final SimpleParticleType CYAN_PORTAL_PARTICLE = FabricParticleTypes.simple();
    public static final SimpleParticleType GRAY_PORTAL_PARTICLE = FabricParticleTypes.simple();
    public static final SimpleParticleType GREEN_PORTAL_PARTICLE = FabricParticleTypes.simple();
    public static final SimpleParticleType LIGHT_BLUE_PORTAL_PARTICLE = FabricParticleTypes.simple();
    public static final SimpleParticleType LIGHT_GRAY_PORTAL_PARTICLE = FabricParticleTypes.simple();
    public static final SimpleParticleType LIME_PORTAL_PARTICLE = FabricParticleTypes.simple();
    public static final SimpleParticleType MAGENTA_PORTAL_PARTICLE = FabricParticleTypes.simple();
    public static final SimpleParticleType ORANGE_PORTAL_PARTICLE = FabricParticleTypes.simple();
    public static final SimpleParticleType PINK_PORTAL_PARTICLE = FabricParticleTypes.simple();
    public static final SimpleParticleType RED_PORTAL_PARTICLE = FabricParticleTypes.simple();
    public static final SimpleParticleType WHITE_PORTAL_PARTICLE = FabricParticleTypes.simple();
    public static final SimpleParticleType YELLOW_PORTAL_PARTICLE = FabricParticleTypes.simple();

    public static void registerParticles() {
        Registry.register(BuiltInRegistries.PARTICLE_TYPE, ResourceLocation.fromNamespaceAndPath(CustomPortals.MOD_ID, "black_portal_particle"), BLACK_PORTAL_PARTICLE);
        Registry.register(BuiltInRegistries.PARTICLE_TYPE, ResourceLocation.fromNamespaceAndPath(CustomPortals.MOD_ID, "blue_portal_particle"), BLUE_PORTAL_PARTICLE);
        Registry.register(BuiltInRegistries.PARTICLE_TYPE, ResourceLocation.fromNamespaceAndPath(CustomPortals.MOD_ID, "brown_portal_particle"), BROWN_PORTAL_PARTICLE);
        Registry.register(BuiltInRegistries.PARTICLE_TYPE, ResourceLocation.fromNamespaceAndPath(CustomPortals.MOD_ID, "cyan_portal_particle"), CYAN_PORTAL_PARTICLE);
        Registry.register(BuiltInRegistries.PARTICLE_TYPE, ResourceLocation.fromNamespaceAndPath(CustomPortals.MOD_ID, "gray_portal_particle"), GRAY_PORTAL_PARTICLE);
        Registry.register(BuiltInRegistries.PARTICLE_TYPE, ResourceLocation.fromNamespaceAndPath(CustomPortals.MOD_ID, "green_portal_particle"), GREEN_PORTAL_PARTICLE);
        Registry.register(BuiltInRegistries.PARTICLE_TYPE, ResourceLocation.fromNamespaceAndPath(CustomPortals.MOD_ID, "light_blue_portal_particle"), LIGHT_BLUE_PORTAL_PARTICLE);
        Registry.register(BuiltInRegistries.PARTICLE_TYPE, ResourceLocation.fromNamespaceAndPath(CustomPortals.MOD_ID, "light_gray_portal_particle"), LIGHT_GRAY_PORTAL_PARTICLE);
        Registry.register(BuiltInRegistries.PARTICLE_TYPE, ResourceLocation.fromNamespaceAndPath(CustomPortals.MOD_ID, "lime_portal_particle"), LIME_PORTAL_PARTICLE);
        Registry.register(BuiltInRegistries.PARTICLE_TYPE, ResourceLocation.fromNamespaceAndPath(CustomPortals.MOD_ID, "magenta_portal_particle"), MAGENTA_PORTAL_PARTICLE);
        Registry.register(BuiltInRegistries.PARTICLE_TYPE, ResourceLocation.fromNamespaceAndPath(CustomPortals.MOD_ID, "orange_portal_particle"), ORANGE_PORTAL_PARTICLE);
        Registry.register(BuiltInRegistries.PARTICLE_TYPE, ResourceLocation.fromNamespaceAndPath(CustomPortals.MOD_ID, "pink_portal_particle"), PINK_PORTAL_PARTICLE);
        Registry.register(BuiltInRegistries.PARTICLE_TYPE, ResourceLocation.fromNamespaceAndPath(CustomPortals.MOD_ID, "red_portal_particle"), RED_PORTAL_PARTICLE);
        Registry.register(BuiltInRegistries.PARTICLE_TYPE, ResourceLocation.fromNamespaceAndPath(CustomPortals.MOD_ID, "white_portal_particle"), WHITE_PORTAL_PARTICLE);
        Registry.register(BuiltInRegistries.PARTICLE_TYPE, ResourceLocation.fromNamespaceAndPath(CustomPortals.MOD_ID, "yellow_portal_particle"), YELLOW_PORTAL_PARTICLE);
    }

    @Environment(EnvType.CLIENT)
    public static void registerFactoryRegistries() {
        ParticleFactoryRegistry.getInstance().register(BLACK_PORTAL_PARTICLE, BlackPortalParticle.Factory::new);
        ParticleFactoryRegistry.getInstance().register(BLUE_PORTAL_PARTICLE, BluePortalParticle.Factory::new);
        ParticleFactoryRegistry.getInstance().register(BROWN_PORTAL_PARTICLE, BrownPortalParticle.Factory::new);
        ParticleFactoryRegistry.getInstance().register(CYAN_PORTAL_PARTICLE, CyanPortalParticle.Factory::new);
        ParticleFactoryRegistry.getInstance().register(GRAY_PORTAL_PARTICLE, GrayPortalParticle.Factory::new);
        ParticleFactoryRegistry.getInstance().register(GREEN_PORTAL_PARTICLE, GreenPortalParticle.Factory::new);
        ParticleFactoryRegistry.getInstance().register(LIGHT_BLUE_PORTAL_PARTICLE, LightBluePortalParticle.Factory::new);
        ParticleFactoryRegistry.getInstance().register(LIGHT_GRAY_PORTAL_PARTICLE, LightGrayPortalParticle.Factory::new);
        ParticleFactoryRegistry.getInstance().register(LIME_PORTAL_PARTICLE, LimePortalParticle.Factory::new);
        ParticleFactoryRegistry.getInstance().register(MAGENTA_PORTAL_PARTICLE, MagentaPortalParticle.Factory::new);
        ParticleFactoryRegistry.getInstance().register(ORANGE_PORTAL_PARTICLE, OrangePortalParticle.Factory::new);
        ParticleFactoryRegistry.getInstance().register(PINK_PORTAL_PARTICLE, PinkPortalParticle.Factory::new);
        ParticleFactoryRegistry.getInstance().register(RED_PORTAL_PARTICLE, RedPortalParticle.Factory::new);
        ParticleFactoryRegistry.getInstance().register(WHITE_PORTAL_PARTICLE, WhitePortalParticle.Factory::new);
        ParticleFactoryRegistry.getInstance().register(YELLOW_PORTAL_PARTICLE, YellowPortalParticle.Factory::new);
    }
    
    @Environment(EnvType.CLIENT)
    static class BlackPortalParticle extends PortalParticle {

            protected BlackPortalParticle(ClientLevel clientWorld, double d, double e, double f, double g, double h, double i, TextureAtlasSprite sprite) {
                    super(clientWorld, d, e, f, g, h, i, sprite);
                    this.rCol = this.gCol = this.bCol = 0.0F;
            }

            @Environment(EnvType.CLIENT)
            public static class Factory implements ParticleProvider<SimpleParticleType> {
                    private final FabricSpriteProvider spriteProvider;

                    public Factory(FabricSpriteProvider sprites) {
                            this.spriteProvider = sprites;
                    }

                    @Override
                    public Particle createParticle(SimpleParticleType simpleParticleType, ClientLevel clientWorld, double d, double e, double f, double g, double h, double i, RandomSource random) {
                            return new BlackPortalParticle(clientWorld, d, e, f, g, h, i, this.spriteProvider.get(random));
                    }
            }
    }

    @Environment(EnvType.CLIENT)
    static class BluePortalParticle extends PortalParticle {

            protected BluePortalParticle(ClientLevel clientWorld, double d, double e, double f, double g, double h, double i, TextureAtlasSprite sprite) {
                    super(clientWorld, d, e, f, g, h, i, sprite);
                    float j = this.random.nextFloat() * 0.6F + 0.4F;
                    this.rCol = 0.2F * j;
                    this.gCol = 0.2F * j;
                    this.bCol = 1.0F * j;
            }

            @Environment(EnvType.CLIENT)
            public static class Factory implements ParticleProvider<SimpleParticleType> {
                    private final FabricSpriteProvider spriteProvider;

                    public Factory(FabricSpriteProvider sprites) {
                            this.spriteProvider = sprites;
                    }

                    @Override
                    public Particle createParticle(SimpleParticleType simpleParticleType, ClientLevel clientWorld, double d, double e, double f, double g, double h, double i, RandomSource random) {
                            return new BluePortalParticle(clientWorld, d, e, f, g, h, i, this.spriteProvider.get(random));
                    }
            }
    }

    @Environment(EnvType.CLIENT)
    static class BrownPortalParticle extends PortalParticle {

            protected BrownPortalParticle(ClientLevel clientWorld, double d, double e, double f, double g, double h, double i, TextureAtlasSprite sprite) {
                    super(clientWorld, d, e, f, g, h, i, sprite);
                    float j = this.random.nextFloat() * 0.6F + 0.4F;
                    this.rCol = 0.575F * j;
                    this.gCol = 0.45F * j;
                    this.bCol = 0.325F * j;
            }

            @Environment(EnvType.CLIENT)
            public static class Factory implements ParticleProvider<SimpleParticleType> {
                    private final FabricSpriteProvider spriteProvider;

                    public Factory(FabricSpriteProvider sprites) {
                            this.spriteProvider = sprites;
                    }

                    @Override
                    public Particle createParticle(SimpleParticleType simpleParticleType, ClientLevel clientWorld, double d, double e, double f, double g, double h, double i, RandomSource random) {
                            return new BrownPortalParticle(clientWorld, d, e, f, g, h, i, this.spriteProvider.get(random));
                    }
            }
    }

    @Environment(EnvType.CLIENT)
    static class CyanPortalParticle extends PortalParticle {

            protected CyanPortalParticle(ClientLevel clientWorld, double d, double e, double f, double g, double h, double i, TextureAtlasSprite sprite) {
                    super(clientWorld, d, e, f, g, h, i, sprite);
                    float j = this.random.nextFloat() * 0.6F + 0.4F;
                    this.bCol = 0.8F * j;
                    this.rCol = 0.2F * j;
                    this.gCol = 0.65F * j;
            }

            @Environment(EnvType.CLIENT)
            public static class Factory implements ParticleProvider<SimpleParticleType> {
                    private final FabricSpriteProvider spriteProvider;

                    public Factory(FabricSpriteProvider sprites) {
                            this.spriteProvider = sprites;
                    }

                    @Override
                    public Particle createParticle(SimpleParticleType simpleParticleType, ClientLevel clientWorld, double d, double e, double f, double g, double h, double i, RandomSource random) {
                            return new CyanPortalParticle(clientWorld, d, e, f, g, h, i, this.spriteProvider.get(random));
                    }
            }
    }

    @Environment(EnvType.CLIENT)
    static class GrayPortalParticle extends PortalParticle {

            protected GrayPortalParticle(ClientLevel clientWorld, double d, double e, double f, double g, double h, double i, TextureAtlasSprite sprite) {
                    super(clientWorld, d, e, f, g, h, i, sprite);
                    float j = this.random.nextFloat() * 0.6F + 0.4F;
                    this.rCol = this.gCol = this.bCol = 0.5F * j;
            }

            @Environment(EnvType.CLIENT)
            public static class Factory implements ParticleProvider<SimpleParticleType> {
                    private final FabricSpriteProvider spriteProvider;

                    public Factory(FabricSpriteProvider sprites) {
                            this.spriteProvider = sprites;
                    }

                    @Override
                    public Particle createParticle(SimpleParticleType simpleParticleType, ClientLevel clientWorld, double d, double e, double f, double g, double h, double i, RandomSource random) {
                            return new GrayPortalParticle(clientWorld, d, e, f, g, h, i, this.spriteProvider.get(random));
                    }
            }
    }

    @Environment(EnvType.CLIENT)
    static class GreenPortalParticle extends PortalParticle {

        protected GreenPortalParticle(ClientLevel clientWorld, double d, double e, double f, double g, double h, double i, TextureAtlasSprite sprite) {
            super(clientWorld, d, e, f, g, h, i, sprite);
            float j = this.random.nextFloat() * 0.6F + 0.4F;
            this.rCol = 0.2F * j;
            this.gCol = 0.5F * j;
            this.bCol = 0.2F * j;
        }

        @Environment(EnvType.CLIENT)
        public static class Factory implements ParticleProvider<SimpleParticleType> {
            private final FabricSpriteProvider spriteProvider;

            public Factory(FabricSpriteProvider sprites) {
                this.spriteProvider = sprites;
            }

            @Override
            public Particle createParticle(SimpleParticleType type, ClientLevel world, double d, double e, double f, double g, double h, double i, RandomSource random) {
                return new GreenPortalParticle(world, d, e, f, g, h, i, this.spriteProvider.get(random));
            }
        }
    }

    @Environment(EnvType.CLIENT)
    static class LightBluePortalParticle extends PortalParticle {

        protected LightBluePortalParticle(ClientLevel clientWorld, double d, double e, double f, double g, double h, double i, TextureAtlasSprite sprite) {
            super(clientWorld, d, e, f, g, h, i, sprite);
            float j = this.random.nextFloat() * 0.6F + 0.4F;
            this.rCol = 0.6F * j;
            this.bCol = 1.0F * j;
            this.gCol = 0.7F * j;
        }

        @Environment(EnvType.CLIENT)
        public static class Factory implements ParticleProvider<SimpleParticleType> {
            private final FabricSpriteProvider spriteProvider;

            public Factory(FabricSpriteProvider sprites) {
                this.spriteProvider = sprites;
            }

            @Override
            public Particle createParticle(SimpleParticleType type, ClientLevel world, double d, double e, double f, double g, double h, double i, RandomSource random) {
                return new LightBluePortalParticle(world, d, e, f, g, h, i, this.spriteProvider.get(random));
            }
        }
    }

    @Environment(EnvType.CLIENT)
    static class LightGrayPortalParticle extends PortalParticle {

        protected LightGrayPortalParticle(ClientLevel clientWorld, double d, double e, double f, double g, double h, double i, TextureAtlasSprite sprite) {
            super(clientWorld, d, e, f, g, h, i, sprite);
            float j = this.random.nextFloat() * 0.6F + 0.4F;
            this.rCol = this.gCol = this.bCol = 0.7F * j;
        }

        @Environment(EnvType.CLIENT)
        public static class Factory implements ParticleProvider<SimpleParticleType> {
            private final FabricSpriteProvider spriteProvider;

            public Factory(FabricSpriteProvider sprites) {
                this.spriteProvider = sprites;
            }

            @Override
            public Particle createParticle(SimpleParticleType type, ClientLevel world, double d, double e, double f, double g, double h, double i, RandomSource random) {
                return new LightGrayPortalParticle(world, d, e, f, g, h, i, this.spriteProvider.get(random));
            }
        }
    }

    @Environment(EnvType.CLIENT)
    static class LimePortalParticle extends PortalParticle {

        protected LimePortalParticle(ClientLevel clientWorld, double d, double e, double f, double g, double h, double i, TextureAtlasSprite sprite) {
            super(clientWorld, d, e, f, g, h, i, sprite);
            float j = this.random.nextFloat() * 0.6F + 0.4F;
            this.rCol = 0.5F * j;
            this.gCol = 1.0F * j;
            this.bCol = 0.3F * j;
        }

        @Environment(EnvType.CLIENT)
        public static class Factory implements ParticleProvider<SimpleParticleType> {
            private final FabricSpriteProvider spriteProvider;

            public Factory(FabricSpriteProvider sprites) {
                this.spriteProvider = sprites;
            }

            @Override
            public Particle createParticle(SimpleParticleType type, ClientLevel world, double d, double e, double f, double g, double h, double i, RandomSource random) {
                return new LimePortalParticle(world, d, e, f, g, h, i, this.spriteProvider.get(random));
            }
        }
    }

    @Environment(EnvType.CLIENT)
    static class MagentaPortalParticle extends PortalParticle {

        protected MagentaPortalParticle(ClientLevel clientWorld, double d, double e, double f, double g, double h, double i, TextureAtlasSprite sprite) {
            super(clientWorld, d, e, f, g, h, i, sprite);
            float j = this.random.nextFloat() * 0.6F + 0.4F;
            this.rCol = 1.0F * j;
            this.gCol = 0.4F * j;
            this.bCol = 1.0F * j;
        }

        @Environment(EnvType.CLIENT)
        public static class Factory implements ParticleProvider<SimpleParticleType> {
            private final FabricSpriteProvider spriteProvider;

            public Factory(FabricSpriteProvider sprites) {
                this.spriteProvider = sprites;
            }

            @Override
            public Particle createParticle(SimpleParticleType type, ClientLevel world, double d, double e, double f, double g, double h, double i, RandomSource random) {
                return new MagentaPortalParticle(world, d, e, f, g, h, i, this.spriteProvider.get(random));
            }
        }
    }

    @Environment(EnvType.CLIENT)
    static class OrangePortalParticle extends PortalParticle {

        protected OrangePortalParticle(ClientLevel clientWorld, double d, double e, double f, double g, double h, double i, TextureAtlasSprite sprite) {
            super(clientWorld, d, e, f, g, h, i, sprite);
            float j = this.random.nextFloat() * 0.6F + 0.4F;
            this.rCol = 1.0F * j;
            this.gCol = 0.7F * j;
            this.bCol = 0.2F * j;
        }

        @Environment(EnvType.CLIENT)
        public static class Factory implements ParticleProvider<SimpleParticleType> {
            private final FabricSpriteProvider spriteProvider;

            public Factory(FabricSpriteProvider sprites) {
                this.spriteProvider = sprites;
            }

            @Override
            public Particle createParticle(SimpleParticleType type, ClientLevel world, double d, double e, double f, double g, double h, double i, RandomSource random) {
                return new OrangePortalParticle(world, d, e, f, g, h, i, this.spriteProvider.get(random));
            }
        }
    }

    @Environment(EnvType.CLIENT)
    static class PinkPortalParticle extends PortalParticle {

        protected PinkPortalParticle(ClientLevel clientWorld, double d, double e, double f, double g, double h, double i, TextureAtlasSprite sprite) {
            super(clientWorld, d, e, f, g, h, i, sprite);
            float j = this.random.nextFloat() * 0.6F + 0.4F;
            this.rCol = 1.0F * j;
            this.gCol = 0.6F * j;
            this.bCol = 0.9F * j;
        }

        @Environment(EnvType.CLIENT)
        public static class Factory implements ParticleProvider<SimpleParticleType> {
            private final FabricSpriteProvider spriteProvider;

            public Factory(FabricSpriteProvider sprites) {
                this.spriteProvider = sprites;
            }

            @Override
            public Particle createParticle(SimpleParticleType type, ClientLevel world, double d, double e, double f, double g, double h, double i, RandomSource random) {
                return new PinkPortalParticle(world, d, e, f, g, h, i, this.spriteProvider.get(random));
            }
        }
    }

    @Environment(EnvType.CLIENT)
    static class RedPortalParticle extends PortalParticle {

        protected RedPortalParticle(ClientLevel clientWorld, double d, double e, double f, double g, double h, double i, TextureAtlasSprite sprite) {
            super(clientWorld, d, e, f, g, h, i, sprite);
            float j = this.random.nextFloat() * 0.6F + 0.4F;
            this.rCol = 1.0F * j;
            this.gCol = 0.2F * j;
            this.bCol = 0.2F * j;
        }

        @Environment(EnvType.CLIENT)
        public static class Factory implements ParticleProvider<SimpleParticleType> {
            private final FabricSpriteProvider spriteProvider;

            public Factory(FabricSpriteProvider sprites) {
                this.spriteProvider = sprites;
            }

            @Override
            public Particle createParticle(SimpleParticleType type, ClientLevel world, double d, double e, double f, double g, double h, double i, RandomSource random) {
                return new RedPortalParticle(world, d, e, f, g, h, i, this.spriteProvider.get(random));
            }
        }
    }

    @Environment(EnvType.CLIENT)
    static class WhitePortalParticle extends PortalParticle {

        protected WhitePortalParticle(ClientLevel clientWorld, double d, double e, double f, double g, double h, double i, TextureAtlasSprite sprite) {
            super(clientWorld, d, e, f, g, h, i, sprite);
            float j = this.random.nextFloat() * 0.6F + 0.4F;
            this.rCol = this.gCol = this.bCol = 1.0F * j;
        }

        @Environment(EnvType.CLIENT)
        public static class Factory implements ParticleProvider<SimpleParticleType> {
            private final FabricSpriteProvider spriteProvider;

            public Factory(FabricSpriteProvider sprites) {
                this.spriteProvider = sprites;
            }

            @Override
            public Particle createParticle(SimpleParticleType type, ClientLevel world, double d, double e, double f, double g, double h, double i, RandomSource random) {
                return new WhitePortalParticle(world, d, e, f, g, h, i, this.spriteProvider.get(random));
            }
        }
    }

    @Environment(EnvType.CLIENT)
    static class YellowPortalParticle extends PortalParticle {

        protected YellowPortalParticle(ClientLevel clientWorld, double d, double e, double f, double g, double h, double i, TextureAtlasSprite sprite) {
            super(clientWorld, d, e, f, g, h, i, sprite);
            float j = this.random.nextFloat() * 0.6F + 0.4F;
            this.rCol = 1.0F * j;
            this.gCol = 1.0F * j;
            this.bCol = 0.2F * j;
        }

        @Environment(EnvType.CLIENT)
        public static class Factory implements ParticleProvider<SimpleParticleType> {
            private final FabricSpriteProvider spriteProvider;

            public Factory(FabricSpriteProvider sprites) {
                this.spriteProvider = sprites;
            }

            @Override
            public Particle createParticle(SimpleParticleType type, ClientLevel world, double d, double e, double f, double g, double h, double i, RandomSource random) {
                return new YellowPortalParticle(world, d, e, f, g, h, i, this.spriteProvider.get(random));
            }
        }
    }
}
