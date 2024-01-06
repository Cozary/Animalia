/*
 *
 *  * Copyright (c) 2024 Cozary
 *  *
 *  * This file is part of Animalia, a mod made for Minecraft.
 *  *
 *  * Animalia is free software: you can redistribute it and/or modify it
 *  * under the terms of the GNU General Public License as published
 *  * by the Free Software Foundation, either version 3 of the License, or
 *  * (at your option) any later version.
 *  *
 *  * Animalia is distributed in the hope that it will be useful, but
 *  * WITHOUT ANY WARRANTY; without even the implied warranty of
 *  * MERCHANTABILITY or FITNESS FOR PARTICULAR PURPOSE.  See the
 *  * GNU General Public License for more details.
 *  *
 *  * You should have received a copy of the GNU General Public License
 *  * License along with Animalia.  If not, see <https://www.gnu.org/licenses/>.
 *
 */

package com.cozary.animalia.util.enums;

import com.cozary.animalia.Animalia;
import com.cozary.animalia.init.ModItems;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.function.Supplier;

public enum ModArmorMaterial implements ArmorMaterial {

    SHELL(Animalia.MOD_ID + ":shell", 20, new int[]{3, 8, 6, 3}, 11, SoundEvents.ARMOR_EQUIP_GENERIC, 0.0F, () -> {
        return Ingredient.of(Items.CLAY_BALL);
    }, 0.0F),
    BEAR(Animalia.MOD_ID + ":bear", 20, new int[]{3, 8, 6, 3}, 11, SoundEvents.ARMOR_EQUIP_GENERIC, 0.0F, () -> {
        return Ingredient.of(ModItems.BEAR_LEATHER.get());
    }, 0.0F),
    BULL(Animalia.MOD_ID + ":bull", 20, new int[]{3, 8, 6, 3}, 11, SoundEvents.ARMOR_EQUIP_GENERIC, 0.0F, () -> {
        return Ingredient.of(ModItems.BULL_HORN.get());
    }, 0.0F),
    FINS(Animalia.MOD_ID + ":fins", 20, new int[]{3, 8, 6, 3}, 11, SoundEvents.ARMOR_EQUIP_GENERIC, 0.0F, () -> {
        return Ingredient.of(ModItems.SHARK_SCALE.get());
    }, 0.0F);


    private static final int[] MAX_DAMAGE_ARRAY = new int[]{7, 11, 10, 9};
    private final String name;
    private final int maxDamageFactor;
    private final int[] damageReductionArray;
    private final int enchantability;
    private final SoundEvent soundEvent;
    private final float thougness;
    private final Supplier<Ingredient> repairMaterial;
    private final float knockbackResistance;

    ModArmorMaterial(String name, int maxDamageFactor, int[] damageReductionAmountArray, int enchantability, SoundEvent soundEvent, float thougness, Supplier<Ingredient> repairMaterial, float knockbackResistance) {
        this.name = name;
        this.maxDamageFactor = maxDamageFactor;
        this.damageReductionArray = damageReductionAmountArray;
        this.enchantability = enchantability;
        this.soundEvent = soundEvent;
        this.thougness = thougness;
        this.repairMaterial = repairMaterial;
        this.knockbackResistance = knockbackResistance;

    }

    @Override
    public int getDurabilityForSlot(EquipmentSlot slotIn) {
        return MAX_DAMAGE_ARRAY[slotIn.getIndex()] * this.maxDamageFactor;
    }

    @Override
    public int getDefenseForSlot(EquipmentSlot slotIn) {
        return this.damageReductionArray[slotIn.getIndex()];
    }

    @Override
    public int getEnchantmentValue() {
        return this.enchantability;
    }

    @Override
    public SoundEvent getEquipSound() {
        return this.soundEvent;
    }

    @Override
    public Ingredient getRepairIngredient() {
        return this.repairMaterial.get();
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public float getToughness() {
        return this.thougness;
    }

    @Override
    public float getKnockbackResistance() {
        return this.knockbackResistance;
    }

}
