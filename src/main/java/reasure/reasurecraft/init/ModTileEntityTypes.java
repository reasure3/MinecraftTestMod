package reasure.reasurecraft.init;

import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import reasure.reasurecraft.ReasureCraft;
import reasure.reasurecraft.block.displaycase.DisplayCaseRenderer;
import reasure.reasurecraft.block.displaycase.DisplayCaseTileEntity;
import reasure.reasurecraft.block.metalpress.MetalPressTileEntity;
import reasure.reasurecraft.block.tileentity.QuarryTileEntity;

import java.util.function.Supplier;

public class ModTileEntityTypes {
    public static final DeferredRegister<TileEntityType<?>> TILE_ENTITIES = DeferredRegister.create(ForgeRegistries.TILE_ENTITIES, ReasureCraft.MOD_ID);

    public static final RegistryObject<TileEntityType<MetalPressTileEntity>> METAL_PRESS = register("metal_press", MetalPressTileEntity::new, ModBlocks.METAL_PRESS);

    public static final RegistryObject<TileEntityType<QuarryTileEntity>> QUARRY = register("quarry", QuarryTileEntity::new, ModBlocks.QUARRY);

    public static final RegistryObject<TileEntityType<DisplayCaseTileEntity>> DISPLAY_CASE = register("display_case", DisplayCaseTileEntity::new, ModBlocks.DISPLAY_CASE);

    private static <T extends TileEntity> RegistryObject<TileEntityType<T>> register(String name, Supplier<T> factory, RegistryObject<? extends Block> block) {
        return TILE_ENTITIES.register(name, () -> {
            //noinspection ConstantConditions
            return TileEntityType.Builder.of(factory, block.get()).build(null);
        });
    }

    @OnlyIn(Dist.CLIENT)
    public static void registerRenderers() {
        ClientRegistry.bindTileEntityRenderer(DISPLAY_CASE.get(), DisplayCaseRenderer::new);
    }
}
