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

import com.cozary.animalia.Animalia;
import com.cozary.animalia.biomes.core.registry.ModFeatures;
import com.cozary.animalia.biomes.features.custom.MudLakeFeature;
import com.cozary.animalia.biomes.features.custom.WaterSpringExtraFeature;
import net.minecraft.data.worldgen.BiomeDefaultFeatures;
import net.minecraft.data.worldgen.Features;
import net.minecraft.data.worldgen.StructureFeatures;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraftforge.common.world.BiomeGenerationSettingsBuilder;
import net.minecraftforge.common.world.MobSpawnInfoBuilder;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Animalia.MOD_ID)
public class AnimaliaBiomeFeatures {

    @SubscribeEvent
    public static void addFeatures(BiomeLoadingEvent event) {
        ResourceLocation biomeName = event.getName();

        if (biomeName == null)
            return;

        if (DataUtil.matchesKeys(biomeName, BiomeRegistryn.BiomeKeys.muddy_swamp)) {
            withMuddySwampFeatures(event.getGeneration(), event.getSpawns());
        }
        if (DataUtil.matchesKeys(biomeName, BiomeRegistryn.BiomeKeys.desert_lakes)) {
            withDesertLakesFeatures(event.getGeneration(), event.getSpawns());
        }
    }

    public static void withDesertLakesFeatures(BiomeGenerationSettingsBuilder settings, MobSpawnInfoBuilder spawns) {

        BiomeDefaultFeatures.addDefaultOverworldLandStructures(settings);
        BiomeDefaultFeatures.addDefaultMonsterRoom(settings);
        BiomeDefaultFeatures.addDefaultUndergroundVariety(settings);
        BiomeDefaultFeatures.addDefaultOres(settings);
        BiomeDefaultFeatures.addDefaultSoftDisks(settings);
        BiomeDefaultFeatures.addDefaultCarvers(settings);
        BiomeDefaultFeatures.addFossilDecoration(settings);
        BiomeDefaultFeatures.addDefaultFlowers(settings);
        BiomeDefaultFeatures.addBadlandGrass(settings);
        BiomeDefaultFeatures.addDesertVegetation(settings);
        BiomeDefaultFeatures.addDefaultMushrooms(settings);
        BiomeDefaultFeatures.addDesertExtraVegetation(settings);
        BiomeDefaultFeatures.addDesertExtraDecoration(settings);
        BiomeDefaultFeatures.addSurfaceFreezing(settings);

        withExtraLakes(settings);

    }

    private static void withExtraLakes(BiomeGenerationSettingsBuilder builder) {
        builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WaterSpringExtraFeature.WATER_SPRING_EXTRA);
    }


    public static void withMuddySwampFeatures(BiomeGenerationSettingsBuilder settings, MobSpawnInfoBuilder spawns) {

        BiomeDefaultFeatures.addDefaultOverworldLandStructures(settings);
        BiomeDefaultFeatures.addDefaultCarvers(settings);
        BiomeDefaultFeatures.addDefaultLakes(settings);
        BiomeDefaultFeatures.addDefaultMonsterRoom(settings);
        BiomeDefaultFeatures.addDefaultUndergroundVariety(settings);
        BiomeDefaultFeatures.addDefaultOres(settings);
        BiomeDefaultFeatures.addDefaultSoftDisks(settings);
        BiomeDefaultFeatures.addDefaultMushrooms(settings);
        BiomeDefaultFeatures.addSwampExtraVegetation(settings);
        BiomeDefaultFeatures.addDefaultSprings(settings);
        BiomeDefaultFeatures.addSurfaceFreezing(settings);
        BiomeDefaultFeatures.addSwampVegetation(settings);

        settings.addStructureStart(StructureFeatures.RUINED_PORTAL_SWAMP);
        settings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, Features.SEAGRASS_SWAMP);

        withMuddySwampVegetation(settings);
        withMuddySwampLake(settings);
    }

    private static void withMuddySwampVegetation(BiomeGenerationSettingsBuilder builder) {
        builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, Features.FLOWER_SWAMP);
        builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, Features.PATCH_WATERLILLY);
        builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, Features.BROWN_MUSHROOM_SWAMP);
        builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, Features.RED_MUSHROOM_SWAMP);
        builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, ModFeatures.Configured.MORE_BLUE_ORCHID);

    }

    private static void withMuddySwampLake(BiomeGenerationSettingsBuilder builder) {
        builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, MudLakeFeature.MUD_LAKES);
    }


}
