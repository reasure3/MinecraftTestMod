package reasure.reasurecraft.init;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.item.*;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.fml.RegistryObject;
import reasure.reasurecraft.block.FrameBlock;
import reasure.reasurecraft.block.ModOreBlock;
import reasure.reasurecraft.block.PeanutBlock;
import reasure.reasurecraft.block.QuarryBlock;
import reasure.reasurecraft.block.displaycase.DisplayCaseBlock;
import reasure.reasurecraft.block.metalpress.MetalPressBlock;
import reasure.reasurecraft.item.itemgroup.ModItemGroup;

import java.util.function.Supplier;

public class ModBlocks {

    public static final RegistryObject<ModOreBlock> SILVER_ORE = registerBlockItem("silver_ore", () -> getOre(2, "silver"));

    public static final RegistryObject<Block> SILVER_BLOCK = registerBlockItem("silver_block", () -> getStorageBlock(2));

    public static final RegistryObject<MetalPressBlock> METAL_PRESS = registerBlockItem("metal_press", () -> new MetalPressBlock(getMachineProperties(SoundType.ANVIL)));

    public static final RegistryObject<QuarryBlock> QUARRY = registerBlockItem("quarry", () -> new QuarryBlock(getMachineProperties()));

    public static final RegistryObject<DisplayCaseBlock> DISPLAY_CASE = registerBlockItem("display_case", () -> new DisplayCaseBlock(getMachineProperties()));

    public static final RegistryObject<FrameBlock> OBSIDIAN_FRAME = registerBlockItem("obsidian_frame", () ->
            new FrameBlock(AbstractBlock.Properties.of(Material.STONE, MaterialColor.COLOR_BLACK)
                    .requiresCorrectToolForDrops().harvestTool(ToolType.PICKAXE).harvestLevel(3).strength(5)));

    public static final RegistryObject<PeanutBlock> PEANUTS = register("peanuts", () -> new PeanutBlock(getCropProperties()));

    private static ModOreBlock getOre(int harvestLevel, String ore) {
        return new ModOreBlock(AbstractBlock.Properties.of(Material.STONE)
                .strength(4, 10)  // hardness and resistance
                .harvestLevel(harvestLevel));
    }

    private static Block getStorageBlock(int harvestLevel) {
        return new Block(AbstractBlock.Properties.of(Material.METAL, MaterialColor.METAL)
                .strength(3.0f, 6.0f)  // hardness and resistance
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
        return Registration.BLOCKS.register(name, block);
    }

    private static <T extends Block> RegistryObject<T> registerBlockItem(String name, Supplier<T> block) {
        return registerBlockItem(name, block, ModItemGroup.reasurecraft);
    }

    @SuppressWarnings("SameParameterValue")
    private static <T extends Block> RegistryObject<T> registerBlockItem(String name, Supplier<T> block, ItemGroup group) {
        RegistryObject<T> ret = register(name, block);
        Registration.ITEMS.register(name, () -> new BlockItem(ret.get(), new Item.Properties().tab(group)));
        return ret;
    }

    private static <T extends Block> RegistryObject<T> registerCropItemNoSeed(String block_name, Supplier<T> block, Food food, String item_name) {
        RegistryObject<T> ret = register(block_name, block);
        Registration.ITEMS.register(item_name, () -> new BlockNamedItem(ret.get(), new Item.Properties().food(food).tab(ModItemGroup.reasurecraft)));
        return ret;
    }

    @SuppressWarnings("CommentedOutCode")
    public static void setRenderType() {
        RenderTypeLookup.setRenderLayer(ModBlocks.SILVER_ORE.get(), RenderType.cutoutMipped());
        RenderTypeLookup.setRenderLayer(ModBlocks.DISPLAY_CASE.get(), RenderType.cutoutMipped());
        RenderTypeLookup.setRenderLayer(ModBlocks.PEANUTS.get(), RenderType.cutout());
        /*Registration.BLOCKS.getEntries().stream()
                .map(RegistryObject::get)
                .forEach(
                        block -> RenderTypeLookup.setRenderLayer(block, RenderType.cutoutMipped())
                );*/
    }

    static void register() {
    }
}
