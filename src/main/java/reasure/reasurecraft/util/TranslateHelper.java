package reasure.reasurecraft.util;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.registries.ForgeRegistryEntry;
import reasure.reasurecraft.ReasureCraft;

import javax.annotation.Nonnull;
import java.util.Objects;

public class TranslateHelper {
    public static String getTooltipPrefix(String key) {
        return "tooltip." + ReasureCraft.MOD_ID + "." + key;
    }

    public static String getTooltipPrefix(Item key) {
        return "tooltip." + ReasureCraft.MOD_ID + "." + name(key);
    }

    public static String getBlockPrefix(Block key) {
        return "block." + ReasureCraft.MOD_ID + "." + name(key);
    }

    private static <KEY extends ForgeRegistryEntry<KEY>> String name(@Nonnull KEY key) {
        return Objects.requireNonNull(key.getRegistryName()).getPath();
    }
}
