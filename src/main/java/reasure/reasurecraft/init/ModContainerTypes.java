package reasure.reasurecraft.init;

import net.minecraft.client.gui.ScreenManager;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.ContainerType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.network.IContainerFactory;
import reasure.reasurecraft.gui.conatiner.DisplayCaseContainer;
import reasure.reasurecraft.block.metalpress.MetalPressContainer;
import reasure.reasurecraft.gui.screen.DisplayCaseScreen;
import reasure.reasurecraft.block.metalpress.MetalPressScreen;

public class ModContainerTypes {
    public static final RegistryObject<ContainerType<MetalPressContainer>> METAL_PRESS = register("metal_press", MetalPressContainer::new);
    public static final RegistryObject<ContainerType<DisplayCaseContainer>> DISPLAY_CASE = register("display_case", DisplayCaseContainer::new);

    private ModContainerTypes() {}

    static void register() {}

    @OnlyIn(Dist.CLIENT)
    public static void registerScreens(FMLClientSetupEvent event) {
        ScreenManager.register(DISPLAY_CASE.get(), DisplayCaseScreen::new);
        ScreenManager.register(METAL_PRESS.get(), MetalPressScreen::new);
    }

    private static <T extends Container> RegistryObject<ContainerType<T>> register(String name, IContainerFactory<T> factory) {
        return Registration.CONTAINERS.register(name, () -> IForgeContainerType.create((factory)));
    }
}
