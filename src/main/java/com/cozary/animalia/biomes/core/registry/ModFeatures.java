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

package com.cozary.animalia.biomes.core.registry;

import com.cozary.animalia.Animalia;
import net.minecraft.core.Registry;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.data.worldgen.Features;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.blockplacers.SimpleBlockPlacer;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.RandomPatchConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.SimpleStateProvider;
import net.minecraft.world.level.levelgen.placement.ChanceDecoratorConfiguration;
import net.minecraft.world.level.levelgen.placement.FeatureDecorator;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

@Mod.EventBusSubscriber(modid = Animalia.MOD_ID)
public class ModFeatures {

    public static final DeferredRegister<Feature<?>> FEATURES = DeferredRegister.create(ForgeRegistries.FEATURES, Animalia.MOD_ID);

    public static final class BlockStates {
        public static final BlockState BLUE_ORCHID = Blocks.BLUE_ORCHID.defaultBlockState();
    }

    public static final class Configs {
        public static final RandomPatchConfiguration MORE_BLUE_ORCHID_CONFIG = (new RandomPatchConfiguration.GrassConfigurationBuilder(new SimpleStateProvider(BlockStates.BLUE_ORCHID), SimpleBlockPlacer.INSTANCE)).xspread(10).zspread(10).tries(128).build();
    }

    public static final class Configured {

        public static final ConfiguredFeature<?, ?> MORE_BLUE_ORCHID = Feature.FLOWER.configured(Configs.MORE_BLUE_ORCHID_CONFIG).decorated(Features.Decorators.HEIGHTMAP).decorated(FeatureDecorator.CHANCE.configured(new ChanceDecoratorConfiguration(1)));

        private static <FC extends FeatureConfiguration> void register(String name, ConfiguredFeature<FC, ?> configuredFeature) {
            Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, new ResourceLocation(Animalia.MOD_ID, name), configuredFeature);
        }

        public static void registerConfiguredFeatures() {
            register("more_blue_orchid", MORE_BLUE_ORCHID);
        }

    }

}
