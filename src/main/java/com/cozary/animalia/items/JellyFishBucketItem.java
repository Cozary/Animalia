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
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.material.Fluid;

import javax.annotation.Nullable;

public class JellyFishBucketItem extends BucketItem {

    private final EntityType<?> fishType;
    private final java.util.function.Supplier<? extends EntityType<?>> fishTypeSupplier;

    @Deprecated
    public JellyFishBucketItem(EntityType<?> fishTypeIn, Fluid fluid, Item.Properties builder) {
        super(fluid, builder);
        this.fishType = fishTypeIn;
        this.fishTypeSupplier = () -> fishTypeIn;
    }

    public JellyFishBucketItem(java.util.function.Supplier<? extends EntityType<?>> fishTypeIn, java.util.function.Supplier<? extends Fluid> fluid, Item.Properties builder) {
        super(fluid, builder);
        this.fishType = null;
        this.fishTypeSupplier = fishTypeIn;
    }

    @Override
    public void checkExtraContent(@Nullable Player p_150711_, Level worldIn, ItemStack p_203792_2_, BlockPos pos) {
        if (worldIn instanceof ServerLevel) {
            this.placeFish((ServerLevel) worldIn, p_203792_2_, pos);
        }
    }

    @Override
    protected void playEmptySound(@Nullable Player player, LevelAccessor worldIn, BlockPos pos) {
        worldIn.playSound(player, pos, SoundEvents.BUCKET_EMPTY_FISH, SoundSource.NEUTRAL, 1.0F, 1.0F);
    }

    private void placeFish(ServerLevel worldIn, ItemStack stack, BlockPos pos) {
        Entity entity = ModEntityTypes.JELLYFISH.get().spawn(worldIn, stack, null, pos, MobSpawnType.BUCKET, true, false);
        if (entity != null) {
            ((JellyfishEntity) entity).setFromBucket(true);
        }
    }

    protected EntityType<?> getFishType() {
        return fishTypeSupplier.get();
    }

}
