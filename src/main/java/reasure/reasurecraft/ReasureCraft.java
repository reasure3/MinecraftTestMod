package reasure.reasurecraft;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import reasure.reasurecraft.init.ModBlocks;
import reasure.reasurecraft.init.ModContainerTypes;
import reasure.reasurecraft.init.ModTileEntityTypes;
import reasure.reasurecraft.init.Registration;
import reasure.reasurecraft.world.OreGeneration;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(ReasureCraft.MOD_ID)
public class ReasureCraft {
    public static final String MOD_ID = "reasurecraft";
    // Directly reference a log4j logger.
    public static final Logger LOGGER = LogManager.getLogger();

    private static ReasureCraft instance;

    public ReasureCraft() {
        instance = this;

        // Register ModBlocks and ModItems
        Registration.register();

        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::clientSetup);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::commonSetup);

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SuppressWarnings("unused")
    public static ReasureCraft getInstance() {
        return instance;
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        event.enqueueWork(OreGeneration::registerOres);
    }

    private void clientSetup(final FMLClientSetupEvent event) {
        event.enqueueWork(() -> {
            ModBlocks.setRenderType();
            ModContainerTypes.registerScreens();
            ModTileEntityTypes.registerRenderers();
        });
    }
}
