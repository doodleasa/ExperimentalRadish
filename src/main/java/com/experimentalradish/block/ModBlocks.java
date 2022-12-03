package com.experimentalradish.block;

import com.experimentalradish.RadMod;
import com.experimentalradish.item.ModItemGroup;
import com.experimentalradish.item.ModItems;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
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

    public static final RegistryObject<Block> RADISH_BLOCK = registerBlock("radish_block", () -> new Block(AbstractBlock.Properties.of(Material.STONE)));

    private static <T extends Block>RegistryObject<T> registerBlock(String name, Supplier<T> block) {
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn);
        return toReturn;
    }

    private static <T extends Block> void registerBlockItem(String name, RegistryObject<T> block) {
        ModItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties().tab(ModItemGroup.TAB_EXPERIMENTAL_RADISH)));


    }

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}
