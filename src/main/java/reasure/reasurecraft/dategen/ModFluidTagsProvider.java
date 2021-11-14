package reasure.reasurecraft.dategen;

import net.minecraft.data.DataGenerator;
import net.minecraft.data.FluidTagsProvider;
import net.minecraft.tags.FluidTags;
import net.minecraftforge.common.data.ExistingFileHelper;
import reasure.reasurecraft.ReasureCraft;
import reasure.reasurecraft.init.ModFluids;
import reasure.reasurecraft.init.ModTags;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class ModFluidTagsProvider extends FluidTagsProvider {
    public ModFluidTagsProvider(DataGenerator generator, @Nullable ExistingFileHelper existingFileHelper) {
        super(generator, ReasureCraft.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags() {
        tag(ModTags.Fluids.RUBBER).add(ModFluids.RUBBER.get(), ModFluids.RUBBER_FLOWING.get());
        tag(FluidTags.WATER).addTag(ModTags.Fluids.RUBBER);
    }

    @Nonnull
    @Override
    public String getName() {
        return "Fluid Tag Provide: r" + ReasureCraft.MOD_ID;
    }
}
