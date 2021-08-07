package reasure.reasurecraft.dategen.client;

import net.minecraft.data.DataGenerator;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import reasure.reasurecraft.ReasureCraft;
import reasure.reasurecraft.util.Metals;

public class ModItemModelProvider extends ItemModelProvider {
    ModelFile itemGenerated = getExistingFile(mcLoc("item/generated")); //default item
    ModelFile itemHandheld = getExistingFile(mcLoc("item/handheld")); //handheld item
    public ModItemModelProvider(DataGenerator generator, ExistingFileHelper existingFileHelper) {
        super(generator, ReasureCraft.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        metals(Metals.METALS_SILVER);

        withExistingParent("metal_press", modLoc("block/metal_press"));
        withExistingParent("quarry", modLoc("block/quarry"));
        withExistingParent("display_case", modLoc("block/display_case"));

        builder(itemGenerated, "poison_apple");
    }

    private void builder(ModelFile model, String name) {
        getBuilder(name).parent(model).texture("layer0", "item/" + name);
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
        if (metal.horse_armor != null) builder(itemHandheld, name + "_horse_armor");

        if (metal.ore != null) withExistingParent(name + "_ore", modLoc("block/" + name + "_ore"));
        if (metal.block != null) withExistingParent(name + "_block", modLoc("block/" + name + "_block"));
    }
}
