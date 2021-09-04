package reasure.reasurecraft.world;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.feature.OreFeatureConfig;
import net.minecraft.world.gen.feature.template.BlockMatchRuleTest;
import net.minecraft.world.gen.feature.template.RuleTest;
import net.minecraft.world.gen.placement.Placement;
import net.minecraft.world.gen.placement.TopSolidRangeConfig;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import reasure.reasurecraft.ReasureCraft;
import reasure.reasurecraft.config.OregenConfig;
import reasure.reasurecraft.init.ModBlocks;

import java.util.List;
import java.util.function.Supplier;

@Mod.EventBusSubscriber(modid = ReasureCraft.MOD_ID)
public class OreGeneration {
    public static ConfiguredFeature<?, ?> CONFIGURED_SILVER_ORE;
    public static ConfiguredFeature<?, ?> CONFIGURED_SILVER_ORE_EXTRA;

    public static RuleTest ENDSTONE = new BlockMatchRuleTest(Blocks.END_STONE);

    public static void registerOres() {
        CONFIGURED_SILVER_ORE = register("silver_ore", OreRange(OreFeatureConfig.FillerBlockType.NATURAL_STONE,
                ModBlocks.SILVER_ORE.get().defaultBlockState(), OregenConfig.silver_ore_veinSize.get(), 32, OregenConfig.silver_ore_count.get()));
        CONFIGURED_SILVER_ORE_EXTRA = register("silver_ore_extra", OreTopSolid(OreFeatureConfig.FillerBlockType.NATURAL_STONE,
                ModBlocks.SILVER_ORE.get().defaultBlockState(), OregenConfig.silver_ore_extra_veinSize.get(), 32, 32, 80, OregenConfig.silver_ore_extra_count.get()));
    }

    private static ConfiguredFeature<?, ?> OreRange(RuleTest fillerType, BlockState state, int veinSize, int range, int frequency) {
        return Feature.ORE.configured(new OreFeatureConfig(fillerType, state, veinSize)).range(range).squared().count(frequency);
    }

    private static ConfiguredFeature<?, ?> OreTopSolid(RuleTest fillerType, BlockState state, int veinSize, int bottomOffset, int topOffset, int maximum, int frequency) {
        return Feature.ORE.configured(new OreFeatureConfig(fillerType, state, veinSize)).decorated(Placement.RANGE.configured(new TopSolidRangeConfig(bottomOffset, topOffset, maximum))).squared().count(frequency);
    }

    private static <FC extends IFeatureConfig> ConfiguredFeature<FC, ?> register(String p_243968_0_, ConfiguredFeature<FC, ?> p_243968_1_) {
        return Registry.register(WorldGenRegistries.CONFIGURED_FEATURE, p_243968_0_, p_243968_1_);
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void generateOres(final BiomeLoadingEvent event) {
        if (!(event.getCategory().equals(Biome.Category.NETHER) || event.getCategory().equals(Biome.Category.THEEND))) {
            List<Supplier<ConfiguredFeature<?, ?>>> feature = event.getGeneration().getFeatures(GenerationStage.Decoration.UNDERGROUND_ORES);
            feature.add(() -> CONFIGURED_SILVER_ORE);
            feature.add(() -> CONFIGURED_SILVER_ORE_EXTRA);
        }
    }
}
