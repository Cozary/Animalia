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
import com.cozary.animalia.init.ModSound;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import javax.annotation.Nullable;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.UUID;


public class BullEntity extends AnimalEntity implements IAngerable {

    public static final Ingredient TEMPTATION_ITEMS = Ingredient.of(Items.WHEAT);
    private static final RangedInteger field_234217_by_ = TickRangeConverter.rangeOfSeconds(20, 39);
    public static DataParameter<Boolean> AGRESSIVE = EntityDataManager.defineId(BullEntity.class, DataSerializers.BOOLEAN);
    private int field_234284_bA_;
    private UUID field_234285_bB_;

    public BullEntity(EntityType<? extends BullEntity> type, World worldIn) {
        super(type, worldIn);
    }

    public static AttributeModifierMap.MutableAttribute setCustomAttributes() {
        return MobEntity.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 10.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.25D)
                .add(Attributes.ATTACK_KNOCKBACK, 8D)
                .add(Attributes.ATTACK_DAMAGE, 1F)
                .add(Attributes.ATTACK_SPEED, 0.7F);
    }

    /**
     * Uses for Spawn
     */

    public static boolean canBullSpawn(EntityType<? extends BullEntity> entityType, IWorld world, SpawnReason spawnReason, BlockPos pos, Random random) {
        return world.getBlockState(pos.below()).is(Blocks.GRASS_BLOCK) && world.getLightEmission(pos) > 8 && world.canSeeSky(pos);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(5, new SwimGoal(this));
        this.goalSelector.addGoal(6, new RandomWalkingGoal(this, 1.0D));
        this.goalSelector.addGoal(2, new EatGrassGoal(this));
        this.goalSelector.addGoal(3, new MeleeBull());
        this.goalSelector.addGoal(4, new LookAtGoal(this, PlayerEntity.class, 5.0F));
        this.goalSelector.addGoal(0, new AttackPlayerGoal());
        this.goalSelector.addGoal(4, new TemptGoal(this, 1.2D, false, TEMPTATION_ITEMS));
        this.goalSelector.addGoal(5, new FollowParentGoal(this, 1.1D));
        this.goalSelector.addGoal(10, new BreedGoal(this, 1.0D));
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(7, new ResetAngerGoal<>(this, false));
    }


    /**
     * Attack Animation
     * <p>
     * <p>
     * public void setAttack(boolean attack) {
     * this.entityData.set(AGRESSIVE, attack);
     * }
     */


    @Override
    protected int getExperienceReward(PlayerEntity player) {
        return 2 + this.level.random.nextInt(4);
    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        return ModSound.BULL_AMBIENT.get();
    }

    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        return ModSound.BULL_DEATH.get();
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return ModSound.BULL_HURT.get();
    }

    @Nullable
    @Override
    protected void playStepSound(BlockPos pos, BlockState blockIn) {
        this.playSound(SoundEvents.COW_STEP, 0.15F, 1.0F);
    }

    /**
     * Kids
     *
     * @return
     */

    @Nullable
    @Override
    public AgeableEntity getBreedOffspring(ServerWorld p_241840_1_, AgeableEntity p_241840_2_) {
        return ModEntityTypes.BULL.get().create(this.level);
    }

    @Override
    public ILivingEntityData finalizeSpawn(IServerWorld worldIn, DifficultyInstance difficultyIn, SpawnReason reason, @Nullable ILivingEntityData spawnDataIn, @Nullable CompoundNBT dataTag) {
        if (spawnDataIn == null) {
            spawnDataIn = new AgeableData(0.1F);
        }

        return super.finalizeSpawn(worldIn, difficultyIn, reason, spawnDataIn, dataTag);
    }

    /**
     * Breeding
     */

    public boolean isBreedingItem(ItemStack stack) {
        return TEMPTATION_ITEMS.test(stack);
    }

    public boolean canBeLeashedTo(PlayerEntity player) {
        return true;
    }

    /**
     * Check if is Agressive
     *
     * @return private boolean isAgressiveL() {
    return this.targetSelector.getRunningGoals().anyMatch(goal -> goal.getGoal().getClass() == AttackPlayerGoal.class);
    }

     @Override public void tick() {
     super.tick();
     if (!level.isClientSide) {

     entityData.set(AGRESSIVE, isAgressiveL());
     }
     }*/

    /**
     * ANGER FUNCTIONS
     *
     * @return
     */

    @Override
    public void readAdditionalSaveData(CompoundNBT compound) {
        super.readAdditionalSaveData(compound);
        this.entityData.define(AGRESSIVE, false);
        this.readPersistentAngerSaveData((ServerWorld) this.level, compound);
    }

    @Override
    public void addAdditionalSaveData(CompoundNBT compound) {
        super.addAdditionalSaveData(compound);
        this.addPersistentAngerSaveData(compound);
    }

    @Override
    public int getRemainingPersistentAngerTime() {
        return this.field_234284_bA_;
    }

    @Override
    public void setRemainingPersistentAngerTime(int time) {
        this.field_234284_bA_ = time;

    }

    @Nullable
    @Override
    public UUID getPersistentAngerTarget() {
        return this.field_234285_bB_;
    }

    @Override
    public void setPersistentAngerTarget(@Nullable UUID target) {
        this.field_234285_bB_ = target;

    }

    @Override
    public void startPersistentAngerTimer() {
        this.setRemainingPersistentAngerTime(field_234217_by_.randomValue(this.random));

    }

    class AttackPlayerGoal extends Goal {
        private final EntityPredicate field_220842_b = (new EntityPredicate()).range(5.0D);
        private int tickDelay = 60;

        private AttackPlayerGoal() {
        }

        /**
         * Returns whether execution should begin. You can also read and cache any state necessary for execution in this
         * method as well.
         */
        public boolean canUse() {
            if (this.tickDelay > 0) {
                --this.tickDelay;
                return false;
            } else {
                this.tickDelay = 60;
                List<PlayerEntity> list = BullEntity.this.level.getNearbyPlayers(this.field_220842_b, BullEntity.this, BullEntity.this.getBoundingBox().inflate(5.0D, 5.0D, 5.0D));
                if (!list.isEmpty()) {
                    list.sort(Comparator.comparing(Entity::getEyeY).reversed()); //getY

                    for (PlayerEntity playerentity : list) {
                        if (BullEntity.this.canAttack(playerentity, EntityPredicate.DEFAULT) && !BullEntity.this.isBaby()) {
                            BullEntity.this.setTarget(playerentity);
                            return true;
                        }
                    }
                }

                return false;
            }
        }

        /**
         * Returns whether an in-progress EntityAIBase should continue executing
         */
        public boolean canContinueToUse() {
            LivingEntity livingentity = BullEntity.this.getTarget();
            return livingentity != null && BullEntity.this.canAttack(livingentity, EntityPredicate.DEFAULT);
        }
    }

    class MeleeBull extends MeleeAttackGoal {

        public MeleeBull() {
            super(BullEntity.this, 2D, true);

        }

        @Override
        protected void checkAndPerformAttack(LivingEntity enemy, double distToEnemySqr) {
            BullEntity.this.setItemInHand(Hand.MAIN_HAND, ItemStack.EMPTY);
            double d0 = this.getAttackReachSqr(enemy);
            if (distToEnemySqr <= d0) {
                this.resetAttackCooldown();
                this.mob.doHurtTarget(enemy);
            } else if (distToEnemySqr <= d0 * 2.0D) {
                if (this.isTimeToAttack()) {
                    this.resetAttackCooldown();
                }

                if (this.getTicksUntilNextAttack() <= 5) {
                }
            } else {
                this.resetAttackCooldown();
            }

        }

        /**
         * Reset the task's internal state. Called when this task is interrupted by another one
         */
        public void stop() {
            super.stop();
        }

        @Override
        protected double getAttackReachSqr(LivingEntity attackTarget) {
            return 4.0F + attackTarget.getBbWidth();
        }
    }

}

