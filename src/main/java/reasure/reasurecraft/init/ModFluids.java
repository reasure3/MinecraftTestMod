package reasure.reasurecraft.init;

import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.fluid.FlowingFluid;
import net.minecraft.fluid.Fluid;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import reasure.reasurecraft.ReasureCraft;
import reasure.reasurecraft.fluid.RubberFluid;

public class ModFluids {
    public static final DeferredRegister<Fluid> FLUIDS = DeferredRegister.create(ForgeRegistries.FLUIDS, ReasureCraft.MOD_ID);

    public static final RegistryObject<FlowingFluid> RUBBER = FLUIDS.register("rubber", RubberFluid.Source::new);
    public static final RegistryObject<FlowingFluid> RUBBER_FLOWING = FLUIDS.register("rubber_flowing", RubberFluid.Flowing::new);

    public static void setRenderType() {
        RenderTypeLookup.setRenderLayer(RUBBER.get(), RenderType.solid());
        RenderTypeLookup.setRenderLayer(RUBBER_FLOWING.get(), RenderType.solid());
    }
}
