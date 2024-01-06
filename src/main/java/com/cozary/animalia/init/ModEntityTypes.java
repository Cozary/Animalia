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
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModEntityTypes {
    public static DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.ENTITIES, Animalia.MOD_ID);

    public static final RegistryObject<EntityType<DirtyPigEntity>> DIRTY_PIG = ENTITY_TYPES.register("dirty_pig", () -> EntityType.Builder.of(DirtyPigEntity::new, EntityClassification.CREATURE)
            .sized(1.0f, 0.8f)
            .build(new ResourceLocation(Animalia.MOD_ID, "dirty_pig").toString()));
    public static final RegistryObject<EntityType<BrownBearEntity>> BROWN_BEAR = ENTITY_TYPES.register("brown_bear", () -> EntityType.Builder.of(BrownBearEntity::new, EntityClassification.CREATURE)
            .sized(1.2f, 1.3f)
            .build(new ResourceLocation(Animalia.MOD_ID, "brown_bear").toString()));
    public static final RegistryObject<EntityType<SnailEntity>> SNAIL = ENTITY_TYPES.register("snail", () -> EntityType.Builder.of(SnailEntity::new, EntityClassification.CREATURE)
            .sized(0.6f, 0.6f)
            .build(new ResourceLocation(Animalia.MOD_ID, "snail").toString()));
    public static final RegistryObject<EntityType<WalrusEntity>> WALRUS = ENTITY_TYPES.register("walrus", () -> EntityType.Builder.of(WalrusEntity::new, EntityClassification.CREATURE)
            .sized(1f, 1f)
            .build(new ResourceLocation(Animalia.MOD_ID, "walrus").toString()));
    public static final RegistryObject<EntityType<PlatypusEntity>> PLATYPUS = ENTITY_TYPES.register("platypus", () -> EntityType.Builder.of(PlatypusEntity::new, EntityClassification.CREATURE)
            .sized(0.6f, 0.5f)
            .build(new ResourceLocation(Animalia.MOD_ID, "platypus").toString()));
    public static final RegistryObject<EntityType<BullEntity>> BULL = ENTITY_TYPES.register("bull", () -> EntityType.Builder.of(BullEntity::new, EntityClassification.CREATURE)
            .sized(1f, 1.5f)
            .build(new ResourceLocation(Animalia.MOD_ID, "bull").toString()));
    public static final RegistryObject<EntityType<EagleEntity>> EAGLE = ENTITY_TYPES.register("eagle", () -> EntityType.Builder.of(EagleEntity::new, EntityClassification.CREATURE)
            .sized(1.0f, 0.6f)
            .build(new ResourceLocation(Animalia.MOD_ID, "eagle").toString()));
    public static final RegistryObject<EntityType<HippopotamusEntity>> HIPPOPOTAMUS = ENTITY_TYPES.register("hippopotamus", () -> EntityType.Builder.of(HippopotamusEntity::new, EntityClassification.CREATURE)
            .sized(2.3f, 1.7f)
            .build(new ResourceLocation(Animalia.MOD_ID, "hippopotamus").toString()));
    public static final RegistryObject<EntityType<LilygatorEntity>> LILYGATOR = ENTITY_TYPES.register("lilygator", () -> EntityType.Builder.of(LilygatorEntity::new, EntityClassification.CREATURE)
            .sized(1.3f, 0.8f)
            .build(new ResourceLocation(Animalia.MOD_ID, "lilygator").toString()));
    public static final RegistryObject<EntityType<JellyfishEntity>> JELLYFISH = ENTITY_TYPES.register("jellyfish", () -> EntityType.Builder.of(JellyfishEntity::new, EntityClassification.CREATURE)
            .sized(0.5f, 0.5f)
            .build(new ResourceLocation(Animalia.MOD_ID, "jellyfish").toString()));
    public static final RegistryObject<EntityType<VultureEntity>> VULTURE = ENTITY_TYPES.register("vulture", () -> EntityType.Builder.of(VultureEntity::new, EntityClassification.CREATURE)
            .sized(1.1f, 1f)
            .build(new ResourceLocation(Animalia.MOD_ID, "vulture").toString()));
    public static final RegistryObject<EntityType<WhiteSharkEntity>> WHITE_SHARK = ENTITY_TYPES.register("white_shark", () -> EntityType.Builder.of(WhiteSharkEntity::new, EntityClassification.MONSTER)
            .sized(1.4f, 1f)
            .build(new ResourceLocation(Animalia.MOD_ID, "white_shark").toString()));
}

