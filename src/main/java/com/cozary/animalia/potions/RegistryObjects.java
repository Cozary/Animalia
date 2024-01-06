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

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.IForgeRegistryEntry;
import net.minecraftforge.registries.ObjectHolder;

public class RegistryObjects {

    @ObjectHolder("animalia:potion_paralize")
    public static final PotionParalizeItem POTION_PARALIZE_ITEM = null;

    @ObjectHolder("animalia:paralize")
    public static final ParalizeEffect PARALIZE_EFFECT = null;

    @ObjectHolder("animalia:potion_health_hinder")
    public static final PotionHealthHinderItem POTION_HEALTH_HINDER_ITEM = null;

    @ObjectHolder("animalia:health_hinder")
    public static final HealthHinderEffect HEALTH_HINDER_EFFECT = null;

    public static <T extends IForgeRegistryEntry<T>, U extends T> RegistryObject<U> makeRegistryObject(final ResourceLocation location, IForgeRegistry<T> registry) {
        return RegistryObject.of(location, registry);
    }

}
