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

import com.cozary.animalia.entities.VultureEntity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.entity.EntityEvent;

public class VultureEvent extends EntityEvent {

    public VultureEvent(VultureEntity entity) {
        super(entity);
    }

    public VultureEntity getSummoner() {
        return (VultureEntity) getEntity();
    }

    @HasResult
    public static class SummonAidEvent extends VultureEvent {
        private final Level world;
        private final int x;
        private final int y;
        private final int z;
        private final LivingEntity attacker;
        private final double summonChance;
        private VultureEntity customSummonedAid;

        public SummonAidEvent(VultureEntity entity, Level world, int x, int y, int z, LivingEntity attacker, double summonChance) {
            super(entity);
            this.world = world;
            this.x = x;
            this.y = y;
            this.z = z;
            this.attacker = attacker;
            this.summonChance = summonChance;
        }

        /**
         * Populate this field to have a custom zombie instead of a normal zombie summoned
         */

        public VultureEntity getCustomSummonedAidMod() {
            return customSummonedAid;
        }

        public void setCustomSummonedAid(VultureEntity customSummonedAid) {
            this.customSummonedAid = customSummonedAid;
        }

        public Level getWorld() {
            return world;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }

        public int getZ() {
            return z;
        }

        public LivingEntity getAttacker() {
            return attacker;
        }

        public double getSummonChance() {
            return summonChance;
        }
    }

}
