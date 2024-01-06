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

import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;

public class DataUtil {
    public DataUtil() {
    }

    public static boolean matchesKeys(ResourceLocation loc, ResourceKey<?>... keys) {
        ResourceKey[] var2 = keys;
        int var3 = keys.length;

        for (int var4 = 0; var4 < var3; ++var4) {
            ResourceKey<?> key = var2[var4];
            if (key.location().equals(loc)) {
                return true;
            }
        }

        return false;
    }
}
