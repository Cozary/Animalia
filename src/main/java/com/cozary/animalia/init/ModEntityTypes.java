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
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fmllegacy.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

@Mod.EventBusSubscriber(modid = Animalia.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModEntityTypes {
    public static DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.ENTITIES, Animalia.MOD_ID);

    public static final RegistryObject<EntityType<DirtyPigEntity>> DIRTY_PIG = ENTITY_TYPES.register("dirty_pig", () -> EntityType.Builder.of(DirtyPigEntity::new, MobCategory.CREATURE)
            .sized(1.0f, 0.8f)
            .build(new ResourceLocation(Animalia.MOD_ID, "dirty_pig").toString()));
    public static final RegistryObject<EntityType<BrownBearEntity>> BROWN_BEAR = ENTITY_TYPES.register("brown_bear", () -> EntityType.Builder.of(BrownBearEntity::new, MobCategory.CREATURE)
            .sized(1.2f, 1.3f)
            .build(new ResourceLocation(Animalia.MOD_ID, "brown_bear").toString()));
    public static final RegistryObject<EntityType<SnailEntity>> SNAIL = ENTITY_TYPES.register("snail", () -> EntityType.Builder.of(SnailEntity::new, MobCategory.CREATURE)
            .sized(0.6f, 0.6f)
            .build(new ResourceLocation(Animalia.MOD_ID, "snail").toString()));
    public static final RegistryObject<EntityType<WalrusEntity>> WALRUS = ENTITY_TYPES.register("walrus", () -> EntityType.Builder.of(WalrusEntity::new, MobCategory.CREATURE)
            .sized(1f, 1f)
            .build(new ResourceLocation(Animalia.MOD_ID, "walrus").toString()));
    public static final RegistryObject<EntityType<PlatypusEntity>> PLATYPUS = ENTITY_TYPES.register("platypus", () -> EntityType.Builder.of(PlatypusEntity::new, MobCategory.CREATURE)
            .sized(0.6f, 0.5f)
            .build(new ResourceLocation(Animalia.MOD_ID, "platypus").toString()));
    public static final RegistryObject<EntityType<BullEntity>> BULL = ENTITY_TYPES.register("bull", () -> EntityType.Builder.of(BullEntity::new, MobCategory.CREATURE)
            .sized(1f, 1.5f)
            .build(new ResourceLocation(Animalia.MOD_ID, "bull").toString()));
    public static final RegistryObject<EntityType<EagleEntity>> EAGLE = ENTITY_TYPES.register("eagle", () -> EntityType.Builder.of(EagleEntity::new, MobCategory.CREATURE)
            .sized(1.0f, 0.6f)
            .build(new ResourceLocation(Animalia.MOD_ID, "eagle").toString()));
    public static final RegistryObject<EntityType<HippopotamusEntity>> HIPPOPOTAMUS = ENTITY_TYPES.register("hippopotamus", () -> EntityType.Builder.of(HippopotamusEntity::new, MobCategory.CREATURE)
            .sized(2.3f, 1.7f)
            .build(new ResourceLocation(Animalia.MOD_ID, "hippopotamus").toString()));
    public static final RegistryObject<EntityType<LilygatorEntity>> LILYGATOR = ENTITY_TYPES.register("lilygator", () -> EntityType.Builder.of(LilygatorEntity::new, MobCategory.CREATURE)
            .sized(1.3f, 0.8f)
            .build(new ResourceLocation(Animalia.MOD_ID, "lilygator").toString()));
    public static final RegistryObject<EntityType<JellyfishEntity>> JELLYFISH = ENTITY_TYPES.register("jellyfish", () -> EntityType.Builder.of(JellyfishEntity::new, MobCategory.CREATURE)
            .sized(0.5f, 0.5f)
            .build(new ResourceLocation(Animalia.MOD_ID, "jellyfish").toString()));
    public static final RegistryObject<EntityType<VultureEntity>> VULTURE = ENTITY_TYPES.register("vulture", () -> EntityType.Builder.of(VultureEntity::new, MobCategory.CREATURE)
            .sized(1.1f, 1f)
            .build(new ResourceLocation(Animalia.MOD_ID, "vulture").toString()));
    public static final RegistryObject<EntityType<WhiteSharkEntity>> WHITE_SHARK = ENTITY_TYPES.register("white_shark", () -> EntityType.Builder.of(WhiteSharkEntity::new, MobCategory.MONSTER)
            .sized(1.4f, 1f)
            .build(new ResourceLocation(Animalia.MOD_ID, "white_shark").toString()));

    @SubscribeEvent
    public static void addEntityAttributes(EntityAttributeCreationEvent event) {
        event.put(DIRTY_PIG.get(), DirtyPigEntity.setCustomAttributes().build());
        event.put(BROWN_BEAR.get(), BrownBearEntity.setCustomAttributes().build());
        event.put(SNAIL.get(), SnailEntity.setCustomAttributes().build());
        event.put(WALRUS.get(), WalrusEntity.setCustomAttributes().build());
        event.put(PLATYPUS.get(), PlatypusEntity.setCustomAttributes().build());
        event.put(BULL.get(), BullEntity.setCustomAttributes().build());
        event.put(EAGLE.get(), EagleEntity.setCustomAttributes().build());
        event.put(HIPPOPOTAMUS.get(), HippopotamusEntity.setCustomAttributes().build());
        event.put(LILYGATOR.get(), LilygatorEntity.setCustomAttributes().build());
        event.put(JELLYFISH.get(), JellyfishEntity.setCustomAttributes().build());
        event.put(VULTURE.get(), VultureEntity.setCustomAttributes().build());
        event.put(WHITE_SHARK.get(), WhiteSharkEntity.setCustomAttributes().build());
    }

/*
    @SubscribeEvent
    public static void registerEntitySpawnEggs(final RegistryEvent.Register<Item> event)
    {

        event.getRegistry().registerAll(
                registerEntitySpawnEgg(DIRTY_PIG.get(), 0xc582a3, 0x5f3c00, "dirty_pig_spawn_egg"),
                registerEntitySpawnEgg(BROWN_BEAR.get(), 0x623a0b, 0x351b05, "brown_bear_spawn_egg"),
                registerEntitySpawnEgg(SNAIL.get(),  0x8c95a8, 0xa45021, "snail_spawn_egg"),
                registerEntitySpawnEgg(WALRUS.get(), 0x0999b9b, 0x666464, "walrus_spawn_egg"),
                registerEntitySpawnEgg(PLATYPUS.get(), 0x63ba93, 0xfc9303, "platypus_spawn_egg"),
                registerEntitySpawnEgg(BULL.get(), 0x000000, 0x202020, "bull_spawn_egg"),
                registerEntitySpawnEgg(EAGLE.get(), 0x462306, 0xdbdbdb, "eagle_spawn_egg"),
                registerEntitySpawnEgg(HIPPOPOTAMUS.get(), 0xbbb595, 0xa8956e, "hippopotamus_spawn_egg"),
                registerEntitySpawnEgg(LILYGATOR.get(), 0x1fcb37, 0x146a21, "lilygator_spawn_egg"),
                registerEntitySpawnEgg(JELLYFISH.get(), 0x531c51, 0x3f163e, "jellyfish_spawn_egg"),
                registerEntitySpawnEgg(VULTURE.get(), 0xc261303, 0x432041, "vulture_spawn_egg"),
                registerEntitySpawnEgg(WHITE_SHARK.get(), 0xb4b4b4, 0x4a4a4a, "white_shark_spawn_egg")
                );
    }

    public static Item registerEntitySpawnEgg(EntityType<? extends Mob> type, int color1, int color2, String name)
    {
        SpawnEggItem item = new SpawnEggItem(type, color1, color2, new Item.Properties().tab(Animalia.TAB));
        item.setRegistryName(name);
        return item;
    }*/

}

