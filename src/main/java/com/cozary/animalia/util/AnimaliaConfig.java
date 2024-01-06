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

package com.cozary.animalia.util;

import net.minecraftforge.common.ForgeConfigSpec;

public class AnimaliaConfig {
    public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public static final Spawn SPAWN = new Spawn(BUILDER);
    public static final Biome BIOME = new Biome(BUILDER);
    public static final ForgeConfigSpec spec = BUILDER.build();

    public static class Biome {
        public final ForgeConfigSpec.ConfigValue<Integer> muddySwampWeight;
        public final ForgeConfigSpec.ConfigValue<Integer> desertLakesWeight;

        Biome(ForgeConfigSpec.Builder builder) {
            builder.push("Configurations for Biomes. Values for biome frequencies; lower = more rare. (Requires restart)");

            muddySwampWeight = builder.define("Muddy Swamp weight", 8);
            desertLakesWeight = builder.define("Desert Lakes weight", 8);

            builder.pop();
        }
    }

    public static class Spawn {
        public final ForgeConfigSpec.IntValue DirtyPigMin;
        public final ForgeConfigSpec.IntValue DirtyPigMax;
        public final ForgeConfigSpec.IntValue DirtyPigWeight;
        public final ForgeConfigSpec.IntValue BrownBearMin;
        public final ForgeConfigSpec.IntValue BrownBearMax;
        public final ForgeConfigSpec.IntValue BrownBearWeight;
        public final ForgeConfigSpec.IntValue BullMin;
        public final ForgeConfigSpec.IntValue BullMax;
        public final ForgeConfigSpec.IntValue BullWeight;
        public final ForgeConfigSpec.IntValue EagleMin;
        public final ForgeConfigSpec.IntValue EagleMax;
        public final ForgeConfigSpec.IntValue EagleWeight;
        public final ForgeConfigSpec.IntValue HippopotamusMin;
        public final ForgeConfigSpec.IntValue HippopotamusMax;
        public final ForgeConfigSpec.IntValue HippopotamusWeight;
        public final ForgeConfigSpec.IntValue LilygatorMin;
        public final ForgeConfigSpec.IntValue LilygatorMax;
        public final ForgeConfigSpec.IntValue LilygatorWeight;
        public final ForgeConfigSpec.IntValue PlatypusMin;
        public final ForgeConfigSpec.IntValue PlatypusMax;
        public final ForgeConfigSpec.IntValue PlatypusWeight;
        public final ForgeConfigSpec.IntValue SnailMin;
        public final ForgeConfigSpec.IntValue SnailMax;
        public final ForgeConfigSpec.IntValue SnailWeight;
        public final ForgeConfigSpec.IntValue WalrusMin;
        public final ForgeConfigSpec.IntValue WalrusMax;
        public final ForgeConfigSpec.IntValue WalrusWeight;
        public final ForgeConfigSpec.IntValue WhiteSharkMin;
        public final ForgeConfigSpec.IntValue WhiteSharkMax;
        public final ForgeConfigSpec.IntValue WhiteSharkWeight;
        public final ForgeConfigSpec.IntValue VultureMin;
        public final ForgeConfigSpec.IntValue VultureMax;
        public final ForgeConfigSpec.IntValue VultureWeight;
        public final ForgeConfigSpec.IntValue MuddySwampDirtyPigMin;
        public final ForgeConfigSpec.IntValue MuddySwampDirtyPigMax;
        public final ForgeConfigSpec.IntValue MuddySwampDirtyPigWeight;
        public final ForgeConfigSpec.IntValue DesertLakesHippopotamusMin;
        public final ForgeConfigSpec.IntValue DesertLakesHippopotamusMax;
        public final ForgeConfigSpec.IntValue DesertLakesHippopotamusWeight;
        public final ForgeConfigSpec.IntValue MuddySwampLilygatorMin;
        public final ForgeConfigSpec.IntValue MuddySwampLilygatorMax;
        public final ForgeConfigSpec.IntValue MuddySwampLilygatorWeight;
        public final ForgeConfigSpec.IntValue JellyfishMin;
        public final ForgeConfigSpec.IntValue JellyfishMax;
        public final ForgeConfigSpec.IntValue JellyfishWeight;
        public final ForgeConfigSpec.IntValue LessJellyfishMin;
        public final ForgeConfigSpec.IntValue LessJellyfishMax;
        public final ForgeConfigSpec.IntValue LessJellyfishWeight;

