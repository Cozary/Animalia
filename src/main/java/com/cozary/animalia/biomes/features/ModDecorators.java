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

package com.cozary.animalia.biomes.features;


import com.cozary.animalia.Animalia;
import com.cozary.animalia.biomes.features.custom.LakeMud;
import com.cozary.animalia.biomes.features.custom.WaterSpringExtra;
import net.minecraft.world.level.levelgen.placement.ChanceDecoratorConfiguration;
import net.minecraft.world.level.levelgen.placement.FeatureDecorator;
import net.minecraftforge.fmllegacy.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Supplier;


public class ModDecorators {

    public static final DeferredRegister<FeatureDecorator<?>> DECORATORS = DeferredRegister.create(ForgeRegistries.DECORATORS, Animalia.MOD_ID);

    public static final RegistryObject<LakeMud> MUD_LAKE = register("mud_lake", () -> new LakeMud(ChanceDecoratorConfiguration.CODEC));
    public static final RegistryObject<WaterSpringExtra> WATER_SPRING_EXTRA = register("water_spring_extra", () -> new WaterSpringExtra(ChanceDecoratorConfiguration.CODEC));

    private static <T extends FeatureDecorator<?>> RegistryObject<T> register(final String name, final Supplier<T> sup) {
        return DECORATORS.register(name, sup);
    }
}
