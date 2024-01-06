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

import com.cozary.animalia.Animalia;
import com.cozary.animalia.init.ModItems;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.World;

public class ArmorBase {

    public static class ShellArmor extends ArmorItem {

        public ShellArmor(IArmorMaterial materialIn, EquipmentSlotType slot, Properties p_i48534_3_) {
            super(materialIn, slot, p_i48534_3_);
        }

        @Override
        public void onArmorTick(ItemStack itemStack, World world, PlayerEntity player) {
            ItemStack head = player.getItemBySlot(EquipmentSlotType.CHEST);
            if (head.getItem() == ModItems.SHELL_ARMOR.get()) {
                if (!player.hasEffect(Effect.byId(11))) {
                    EffectInstance statusEffect = new EffectInstance(Effects.DAMAGE_RESISTANCE, 100, 1);
                    player.addEffect(statusEffect);
                }
            }
        }

        @Override
        public <A extends BipedModel<?>> A getArmorModel(LivingEntity entityLiving, ItemStack itemStack, EquipmentSlotType armorSlot, A _default) {
            return Animalia.PROXY.getShellModel(armorSlot);
        }

    }

    public static class BearArmor extends ArmorItem {

        public BearArmor(IArmorMaterial materialIn, EquipmentSlotType slot, Properties p_i48534_3_) {
            super(materialIn, slot, p_i48534_3_);
        }

        @Override
        public <A extends BipedModel<?>> A getArmorModel(LivingEntity entityLiving, ItemStack itemStack, EquipmentSlotType armorSlot, A _default) {
            return Animalia.PROXY.getBearHatModel(armorSlot);
        }

        @Override
        public void onArmorTick(ItemStack itemStack, World world, PlayerEntity player) {
            ItemStack head = player.getItemBySlot(EquipmentSlotType.HEAD);
            if (head.getItem() == ModItems.BEAR_ARMOR.get()) {
                if (!player.hasEffect(Effect.byId(10)) && !player.hasEffect(Effect.byId(1))) {
                    EffectInstance statusEffect = new EffectInstance(Effects.REGENERATION, 100, 0);
                    EffectInstance statusEffect2 = new EffectInstance(Effects.MOVEMENT_SPEED, 100, 0);
                    player.addEffect(statusEffect);
                    player.addEffect(statusEffect2);
                }
            }
        }
    }

    public static class BullArmor extends ArmorItem {

        public BullArmor(IArmorMaterial materialIn, EquipmentSlotType slot, Properties p_i48534_3_) {
            super(materialIn, slot, p_i48534_3_);
        }

        @Override
        public <A extends BipedModel<?>> A getArmorModel(LivingEntity entityLiving, ItemStack itemStack, EquipmentSlotType armorSlot, A _default) {
            return Animalia.PROXY.getBullHatModel(armorSlot);
        }

        @Override
        public void onArmorTick(ItemStack itemStack, World world, PlayerEntity player) {
            ItemStack head = player.getItemBySlot(EquipmentSlotType.HEAD);
            if (head.getItem() == ModItems.BULL_HAT.get()) {
                if (!player.hasEffect(Effect.byId(5))) {
                    EffectInstance statusEffect = new EffectInstance(Effects.DAMAGE_BOOST, 100, 0);
                    player.addEffect(statusEffect);
                }
            }
        }

    }

    public static class FinsArmor extends ArmorItem {

        public FinsArmor(IArmorMaterial materialIn, EquipmentSlotType slot, Properties p_i48534_3_) {
            super(materialIn, slot, p_i48534_3_);
        }

        @Override
        public <A extends BipedModel<?>> A getArmorModel(LivingEntity entityLiving, ItemStack itemStack, EquipmentSlotType armorSlot, A _default) {
            return Animalia.PROXY.getFinsModel(armorSlot);
        }

        @Override
        public void onArmorTick(ItemStack itemStack, World world, PlayerEntity player) {
            ItemStack head = player.getItemBySlot(EquipmentSlotType.CHEST);
            if (head.getItem() == ModItems.FINS.get() && player.isEyeInFluid(FluidTags.WATER)) {
                if (!player.hasEffect(Effect.byId(30))) {
                    EffectInstance statusEffect = new EffectInstance(Effects.DOLPHINS_GRACE, 100, 0);
                    player.addEffect(statusEffect);
                }
            }
        }

    }

}
