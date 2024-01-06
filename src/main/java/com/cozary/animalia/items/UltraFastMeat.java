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

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class UltraFastMeat extends Item {

    public UltraFastMeat(Properties food) {
        super(food);
    }

    @Override
    public int getUseDuration(ItemStack stack) {
        if (stack.getItem().isEdible()) {
            return 10;
        } else {
            return 0;
        }
    }

}
