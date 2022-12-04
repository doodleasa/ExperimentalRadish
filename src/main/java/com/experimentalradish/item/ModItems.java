package com.experimentalradish.item;

import com.experimentalradish.RadMod;
import com.experimentalradish.block.ModBlocks;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Food;
import net.minecraft.item.Item;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Supplier;

public class ModItems {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, RadMod.MOD_ID);

    public static final RegistryObject<Item> RADISH = ITEMS.register("radish",
            () -> new BlockItem(ModBlocks.RADISH_CROP.get(), new Item.Properties()
                    .group(ModItemGroup.TAB_EXPERIMENTAL_RADISH)
                    .food(new Food.Builder().hunger(-1).fastToEat().meat().setAlwaysEdible()
                            .effect(() -> new EffectInstance(Effects.SPEED.getEffect(), 200, 1), 0.8f)
                            .effect(() -> new EffectInstance(Effects.HUNGER.getEffect(), 200, 2), 0.5f)
                            .build())));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }

}
