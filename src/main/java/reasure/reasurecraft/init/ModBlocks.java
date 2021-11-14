package reasure.reasurecraft.init;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.FlowingFluidBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import reasure.reasurecraft.ReasureCraft;
import reasure.reasurecraft.block.*;
import reasure.reasurecraft.block.displaycase.DisplayCaseBlock;
import reasure.reasurecraft.block.metalpress.MetalPressBlock;
import reasure.reasurecraft.item.itemgroup.ModItemGroup;

import java.util.function.Supplier;

public class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, ReasureCraft.MOD_ID);

    public static final RegistryObject<ModOreBlock> SILVER_ORE = registerBlockItem("silver_ore", getOre(2));

    public static final RegistryObject<Block> SILVER_BLOCK = registerBlockItem("silver_block", getStorageBlock(2, MaterialColor.METAL, 5.0f, 6.0f));

    public static final RegistryObject<MetalPressBlock> METAL_PRESS = registerBlockItem("metal_press", () -> new MetalPressBlock(getMachineProperties(SoundType.ANVIL)));

    public static final RegistryObject<QuarryBlock> QUARRY = registerBlockItem("quarry", () -> new QuarryBlock(getMachineProperties()));

    public static final RegistryObject<DisplayCaseBlock> DISPLAY_CASE = registerBlockItem("display_case", () -> new DisplayCaseBlock(getMachineProperties()));

    public static final RegistryObject<FrameBlock> OBSIDIAN_FRAME = registerBlockItem("obsidian_frame", () ->
            new FrameBlock(AbstractBlock.Properties.of(Material.STONE, MaterialColor.COLOR_BLACK)
                    .requiresCorrectToolForDrops().harvestTool(ToolType.PICKAXE).harvestLevel(3).strength(5)));

    public static final RegistryObject<PeanutBlock> PEANUTS = register("peanuts", () -> new PeanutBlock(getCropProperties()));

    public static final RegistryObject<FlowingFluidBlock> RUBBER = register("rubber",
            () -> new RubberFlowingBlock(ModFluids.RUBBER, AbstractBlock.Properties.of(ModMaterial.RUBBER).noCollission().randomTicks().strength(100.0F).noDrops()));

    public static final RegistryObject<LighterBlock> LIGHTER_BLOCK = registerBlockItem("lighter_block", () -> new LighterBlock(AbstractBlock.Properties.of(Material.STONE, MaterialColor.FIRE)));

    public static final RegistryObject<FaucetBlock> FAUCET_BLOCK = registerBlockItem("faucet", () -> new FaucetBlock(AbstractBlock.Properties.of(Material.WOOD, MaterialColor.WOOD).strength(2.0F, 3.0F).sound(SoundType.WOOD)));

    private ModBlocks() {
    }

    private static Supplier<ModOreBlock> getOre(int harvestLevel) {
        return () -> new ModOreBlock(AbstractBlock.Properties.of(Material.STONE)
                .strength(3.0f, 3.0f)  // hardness and resistance
                .harvestLevel(harvestLevel));
    }

    private static Supplier<Block> getStorageBlock(int harvestLevel, MaterialColor color, float hardness, float resistance) {
        return () -> new Block(AbstractBlock.Properties.of(Material.METAL, color)
                .strength(hardness, resistance)
                .requiresCorrectToolForDrops()
                .harvestTool(ToolType.PICKAXE)
                .harvestLevel(harvestLevel)
                .sound(SoundType.METAL));
    }

    private static AbstractBlock.Properties getMachineProperties() {
        return getMachineProperties(SoundType.METAL);
    }

    private static AbstractBlock.Properties getMachineProperties(SoundType sound) {
        return AbstractBlock.Properties.of(Material.METAL)
                .strength(4, 20)
                .sound(sound);
    }

    private static AbstractBlock.Properties getCropProperties() {
        return AbstractBlock.Properties.of(Material.PLANT)
                .noCollission()
                .randomTicks()
                .instabreak()
                .sound(SoundType.CROP);
    }

    private static <T extends Block> RegistryObject<T> register(String name, Supplier<T> block) {
        return BLOCKS.register(name, block);
    }

    private static <T extends Block> RegistryObject<T> registerBlockItem(String name, Supplier<T> block) {
        return registerBlockItem(name, block, ModItemGroup.reasurecraft);
    }

    @SuppressWarnings("SameParameterValue")
    private static <T extends Block> RegistryObject<T> registerBlockItem(String name, Supplier<T> block, ItemGroup group) {
        RegistryObject<T> ret = register(name, block);
        ModItems.ITEMS.register(name, () -> new BlockItem(ret.get(), new Item.Properties().tab(group)));
        return ret;
    }

    public static void setRenderType() {
        RenderTypeLookup.setRenderLayer(RUBBER.get(), RenderType.solid());
        RenderTypeLookup.setRenderLayer(SILVER_ORE.get(), RenderType.cutoutMipped());
        RenderTypeLookup.setRenderLayer(DISPLAY_CASE.get(), RenderType.cutoutMipped());
        RenderTypeLookup.setRenderLayer(PEANUTS.get(), RenderType.cutout());
    }
}
