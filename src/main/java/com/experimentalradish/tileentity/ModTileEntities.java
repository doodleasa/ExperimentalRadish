package com.experimentalradish.tileentity;

import com.experimentalradish.RadMod;
import com.experimentalradish.block.ModBlocks;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModTileEntities {

    public static DeferredRegister<TileEntityType<?>> TILE_ENTITIES = DeferredRegister.create(ForgeRegistries.TILE_ENTITIES, RadMod.MOD_ID);

    public static RegistryObject<TileEntityType<RadiationBlasterTile>> RADIATION_BLASTER_TILE = TILE_ENTITIES.register("radiation_blaster_tile", () -> TileEntityType.Builder.create(RadiationBlasterTile::new, ModBlocks.RADIATION_BLASTER.get()).build(null));

    public static void register(IEventBus eventbus) {
        TILE_ENTITIES.register(eventbus);
    }
}
