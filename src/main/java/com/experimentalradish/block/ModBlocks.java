package com.experimentalradish.block;

import com.experimentalradish.RadMod;
import com.experimentalradish.block.custom.RadiationBlasterBlock;
import com.experimentalradish.block.custom.RadishCrop;
import com.experimentalradish.item.ModItemGroup;
import com.experimentalradish.item.ModItems;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.material.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Supplier;

public class ModBlocks {

    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, RadMod.MOD_ID);

    public static final RegistryObject<Block> RADISH_BLOCK = registerBlock("radish_block",
            () -> new Block(AbstractBlock.Properties.create(Material.EARTH)));


    public static final RegistryObject<Block> RADIATION_BLASTER = registerBlock("radiation_blaster",
            () -> new RadiationBlasterBlock(AbstractBlock.Properties.create(Material.IRON)));

    public static final RegistryObject<Block> RADISH_CROP = BLOCKS.register("radish_crop",
            () -> new RadishCrop(AbstractBlock.Properties.from(Blocks.WHEAT)));

    private static <T extends Block>RegistryObject<T> registerBlock(String name, Supplier<T> block) {
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn);
        return toReturn;
    }

    private static <T extends Block> void registerBlockItem(String name, RegistryObject<T> block) {
        ModItems.ITEMS.register(name, () -> new BlockItem(block.get(),
                new Item.Properties().group(ModItemGroup.TAB_EXPERIMENTAL_RADISH)));


    }

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}
