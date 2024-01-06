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

package com.cozary.animalia.potions;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;

public class PotionHealthHinderItem extends Item {
    public PotionHealthHinderItem(Properties properties) {
        super(properties);
    }

    /**
     * How long it takes to use or consume an item
     */

    @Override
    public int getUseDuration(ItemStack stack) {
        return 32;
    }

    /**
     * returns the action that specifies what animation to play when the items is
     * being used
     */

    @Override
    public UseAnim getUseAnimation(ItemStack p_77661_1_) {
        return UseAnim.DRINK;
    }

    /**
     * Called to trigger the item's "innate" right click behavior. To handle when
     * this item is used on a Block, see .
     */

    @Override
    public InteractionResultHolder<ItemStack> use(Level worldIn, Player playerIn, InteractionHand handIn) {
        playerIn.startUsingItem(handIn);
        return InteractionResultHolder.success(playerIn.getItemInHand(handIn));
    }

    /**
     * Called when the player finishes using this Item (E.g. finishes eating.). Not
     * called when the player stops using the Item before the action is complete.
     */

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level world, LivingEntity entityLiving) {
        Player playerentity = entityLiving instanceof Player ? (Player) entityLiving : null;
        if (playerentity instanceof ServerPlayer) {
            CriteriaTriggers.CONSUME_ITEM.trigger((ServerPlayer) playerentity, stack);
        }
        if (world instanceof ServerLevel) {
            playerentity.addEffect(new MobEffectInstance(RegistryObjects.HEALTH_HINDER_EFFECT, 3600, 0));
        }
        if (playerentity != null) {
            playerentity.awardStat(Stats.ITEM_USED.get(this));
            if (!playerentity.getAbilities().instabuild) {
                stack.shrink(1);
            }
        }
        if (playerentity == null || !playerentity.getAbilities().instabuild) {
            if (stack.isEmpty()) {
                return new ItemStack(Items.GLASS_BOTTLE);
            }
            if (playerentity != null) {
                playerentity.getInventory().add(new ItemStack(Items.GLASS_BOTTLE));
            }
        }
        return stack;
    }
}
