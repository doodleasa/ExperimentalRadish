package com.experimentalradish.item.custom;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.FloatNBT;
import net.minecraft.nbt.IntNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;

import javax.annotation.Nullable;
import java.util.Objects;

public class RadishUtil {

    @Nullable
    public static Effect getEffectByString(String id) {
        switch (id) {
            case "SPEED": return Effects.SPEED;
            case "SLOWNESS": return Effects.SLOWNESS;
            case "HASTE": return Effects.HASTE;
            case "MINING_FATIGUE": return Effects.MINING_FATIGUE;
            case "STRENGTH": return Effects.STRENGTH;
            case "INSTANT_HEALTH": return Effects.INSTANT_HEALTH;
            case "INSTANT_DAMAGE": return Effects.INSTANT_DAMAGE;
            case "JUMP_BOOST": return Effects.JUMP_BOOST;
            case "NAUSEA": return Effects.NAUSEA;
            case "REGENERATION": return Effects.REGENERATION;
            case "RESISTANCE": return Effects.RESISTANCE;
            case "FIRE_RESISTANCE": return Effects.FIRE_RESISTANCE;
            case "WATER_BREATHING": return Effects.WATER_BREATHING;
            case "INVISIBILITY": return Effects.INVISIBILITY;
            case "BLINDNESS": return Effects.BLINDNESS;
            case "NIGHT_VISION": return Effects.NIGHT_VISION;
            case "HUNGER": return Effects.HUNGER;
            case "WEAKNESS": return Effects.WEAKNESS;
            case "POISON": return Effects.POISON;
            case "WITHER": return Effects.WITHER;
            case "HEALTH_BOOST": return Effects.HEALTH_BOOST;
            case "ABSORPTION": return Effects.ABSORPTION;
            case "SATURATION": return Effects.SATURATION;
            // I skip a few effects I don't anticipate being implemented.
            case "GLOWING": return Effects.GLOWING;
            case "LEVITATION": return Effects.LEVITATION;
            case "BAD_OMEN": return Effects.BAD_OMEN;
        }
        return null;
    }

    public static EffectInstance[] getEffects(CompoundNBT nbt) {
        String path = RadishItems.PATH + "effect.";
        ListNBT list = (ListNBT) nbt.get(path);
        if (list == null || list.isEmpty()) {
            return new EffectInstance[0];
        }
        EffectInstance[] effectList = new EffectInstance[list.size()];
        for (int i = 0; i < list.size(); i++) {
            Effect type = getEffectByString(String.valueOf(list.getCompound(i).get(path + "id")));
            int potency = ((IntNBT) Objects.requireNonNull(list.getCompound(i).get(path + "potency"))).getInt();
            int duration = ((IntNBT) Objects.requireNonNull(list.getCompound(i).get(path + "duration"))).getInt();
            assert type != null;
            effectList[i] = new EffectInstance(type, potency, duration);
        }
        return effectList;
    }

    private static float getFloatByPath(CompoundNBT nbt, String path) {
        path = RadishItems.PATH + path;
        return ((FloatNBT) (Objects.requireNonNull(nbt.get(path)))).getFloat();
    }

    public static int getHunger(CompoundNBT nbt) {
        return ((IntNBT) (Objects.requireNonNull(nbt.get(RadishItems.PATH + "hunger")))).getInt();
    }

    public static float getSaturation(CompoundNBT nbt) {
        return getFloatByPath(nbt, "saturation");
    }

    public static float getGrowth(CompoundNBT nbt) {
        return getFloatByPath(nbt, "growth");
    }

    public static float getYield(CompoundNBT nbt) {
        return getFloatByPath(nbt, "yield");
    }

    public static float getDecay(CompoundNBT nbt) {
        return getFloatByPath(nbt, "decay");
    }

}
