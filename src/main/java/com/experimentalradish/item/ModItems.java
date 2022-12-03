package com.experimentalradish.item;

import com.experimentalradish.RadMod;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModItems {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, RadMod.MOD_ID);

    public static final RegistryObject<Item> RADISH = ITEMS.register("radish",
            () -> new Item(new Item.Properties().tab(ModItemGroup.TAB_EXPERIMENTAL_RADISH)));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }

}
