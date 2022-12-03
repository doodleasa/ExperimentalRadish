package com.experimentalradish.item;

import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;

public class ModItemGroup {

    public static final ItemGroup TAB_EXPERIMENTAL_RADISH = new ItemGroup("experimentalRadishModTab") {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(ModItems.RADISH.get());
        }
    };
}
