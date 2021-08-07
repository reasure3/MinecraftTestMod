package reasure.reasurecraft.dategen.client;

import net.minecraft.block.Block;
import net.minecraft.data.DataGenerator;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.generators.BlockModelBuilder;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.client.model.generators.ModelBuilder;
import net.minecraftforge.common.data.ExistingFileHelper;
import reasure.reasurecraft.ReasureCraft;
import reasure.reasurecraft.block.ModBlockProperty;
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

        oreBlock(ModBlocks.SILVER_ORE.get(), "stone");

        BasicMachine(ModBlocks.METAL_PRESS.get());

        ExistingModel(ModBlocks.DISPLAY_CASE.get());
    }

    private void oreBlock(ModOreBlock block, String stone) {
        ResourceLocation oreLoc = modLoc("block/" + name(block));

        BlockModelBuilder modelBuilder = models().getBuilder(name(block))
                .parent(models().getExistingFile(modLoc("block/ore")))
                .texture("stone", mcLoc("block/" + stone))
                .texture("ore", oreLoc)
                .texture("particle", oreLoc);

        getVariantBuilder(block).partialState().setModels(new ConfiguredModel(modelBuilder));
    }

    private void BasicMachine(Block block) {
        final String block_name = name(block);

        ResourceLocation side = modLoc("block/basic_machine_side");
        ResourceLocation bottom = modLoc("block/basic_machine_bottom");
        ResourceLocation top = modLoc("block/basic_machine_top");
        ResourceLocation front_on = modLoc("block/" + block_name + "_front_on");
        ResourceLocation front_off = modLoc("block/" + block_name + "_front_off");

        ModelBuilder<BlockModelBuilder> on = models().orientableWithBottom(block_name + "_on", side, front_on, bottom, top);
        ModelBuilder<BlockModelBuilder> off = models().orientableWithBottom(block_name, side, front_off, bottom, top);

        getVariantBuilder(block)
                .forAllStates(state -> ConfiguredModel.builder()
                        .modelFile(state.getValue(ModBlockProperty.ON) ? on : off)
                        .rotationY(((int) state.getValue(BlockStateProperties.HORIZONTAL_FACING).toYRot() + 180) % 360)
                        .build()
                );
    }

    private void ExistingModel(Block block) {
        getVariantBuilder(block).partialState().setModels(new ConfiguredModel(models().getExistingFile(block.getRegistryName())));
    }

    private String name(Block block) {
        return Objects.requireNonNull(block.getRegistryName()).getPath();
    }
}
