package reasure.reasurecraft.init;

import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.fml.RegistryObject;
import reasure.reasurecraft.block.tileentity.DisplayCaseTileEntity;
import reasure.reasurecraft.block.metalpress.MetalPressTileEntity;
import reasure.reasurecraft.block.tileentity.QuarryTileEntity;

import java.util.function.Supplier;

public class ModTileEntityTypes {
    public static final RegistryObject<TileEntityType<MetalPressTileEntity>> METAL_PRESS = register("metal_press", MetalPressTileEntity::new, ModBlocks.METAL_PRESS);

    public static final RegistryObject<TileEntityType<QuarryTileEntity>> QUARRY = register("quarry", QuarryTileEntity::new, ModBlocks.QUARRY);

    public static final RegistryObject<TileEntityType<DisplayCaseTileEntity>> DISPLAY_CASE = register("display_case", DisplayCaseTileEntity::new, ModBlocks.DISPLAY_CASE);

    static void register() {
    }

    private static <T extends TileEntity> RegistryObject<TileEntityType<T>> register(String name, Supplier<T> factory, RegistryObject<? extends Block> block) {
        return Registration.TILE_ENTITIES.register(name, () -> {
            //noinspection ConstantConditions
            return TileEntityType.Builder.of(factory, block.get()).build(null);
        });
    }
}
