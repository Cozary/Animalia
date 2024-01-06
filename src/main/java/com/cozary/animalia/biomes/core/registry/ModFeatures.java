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
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.gen.blockplacer.SimpleBlockPlacer;
import net.minecraft.world.gen.blockstateprovider.WeightedBlockStateProvider;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.placement.ChanceConfig;
import net.minecraft.world.gen.placement.Placement;
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
        public static final BlockClusterFeatureConfig MORE_BLUE_ORCHID_CONFIG = (new BlockClusterFeatureConfig.Builder(new WeightedBlockStateProvider().add(BlockStates.BLUE_ORCHID, 1), SimpleBlockPlacer.INSTANCE)).xspread(5).zspread(5).tries(128).build();
    }

    public static final class Configured {

        public static final ConfiguredFeature<?, ?> MORE_BLUE_ORCHID = Feature.FLOWER.configured(Configs.MORE_BLUE_ORCHID_CONFIG).decorated(Features.Placements.HEIGHTMAP).decorated(Placement.CHANCE.configured(new ChanceConfig(1)));

        private static <FC extends IFeatureConfig> void register(String name, ConfiguredFeature<FC, ?> configuredFeature) {
            Registry.register(WorldGenRegistries.CONFIGURED_FEATURE, new ResourceLocation(Animalia.MOD_ID, name), configuredFeature);
        }

        public static void registerConfiguredFeatures() {
            register("more_blue_orchid", MORE_BLUE_ORCHID);
        }

    }

}
