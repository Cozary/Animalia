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
import com.cozary.animalia.entities.*;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

import java.util.List;

@EventBusSubscriber(modid = Animalia.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
public class ModAnimalRegistry {
    private static final List<EntityType<?>> ENTITIES = Lists.newArrayList();

    @SubscribeEvent
    public static void registerAnimals(RegistryEvent.Register<EntityType<?>> event) {
        for (EntityType entity : ENTITIES) {
            Preconditions.checkNotNull(entity.getRegistryName(), "registryName");
            event.getRegistry().register(entity);
            SpawnPlacements.register(entity, SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, DirtyPigEntity::canDirtyPigSpawn);
            SpawnPlacements.register(entity, SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, BrownBearEntity::canBrownBearSpawn);
            SpawnPlacements.register(entity, SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, BullEntity::canBullSpawn);
            SpawnPlacements.register(entity, SpawnPlacements.Type.NO_RESTRICTIONS, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, EagleEntity::canEagleSpawn);
            SpawnPlacements.register(entity, SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, HippopotamusEntity::canHippopotamusSpawn);
            SpawnPlacements.register(entity, SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, LilygatorEntity::canLilygatorSpawn);
            SpawnPlacements.register(entity, SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, PlatypusEntity::canPlatypusSpawn);
            SpawnPlacements.register(entity, SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, SnailEntity::canSnailSpawn);
            SpawnPlacements.register(entity, SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, WalrusEntity::canWalrusSpawn);
            SpawnPlacements.register(entity, SpawnPlacements.Type.IN_WATER, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, WhiteSharkEntity::canWhiteSharkSpawn);
            SpawnPlacements.register(entity, SpawnPlacements.Type.NO_RESTRICTIONS, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, VultureEntity::canVultureSpawn);
            SpawnPlacements.register(entity, SpawnPlacements.Type.IN_WATER, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, JellyfishEntity::canJellyfishSpawn);
        }
    }

}