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

import net.minecraft.block.BlockState;
import net.minecraft.block.BreakableBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

public class JellyBlock extends BreakableBlock {
    public JellyBlock() {
        super(Properties.of(Material.CLAY, MaterialColor.GRASS)
                .friction(0.8F)
                .noOcclusion()
                .strength(0f, 1f)
                .sound(SoundType.SLIME_BLOCK)
        );
    }

    @Override
    public void fallOn(World p_180658_1_, BlockPos p_180658_2_, Entity p_180658_3_, float p_180658_4_) {
        if (p_180658_3_.isSuppressingBounce()) {
            super.fallOn(p_180658_1_, p_180658_2_, p_180658_3_, p_180658_4_);
        } else {
            p_180658_3_.causeFallDamage(p_180658_4_, 0.0F);
        }

    }

    @Override
    public void updateEntityAfterFallOn(IBlockReader p_176216_1_, Entity p_176216_2_) {
        if (p_176216_2_.isSuppressingBounce()) {
            super.updateEntityAfterFallOn(p_176216_1_, p_176216_2_);
        } else {
            this.bounceUp(p_176216_2_);
        }

    }

    private void bounceUp(Entity p_226946_1_) {
        Vector3d vector3d = p_226946_1_.getDeltaMovement();
        if (vector3d.y < 0.0D) {
            double d0 = p_226946_1_ instanceof LivingEntity ? 1.0D : 0.8D;
            p_226946_1_.setDeltaMovement(vector3d.x, -vector3d.y * d0, vector3d.z);
        }

    }

    @Override
    public void stepOn(World p_176199_1_, BlockPos p_176199_2_, Entity p_176199_3_) {
        double d0 = Math.abs(p_176199_3_.getDeltaMovement().y);
        if (d0 < 0.1D && !p_176199_3_.isSteppingCarefully()) {
            double d1 = 0.4D + d0 * 0.2D;
            p_176199_3_.setDeltaMovement(p_176199_3_.getDeltaMovement().multiply(d1, 1.0D, d1));
        }

        super.stepOn(p_176199_1_, p_176199_2_, p_176199_3_);
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
