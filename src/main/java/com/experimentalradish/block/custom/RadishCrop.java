package com.experimentalradish.block.custom;

import com.experimentalradish.item.ModItems;
import com.experimentalradish.item.custom.RadishItems;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.CropsBlock;

import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootContext;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Objects;

public class RadishCrop extends CropsBlock implements RadishItems {
    protected CompoundNBT mutations = new CompoundNBT();
    private static final VoxelShape[] SHAPE_BY_AGE = new VoxelShape[]{
            Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 2.0D, 16.0D),
            Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 4.0D, 16.0D),
            Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 6.0D, 16.0D),
            Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 8.0D, 16.0D),
            Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 10.0D, 16.0D),
            Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 12.0D, 16.0D),
            Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 14.0D, 16.0D),
            Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D)};

    public RadishCrop(Properties builder) {
        super(builder);
    }

    public void setMutations(CompoundNBT mutations) {
        if (!Objects.isNull(mutations)) {
            this.mutations = mutations;
        }
    }

    public CompoundNBT getMutations() {
        return this.mutations;
    }

    @SuppressWarnings("deprecation")
    @Override
    @Nonnull
    public List<ItemStack> getDrops(@Nonnull BlockState state, @Nonnull LootContext.Builder builder) {
        List<ItemStack> itemStacks = super.getDrops(state, builder);
        for (ItemStack stack: itemStacks) {
            if (stack.getItem() instanceof RadishItems) {
                stack.setTag(this.mutations);
                ((RadishItems) stack.getItem()).setMutations(this.mutations);
            }
        }
        return itemStacks;
    }

    @Override
    @Nonnull
    protected IItemProvider getSeedsItem() {
        return ModItems.RADISH_SEEDS.get();
    }

    @Override
    @Nonnull
    public VoxelShape getShape(BlockState state, @Nonnull IBlockReader worldIn, @Nonnull BlockPos pos, @Nonnull ISelectionContext context) {
        return SHAPE_BY_AGE[state.get(this.getAgeProperty())];
    }
}
