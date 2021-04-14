package reasure.reasurecraft.util;

import net.minecraft.util.ResourceLocation;
import reasure.reasurecraft.ReasureCraft;

public class ModResourceLocation extends ResourceLocation {
    public ModResourceLocation(String resourceName) {
        super(addModNamespace(resourceName));
    }

    private static String addModNamespace(String resourceName) {
        if (resourceName.contains(":")) {
            return resourceName;
        }
        return ReasureCraft.MOD_ID + ":" + resourceName;
    }
}
