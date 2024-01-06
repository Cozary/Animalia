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

import com.cozary.animalia.init.ModEntityTypes;
import com.cozary.animalia.init.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.Random;

public class DirtyPigEntity extends Animal {

    public static final Ingredient TEMPTATION_ITEMS = Ingredient.of(Items.CARROT, Items.POTATO, Items.BEETROOT);
    public int timeUntilNextMud = this.random.nextInt(3600) + 3600;

    public DirtyPigEntity(EntityType<? extends Animal> type, Level worldIn) {
        super(type, worldIn);
        this.setPathfindingMalus(BlockPathTypes.WATER, 0.0F);
        this.setPathfindingMalus(BlockPathTypes.WATER_BORDER, 16.0F);
    }

    public static AttributeSupplier.Builder setCustomAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 10.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.25D);
    }

    /**
     * Uses for Spawn
     */

    public static boolean canDirtyPigSpawn(EntityType<? extends DirtyPigEntity> animal, LevelAccessor world, MobSpawnType reason, BlockPos pos, Random random) {
        return world.getBlockState(pos.below()).is(Blocks.GRASS_BLOCK) || world.getBlockState(pos.below()).is(Blocks.LILY_PAD) && world.getLightEmission(pos) > 8 && world.canSeeSky(pos);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag p_28243_) {
        super.readAdditionalSaveData(p_28243_);
        if (p_28243_.contains("EggLayTime")) {
            this.timeUntilNextMud = p_28243_.getInt("EggLayTime");
        }

    }

    @Override

    public void addAdditionalSaveData(CompoundTag p_28257_) {
        super.addAdditionalSaveData(p_28257_);
        p_28257_.putInt("EggLayTime", this.timeUntilNextMud);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(0, new PanicGoal(this, 1.50D));
        this.goalSelector.addGoal(1, new FloatGoal(this));
        this.goalSelector.addGoal(2, new RandomStrollGoal(this, 1.0D));
        this.goalSelector.addGoal(3, new TemptGoal(this, 1.2D, Ingredient.of(Items.CARROT_ON_A_STICK), false));
        this.goalSelector.addGoal(4, new TemptGoal(this, 1.2D, TEMPTATION_ITEMS, false));
        this.goalSelector.addGoal(5, new FollowParentGoal(this, 1.1D));
        this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 6.0F));
        this.goalSelector.addGoal(7, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(8, new TryFindWaterGoal(this));
        this.goalSelector.addGoal(9, new RandomSwimmingGoal(this, 1.0D, 10));
        this.goalSelector.addGoal(10, new BreedGoal(this, 1.0D));

    }

    @Override
    protected int getExperienceReward(Player player) {
        return 1 + this.level.random.nextInt(4);
    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        return SoundEvents.PIG_AMBIENT;
    }

    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.PIG_DEATH;
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return SoundEvents.PIG_HURT;
    }

    @Nullable
    @Override
    protected void playStepSound(BlockPos pos, BlockState blockIn) {
        this.playSound(SoundEvents.PIG_STEP, 0.15F, 0.8F);
    }

    /**
     * Kids
     *
     * @return
     */

    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor worldIn, DifficultyInstance difficultyIn, MobSpawnType reason, @Nullable SpawnGroupData spawnDataIn, @Nullable CompoundTag dataTag) {
        if (spawnDataIn == null) {
            spawnDataIn = new AgeableMobGroupData(0.1F);
        }

        return super.finalizeSpawn(worldIn, difficultyIn, reason, spawnDataIn, dataTag);
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(ServerLevel p_146743_, AgeableMob p_146744_) {
        return ModEntityTypes.DIRTY_PIG.get().create(this.level);
    }

    public boolean canBeLeashedTo(Player player) {
        return true;
    }

    /**
     * Breeding
     */

    public boolean isBreedingItem(ItemStack stack) {
        return TEMPTATION_ITEMS.test(stack);
    }

    @OnlyIn(Dist.CLIENT)
    public Vec3 func_241205_ce_() {
        return new Vec3(0.0D, 0.6F * this.getEyeHeight(), this.getBbWidth() * 0.4F);
    }

    /**
     * This counts ticks. Im using for making the Pig poop Mud.
     */

    @Override
    public void tick() {
        super.tick();
        if (!this.level.isClientSide && this.isAlive() && !this.isBaby() && --this.timeUntilNextMud <= 0) {
            this.playSound(SoundEvents.HONEY_BLOCK_STEP, 1.0F, (this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F);
            this.spawnAtLocation(ModItems.MUD.get());
            this.timeUntilNextMud = this.random.nextInt(3600) + 3600;
        }
    }

    @Override
    public boolean canBreatheUnderwater() {
        return true;
    }

}

