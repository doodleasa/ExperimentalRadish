package com.experimentalradish.tileentity;

import com.experimentalradish.item.ModItems;
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class RadiationBlasterTile extends TileEntity implements ITickableTileEntity {

    private final ItemStackHandler itemHandler = createHandler();
    private final LazyOptional<IItemHandler> handler = LazyOptional.of(() -> itemHandler);

    private int cookProgress;

    private final int cookTime = 300;

    private int fuel;

    public RadiationBlasterTile(TileEntityType<?> tileEntityTypeIn) {
        super(tileEntityTypeIn);
    }

    public RadiationBlasterTile() {
        this(ModTileEntities.RADIATION_BLASTER_TILE.get());
    }

    @Override
    public void read(BlockState state, CompoundNBT nbt) {
        itemHandler.deserializeNBT(nbt.getCompound("inv"));
        super.read(state, nbt);
    }

    @Override
    public CompoundNBT write(CompoundNBT compound) {
        compound.put("inv", itemHandler.serializeNBT());
        return super.write(compound);
    }

    private ItemStackHandler createHandler() {
        return new ItemStackHandler(3) {
            @Override
            protected void onContentsChanged(int slot) {
                markDirty();
            }

            @Override
            public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
                switch (slot) {
                    case 0: return stack.getItem() == ModItems.RADISH_SEEDS.get();
                    case 1: return  stack.getItem() == Items.REDSTONE;
                    default: return false;
                }
            }

            @Nonnull
            @Override
            public ItemStack insertItem(int slot, @Nonnull ItemStack stack, boolean simulate) {
                if(!isItemValid(slot, stack)) {
                    return stack;
                }
                return super.insertItem(slot, stack, simulate);
            }

        };
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        if(cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            return handler.cast();
        }

        return super.getCapability(cap, side);
    }

    public boolean isWorking() {
        refuel();
        return(fuel > 0);
    }

    public void refuel() {
        if (itemHandler.getStackInSlot(0).getItem() == ModItems.RADISH_SEEDS.get() && itemHandler.getStackInSlot(0).getCount() > 0 && this.itemHandler.getStackInSlot(1).getItem() == Items.REDSTONE && this.itemHandler.getStackInSlot(1).getCount() > 0 && fuel == 0)
        {
            this.itemHandler.getStackInSlot(1).shrink(1);
            fuel = 300;
        }
    }

    @Override
    public void tick() {
        if (isWorking()) {
            cookProgress++;
            fuel--;
            System.out.println(cookProgress);
            System.out.println(cookProgress == cookTime && this.itemHandler.getStackInSlot(0).getItem() == ModItems.RADISH_SEEDS.get() && this.itemHandler.getStackInSlot(0).getCount() > 0);
            System.out.println("B");
            if (cookProgress == cookTime && this.itemHandler.getStackInSlot(0).getItem() == ModItems.RADISH_SEEDS.get() && this.itemHandler.getStackInSlot(0).getCount() > 0) {
                cookProgress = 0;

                //todo: give new radish with NBT data
            }
        }
    }
}
