package reasure.reasurecraft.dategen.client;

import net.minecraft.block.Block;
import net.minecraft.data.DataGenerator;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.generators.BlockModelBuilder;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.common.data.ExistingFileHelper;
import reasure.reasurecraft.ReasureCraft;
import reasure.reasurecraft.block.ModOreBlock;
import reasure.reasurecraft.init.ModBlocks;

import java.util.Objects;

public class ModBlockStateProvider extends BlockStateProvider {
    public ModBlockStateProvider(DataGenerator gen, ExistingFileHelper exFileHelper) {
        super(gen, ReasureCraft.MOD_ID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        simpleBlock(ModBlocks.SILVER_BLOCK.get());
        simpleBlock(ModBlocks.QUARRY.get());

        models().getBuilder("ore").parent(models().getExistingFile(mcLoc("block/block")))
                .element().cube("#stone").end()
                .element().cube("#ore").end();

        models().getBuilder("basic_machine").parent(models().getExistingFile(mcLoc("block/block")))
                .texture("particle", modLoc("block/basic_machine_side"))
                .element().allFaces((direction, faceBuilder) -> {
            faceBuilder.cullface(direction);
            if (direction == Direction.NORTH) faceBuilder.texture("#front");
            else if (direction == Direction.UP) faceBuilder.texture("#top");
            else if (direction == Direction.DOWN) faceBuilder.texture("#bottom");
            else faceBuilder.texture("#side");
        }).end();

        oreBlock(ModBlocks.SILVER_ORE.get(), "stone");

        BasicMachine(ModBlocks.METAL_PRESS.get());

        ExistingModel(ModBlocks.DISPLAY_CASE.get());
    }

    private void oreBlock(ModOreBlock block, String stone) {
        ResourceLocation oreLoc = modLoc("block/" + Objects.requireNonNull(block.getRegistryName()).getPath());

        BlockModelBuilder modelBuilder = models().getBuilder(Objects.requireNonNull(block.getRegistryName()).getPath())
                .parent(models().getExistingFile(modLoc("block/ore")))
                .texture("stone", mcLoc("block/" + stone))
                .texture("ore", oreLoc)
                .texture("particle", oreLoc);

        getVariantBuilder(block).partialState().setModels(new ConfiguredModel(modelBuilder));
    }

    private void BasicMachine(Block block) {
        BlockModelBuilder modelBuilder = models().getBuilder(Objects.requireNonNull(block.getRegistryName()).getPath())
                .parent(models().getExistingFile(modLoc("block/basic_machine")))
                .texture("front", modLoc("block/" + block.getRegistryName().getPath() + "_front_on"))
                .texture("top", modLoc("block/basic_machine_top"))
                .texture("bottom", modLoc("block/basic_machine_bottom"))
                .texture("side", modLoc("block/basic_machine_side"));

        getVariantBuilder(block).partialState().setModels(new ConfiguredModel(modelBuilder));
    }

    private void ExistingModel(Block block) {
        getVariantBuilder(block).partialState().setModels(new ConfiguredModel(models().getExistingFile(block.getRegistryName())));
    }
}
