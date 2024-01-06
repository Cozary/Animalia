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
import com.google.common.collect.ImmutableSet;
import net.minecraft.core.Registry;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.SpringConfiguration;
import net.minecraft.world.level.levelgen.placement.ChanceDecoratorConfiguration;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;


public class WaterSpringExtraFeature {

    public static final FluidState WATER = Fluids.WATER.defaultFluidState();
    public static ConfiguredFeature<?, ?> WATER_SPRING_EXTRA;

    public static void registerConfiguredFeatures() {
        Registry<ConfiguredFeature<?, ?>> registry = BuiltinRegistries.CONFIGURED_FEATURE;

        WATER_SPRING_EXTRA = Feature.SPRING
                .configured(new SpringConfiguration(WATER, true, 4, 1, ImmutableSet.of(Blocks.STONE, Blocks.GRANITE, Blocks.DIORITE, Blocks.ANDESITE)))
                .decorated(ModDecorators.WATER_SPRING_EXTRA.get().configured(new ChanceDecoratorConfiguration(100)));
        Registry.register(registry, AnimaliaRegistry.location("water_spring_extra"), WATER_SPRING_EXTRA);
    }

}