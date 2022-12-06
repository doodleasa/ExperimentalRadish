package com.experimentalradish.item.custom;

import com.experimentalradish.block.ModBlocks;
import com.experimentalradish.item.ModItems;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.potion.Effects;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.*;

public class RadishSeeds extends BlockItem {
    public static final String PATH = "experimentalradish.seeds.";

    private static final Random rand = new Random();

    public RadishSeeds(Properties properties) {
        super(ModBlocks.RADISH_CROP.get(), properties);
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        if (Screen.hasShiftDown() && stack.getTag() != null) {
            tooltip.add(ITextComponent.getTextComponentOrEmpty(createNBTTooltip(stack.getTag())));
        }
        super.addInformation(stack, worldIn, tooltip, flagIn);
    }

    private String createNBTTooltip(CompoundNBT nbt) {
        StringBuilder str = new StringBuilder();
        Set<String> keys = nbt.keySet();
        for (String key: keys) {
            str.append(key).append(": ").append(Objects.requireNonNull(nbt.get(key)).toString().trim()).append("\n");
        }
        return str.toString();
    }

    public static CompoundNBT initializeNBT() {
        ListNBT list = new ListNBT();
        CompoundNBT nbt = new CompoundNBT();
        nbt.put(PATH + "effect", list);
        nbt.putInt(PATH + "hunger", 4);
        nbt.putFloat(PATH + "saturation", 0.6f);
        nbt.putFloat(PATH + "growth", 1.0f);
        nbt.putFloat(PATH + "decay", 0.0f);
        nbt.putFloat(PATH + "yield", 1.0f);
        nbt.putBoolean(PATH + "fertile", true);
        return nbt;
    }

    public static CompoundNBT newEffect(String id, int potency, int duration) {
        CompoundNBT nbt = new CompoundNBT();
        nbt.putString(PATH + "effect.id", id);
        nbt.putInt(PATH + "effect.potency", potency);
        nbt.putInt(PATH + "effect.duration", duration);
        return nbt;
    }

    public static CompoundNBT randEffect() {
        List<String> effects = new ArrayList<>();

        effects.add("SPEED");
        effects.add("REGENERATION");


        int potency;
        int duration;

        int random = rand.nextInt(4);

        switch (random) {
            case 0: potency = 1; duration = 300; break;
            case 1: potency = 1; duration = 600; break;
            case 2: potency = 1; duration = 1200; break;
            case 3: potency = 2; duration = 300; break;
            default: potency = 2; duration = 600; break;
        }

        return newEffect(effects.get(rand.nextInt(effects.size())), potency, duration);
    }

    public static CompoundNBT mutate(ItemStack stack) {
        assert stack.getItem() == ModItems.RADISH_SEEDS.get();

        if(!stack.hasTag()) {
            stack.setTag(initializeNBT());
        }
        CompoundNBT nbt = stack.getTag();

        int hunger = Math.min(Math.max(nbt.getInt(PATH + "hunger") + (rand.nextInt(4) - 2), 0), 10); //+- up to 2 (max 10 min 0)
        float saturation = Math.min(Math.max(nbt.getFloat(PATH + "saturation") + (rand.nextFloat() / 2 - 0.25f), 0), 1); //+- up to 0.25 (max 1 min 0)
        float growth = Math.min(Math.max(nbt.getFloat(PATH + "growth") + (rand.nextFloat() - 0.5f), 0), 2); //+- up to 0.5 (max 2 min 0)
        float decay = Math.min(nbt.getFloat(PATH + "decay") + (rand.nextFloat() / 2) + 0.2f, 1); //+ 0.2 to 0.7 max 1
        float yield = Math.min(Math.max(nbt.getFloat(PATH + "yield") + (rand.nextFloat() - 0.5f), 0), 2); //+- up to 0.5 (max 2 min 0)

        nbt.putInt(PATH + "hunger", hunger);
        nbt.putFloat(PATH + "saturation", saturation);
        nbt.putFloat(PATH + "growth", growth);
        nbt.putFloat(PATH + "decay", decay);
        nbt.putFloat(PATH + "yield", yield);

        if (rand.nextInt(4) == 1)
        {

            ListNBT list = (ListNBT) nbt.get(PATH + "effect");
            CompoundNBT randEffect = randEffect();
            boolean valid = true;
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i).equals(randEffect))
                {
                    valid = false;
                }
            }
            if (valid) {
                list.add(randEffect());
            }
            nbt.put(PATH + "effect", list);
        }
        return nbt;
    }
}
