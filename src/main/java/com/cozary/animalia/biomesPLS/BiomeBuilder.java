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
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.biome.*;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.Features;
import net.minecraft.world.gen.feature.structure.StructureFeatures;
import net.minecraft.world.gen.surfacebuilders.ConfiguredSurfaceBuilder;
import net.minecraft.world.gen.surfacebuilders.ConfiguredSurfaceBuilders;
import net.minecraft.world.gen.surfacebuilders.ISurfaceBuilderConfig;

public class BiomeBuilder {

    public static Biome makeMuddySwamp(float depth, float scale) {
        MobSpawnInfo.Builder spawn = new MobSpawnInfo.Builder()
                .setPlayerCanSpawn()
                .addSpawn(EntityClassification.MONSTER, new MobSpawnInfo.Spawners(EntityType.SLIME, 1, 1, 1));

        BiomeAmbience.Builder ambience = new BiomeAmbience.Builder()
                .waterColor(8610623)
                .waterFogColor(10779727)
                .fogColor(15128208)
                .skyColor(getSkyColorFromTemp(0.75F))
                .ambientMoodSound(MoodSoundAmbience.LEGACY_CAVE_SETTINGS)
                .foliageColorOverride(8421376)
                .grassColorOverride(5994060);

        BiomeGenerationSettings.Builder settings = new BiomeGenerationSettings.Builder()
                .surfaceBuilder(ConfiguredSurfaceBuilders.SWAMP)
                .addStructureStart(StructureFeatures.RUINED_PORTAL_SWAMP)
                .addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Features.SEAGRASS_SWAMP)
                .addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Features.FLOWER_SWAMP)
                .addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Features.PATCH_WATERLILLY)
                .addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Features.BROWN_MUSHROOM_SWAMP)
                .addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Features.RED_MUSHROOM_SWAMP)
                .addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, ModFeatures.Configured.MORE_BLUE_ORCHID)
                .addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, MudLakeFeature.MUD_LAKES);

        DefaultBiomeFeatures.addDefaultOverworldLandStructures(settings);
        DefaultBiomeFeatures.addDefaultCarvers(settings);
        DefaultBiomeFeatures.addDefaultLakes(settings);
        DefaultBiomeFeatures.addDefaultMonsterRoom(settings);
        DefaultBiomeFeatures.addDefaultUndergroundVariety(settings);
        DefaultBiomeFeatures.addDefaultOres(settings);
        DefaultBiomeFeatures.addDefaultSoftDisks(settings);
        DefaultBiomeFeatures.addDefaultMushrooms(settings);
        DefaultBiomeFeatures.addSwampExtraVegetation(settings);
        DefaultBiomeFeatures.addDefaultSprings(settings);
        DefaultBiomeFeatures.addSurfaceFreezing(settings);
        DefaultBiomeFeatures.addSwampVegetation(settings);

        Biome.Builder builder = new Biome.Builder()
                .scale(scale)
                .temperature(0.8F)
                .biomeCategory(Biome.Category.SWAMP)
                .precipitation(Biome.RainType.RAIN)
                .downfall(0.4F)
                .depth(depth)
                .mobSpawnSettings(spawn.build())
                .specialEffects(ambience.build())
                .generationSettings((new BiomeGenerationSettings.Builder())
                        .surfaceBuilder(ConfiguredSurfaceBuilders.SWAMP)
                        .build());
        //.generationSettings(settings.build());

        return builder.build();
    }

    public static Biome makeDesertLakes() {
        MobSpawnInfo.Builder spawn = new MobSpawnInfo.Builder()
                .addSpawn(EntityClassification.CREATURE, new MobSpawnInfo.Spawners(EntityType.RABBIT, 4, 2, 3))
                .addSpawn(EntityClassification.MONSTER, new MobSpawnInfo.Spawners(EntityType.HUSK, 80, 4, 4));

        BiomeAmbience.Builder ambience = new BiomeAmbience.Builder()
                .waterColor(0x05bdf2)
                .waterFogColor(0x38a3c2)
                .fogColor(0xa5e7ff)
                .skyColor(getSkyColorFromTemp(2F))
                .ambientMoodSound(MoodSoundAmbience.LEGACY_CAVE_SETTINGS)
                .foliageColorOverride(8421376)
                .grassColorOverride(5994060);

        BiomeGenerationSettings.Builder settings = new BiomeGenerationSettings.Builder()
                .surfaceBuilder(ConfiguredSurfaceBuilders.DESERT)
                .addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, WaterSpringExtraFeature.WATER_SPRING_EXTRA);


        DefaultBiomeFeatures.addDefaultOverworldLandStructures(settings);
        DefaultBiomeFeatures.addDefaultMonsterRoom(settings);
        DefaultBiomeFeatures.addDefaultUndergroundVariety(settings);
        DefaultBiomeFeatures.addDefaultOres(settings);
        DefaultBiomeFeatures.addDefaultSoftDisks(settings);
        DefaultBiomeFeatures.addDefaultCarvers(settings);
        DefaultBiomeFeatures.addFossilDecoration(settings);
        DefaultBiomeFeatures.addDefaultFlowers(settings);
        DefaultBiomeFeatures.addBadlandGrass(settings);
        DefaultBiomeFeatures.addDesertVegetation(settings);
        DefaultBiomeFeatures.addDefaultMushrooms(settings);
        DefaultBiomeFeatures.addDesertExtraVegetation(settings);
        DefaultBiomeFeatures.addDesertExtraDecoration(settings);
        DefaultBiomeFeatures.addSurfaceFreezing(settings);

        Biome.Builder builder = new Biome.Builder()
                .scale(0.05F)
                .temperature(1.9F)
                .biomeCategory(Biome.Category.DESERT)
                .precipitation(Biome.RainType.NONE)
                .downfall(0.0f)
                .depth(-0.125F)
                .mobSpawnSettings(spawn.build())
                .specialEffects(ambience.build())
                .generationSettings((new BiomeGenerationSettings.Builder())
                        .surfaceBuilder(ConfiguredSurfaceBuilders.DESERT)
                        .build());
        //.generationSettings(settings.build());

        return builder.build();
    }


    private static int getSkyColorFromTemp(float temp) {
        float i = temp / 3.0F;
        i = MathHelper.clamp(i, -1.0F, 1.0F);
        return MathHelper.hsvToRgb(0.62222224F - i * 0.05F, 0.5F + i * 0.1F, 1.0F);
    }

    private static <SC extends ISurfaceBuilderConfig> ConfiguredSurfaceBuilder<SC> makeSurfaceBuilder(String p_244192_0_, ConfiguredSurfaceBuilder<SC> p_244192_1_) {
        return WorldGenRegistries.register(WorldGenRegistries.CONFIGURED_SURFACE_BUILDER, p_244192_0_, p_244192_1_);
    }
}
