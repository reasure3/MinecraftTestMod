package reasure.reasurecraft.util;

import net.minecraft.item.Item;
import reasure.reasurecraft.ReasureCraft;

import java.util.Objects;

public class TranslateHelper {
    public static String getTooltipPrefix(String key) {
        return "tooltip." + ReasureCraft.MOD_ID + "." + key;
    }

    public static String getTooltipPrefix(Item key) {
        return "tooltip." + ReasureCraft.MOD_ID + "." + Objects.requireNonNull(key.getRegistryName()).getPath();
    }
}
