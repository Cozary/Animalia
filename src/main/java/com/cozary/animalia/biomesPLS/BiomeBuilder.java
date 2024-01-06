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

import com.cozary.animalia.biomes.core.registry.ModFeatures;
import com.cozary.animalia.biomes.features.custom.MudLakeFeature;
import com.cozary.animalia.biomes.features.custom.WaterSpringExtraFeature;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.data.worldgen.BiomeDefaultFeatures;
import net.minecraft.data.worldgen.Features;
import net.minecraft.data.worldgen.StructureFeatures;
import net.minecraft.data.worldgen.SurfaceBuilders;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.*;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.surfacebuilders.ConfiguredSurfaceBuilder;
import net.minecraft.world.level.levelgen.surfacebuilders.SurfaceBuilderConfiguration;

public class BiomeBuilder {

    public static Biome makeMuddySwamp(float depth, float scale) {
        MobSpawnSettings.Builder spawn = new MobSpawnSettings.Builder()
                .setPlayerCanSpawn()
                .addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityType.SLIME, 1, 1, 1));

        BiomeSpecialEffects.Builder ambience = new BiomeSpecialEffects.Builder()
                .waterColor(8610623)
                .waterFogColor(10779727)
                .fogColor(15128208)
                .skyColor(getSkyColorFromTemp(0.75F))
                .ambientMoodSound(AmbientMoodSettings.LEGACY_CAVE_SETTINGS)
                .foliageColorOverride(8421376)
                .grassColorOverride(5994060);

        BiomeGenerationSettings.Builder settings = new BiomeGenerationSettings.Builder()
                .surfaceBuilder(SurfaceBuilders.SWAMP)
                .addStructureStart(StructureFeatures.RUINED_PORTAL_SWAMP)
                .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, Features.SEAGRASS_SWAMP)
                .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, Features.FLOWER_SWAMP)
                .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, Features.PATCH_WATERLILLY)
                .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, Features.BROWN_MUSHROOM_SWAMP)
                .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, Features.RED_MUSHROOM_SWAMP)
                .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, ModFeatures.Configured.MORE_BLUE_ORCHID)
                .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, MudLakeFeature.MUD_LAKES);

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
        BiomeDefaultFeatures.addDefaultCrystalFormations(settings);

        Biome.BiomeBuilder builder = new Biome.BiomeBuilder()
                .scale(scale)
                .temperature(0.8F)
                .biomeCategory(Biome.BiomeCategory.SWAMP)
                .precipitation(Biome.Precipitation.RAIN)
                .downfall(0.4F)
                .depth(depth)
                .mobSpawnSettings(spawn.build())
                .specialEffects(ambience.build())
                .generationSettings((new BiomeGenerationSettings.Builder())
                        .surfaceBuilder(SurfaceBuilders.SWAMP)
                        .build());
        //.generationSettings(settings.build());

        return builder.build();
    }

    public static Biome makeDesertLakes() {
        MobSpawnSettings.Builder spawn = new MobSpawnSettings.Builder()
                .addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.RABBIT, 4, 2, 3))
                .addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityType.HUSK, 80, 4, 4));

        BiomeSpecialEffects.Builder ambience = new BiomeSpecialEffects.Builder()
                .waterColor(0x05bdf2)
                .waterFogColor(0x38a3c2)
                .fogColor(0xa5e7ff)
                .skyColor(getSkyColorFromTemp(2F))
                .ambientMoodSound(AmbientMoodSettings.LEGACY_CAVE_SETTINGS)
                .foliageColorOverride(8421376)
                .grassColorOverride(5994060);

        BiomeGenerationSettings.Builder settings = new BiomeGenerationSettings.Builder()
                .surfaceBuilder(SurfaceBuilders.DESERT)
                .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WaterSpringExtraFeature.WATER_SPRING_EXTRA);


        BiomeDefaultFeatures.addDefaultOverworldLandStructures(settings);
        BiomeDefaultFeatures.addDefaultMonsterRoom(settings);
        BiomeDefaultFeatures.addDefaultUndergroundVariety(settings);
        BiomeDefaultFeatures.addDefaultOres(settings);
        BiomeDefaultFeatures.addDefaultSoftDisks(settings);
        BiomeDefaultFeatures.addDefaultCarvers(settings);
        BiomeDefaultFeatures.addFossilDecoration(settings);
        BiomeDefaultFeatures.addDefaultFlowers(settings);
        BiomeDefaultFeatures.addDefaultGrass(settings);
        BiomeDefaultFeatures.addDesertVegetation(settings);
        BiomeDefaultFeatures.addDefaultMushrooms(settings);
        BiomeDefaultFeatures.addDesertExtraVegetation(settings);
        BiomeDefaultFeatures.addDesertExtraDecoration(settings);
        BiomeDefaultFeatures.addSurfaceFreezing(settings);
        BiomeDefaultFeatures.addDefaultCrystalFormations(settings);
        BiomeDefaultFeatures.addDefaultUndergroundVariety(settings);

        Biome.BiomeBuilder builder = new Biome.BiomeBuilder()
                .scale(0.05F)
                .temperature(1.9F)
                .biomeCategory(Biome.BiomeCategory.DESERT)
                .precipitation(Biome.Precipitation.NONE)
                .downfall(0.0f)
                .depth(-0.125F)
                .mobSpawnSettings(spawn.build())
                .specialEffects(ambience.build())
                .generationSettings((new BiomeGenerationSettings.Builder())
                        .surfaceBuilder(SurfaceBuilders.DESERT)
                        .build());
        //.generationSettings(settings.build());

        return builder.build();
    }


    private static int getSkyColorFromTemp(float temp) {
        float i = temp / 3.0F;
        i = Mth.clamp(i, -1.0F, 1.0F);
        return Mth.hsvToRgb(0.62222224F - i * 0.05F, 0.5F + i * 0.1F, 1.0F);
    }

    private static <SC extends SurfaceBuilderConfiguration> ConfiguredSurfaceBuilder<SC> makeSurfaceBuilder(String p_244192_0_, ConfiguredSurfaceBuilder<SC> p_244192_1_) {
        return BuiltinRegistries.register(BuiltinRegistries.CONFIGURED_SURFACE_BUILDER, p_244192_0_, p_244192_1_);
    }
}