        Spawn(ForgeConfigSpec.Builder builder) {
            builder.push("Spawn Chances.");
            builder.comment("Configure Mob spawn weight & min/max group size. Set weight to 0 to disable.");
            builder.pop();
            builder.push("Muddy Swamp Lilygator");
            MuddySwampLilygatorMin = builder.defineInRange("min", 1, 0, 64);
            MuddySwampLilygatorMax = builder.defineInRange("max", 2, 0, 64);
            MuddySwampLilygatorWeight = builder.defineInRange("weight", 1, 0, 100);
            builder.pop();
            builder.push("Desert Lakes Hippopotamus");
            DesertLakesHippopotamusMin = builder.defineInRange("min", 1, 0, 64);
            DesertLakesHippopotamusMax = builder.defineInRange("max", 4, 0, 64);
            DesertLakesHippopotamusWeight = builder.defineInRange("weight", 12, 0, 100);
            builder.pop();
            builder.push("Muddy Swamp Dirty Pig");
            MuddySwampDirtyPigMin = builder.defineInRange("min", 3, 0, 64);
            MuddySwampDirtyPigMax = builder.defineInRange("max", 6, 0, 64);
            MuddySwampDirtyPigWeight = builder.defineInRange("weight", 99, 0, 100);
            builder.pop();
            builder.push("Jellyfish");
            JellyfishMin = builder.defineInRange("min", 5, 0, 64);
            JellyfishMax = builder.defineInRange("max", 10, 0, 64);
            JellyfishWeight = builder.defineInRange("weight", 10, 0, 100);
            builder.pop();
            builder.push("Less Jellyfish");
            LessJellyfishMin = builder.defineInRange("min", 3, 0, 64);
            LessJellyfishMax = builder.defineInRange("max", 5, 0, 64);
            LessJellyfishWeight = builder.defineInRange("weight", 5, 0, 100);
            builder.pop();
            builder.push("Dirty Pig");
            DirtyPigMin = builder.defineInRange("min", 1, 0, 64);
            DirtyPigMax = builder.defineInRange("max", 4, 0, 64);
            DirtyPigWeight = builder.defineInRange("weight", 11, 0, 100);
            builder.pop();
            builder.push("Brown Bear");
            BrownBearMin = builder.defineInRange("min", 1, 0, 64);
            BrownBearMax = builder.defineInRange("max", 3, 0, 64);
            BrownBearWeight = builder.defineInRange("weight", 9, 0, 100);
            builder.pop();
            builder.push("Bull");
            BullMin = builder.defineInRange("min", 2, 0, 64);
            BullMax = builder.defineInRange("max", 3, 0, 64);
            BullWeight = builder.defineInRange("weight", 9, 0, 100);
            builder.pop();
            builder.push("Eagle");
            EagleMin = builder.defineInRange("min", 1, 0, 64);
            EagleMax = builder.defineInRange("max", 4, 0, 64);
            EagleWeight = builder.defineInRange("weight", 7, 0, 100);
            builder.pop();
            builder.push("Hippopotamus");
            HippopotamusMin = builder.defineInRange("min", 1, 0, 64);
            HippopotamusMax = builder.defineInRange("max", 2, 0, 64);
            HippopotamusWeight = builder.defineInRange("weight", 3, 0, 100);
            builder.pop();
            builder.push("Lilygator");
            LilygatorMin = builder.defineInRange("min", 1, 0, 64);
            LilygatorMax = builder.defineInRange("max", 4, 0, 64);
            LilygatorWeight = builder.defineInRange("weight", 9, 0, 100);
            builder.pop();
            builder.push("Platypus");
            PlatypusMin = builder.defineInRange("min", 1, 0, 64);
            PlatypusMax = builder.defineInRange("max", 4, 0, 64);
            PlatypusWeight = builder.defineInRange("weight", 5, 0, 100);
            builder.pop();
            builder.push("Snail");
            SnailMin = builder.defineInRange("min", 1, 0, 64);
            SnailMax = builder.defineInRange("max", 4, 0, 64);
            SnailWeight = builder.defineInRange("weight", 9, 0, 100);
            builder.pop();
            builder.push("Walrus");
            WalrusMin = builder.defineInRange("min", 1, 0, 64);
            WalrusMax = builder.defineInRange("max", 3, 0, 64);
            WalrusWeight = builder.defineInRange("weight", 9, 0, 100);
            builder.pop();
            builder.push("White Shark");
            WhiteSharkMin = builder.defineInRange("min", 1, 0, 64);
            WhiteSharkMax = builder.defineInRange("max", 2, 0, 64);
            WhiteSharkWeight = builder.defineInRange("weight", 3, 0, 100);
            builder.pop();
            builder.push("Vulture");
            VultureMin = builder.defineInRange("min", 1, 0, 64);
            VultureMax = builder.defineInRange("max", 3, 0, 64);
            VultureWeight = builder.defineInRange("weight", 4, 0, 100);

            builder.pop();
        }
    }

}
