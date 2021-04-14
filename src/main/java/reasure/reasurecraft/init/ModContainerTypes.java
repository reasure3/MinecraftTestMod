package reasure.reasurecraft.init;

import net.minecraft.inventory.container.ContainerType;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.fml.RegistryObject;
import reasure.reasurecraft.gui.conatiner.DisplayCaseContainer;

public class ModContainerTypes {
    static void register() {}

    public static final RegistryObject<ContainerType<DisplayCaseContainer>> DISPLAY_CASE = Registration.CONTAINERS.register("display_case", () ->
            IForgeContainerType.create(DisplayCaseContainer::new));
}
