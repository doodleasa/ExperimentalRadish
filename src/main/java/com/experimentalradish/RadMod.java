package com.experimentalradish;

import com.experimentalradish.block.ModBlocks;
import com.experimentalradish.container.ModContainers;
import com.experimentalradish.item.ModItems;
import com.experimentalradish.screen.RadiationBlasterScreen;
import com.experimentalradish.tileentity.ModTileEntities;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.client.gui.ScreenManager;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(RadMod.MOD_ID)
public class RadMod {

    public static final String MOD_ID = "experimentalradish";

    private static final Logger LOGGER = LogManager.getLogger();

    public RadMod() {
        IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();

        ModItems.register(eventBus);
        ModBlocks.register(eventBus);
        ModContainers.register(eventBus);
        ModTileEntities.register(eventBus);

        eventBus.addListener(this::doClientStuff);

        MinecraftForge.EVENT_BUS.register(this);
    }

    private void doClientStuff(final FMLClientSetupEvent event) {
        event.enqueueWork(() -> {
            RenderTypeLookup.setRenderLayer(ModBlocks.RADISH_CROP.get(), RenderType.getCutout());
            RenderTypeLookup.setRenderLayer(ModBlocks.RADISH_BLOCK.get(), RenderType.getCutout());

            ScreenManager.registerFactory(ModContainers.RADIATION_BLASTER_CONTAINER.get(), RadiationBlasterScreen::new);
        });
    }

}
