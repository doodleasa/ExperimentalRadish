package com.experimentalradish.item.custom;

import net.minecraft.nbt.CompoundNBT;

public interface RadishItems {
    String PATH = "experimentalradish.mutations.";
    void setMutations(CompoundNBT mutations);
    CompoundNBT getMutations();

}
