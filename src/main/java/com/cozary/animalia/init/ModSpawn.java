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

package com.cozary.animalia.init;

import com.cozary.animalia.Animalia;
import com.cozary.animalia.biomesPLS.BiomeRegistryn;
import com.cozary.animalia.util.AnimaliaConfig;
import net.minecraft.entity.EntityClassification;
import net.minecraft.util.RegistryKey;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biomes;
import net.minecraft.world.biome.MobSpawnInfo;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;


@Mod.EventBusSubscriber(modid = Animalia.MOD_ID)
public class ModSpawn {

    @SubscribeEvent(priority = EventPriority.HIGH)
    public static void addSpawn(BiomeLoadingEvent event) {
        if (event.getName() != null) {
            Biome biome = ForgeRegistries.BIOMES.getValue(event.getName());

            RegistryKey<Biome> biomeKey = RegistryKey.create(ForgeRegistries.Keys.BIOMES, event.getName());

            if (biome != null) {
                if (biomeKey == BiomeRegistryn.BiomeKeys.muddy_swamp) {
                    event.getSpawns().getSpawner(EntityClassification.CREATURE).add(new MobSpawnInfo.Spawners(ModEntityTypes.DIRTY_PIG.get(), AnimaliaConfig.SPAWN.MuddySwampDirtyPigWeight.get(), AnimaliaConfig.SPAWN.MuddySwampDirtyPigMin.get(), AnimaliaConfig.SPAWN.MuddySwampDirtyPigMax.get()));
                }
                if (biomeKey == Biomes.SWAMP || biomeKey == Biomes.SWAMP_HILLS) {
                    event.getSpawns().getSpawner(EntityClassification.CREATURE).add(new MobSpawnInfo.Spawners(ModEntityTypes.DIRTY_PIG.get(), AnimaliaConfig.SPAWN.DirtyPigWeight.get(), AnimaliaConfig.SPAWN.DirtyPigMin.get(), AnimaliaConfig.SPAWN.DirtyPigMax.get()));
                }
                if (biomeKey == Biomes.BIRCH_FOREST || biomeKey == Biomes.BIRCH_FOREST_HILLS || biomeKey == Biomes.TALL_BIRCH_FOREST || biomeKey == Biomes.TALL_BIRCH_HILLS) {
                    event.getSpawns().getSpawner(EntityClassification.CREATURE).add(new MobSpawnInfo.Spawners(ModEntityTypes.SNAIL.get(), AnimaliaConfig.SPAWN.SnailWeight.get(), AnimaliaConfig.SPAWN.SnailMin.get(), AnimaliaConfig.SPAWN.SnailMax.get()));
                }
                if (biomeKey == Biomes.ICE_SPIKES || biomeKey == Biomes.FROZEN_OCEAN || biomeKey == Biomes.SNOWY_BEACH || biomeKey == Biomes.SNOWY_TUNDRA) {
                    event.getSpawns().getSpawner(EntityClassification.CREATURE).add(new MobSpawnInfo.Spawners(ModEntityTypes.WALRUS.get(), AnimaliaConfig.SPAWN.WalrusWeight.get(), AnimaliaConfig.SPAWN.WalrusMin.get(), AnimaliaConfig.SPAWN.WalrusMax.get()));
                }
                if (biomeKey == Biomes.RIVER || biomeKey == Biomes.FOREST || biomeKey == Biomes.SWAMP) {
                    event.getSpawns().getSpawner(EntityClassification.CREATURE).add(new MobSpawnInfo.Spawners(ModEntityTypes.PLATYPUS.get(), AnimaliaConfig.SPAWN.PlatypusWeight.get(), AnimaliaConfig.SPAWN.PlatypusMin.get(), AnimaliaConfig.SPAWN.PlatypusMax.get()));
                }
                if (biomeKey == Biomes.FOREST || biomeKey == Biomes.BIRCH_FOREST) {
                    event.getSpawns().getSpawner(EntityClassification.CREATURE).add(new MobSpawnInfo.Spawners(ModEntityTypes.BROWN_BEAR.get(), AnimaliaConfig.SPAWN.BrownBearWeight.get(), AnimaliaConfig.SPAWN.BrownBearMin.get(), AnimaliaConfig.SPAWN.BrownBearMax.get()));
                }
                if (biomeKey == Biomes.PLAINS) {
                    event.getSpawns().getSpawner(EntityClassification.CREATURE).add(new MobSpawnInfo.Spawners(ModEntityTypes.BULL.get(), AnimaliaConfig.SPAWN.BullWeight.get(), AnimaliaConfig.SPAWN.BullMin.get(), AnimaliaConfig.SPAWN.BullMax.get()));
                }
                if (event.getCategory() == Biome.Category.FOREST || event.getCategory() == Biome.Category.PLAINS || event.getCategory() == Biome.Category.EXTREME_HILLS) {
                    event.getSpawns().getSpawner(EntityClassification.CREATURE).add(new MobSpawnInfo.Spawners(ModEntityTypes.EAGLE.get(), AnimaliaConfig.SPAWN.EagleWeight.get(), AnimaliaConfig.SPAWN.EagleMin.get(), AnimaliaConfig.SPAWN.EagleMax.get()));
                }
                if (event.getCategory() == Biome.Category.DESERT) {
                    event.getSpawns().getSpawner(EntityClassification.CREATURE).add(new MobSpawnInfo.Spawners(ModEntityTypes.HIPPOPOTAMUS.get(), AnimaliaConfig.SPAWN.HippopotamusWeight.get(), AnimaliaConfig.SPAWN.HippopotamusMin.get(), AnimaliaConfig.SPAWN.HippopotamusMax.get()));
                }
                if (biomeKey == BiomeRegistryn.BiomeKeys.desert_lakes) {
                    event.getSpawns().getSpawner(EntityClassification.CREATURE).add(new MobSpawnInfo.Spawners(ModEntityTypes.HIPPOPOTAMUS.get(), AnimaliaConfig.SPAWN.DesertLakesHippopotamusWeight.get(), AnimaliaConfig.SPAWN.DesertLakesHippopotamusMin.get(), AnimaliaConfig.SPAWN.DesertLakesHippopotamusMax.get()));
                }
                if (biomeKey == Biomes.SWAMP) {
                    event.getSpawns().getSpawner(EntityClassification.CREATURE).add(new MobSpawnInfo.Spawners(ModEntityTypes.LILYGATOR.get(), AnimaliaConfig.SPAWN.LilygatorWeight.get(), AnimaliaConfig.SPAWN.LilygatorMin.get(), AnimaliaConfig.SPAWN.LilygatorMax.get()));
                }
                if (biomeKey == BiomeRegistryn.BiomeKeys.muddy_swamp) {
                    event.getSpawns().getSpawner(EntityClassification.CREATURE).add(new MobSpawnInfo.Spawners(ModEntityTypes.LILYGATOR.get(), AnimaliaConfig.SPAWN.MuddySwampLilygatorWeight.get(), AnimaliaConfig.SPAWN.MuddySwampLilygatorMin.get(), AnimaliaConfig.SPAWN.MuddySwampLilygatorMax.get()));
                }
                if (biomeKey == Biomes.DEEP_COLD_OCEAN || biomeKey == Biomes.DEEP_FROZEN_OCEAN || biomeKey == Biomes.DEEP_OCEAN || biomeKey == Biomes.DEEP_LUKEWARM_OCEAN || biomeKey == Biomes.DEEP_WARM_OCEAN) {
                    event.getSpawns().getSpawner(EntityClassification.WATER_CREATURE).add(new MobSpawnInfo.Spawners(ModEntityTypes.JELLYFISH.get(), AnimaliaConfig.SPAWN.JellyfishWeight.get(), AnimaliaConfig.SPAWN.JellyfishMin.get(), AnimaliaConfig.SPAWN.JellyfishMax.get()));
                }
                if (biomeKey == Biomes.OCEAN || biomeKey == Biomes.COLD_OCEAN || biomeKey == Biomes.FROZEN_OCEAN || biomeKey == Biomes.LUKEWARM_OCEAN || biomeKey == Biomes.WARM_OCEAN) {
                    event.getSpawns().getSpawner(EntityClassification.WATER_CREATURE).add(new MobSpawnInfo.Spawners(ModEntityTypes.JELLYFISH.get(), AnimaliaConfig.SPAWN.LessJellyfishWeight.get(), AnimaliaConfig.SPAWN.LessJellyfishMin.get(), AnimaliaConfig.SPAWN.LessJellyfishMax.get()));
                }
                if (biomeKey == Biomes.DESERT || event.getCategory() == Biome.Category.SAVANNA) {
                    event.getSpawns().getSpawner(EntityClassification.CREATURE).add(new MobSpawnInfo.Spawners(ModEntityTypes.VULTURE.get(), AnimaliaConfig.SPAWN.VultureWeight.get(), AnimaliaConfig.SPAWN.VultureMin.get(), AnimaliaConfig.SPAWN.VultureMax.get()));
                }
                if (biomeKey == Biomes.LUKEWARM_OCEAN || biomeKey == Biomes.WARM_OCEAN || biomeKey == Biomes.DEEP_WARM_OCEAN || biomeKey == Biomes.DEEP_LUKEWARM_OCEAN) {
                    event.getSpawns().getSpawner(EntityClassification.WATER_CREATURE).add(new MobSpawnInfo.Spawners(ModEntityTypes.WHITE_SHARK.get(), AnimaliaConfig.SPAWN.WhiteSharkWeight.get(), AnimaliaConfig.SPAWN.WhiteSharkMin.get(), AnimaliaConfig.SPAWN.WhiteSharkMax.get()));
                }
            }
        }
    }

}
