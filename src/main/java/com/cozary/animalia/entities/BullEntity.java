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
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.TimeUtil;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.ResetUniversalAngerTargetGoal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
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

import javax.annotation.Nullable;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.UUID;

public class BullEntity extends Animal implements NeutralMob {

    public static final Ingredient TEMPTATION_ITEMS = Ingredient.of(Items.WHEAT);
    private static final UniformInt field_234217_by_ = TimeUtil.rangeOfSeconds(20, 39);
    public static EntityDataAccessor<Boolean> AGRESSIVE = SynchedEntityData.defineId(BullEntity.class, EntityDataSerializers.BOOLEAN);
    private int field_234284_bA_;
    private UUID field_234285_bB_;

    public BullEntity(EntityType<? extends BullEntity> type, Level worldIn) {
        super(type, worldIn);
    }

    public static AttributeSupplier.Builder setCustomAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 10.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.25D)
                .add(Attributes.ATTACK_KNOCKBACK, 8D)
                .add(Attributes.ATTACK_DAMAGE, 1F)
                .add(Attributes.ATTACK_SPEED, 0.7F);
    }

    /**
     * Uses for Spawn
     */

    public static boolean canBullSpawn(EntityType<? extends BullEntity> entityType, LevelAccessor world, MobSpawnType spawnReason, BlockPos pos, Random random) {
        return world.getBlockState(pos.below()).is(Blocks.GRASS_BLOCK) && world.getLightEmission(pos) > 8 && world.canSeeSky(pos);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(5, new FloatGoal(this));
        this.goalSelector.addGoal(6, new RandomStrollGoal(this, 1.0D));
        this.goalSelector.addGoal(2, new EatBlockGoal(this));
        this.goalSelector.addGoal(3, new MeleeBull());
        this.goalSelector.addGoal(4, new LookAtPlayerGoal(this, Player.class, 5.0F));
        this.goalSelector.addGoal(0, new AttackPlayerGoal());
        this.goalSelector.addGoal(4, new TemptGoal(this, 1.2D, TEMPTATION_ITEMS, false));
        this.goalSelector.addGoal(5, new FollowParentGoal(this, 1.1D));
        this.goalSelector.addGoal(10, new BreedGoal(this, 1.0D));
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(7, new ResetUniversalAngerTargetGoal<>(this, false));
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
    protected int getExperienceReward(Player player) {
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

    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor worldIn, DifficultyInstance difficultyIn, MobSpawnType reason, @Nullable SpawnGroupData spawnDataIn, @Nullable CompoundTag dataTag) {
        if (spawnDataIn == null) {
            spawnDataIn = new AgeableMob.AgeableMobGroupData(0.1F);
        }

        return super.finalizeSpawn(worldIn, difficultyIn, reason, spawnDataIn, dataTag);
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(ServerLevel p_146743_, AgeableMob p_146744_) {
        return ModEntityTypes.BULL.get().create(this.level);
    }

    /**
     * Breeding
     */

    public boolean isBreedingItem(ItemStack stack) {
        return TEMPTATION_ITEMS.test(stack);
    }

    public boolean canBeLeashedTo(Player player) {
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
    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        this.entityData.define(AGRESSIVE, false);
        this.readPersistentAngerSaveData(this.level, compound);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compound) {
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
        this.setRemainingPersistentAngerTime(field_234217_by_.sample(this.random));

    }

    class AttackPlayerGoal extends Goal {
        private final TargetingConditions field_220842_b = (TargetingConditions.DEFAULT).range(5.0D);
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
                List<Player> list = BullEntity.this.level.getNearbyPlayers(this.field_220842_b, BullEntity.this, BullEntity.this.getBoundingBox().inflate(5.0D, 5.0D, 5.0D));
                if (!list.isEmpty()) {
                    list.sort(Comparator.comparing(Entity::getEyeY).reversed()); //getY

                    for (Player playerentity : list) {
                        if (BullEntity.this.canAttack(playerentity, TargetingConditions.DEFAULT) && !BullEntity.this.isBaby()) {
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
            return livingentity != null && BullEntity.this.canAttack(livingentity, TargetingConditions.DEFAULT);
        }
    }

    class MeleeBull extends net.minecraft.world.entity.ai.goal.MeleeAttackGoal {

        public MeleeBull() {
            super(BullEntity.this, 2D, true);

        }

        @Override
        protected void checkAndPerformAttack(LivingEntity enemy, double distToEnemySqr) {
            BullEntity.this.setItemInHand(InteractionHand.MAIN_HAND, ItemStack.EMPTY);
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

