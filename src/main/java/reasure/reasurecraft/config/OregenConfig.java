package reasure.reasurecraft.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class OregenConfig {
    public static ForgeConfigSpec.IntValue silver_ore_veinSize;
    public static ForgeConfigSpec.IntValue silver_ore_count;
    public static ForgeConfigSpec.IntValue silver_ore_extra_veinSize;
    public static ForgeConfigSpec.IntValue silver_ore_extra_count;

    public static void init(ForgeConfigSpec.Builder common) {
        common.comment("Oregen Config");

        silver_ore_veinSize = common
                .comment("size of ore veins of the silver ore that can spawn in one chunk (in y 0 ~ 31)")
                .defineInRange("oregen.silver_ore.veinSize", 9, 1, 100);
        silver_ore_count = common
                .comment("Maximum number of ore veins of the silver ore that can spawn in one chunk (in y 0 ~ 31)")
                .defineInRange("oregen.silver_ore.count", 4, 1, 100);

        silver_ore_extra_veinSize = common
                .comment("size of ore veins of the silver ore that can spawn in one chunk (in y 32 ~ 47)")
                .defineInRange("oregen.silver_ore_extra.veinSize", 9, 1, 100);
        silver_ore_extra_count = common
                .comment("Maximum number of ore veins of the silver ore that can spawn in one chunk (in y 32 ~ 47)")
                .defineInRange("oregen.silver_ore_extra.count", 24, 1, 100);
    }
}
