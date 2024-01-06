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

package com.cozary.animalia.biomes.features.custom;

import com.cozary.animalia.AnimaliaRegistry;
import com.cozary.animalia.biomes.features.ModDecorators;
import com.cozary.animalia.init.ModFluids;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.gen.feature.BlockStateFeatureConfig;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.placement.ChanceConfig;


public class MudLakeFeature {
    public static ConfiguredFeature<?, ?> MUD_LAKES;

    public static void registerConfiguredFeatures() {
        Registry<ConfiguredFeature<?, ?>> registry = WorldGenRegistries.CONFIGURED_FEATURE;

        MUD_LAKES = Feature.LAKE
                .configured(new BlockStateFeatureConfig(ModFluids.MUD_BLOCK.get().defaultBlockState()))
                .decorated(ModDecorators.MUD_LAKE.get().configured(new ChanceConfig(50)));
        Registry.register(registry, AnimaliaRegistry.location("mud_lakes"), MUD_LAKES);
    }

}