package dev.custom.portals.util;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.world.level.block.Block;

@Environment(EnvType.CLIENT)
public class ClientUtil {
    public static boolean isTransitioning;
    public static Block transitionBackgroundSpriteModel;
}
