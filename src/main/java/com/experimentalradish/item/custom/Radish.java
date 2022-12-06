package com.experimentalradish.item.custom;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

public class Radish extends Item {

    public Radish(Properties properties) {
        super(properties);
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, @Nonnull List<ITextComponent> tooltip, @Nonnull ITooltipFlag flagIn) {
        if (stack.getTag() != null && stack.getTag().keySet().size() > 0) {
            tooltip.add(ITextComponent.getTextComponentOrEmpty(RadishSeeds.createNBTTooltip(stack.getTag())));
        }
        super.addInformation(stack, worldIn, tooltip, flagIn);
    }
}
