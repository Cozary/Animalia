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

package com.cozary.animalia.items;

import com.cozary.animalia.entities.JellyfishEntity;
import com.cozary.animalia.init.ModEntityTypes;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.BucketItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import javax.annotation.Nullable;

public class JellyFishBucketItem extends BucketItem {

    private final EntityType<?> fishType;
    private final java.util.function.Supplier<? extends EntityType<?>> fishTypeSupplier;

    @Deprecated
    public JellyFishBucketItem(EntityType<?> fishTypeIn, Fluid fluid, Properties builder) {
        super(fluid, builder);
        this.fishType = fishTypeIn;
        this.fishTypeSupplier = () -> fishTypeIn;
    }

    public JellyFishBucketItem(java.util.function.Supplier<? extends EntityType<?>> fishTypeIn, java.util.function.Supplier<? extends Fluid> fluid, Properties builder) {
        super(fluid, builder);
        this.fishType = null;
        this.fishTypeSupplier = fishTypeIn;
    }

    @Override
    public void checkExtraContent(World worldIn, ItemStack p_203792_2_, BlockPos pos) {
        if (worldIn instanceof ServerWorld) {
            this.placeFish((ServerWorld) worldIn, p_203792_2_, pos);
        }
    }

    @Override
    protected void playEmptySound(@Nullable PlayerEntity player, IWorld worldIn, BlockPos pos) {
        worldIn.playSound(player, pos, SoundEvents.BUCKET_EMPTY_FISH, SoundCategory.NEUTRAL, 1.0F, 1.0F);
    }

    private void placeFish(ServerWorld worldIn, ItemStack stack, BlockPos pos) {
        Entity entity = ModEntityTypes.JELLYFISH.get().spawn(worldIn, stack, null, pos, SpawnReason.BUCKET, true, false);
        if (entity != null) {
            ((JellyfishEntity) entity).setFromBucket(true);
        }
    }

    protected EntityType<?> getFishType() {
        return fishTypeSupplier.get();
    }

}
