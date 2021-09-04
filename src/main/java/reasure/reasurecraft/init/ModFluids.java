package reasure.reasurecraft.init;

import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.fluid.FlowingFluid;
import net.minecraft.fluid.Fluid;
import net.minecraftforge.fml.RegistryObject;
import reasure.reasurecraft.fluid.RubberFluid;

import java.util.function.Supplier;

public class ModFluids {
    public static final RegistryObject<FlowingFluid> RUBBER = register("rubber", RubberFluid.Source::new);
    public static final RegistryObject<FlowingFluid> RUBBER_FLOWING = register("rubber_flowing", RubberFluid.Flowing::new);


    private ModFluids() {
    }

    private static <T extends Fluid> RegistryObject<T> register(String name, Supplier<T> item) {
        return Registration.FLUIDS.register(name, item);
    }

    static void register() {
    }

    public static void setRenderType() {
        RenderTypeLookup.setRenderLayer(ModFluids.RUBBER.get(), RenderType.solid());
        RenderTypeLookup.setRenderLayer(ModFluids.RUBBER_FLOWING.get(), RenderType.solid());
    }
}
