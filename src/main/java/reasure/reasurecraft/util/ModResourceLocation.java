package reasure.reasurecraft.util;

import net.minecraft.util.ResourceLocation;
import reasure.reasurecraft.ReasureCraft;

public class ModResourceLocation extends ResourceLocation {
    protected ModResourceLocation(String resourceName) {
        super(addModNamespace(resourceName));
    }

    private static String addModNamespace(String resourceName) {
        if (resourceName.contains(":")) {
            return resourceName;
        }
        return ReasureCraft.MOD_ID + ":" + resourceName;
    }

    public static ModResourceLocation getId(String path) {
        if (path.contains(":")) {
            throw new IllegalArgumentException("path contains namespace");
        }
        return new ModResourceLocation(path);
    }
}
