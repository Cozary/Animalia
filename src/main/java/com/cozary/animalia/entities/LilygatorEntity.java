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

package com.cozary.animalia.entities;

import com.cozary.animalia.init.ModSound;
import net.minecraft.core.BlockPos;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.Pig;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nullable;
import java.util.Random;

public class LilygatorEntity extends Animal {
    public static EntityDataAccessor<Boolean> AGRESSIVE = SynchedEntityData.defineId(LilygatorEntity.class, EntityDataSerializers.BOOLEAN);

    public LilygatorEntity(EntityType<? extends Animal> type, Level worldIn) {
        super(type, worldIn);
        entityData.define(AGRESSIVE, false);
    }

    public static AttributeSupplier.Builder setCustomAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 15.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.3D)
                .add(Attributes.ATTACK_DAMAGE, 7.0D)
                .add(Attributes.FOLLOW_RANGE, 10D)
                .add(Attributes.ARMOR, 4);
    }

    /**
     * Uses for Spawn
     */

    public static boolean canLilygatorSpawn(EntityType<? extends LilygatorEntity> entityType, LevelAccessor world, MobSpawnType spawnReason, BlockPos pos, Random random) {
        return world.getBlockState(pos.below()).getBlock() == Blocks.GRASS_BLOCK || world.getBlockState(pos.below()).getBlock() == Blocks.LILY_PAD && world.getLightEmission(pos) > 8 && world.canSeeSky(pos);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(4, new FloatGoal(this));
        this.goalSelector.addGoal(1, new RandomStrollGoal(this, 1.0D));
        this.goalSelector.addGoal(2, new LookAtPlayerGoal(this, Player.class, 10.0F));
        this.goalSelector.addGoal(3, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(0, new MeleeAttackGoal(this, 1.5D, true));
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Player.class, true));
        this.targetSelector.addGoal(0, new NearestAttackableTargetGoal<>(this, DirtyPigEntity.class, true));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Pig.class, true));
    }

    public boolean canBreatheUnderwater() {
        return true;
    }

    @Override
    protected float getWaterSlowDown() {
        return 1F;
    }

    @Override
    protected int getExperienceReward(Player player) {
        return 4 + this.level.random.nextInt(4);
    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        return ModSound.LILYGATOR_AMBIENT.get();
    }

    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        return ModSound.LILYGATOR_DEATH.get();
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return ModSound.LILYGATOR_ROAR.get();
    }

    @Nullable
    @Override
    protected void playStepSound(BlockPos pos, BlockState blockIn) {
        this.playSound(SoundEvents.PIG_STEP, 0.15F, 0.5F);
    }

    /**
     * Kids
     *
     * @return
     */

    private boolean isAgressiveL() {
        return this.targetSelector.getRunningGoals().anyMatch(goal -> goal.getGoal().getClass() == NearestAttackableTargetGoal.class);
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(ServerLevel p_146743_, AgeableMob p_146744_) {
        return null;
    }

    @Override
    public void aiStep() {
        super.aiStep();
        if (!level.isClientSide) {
            if (isAgressiveL() && !entityData.get(AGRESSIVE)) {
                entityData.set(AGRESSIVE, true);

            } else if (!isAgressiveL() && entityData.get(AGRESSIVE)) {
                entityData.set(AGRESSIVE, false);
            }
        }
    }

    public boolean canBeLeashedTo(Player player) {
        return false;
    }

}

