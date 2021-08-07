package reasure.reasurecraft.init;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
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
import reasure.reasurecraft.block.DisplayCaseBlock;
import reasure.reasurecraft.block.ModOreBlock;
import reasure.reasurecraft.block.metalpress.MetalPressBlock;
import reasure.reasurecraft.block.QuarryBlock;
import reasure.reasurecraft.item.itemgroup.ModItemGroup;

import java.util.function.Supplier;

public class ModBlocks {

    public static final RegistryObject<ModOreBlock> SILVER_ORE = registerBlockItem("silver_ore", () -> getOre(2, "silver"));

    public static final RegistryObject<Block> SILVER_BLOCK = registerBlockItem("silver_block", () -> getStorageBlock(2));

    public static final RegistryObject<MetalPressBlock> METAL_PRESS = registerBlockItem("metal_press", () -> new MetalPressBlock(getMachineProperties(SoundType.ANVIL)));

    public static final RegistryObject<QuarryBlock> QUARRY = registerBlockItem("quarry", () -> new QuarryBlock(getMachineProperties()));

    public static final RegistryObject<DisplayCaseBlock> DISPLAY_CASE = registerBlockItem("display_case", () -> new DisplayCaseBlock(getMachineProperties()));

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

    private static <T extends Block> RegistryObject<T> registerNoItem(String name, Supplier<T> block) {
        return Registration.BLOCKS.register(name, block);
    }

    private static <T extends Block> RegistryObject<T> registerBlockItem(String name, Supplier<T> block) {
        return registerBlockItem(name, block, ModItemGroup.reasurecraft);
    }

    private static <T extends Block> RegistryObject<T> registerBlockItem(String name, Supplier<T> block, ItemGroup group) {
        RegistryObject<T> ret = registerNoItem(name, block);
        Registration.ITEMS.register(name, () -> new BlockItem(ret.get(), new Item.Properties().tab(group)));
        return ret;
    }

    public static void setRenderType() {
//        RenderTypeLookup.setRenderLayer(ModBlocks.QUARTZ_ORE.get(), RenderType.getCutout());
        Registration.BLOCKS.getEntries().stream()
                .map(RegistryObject::get)
                .forEach(
                        block -> RenderTypeLookup.setRenderLayer(block, RenderType.cutout())
                );
    }

    static void register() {
    }
}
