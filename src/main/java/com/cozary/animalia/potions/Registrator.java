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
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.IForgeRegistryEntry;

public class Registrator<T extends IForgeRegistryEntry<T>> {

    public IForgeRegistry<T> registry;

    public Registrator(IForgeRegistry<T> registry) {
        this.registry = registry;
    }

    public T register(String registryKey, T entry) {
        ResourceLocation loc = new ResourceLocation(Animalia.MOD_ID, registryKey);
        return this.register(loc, entry);
    }

    public T register(ResourceLocation location, T entry) {
        entry.setRegistryName(location);
        this.registry.register(entry);
        return entry;
    }

}
