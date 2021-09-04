package reasure.reasurecraft.dategen.client;

import net.minecraft.block.Block;
import net.minecraft.data.DataGenerator;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import reasure.reasurecraft.ReasureCraft;
import reasure.reasurecraft.init.ModBlocks;
import reasure.reasurecraft.init.ModItems;
import reasure.reasurecraft.util.Metals;

import java.util.Objects;

public class ModItemModelProvider extends ItemModelProvider {
    ModelFile itemGenerated = getExistingFile(mcLoc("item/generated")); //default item
    ModelFile itemHandheld = getExistingFile(mcLoc("item/handheld")); //handheld item

    public ModItemModelProvider(DataGenerator generator, ExistingFileHelper existingFileHelper) {
        super(generator, ReasureCraft.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        metals(Metals.METALS_SILVER);

        withExistingParent(ModBlocks.METAL_PRESS.get());
        withExistingParent(ModBlocks.QUARRY.get());
        withExistingParent(ModBlocks.DISPLAY_CASE.get());
        withExistingParent(ModBlocks.OBSIDIAN_FRAME.get());
        withExistingParent(ModBlocks.LIGHTER_BLOCK.get());
        withExistingParent(ModBlocks.FAUCET_BLOCK.get());

        builder(itemGenerated, ModItems.POISON_APPLE.get());
        builder(itemGenerated, ModItems.SPECIAL_COAL.get());
        builder(itemGenerated, ModItems.BLAZE_INGOT.get());
        builder(itemGenerated, ModItems.BLAZE_AND_STEEL.get());
        builder(itemGenerated, ModItems.PEANUT.get());
        builder(itemGenerated, ModItems.RUBBER_BUCKET.get());
        builder(itemGenerated, ModItems.OBSIDIAN_STICK.get());
        builder(itemGenerated, ModItems.ENDER_STICK.get());
        builder(itemHandheld, ModItems.TELEPORT_STAFF.get());
        builder(itemGenerated, ModItems.TORCH_PLACER.get());
    }

    private void builder(ModelFile model, Item item) {
        builder(model, name(item));
    }

    private void builder(ModelFile model, String name) {
        getBuilder(name).parent(model).texture("layer0", "item/" + name);
    }

    private void withExistingParent(Block block) {
        String name = name(block);
        withExistingParent(name, modLoc("block/" + name));
    }

    private void metals(Metals metal) {
        String name = metal.name;
        if (metal.ingot != null) builder(itemGenerated, name + "_ingot");
        if (metal.nugget != null) builder(itemGenerated, name + "_nugget");
        if (metal.dust != null) builder(itemGenerated, name + "_dust");
        if (metal.chunks != null) builder(itemGenerated, name + "_chunk");
        if (metal.sword != null) builder(itemHandheld, name + "_sword");
        if (metal.shovel != null) builder(itemHandheld, name + "_shovel");
        if (metal.pickaxe != null) builder(itemHandheld, name + "_pickaxe");
        if (metal.axe != null) builder(itemHandheld, name + "_axe");
        if (metal.hoe != null) builder(itemHandheld, name + "_hoe");
        if (metal.helmet != null) builder(itemHandheld, name + "_helmet");
        if (metal.chestplate != null) builder(itemHandheld, name + "_chestplate");
        if (metal.leggings != null) builder(itemHandheld, name + "_leggings");
        if (metal.boots != null) builder(itemHandheld, name + "_boots");
        if (metal.horse_armor != null) builder(itemGenerated, name + "_horse_armor");

        if (metal.ore != null) withExistingParent(name + "_ore", modLoc("block/" + name + "_ore"));
        if (metal.block != null) withExistingParent(name + "_block", modLoc("block/" + name + "_block"));
    }

    private String name(Item item) {
        return Objects.requireNonNull(item.getRegistryName()).getPath();
    }

    private String name(Block block) {
        return Objects.requireNonNull(block.getRegistryName()).getPath();
    }
}
