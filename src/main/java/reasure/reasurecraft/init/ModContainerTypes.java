package reasure.reasurecraft.init;

import net.minecraft.client.gui.ScreenManager;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.ContainerType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.network.IContainerFactory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import reasure.reasurecraft.ReasureCraft;
import reasure.reasurecraft.block.displaycase.DisplayCaseContainer;
import reasure.reasurecraft.block.displaycase.DisplayCaseScreen;
import reasure.reasurecraft.block.metalpress.MetalPressContainer;
import reasure.reasurecraft.block.metalpress.MetalPressScreen;

public class ModContainerTypes {
    public static final DeferredRegister<ContainerType<?>> CONTAINERS = DeferredRegister.create(ForgeRegistries.CONTAINERS, ReasureCraft.MOD_ID);

    public static final RegistryObject<ContainerType<MetalPressContainer>> METAL_PRESS = register("metal_press", MetalPressContainer::new);
    public static final RegistryObject<ContainerType<DisplayCaseContainer>> DISPLAY_CASE = register("display_case", DisplayCaseContainer::new);

    @OnlyIn(Dist.CLIENT)
    public static void registerScreens() {
        ScreenManager.register(DISPLAY_CASE.get(), DisplayCaseScreen::new);
        ScreenManager.register(METAL_PRESS.get(), MetalPressScreen::new);
    }

    private static <T extends Container> RegistryObject<ContainerType<T>> register(String name, IContainerFactory<T> factory) {
        return CONTAINERS.register(name, () -> IForgeContainerType.create((factory)));
    }
}
