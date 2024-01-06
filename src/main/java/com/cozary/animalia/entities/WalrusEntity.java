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
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.util.TimeUtil;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.goal.target.ResetUniversalAngerTargetGoal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.PolarBear;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.Random;
import java.util.UUID;

public class WalrusEntity extends Animal implements NeutralMob {

    public static final Ingredient TEMPTATION_ITEMS = Ingredient.of(Items.TROPICAL_FISH, Items.PUFFERFISH, Items.COD, Items.SALMON);
    //private static final DataParameter<Boolean> IS_STANDING = EntityDataManager.defineId(WalrusEntity.class, DataSerializers.BOOLEAN);
    private static final UniformInt field_234217_by_ = TimeUtil.rangeOfSeconds(20, 39);
    private static final boolean test = false;
    private float clientSideStandAnimation0;
    private float clientSideStandAnimation;
    private int warningSoundTicks;
    private int field_234218_bz_;
    private UUID field_234216_bA_;

    public WalrusEntity(EntityType<? extends WalrusEntity> type, Level worldIn) {
        super(type, worldIn);
    }

    public static AttributeSupplier.Builder setCustomAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 35.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.25D)
                .add(Attributes.FOLLOW_RANGE, 20.0D)
                .add(Attributes.ATTACK_DAMAGE, 6.0D);
    }

    public static boolean canWalrusSpawn(EntityType<? extends WalrusEntity> entityType, LevelAccessor world, MobSpawnType spawnReason, BlockPos pos, Random random) {
        return world.getBlockState(pos.below()).getBlock() == Blocks.ICE || world.getBlockState(pos.below()).getBlock() == Blocks.SNOW || world.getBlockState(pos.below()).getBlock() == Blocks.SNOW_BLOCK || world.getBlockState(pos.below()).getBlock() == Blocks.BLUE_ICE && world.getLightEmission(pos) > 8 && world.canSeeSky(pos);

    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(9, new FloatGoal(this));
        this.goalSelector.addGoal(1, new RandomStrollGoal(this, 1.0D));
        this.goalSelector.addGoal(2, new TemptGoal(this, 1.2D, TEMPTATION_ITEMS, false));
        this.goalSelector.addGoal(3, new FollowParentGoal(this, 1.1D));
        this.goalSelector.addGoal(4, new LookAtPlayerGoal(this, Player.class, 6.0F));
        this.goalSelector.addGoal(5, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(6, new TryFindWaterGoal(this));
        this.goalSelector.addGoal(7, new BreedGoal(this, 1.0D));
        this.goalSelector.addGoal(8, new FloatGoal(this));
        this.goalSelector.addGoal(0, new MeleeAttackGoal(this, 1.25D, true));
        this.targetSelector.addGoal(0, new HurtByTargetGoal());
        this.targetSelector.addGoal(1, new AttackPlayerGoal());
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, 10, true, false, this::isAngryAt));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, PolarBear.class, 10, true, true, null));
        this.targetSelector.addGoal(4, new ResetUniversalAngerTargetGoal<>(this, false));
    }

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */


    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        this.readPersistentAngerSaveData(this.level, compound);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        //this.entityData.define(IS_STANDING, false);
        this.addPersistentAngerSaveData(compound);
    }

    @Override
    public int getRemainingPersistentAngerTime() {
        return this.field_234218_bz_;
    }

    @Override
    public void setRemainingPersistentAngerTime(int time) {
        this.field_234218_bz_ = time;

    }

    @Nullable
    @Override
    public UUID getPersistentAngerTarget() {
        return this.field_234216_bA_;
    }

    @Override
    public void setPersistentAngerTarget(@Nullable UUID target) {
        this.field_234216_bA_ = target;

    }

    @Override
    public void startPersistentAngerTimer() {
        this.setRemainingPersistentAngerTime(field_234217_by_.sample(this.random));

    }

    /**
     * Called to update the entity's position/logic.
     */
    public void tick() {
        super.tick();
        if (this.level.isClientSide) {
            if (this.clientSideStandAnimation != this.clientSideStandAnimation0) {
                this.refreshDimensions();
            }

            this.clientSideStandAnimation0 = this.clientSideStandAnimation;
            //if (this.isStanding()) {
            //    this.clientSideStandAnimation = MathHelper.clamp(this.clientSideStandAnimation + 1.0F, 0.0F, 6.0F);
            //} else {
            this.clientSideStandAnimation = Mth.clamp(this.clientSideStandAnimation - 1.0F, 0.0F, 6.0F);
            //}
        }

        if (this.warningSoundTicks > 0) {
            --this.warningSoundTicks;
        }

        if (!this.level.isClientSide) {
            this.updatePersistentAnger((ServerLevel) this.level, true);
        }

    }

    public EntityDimensions getDimensions(Pose poseIn) {
        if (this.clientSideStandAnimation > 0.0F) {
            float f = this.clientSideStandAnimation / 6.0F;
            float f1 = 1.0F + f;
            return super.getDimensions(poseIn).scale(1.0F, f1);
        } else {
            return super.getDimensions(poseIn);
        }
    }

    public boolean doHurtTarget(Entity entityIn) {
        boolean flag = entityIn.hurt(DamageSource.mobAttack(this), (float) ((int) this.getAttributeValue(Attributes.ATTACK_DAMAGE)));
        if (flag) {
            this.doEnchantDamageEffects(this, entityIn);
        }

        return flag;
    }

    // public boolean isStanding() {
    //    return this.entityData.get(IS_STANDING);
    //}

    //public void setStanding(boolean standing) {
    //     this.entityData.set(IS_STANDING, standing);
    //}

    // @OnlyIn(Dist.CLIENT)
    // public float getStandingAnimationScale(float p_189795_1_) {
    //     return MathHelper.lerp(p_189795_1_, this.clientSideStandAnimation0, this.clientSideStandAnimation) / 6.0F;
    // }

    protected float getWaterSlowDown() {
        return 0.98F;
    }

    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor worldIn, DifficultyInstance difficultyIn, MobSpawnType reason, @Nullable SpawnGroupData spawnDataIn, @Nullable CompoundTag dataTag) {
        if (spawnDataIn == null) {
            spawnDataIn = new AgeableMobGroupData(0.5F);
        }

        return super.finalizeSpawn(worldIn, difficultyIn, reason, spawnDataIn, dataTag);
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(ServerLevel p_146743_, AgeableMob p_146744_) {
        return ModEntityTypes.WALRUS.get().create(this.level);
    }

    @Override
    protected int getExperienceReward(Player player) {
        return 3 + this.level.random.nextInt(4);
    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        return ModSound.WALRUS_AMBIENT.get();
    }

    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        return ModSound.WALRUS_DEATH.get();
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return ModSound.WALRUS_HURT.get();
    }

    @Nullable
    @Override
    protected void playStepSound(BlockPos pos, BlockState blockIn) {
        this.playSound(SoundEvents.
                PIG_STEP, 0.15F, 0.5F);
    }

    public boolean isBreedingItem(ItemStack stack) {
        return TEMPTATION_ITEMS.test(stack);
    }

    @OnlyIn(Dist.CLIENT)
    public Vec3 func_241205_ce_() {
        return new Vec3(0.0D, 0.6F * this.getEyeHeight(), this.getBbWidth() * 0.4F);
    }

    public boolean canBeLeashedTo(Player player) {
        return false;
    }

    @Override
    public boolean canBreatheUnderwater() {
        return true;
    }

    class AttackPlayerGoal extends NearestAttackableTargetGoal<Player> {
        public AttackPlayerGoal() {
            super(WalrusEntity.this, Player.class, 20, true, true, null);
        }

        public boolean canUse() {
            if (WalrusEntity.this.isBaby()) {
                return false;
            } else {
                if (super.canUse()) {
                    for (WalrusEntity walrusentity : WalrusEntity.this.level.getEntitiesOfClass(WalrusEntity.class, WalrusEntity.this.getBoundingBox().inflate(8.0D, 4.0D, 8.0D))) {
                        if (walrusentity.isBaby()) {
                            return true;
                        }
                    }
                }

                return false;
            }
        }

        @Override
        protected double getFollowDistance() {
            return super.getFollowDistance() * 0.5D;
        }
    }

    class HurtByTargetGoal extends net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal {
        public HurtByTargetGoal() {
            super(WalrusEntity.this);
        }
    }

}
