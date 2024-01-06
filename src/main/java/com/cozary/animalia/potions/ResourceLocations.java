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

import com.cozary.animalia.Animalia;
import net.minecraft.util.ResourceLocation;

public class ResourceLocations {

    public static final ResourceLocation POTION_PARALIZE = getModRL("potion_paralize");
    public static final ResourceLocation PARALIZE = getModRL("paralize");
    public static final ResourceLocation POTION_PARALIZE_INGREDIENTS = getModRL("potion_paralize_ingredients");

    public static final ResourceLocation POTION_HEALTH_HINDER = getModRL("potion_health_hinder");
    public static final ResourceLocation HEALTH_HINDER = getModRL("health_hinder");
    public static final ResourceLocation POTION_HEALTH_HINDER_INGREDIENTS = getModRL("potion_health_hinder_ingredients");

    public static ResourceLocation getModRL(String name) {
        return new ResourceLocation(Animalia.MOD_ID, name);
    }

}
