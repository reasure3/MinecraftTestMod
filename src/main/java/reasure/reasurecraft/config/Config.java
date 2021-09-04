package reasure.reasurecraft.config;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class Config {
    public static final ForgeConfigSpec common_config;
    private static final ForgeConfigSpec.Builder common_builder = new ForgeConfigSpec.Builder();

    static {
        ItemConfig.init(common_builder);
        OregenConfig.init(common_builder);

        common_config = common_builder.build();
    }
}
