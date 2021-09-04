package reasure.reasurecraft.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class ItemConfig {
    public static ForgeConfigSpec.DoubleValue teleport_staff_distance;
    public static ForgeConfigSpec.IntValue teleport_staff_cooldown;
    public static ForgeConfigSpec.IntValue torch_replacer_brightness;

    public static void init(ForgeConfigSpec.Builder common) {
        common.comment("Item Config");

        teleport_staff_distance = common
                .comment("Maximum distance that a player can teleport using Teleport Staff")
                .defineInRange("item.teleport_staff.distance", 10D, 1D, 1000D);

        teleport_staff_cooldown = common
                .comment("Cooldown tick of Teleport Staff (1 second = 20 ticks)")
                .defineInRange("item.teleport_staff.cooldown", 60, 0, 100000);

        torch_replacer_brightness = common
                .comment("Maximum brightness of the torch to be placed automatically. If the brightness exceeds this value, torch not to be placed.")
                .defineInRange("item.torch_replacer.max_brightness", 7, 0, 15);
    }
}
