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

package com.cozary.animalia.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.HalfTransparentBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraft.world.phys.Vec3;

public class JellyBlock extends HalfTransparentBlock {
    public JellyBlock() {
        super(Properties.of(Material.CLAY, MaterialColor.GRASS)
                .friction(0.8F)
                .noOcclusion()
                .strength(0f, 1f)
                .sound(SoundType.SLIME_BLOCK)
        );
    }

    @Override
    public void fallOn(Level p_154567_, BlockState p_154568_, BlockPos p_154569_, Entity p_154570_, float p_154571_) {
        if (p_154570_.isSuppressingBounce()) {
            super.fallOn(p_154567_, p_154568_, p_154569_, p_154570_, p_154571_);
        } else {
            p_154570_.causeFallDamage(p_154571_, 0.0F, DamageSource.FALL);
        }

    }

    @Override
    public void updateEntityAfterFallOn(BlockGetter p_176216_1_, Entity p_176216_2_) {
        if (p_176216_2_.isSuppressingBounce()) {
            super.updateEntityAfterFallOn(p_176216_1_, p_176216_2_);
        } else {
            this.bounceUp(p_176216_2_);
        }

    }

    private void bounceUp(Entity p_226946_1_) {
        Vec3 vector3d = p_226946_1_.getDeltaMovement();
        if (vector3d.y < 0.0D) {
            double d0 = p_226946_1_ instanceof LivingEntity ? 1.0D : 0.8D;
            p_226946_1_.setDeltaMovement(vector3d.x, -vector3d.y * d0, vector3d.z);
        }

    }

    @Override
    public void stepOn(Level p_154573_, BlockPos p_154574_, BlockState p_154575_, Entity p_154576_) {
        double d0 = Math.abs(p_154576_.getDeltaMovement().y);
        if (d0 < 0.1D && !p_154576_.isSteppingCarefully()) {
            double d1 = 0.4D + d0 * 0.2D;
            p_154576_.setDeltaMovement(p_154576_.getDeltaMovement().multiply(d1, 1.0D, d1));
        }

        super.stepOn(p_154573_, p_154574_, p_154575_, p_154576_);
    }

    @Override
    public boolean isSlimeBlock(BlockState state) {
        return true;
    }

    @Override
    public boolean isStickyBlock(BlockState state) {
        return true;
    }
}
