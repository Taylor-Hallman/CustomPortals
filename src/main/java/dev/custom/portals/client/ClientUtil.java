package dev.custom.portals.client;

import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.Nullable;

/** Client-only state shared between the payload handler and LevelLoadingScreenMixin. */
public class ClientUtil {
    public static boolean isTransitioning;
    @Nullable
    public static Block transitionBackgroundSpriteModel;
}
