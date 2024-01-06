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

package com.cozary.animalia.biomesPLS;

import com.cozary.animalia.AnimaliaRegistry;
import com.cozary.animalia.util.AnimaliaConfig;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeManager;
import net.minecraftforge.event.RegistryEvent;

import java.util.HashSet;
import java.util.Set;

public class BiomeRegistryn {
    private static final Set<Biome> BIOMES = new HashSet<>();

    public static Biome register(String name, Biome biome) {
        biome.setRegistryName(AnimaliaRegistry.location(name));
        BIOMES.add(biome);
        return biome;
    }

    public static Biome silentRegister(String name, Biome biome) {
        biome.setRegistryName(AnimaliaRegistry.location(name));
        return biome;
    }

    public static void register(RegistryEvent.Register<Biome> event) {

        register("muddy_swamp", BiomeBuilder.makeMuddySwamp(-0.2F, 0.3F));
        register("desert_lakes", BiomeBuilder.makeDesertLakes());

        event.getRegistry().registerAll(BIOMES.toArray(new Biome[0]));
        registerBiomes();
    }

    public static void registerBiomes() {

        BiomeManager.addBiome(BiomeManager.BiomeType.WARM, new BiomeManager.BiomeEntry(BiomeKeys.muddy_swamp, AnimaliaConfig.BIOME.muddySwampWeight.get()));
        BiomeDictionary.addTypes(BiomeKeys.muddy_swamp, BiomeDictionary.Type.SWAMP, BiomeDictionary.Type.LUSH, BiomeDictionary.Type.DENSE, BiomeDictionary.Type.OVERWORLD);

        BiomeManager.addBiome(BiomeManager.BiomeType.DESERT, new BiomeManager.BiomeEntry(BiomeKeys.desert_lakes, AnimaliaConfig.BIOME.desertLakesWeight.get()));
        BiomeDictionary.addTypes(BiomeKeys.desert_lakes, BiomeDictionary.Type.SANDY, BiomeDictionary.Type.MODIFIED, BiomeDictionary.Type.SPARSE, BiomeDictionary.Type.OVERWORLD);
    }

    public static class BiomeKeys {

        public static final RegistryKey<Biome> muddy_swamp = RegistryKey.create(Registry.BIOME_REGISTRY, AnimaliaRegistry.location("muddy_swamp"));
        public static final RegistryKey<Biome> desert_lakes = RegistryKey.create(Registry.BIOME_REGISTRY, AnimaliaRegistry.location("desert_lakes"));

    }
}
