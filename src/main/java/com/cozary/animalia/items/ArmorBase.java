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

package com.cozary.animalia.items;

import com.cozary.animalia.init.ModItems;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class ArmorBase {

    public static class ShellArmor extends ArmorItem {

        public ShellArmor(ArmorMaterial materialIn, EquipmentSlot slot, Properties p_i48534_3_) {
            super(materialIn, slot, p_i48534_3_);
        }

        @Override
        public void onArmorTick(ItemStack itemStack, Level world, Player player) {
            ItemStack head = player.getItemBySlot(EquipmentSlot.CHEST);
            if (head.getItem() == ModItems.SHELL_ARMOR.get()) {
                if (!player.hasEffect(MobEffect.byId(11))) {
                    MobEffectInstance statusEffect = new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 100, 1);
                    player.addEffect(statusEffect);
                }
            }
        }

    }

    public static class BearArmor extends ArmorItem {

        public BearArmor(ArmorMaterial materialIn, EquipmentSlot slot, Properties p_i48534_3_) {
            super(materialIn, slot, p_i48534_3_);
        }

        @Override
        public void onArmorTick(ItemStack itemStack, Level world, Player player) {
            ItemStack head = player.getItemBySlot(EquipmentSlot.HEAD);
            if (head.getItem() == ModItems.BEAR_ARMOR.get()) {
                if (!player.hasEffect(MobEffect.byId(10)) && !player.hasEffect(MobEffect.byId(1))) {
                    MobEffectInstance statusEffect = new MobEffectInstance(MobEffects.REGENERATION, 100, 0);
                    MobEffectInstance statusEffect2 = new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 100, 0);
                    player.addEffect(statusEffect);
                    player.addEffect(statusEffect2);
                }
            }
        }
    }

    public static class BullArmor extends ArmorItem {

        public BullArmor(ArmorMaterial materialIn, EquipmentSlot slot, Properties p_i48534_3_) {
            super(materialIn, slot, p_i48534_3_);
        }

        @Override
        public void onArmorTick(ItemStack itemStack, Level world, Player player) {
            ItemStack head = player.getItemBySlot(EquipmentSlot.HEAD);
            if (head.getItem() == ModItems.BULL_HAT.get()) {
                if (!player.hasEffect(MobEffect.byId(5))) {
                    MobEffectInstance statusEffect = new MobEffectInstance(MobEffects.DAMAGE_BOOST, 100, 0);
                    player.addEffect(statusEffect);
                }
            }
        }

    }

    public static class FinsArmor extends ArmorItem {

        public FinsArmor(ArmorMaterial materialIn, EquipmentSlot slot, Properties p_i48534_3_) {
            super(materialIn, slot, p_i48534_3_);
        }

        @Override
        public void onArmorTick(ItemStack itemStack, Level world, Player player) {
            ItemStack head = player.getItemBySlot(EquipmentSlot.CHEST);
            if (head.getItem() == ModItems.FINS.get() && player.isEyeInFluid(FluidTags.WATER)) {
                if (!player.hasEffect(MobEffect.byId(30))) {
                    MobEffectInstance statusEffect = new MobEffectInstance(MobEffects.DOLPHINS_GRACE, 100, 0);
                    player.addEffect(statusEffect);
                }
            }
        }

    }

}
