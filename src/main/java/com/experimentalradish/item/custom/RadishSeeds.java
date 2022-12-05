package com.experimentalradish.item.custom;

import com.experimentalradish.block.ModBlocks;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.nio.file.Path;
import java.util.List;

public class RadishSeeds extends BlockItem {
    public static final String PATH = "experimentalradish.seeds.";

    public RadishSeeds(Properties properties) {
        super(ModBlocks.RADISH_CROP.get(), properties);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
        ItemStack heldItem = playerIn.getHeldItem(handIn);
        if (!heldItem.hasTag()) {
            CompoundNBT nbt = initializeNBT();
            heldItem.setTag(nbt);
        }
        return super.onItemRightClick(worldIn, playerIn, handIn);
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        if (Screen.hasShiftDown() && stack.getTag() != null && stack.getTag().contains(PATH + "counter")) {
            tooltip.add(ITextComponent.getTextComponentOrEmpty(String.valueOf(stack.getTag().get(PATH + "counter"))));
        }

        super.addInformation(stack, worldIn, tooltip, flagIn);
    }

    private CompoundNBT initializeNBT() {
        ListNBT list = new ListNBT();
        list.add(newEffect("SPEED", 1.0f, 10));
        CompoundNBT nbt = new CompoundNBT();
        nbt.put(PATH + "effect", list);
        nbt.putInt(PATH + "hunger", 4);
        nbt.putFloat(PATH + "saturation", 0.6f);
        nbt.putFloat(PATH + "growth", 1.0f);
        nbt.putFloat(PATH + "decay", 0.0f);
        nbt.putFloat(PATH + "yield", 1.0f);
        return nbt;
    }

    private CompoundNBT newEffect(String id, float potency, int duration) {
        CompoundNBT nbt = new CompoundNBT();
        nbt.putString(PATH + "effect.id", id);
        nbt.putFloat(PATH + "effect.potency", potency);
        nbt.putInt(PATH + "effect.duration", duration);
        return nbt;
    }

}
