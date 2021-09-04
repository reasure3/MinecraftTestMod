package reasure.reasurecraft.dategen.client;

import net.minecraft.block.Block;
import net.minecraft.block.CropsBlock;
import net.minecraft.data.DataGenerator;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.generators.*;
import net.minecraftforge.common.data.ExistingFileHelper;
import reasure.reasurecraft.ReasureCraft;
import reasure.reasurecraft.block.FrameBlock;
import reasure.reasurecraft.block.LighterBlock;
import reasure.reasurecraft.block.ModBlockStateProperties;
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

        getVariantBuilder(ModBlocks.RUBBER.get()).partialState()
                .setModels(new ConfiguredModel(models().getBuilder("rubber").texture("particle", "reasurecraft:block/rubber_still")));

        models().getBuilder("ore").parent(models().getExistingFile(mcLoc("block/block")))
                .element().cube("#stone").end()
                .element().cube("#ore").end();

        oreBlock(ModBlocks.SILVER_ORE.get(), "stone");

        BasicMachineBlock(ModBlocks.METAL_PRESS.get());

        ExistingModel(ModBlocks.DISPLAY_CASE.get());

        FrameBlock(ModBlocks.OBSIDIAN_FRAME.get());

        CropBlock(ModBlocks.PEANUTS.get(), 4, new int[]{0, 0, 1, 1, 2, 2, 2, 3});

        lighterBlock(ModBlocks.LIGHTER_BLOCK.get());

        horizontalBlock(ModBlocks.FAUCET_BLOCK.get(), models().getExistingFile(ModBlocks.FAUCET_BLOCK.getId()));
    }

    private void lighterBlock(LighterBlock block) {
        ModelFile default_model = cubeAll(block);
        ModelFile on = models().cubeAll(name(block) + "_on", modLoc("block/" + name(block) + "_on"));
        ModelFile off = models().cubeAll(name(block) + "_off", modLoc("block/" + name(block) + "_off"));

        getVariantBuilder(block).forAllStates(state -> {
            if (!state.getValue(ModBlockStateProperties.REDSTONE_REACTION)) {
                return ConfiguredModel.builder().modelFile(default_model).build();
            }
            return ConfiguredModel.builder().modelFile(state.getValue(ModBlockStateProperties.ON) ? on : off).build();
        });
    }

    private void oreBlock(ModOreBlock block, String stone) {
        ResourceLocation oreLoc = modLoc("block/" + name(block));

        ModelBuilder<BlockModelBuilder> modelBuilder = models().getBuilder(name(block))
                .parent(models().getExistingFile(modLoc("block/ore")))
                .texture("stone", mcLoc("block/" + stone))
                .texture("ore", oreLoc)
                .texture("particle", oreLoc);

        getVariantBuilder(block).partialState().setModels(new ConfiguredModel(modelBuilder));
    }

    private void BasicMachineBlock(Block block) {
        final String block_name = name(block);

        ResourceLocation side = modLoc("block/basic_machine_side");
        ResourceLocation bottom = modLoc("block/basic_machine_bottom");
        ResourceLocation top = modLoc("block/basic_machine_top");
        ResourceLocation front_on = modLoc("block/" + block_name + "_front_on");
        ResourceLocation front_off = modLoc("block/" + block_name + "_front_off");

        ModelBuilder<BlockModelBuilder> on = models().orientableWithBottom(block_name + "_on", side, front_on, bottom, top);
        ModelBuilder<BlockModelBuilder> off = models().orientableWithBottom(block_name, side, front_off, bottom, top);

        getVariantBuilder(block).forAllStates(state -> ConfiguredModel.builder()
                .modelFile(state.getValue(ModBlockStateProperties.ON) ? on : off)
                .rotationY(((int)state.getValue(BlockStateProperties.HORIZONTAL_FACING).toYRot() + 180) % 360)
                .build());
    }

    private void FrameBlock(FrameBlock block) {
        ModelBuilder<BlockModelBuilder> modelBuilder = models().getBuilder(name(block))
                .parent(models().getExistingFile(modLoc("block/frame")))
                .texture("0", modLoc("block/" + name(block)));

        getVariantBuilder(block).partialState().setModels(new ConfiguredModel(modelBuilder));
    }

    private void CropBlock(CropsBlock block, int size, int[] stages) {
        //noinspection unchecked
        ModelBuilder<BlockModelBuilder>[] modelBuilders = new ModelBuilder[size];

        for (int i = 0; i < size; i++) {
            String name = "block/" + name(block) + "_stage" + i;
            modelBuilders[i] = models().crop(name, modLoc(name));
        }

        getVariantBuilder(block).forAllStates(state -> ConfiguredModel.builder()
                .modelFile(modelBuilders[stages[state.getValue(BlockStateProperties.AGE_7)]])
                .build());
    }

    private void ExistingModel(Block block) {
        getVariantBuilder(block).partialState().setModels(new ConfiguredModel(models().getExistingFile(block.getRegistryName())));
    }

    private String name(Block block) {
        return Objects.requireNonNull(block.getRegistryName()).getPath();
    }
}
