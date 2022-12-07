package com.experimentalradish.item.custom;

import com.experimentalradish.block.ModBlocks;
import com.experimentalradish.block.custom.RadishCrop;
import com.experimentalradish.item.ModItems;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.BlockItem;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.*;

public class RadishSeeds extends BlockItem implements RadishItems {
    private static final Random rand = new Random();
    private CompoundNBT mutations;

    public RadishSeeds(Properties properties) {
        super(ModBlocks.RADISH_CROP.get(), properties);
    }

    @Override
    @Nonnull
    public ActionResultType tryPlace(@Nonnull BlockItemUseContext context) {
        ActionResultType result = super.tryPlace(context);
        if (result == ActionResultType.SUCCESS) {
            World world = context.getWorld();
            BlockState state = world.getBlockState(context.getPos()).getBlockState();
            Block block = state.getBlock();
            if (block instanceof RadishCrop) {
                ((RadishCrop) block).setMutations(context.getItem().getTag());
            }

        }
        return result;
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, @Nonnull List<ITextComponent> tooltip, @Nonnull ITooltipFlag flagIn) {
        if (stack.getTag() != null && stack.getTag().keySet().size() > 0) {
            tooltip.add(ITextComponent.getTextComponentOrEmpty(createNBTTooltip(stack.getTag())));
        }
        super.addInformation(stack, worldIn, tooltip, flagIn);
    }

    public static String createNBTTooltip(CompoundNBT nbt) {
        StringBuilder str = new StringBuilder();
        Set<String> keys = nbt.keySet();
        for (String key: keys) {
            str.append(key).append(": ").append(Objects.requireNonNull(nbt.get(key)).toString().trim()).append("\n");
        }
        return str.substring(0, str.length() - 1);
    }

    public static CompoundNBT initializeNBT() {
        String PATH = RadishItems.PATH;
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
        String PATH = RadishItems.PATH;
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
        String PATH = RadishItems.PATH;
        assert stack.getItem() == ModItems.RADISH_SEEDS.get();

        if(!stack.hasTag()) {
            stack.setTag(initializeNBT());
        }
        CompoundNBT nbt = stack.getTag();

        //noinspection ConstantConditions
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
            //noinspection ConstantConditions
            for (net.minecraft.nbt.INBT inbt : list) {
                if (inbt.equals(randEffect)) {
                    valid = false;
                    break;
                }
            }
            if (valid) {
                list.add(randEffect);
            }
            nbt.put(PATH + "effect", list);
        }
        return nbt;
    }

    @Override
    public void setMutations(CompoundNBT mutations) {
        this.mutations = mutations;
        System.out.println("Added mutations to Radish: " + this.mutations + " " + mutations);
    }

    @Override
    @Nullable
    public CompoundNBT getMutations() {
        return this.mutations;
    }
}
