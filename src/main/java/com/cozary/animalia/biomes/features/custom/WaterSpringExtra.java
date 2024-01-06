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

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.levelgen.placement.ChanceDecoratorConfiguration;
import net.minecraft.world.level.levelgen.placement.DecorationContext;
import net.minecraft.world.level.levelgen.placement.FeatureDecorator;

import java.util.Random;
import java.util.stream.Stream;


public class WaterSpringExtra extends FeatureDecorator<ChanceDecoratorConfiguration> {
    public WaterSpringExtra(Codec<ChanceDecoratorConfiguration> codec) {
        super(codec);
    }

    @Override
    public Stream<BlockPos> getPositions(DecorationContext helper, Random rand, ChanceDecoratorConfiguration chanceConfig, BlockPos pos) {
        if (rand.nextInt(100) < 99) {
            int x = rand.nextInt(16) + pos.getX();
            int z = rand.nextInt(16) + pos.getZ();
            int y = 62 + rand.nextInt(3);
            return Stream.of(new BlockPos(x, y, z));
        }

        return Stream.empty();
    }
}
