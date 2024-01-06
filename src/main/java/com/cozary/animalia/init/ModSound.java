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
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.common.util.Lazy;
import net.minecraftforge.fmllegacy.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModSound {
    public static final DeferredRegister<SoundEvent> SOUNDS = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, Animalia.MOD_ID);

    public static final RegistryObject<SoundEvent> WALRUS_AMBIENT = SOUNDS.register("entity.walrus.ambient", () -> new SoundEvent(new ResourceLocation(Animalia.MOD_ID, "entity.walrus.ambient")));
    public static final RegistryObject<SoundEvent> WALRUS_DEATH = SOUNDS.register("entity.walrus.death", () -> new SoundEvent(new ResourceLocation(Animalia.MOD_ID, "entity.walrus.death")));
    public static final RegistryObject<SoundEvent> WALRUS_HURT = SOUNDS.register("entity.walrus.hurt", () -> new SoundEvent(new ResourceLocation(Animalia.MOD_ID, "entity.walrus.hurt")));

    public static final RegistryObject<SoundEvent> PLAYPATUS_AMBIENT = SOUNDS.register("entity.playpatus.ambient", () -> new SoundEvent(new ResourceLocation(Animalia.MOD_ID, "entity.playpatus.ambient")));

    public static final RegistryObject<SoundEvent> SNAIL_AMBIENT = SOUNDS.register("entity.snail.ambient", () -> new SoundEvent(new ResourceLocation(Animalia.MOD_ID, "entity.snail.ambient")));
    public static final RegistryObject<SoundEvent> SNAIL_DEATH = SOUNDS.register("entity.snail.death", () -> new SoundEvent(new ResourceLocation(Animalia.MOD_ID, "entity.snail.death")));
    public static final RegistryObject<SoundEvent> SNAIL_HURT = SOUNDS.register("entity.snail.hurt", () -> new SoundEvent(new ResourceLocation(Animalia.MOD_ID, "entity.snail.hurt")));

    public static final RegistryObject<SoundEvent> LILYGATOR_AMBIENT = SOUNDS.register("entity.lilygator.ambient", () -> new SoundEvent(new ResourceLocation(Animalia.MOD_ID, "entity.lilygator.ambient")));
    public static final RegistryObject<SoundEvent> LILYGATOR_DEATH = SOUNDS.register("entity.lilygator.death", () -> new SoundEvent(new ResourceLocation(Animalia.MOD_ID, "entity.lilygator.death")));
    public static final RegistryObject<SoundEvent> LILYGATOR_ROAR = SOUNDS.register("entity.lilygator.roar", () -> new SoundEvent(new ResourceLocation(Animalia.MOD_ID, "entity.lilygator.roar")));

    public static final RegistryObject<SoundEvent> BULL_AMBIENT = SOUNDS.register("entity.bull.ambient", () -> new SoundEvent(new ResourceLocation(Animalia.MOD_ID, "entity.bull.ambient")));
    public static final RegistryObject<SoundEvent> BULL_DEATH = SOUNDS.register("entity.bull.death", () -> new SoundEvent(new ResourceLocation(Animalia.MOD_ID, "entity.bull.death")));
    public static final RegistryObject<SoundEvent> BULL_HURT = SOUNDS.register("entity.bull.hurt", () -> new SoundEvent(new ResourceLocation(Animalia.MOD_ID, "entity.bull.hurt")));

    public static final RegistryObject<SoundEvent> EAGLE_AMBIENT = SOUNDS.register("entity.eagle.ambient", () -> new SoundEvent(new ResourceLocation(Animalia.MOD_ID, "entity.eagle.ambient")));
    public static final RegistryObject<SoundEvent> EAGLE_DEATH = SOUNDS.register("entity.eagle.death", () -> new SoundEvent(new ResourceLocation(Animalia.MOD_ID, "entity.eagle.death")));
    public static final RegistryObject<SoundEvent> EAGLE_HURT = SOUNDS.register("entity.eagle.hurt", () -> new SoundEvent(new ResourceLocation(Animalia.MOD_ID, "entity.eagle.hurt")));

    public static final RegistryObject<SoundEvent> HIPPOPOTAMUS_AMBIENT = SOUNDS.register("entity.hippopotamus.ambient", () -> new SoundEvent(new ResourceLocation(Animalia.MOD_ID, "entity.hippopotamus.ambient")));
    public static final RegistryObject<SoundEvent> HIPPOPOTAMUS_DEATH = SOUNDS.register("entity.hippopotamus.death", () -> new SoundEvent(new ResourceLocation(Animalia.MOD_ID, "entity.hippopotamus.death")));
    public static final RegistryObject<SoundEvent> HIPPOPOTAMUS_HURT = SOUNDS.register("entity.hippopotamus.hurt", () -> new SoundEvent(new ResourceLocation(Animalia.MOD_ID, "entity.hippopotamus.hurt")));

    public static final RegistryObject<SoundEvent> BROWN_BEAR_AMBIENT = SOUNDS.register("entity.brown_bear.ambient", () -> new SoundEvent(new ResourceLocation(Animalia.MOD_ID, "entity.brown_bear.ambient")));
    public static final RegistryObject<SoundEvent> BROWN_BEAR_DEATH = SOUNDS.register("entity.brown_bear.death", () -> new SoundEvent(new ResourceLocation(Animalia.MOD_ID, "entity.brown_bear.death")));
    public static final RegistryObject<SoundEvent> BROWN_BEAR_HURT = SOUNDS.register("entity.brown_bear.hurt", () -> new SoundEvent(new ResourceLocation(Animalia.MOD_ID, "entity.brown_bear.hurt")));

    public static final RegistryObject<SoundEvent> JELLYFISH_AMBIENT = SOUNDS.register("entity.jellyfish.ambient", () -> new SoundEvent(new ResourceLocation(Animalia.MOD_ID, "entity.jellyfish.ambient")));
    public static final RegistryObject<SoundEvent> JELLYFISH_DEATH = SOUNDS.register("entity.jellyfish.death", () -> new SoundEvent(new ResourceLocation(Animalia.MOD_ID, "entity.jellyfish.death")));
    public static final RegistryObject<SoundEvent> JELLYFISH_HURT = SOUNDS.register("entity.jellyfish.hurt", () -> new SoundEvent(new ResourceLocation(Animalia.MOD_ID, "entity.jellyfish.hurt")));

    public static final RegistryObject<SoundEvent> WHITE_SHARK_AMBIENT = SOUNDS.register("entity.white_shark.ambient", () -> new SoundEvent(new ResourceLocation(Animalia.MOD_ID, "entity.white_shark.ambient")));
    public static final RegistryObject<SoundEvent> WHITE_SHARK_DEATH = SOUNDS.register("entity.white_shark.death", () -> new SoundEvent(new ResourceLocation(Animalia.MOD_ID, "entity.white_shark.death")));
    public static final RegistryObject<SoundEvent> WHITE_SHARK_HURT = SOUNDS.register("entity.white_shark.hurt", () -> new SoundEvent(new ResourceLocation(Animalia.MOD_ID, "entity.white_shark.hurt")));

    public static final RegistryObject<SoundEvent> VULTURE_AMBIENT = SOUNDS.register("entity.vulture.ambient", () -> new SoundEvent(new ResourceLocation(Animalia.MOD_ID, "entity.vulture.ambient")));
    public static final RegistryObject<SoundEvent> VULTURE_DEATH = SOUNDS.register("entity.vulture.death", () -> new SoundEvent(new ResourceLocation(Animalia.MOD_ID, "entity.vulture.death")));
    public static final RegistryObject<SoundEvent> VULTURE_HURT = SOUNDS.register("entity.vulture.hurt", () -> new SoundEvent(new ResourceLocation(Animalia.MOD_ID, "entity.vulture.hurt")));

}
