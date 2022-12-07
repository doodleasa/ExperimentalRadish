package com.experimentalradish.item.custom;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Food;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

public class Radish extends Item implements RadishItems {

    protected CompoundNBT mutations;

    public Radish(Properties properties) {
        super(properties);
    }

    // If this solution doesn't work, override onItemUseFinish()
    @Override
    @Nullable
    public Food getFood() {
        if (this.mutations != null && !this.mutations.isEmpty()) {
            return buildEffects().meat().fastToEat()
                    .hunger(RadishUtil.getHunger(mutations))
                    .saturation(RadishUtil.getSaturation(mutations)).build();
        }
        return super.getFood();
    }

    private Food.Builder buildEffects() {
        EffectInstance[] effectList = RadishUtil.getEffects(mutations);
        Food.Builder builder = new Food.Builder();
        for (EffectInstance effect : effectList) {
            builder.effect(() -> effect, 1.0f);
        }
        if (effectList.length > 0) {
            builder.setAlwaysEdible();
        }
        return builder;
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, @Nonnull List<ITextComponent> tooltip, @Nonnull ITooltipFlag flagIn) {
        if (stack.getTag() != null && stack.getTag().keySet().size() > 0) {
            tooltip.add(ITextComponent.getTextComponentOrEmpty(RadishSeeds.createNBTTooltip(stack.getTag())));
        }
        super.addInformation(stack, worldIn, tooltip, flagIn);
    }

    @Override
    public void setMutations(@Nonnull CompoundNBT mutations) {
        this.mutations = mutations;
        System.out.println("Added mutations to Radish: " + this.mutations + " " + mutations);
    }

    @Override
    @Nullable
    public CompoundNBT getMutations() {
        return this.mutations;
    }
}
