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
import com.cozary.animalia.potions.RegistryObjects;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.LookControl;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.navigation.WaterBoundPathNavigation;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;
import java.util.EnumSet;
import java.util.Random;

public class WhiteSharkEntity extends Monster {

    private static final EntityDataAccessor<Boolean> MOVING = SynchedEntityData.defineId(WhiteSharkEntity.class, EntityDataSerializers.BOOLEAN);
    public static int g = -1;
    public int timeUntilRelax = this.random.nextInt(600) + 100;
    public boolean angry = false;
    protected RandomStrollGoal wander;

    public WhiteSharkEntity(EntityType<? extends WhiteSharkEntity> type, Level worldIn) {
        super(type, worldIn);
        this.xpReward = 20;
        this.setPathfindingMalus(BlockPathTypes.WATER, 0.0F);
        this.moveControl = new MoveHelperController(this);
    }

    public static AttributeSupplier.Builder setCustomAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 20.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.4D)
                .add(Attributes.FOLLOW_RANGE, 25D)
                .add(Attributes.ATTACK_DAMAGE, 0.1D);
    }

    public static boolean canWhiteSharkSpawn(EntityType<? extends WhiteSharkEntity> p_223329_0_, LevelAccessor worldIn, MobSpawnType reason, BlockPos pos, Random p_223329_4_) {
        return pos.getY() > 5 && pos.getY() < worldIn.getSeaLevel();
    }

    @Override
    protected void registerGoals() {
        MoveTowardsRestrictionGoal movetowardsrestrictiongoal = new MoveTowardsRestrictionGoal(this, 1.0D);
        this.wander = new RandomStrollGoal(this, 1.0D, 90);
        this.targetSelector.addGoal(0, new NearestAttackableTargetGoal<>(this, Player.class, true));
        this.goalSelector.addGoal(1, movetowardsrestrictiongoal);
        this.goalSelector.addGoal(2, this.wander);
        this.goalSelector.addGoal(5, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(3, new LookAtPlayerGoal(this, WhiteSharkEntity.class, 12.0F, 0.01F));
        this.goalSelector.addGoal(4, new RandomLookAroundGoal(this));
        this.wander.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
        movetowardsrestrictiongoal.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
        this.goalSelector.addGoal(0, new MeleeAttackGoal(this, 1D, true));
    }

    @Override
    protected float getWaterSlowDown() {
        return 1.5F;
    }

    @Override
    public boolean doHurtTarget(Entity entityIn) {
        boolean flag = super.doHurtTarget(entityIn);
        if (flag && this.getMainHandItem().isEmpty() && entityIn instanceof LivingEntity) {
            float f = this.level.getCurrentDifficultyAt(this.blockPosition()).getEffectiveDifficulty();
            g = g + 1;
            angry = true;
            ((ServerLevel) this.getCommandSenderWorld()).sendParticles(ParticleTypes.ANGRY_VILLAGER, this.getX(), this.getY(), this.getZ(), 40, 1D, 1D, 1D, 0.05);
            ((LivingEntity) entityIn).addEffect(new MobEffectInstance(RegistryObjects.HEALTH_HINDER_EFFECT, 140 * (int) f, g));
            this.timeUntilRelax = this.random.nextInt(600) + 100;
        }

        return flag;
    }

    @Override
    public void tick() {
        super.tick();
        if (!this.level.isClientSide && this.isAlive() && !this.isBaby() && --this.timeUntilRelax <= 0 && angry) {
            ((ServerLevel) this.getCommandSenderWorld()).sendParticles(ParticleTypes.HAPPY_VILLAGER, this.getX(), this.getY(), this.getZ(), 50, 1D, 1D, 1D, 0.05);
            this.playSound(SoundEvents.ZOMBIE_VILLAGER_CONVERTED, 1.0F, (this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F);
            this.timeUntilRelax = this.random.nextInt(600) + 100;
            g = -1;
            angry = false;
        }
        if (this.isAlive()) {
            if (this.isInWaterOrBubble()) {
                this.setAirSupply(300);
            } else if (this.onGround) {
                this.setDeltaMovement(this.getDeltaMovement().add((this.random.nextFloat() * 2.0F - 1.0F) * 0.4F, 0.5D, (this.random.nextFloat() * 2.0F - 1.0F) * 0.4F));
                this.yRotO = this.random.nextFloat() * 360.0F;
                this.onGround = false;
                this.hasImpulse = true;
            }
        }
    }

    /**
     * Returns new PathNavigateGround instance
     */

    @Override
    protected int getExperienceReward(Player player) {
        return 10 + this.level.random.nextInt(4);
    }

    @Override
    protected PathNavigation createNavigation(Level worldIn) {
        return new WaterBoundPathNavigation(this, worldIn);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(MOVING, false);
    }

    @Override
    public boolean canBreatheUnderwater() {
        return true;
    }

    @Override
    public MobType getMobType() {
        return MobType.WATER;
    }

    public boolean isMoving() {
        return this.entityData.get(MOVING);
    }

    private void setMoving(boolean moving) {
        this.entityData.set(MOVING, moving);
    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        return ModSound.WHITE_SHARK_AMBIENT.get();
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return ModSound.WHITE_SHARK_HURT.get();
    }

    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        return ModSound.WHITE_SHARK_DEATH.get();
    }

    @Override
    protected float getStandingEyeHeight(Pose poseIn, EntityDimensions sizeIn) {
        return sizeIn.height * 0.5F;
    }

    @Override
    public float getWalkTargetValue(BlockPos pos, LevelReader worldIn) {
        return worldIn.getFluidState(pos).is(FluidTags.WATER) ? 10.0F + worldIn.getBrightness(pos) - 0.5F : super.getWalkTargetValue(pos, worldIn);
    }

    @Override
    public boolean checkSpawnObstruction(LevelReader worldIn) {
        return worldIn.isUnobstructed(this);
    }

    @Override
    public int getMaxHeadXRot() {
        return 180;
    }


    @Override
    public void travel(Vec3 travelVector) {
        if (this.isEffectiveAi() && this.isInWater()) {
            this.moveRelative(0.1F, travelVector);
            this.move(MoverType.SELF, this.getDeltaMovement());
            this.setDeltaMovement(this.getDeltaMovement().scale(0.9D));
            if (!this.isMoving() && this.getTarget() == null) {
                this.setDeltaMovement(this.getDeltaMovement().add(0.0D, -0.005D, 0.0D));
            }
        } else {
            super.travel(travelVector);
        }

    }

    public boolean canBeLeashedTo(Player player) {
        return false;
    }

    static class MoveHelperController extends MoveControl {
        private final WhiteSharkEntity entityGuardian;

        public MoveHelperController(WhiteSharkEntity guardian) {
            super(guardian);
            this.entityGuardian = guardian;
        }

        @Override
        public void tick() {
            if (this.operation == MoveControl.Operation.MOVE_TO && !this.entityGuardian.getNavigation().isDone()) {
                Vec3 vector3d = new Vec3(this.wantedX - this.entityGuardian.getX(), this.wantedY - this.entityGuardian.getY(), this.wantedZ - this.entityGuardian.getZ());
                double d0 = vector3d.length();
                double d1 = vector3d.x / d0;
                double d2 = vector3d.y / d0;
                double d3 = vector3d.z / d0;
                float f = (float) (Mth.atan2(vector3d.z, vector3d.x) * (double) (180F / (float) Math.PI)) - 90.0F;
                this.entityGuardian.yRotO = this.rotlerp(this.entityGuardian.yRotO, f, 90.0F);
                this.entityGuardian.yBodyRot = this.entityGuardian.yRotO;
                float f1 = (float) (this.speedModifier * this.entityGuardian.getAttributeValue(Attributes.MOVEMENT_SPEED));
                float f2 = Mth.lerp(0.125F, this.entityGuardian.getSpeed(), f1);
                this.entityGuardian.setSpeed(f2);
                double d4 = Math.sin((double) (this.entityGuardian.tickCount + this.entityGuardian.getId()) * 0.5D) * 0.05D;
                double d5 = Math.cos(this.entityGuardian.yRotO * ((float) Math.PI / 180F));
                double d6 = Math.sin(this.entityGuardian.yRotO * ((float) Math.PI / 180F));
                double d7 = Math.sin((double) (this.entityGuardian.tickCount + this.entityGuardian.getId()) * 0.75D) * 0.05D;
                this.entityGuardian.setDeltaMovement(this.entityGuardian.getDeltaMovement().add(d4 * d5, d7 * (d6 + d5) * 0.25D + (double) f2 * d2 * 0.1D, d4 * d6));
                LookControl lookcontroller = this.entityGuardian.getLookControl();
                double d8 = this.entityGuardian.getX() + d1 * 2.0D;
                double d9 = this.entityGuardian.getEyeY() + d2 / d0;
                double d10 = this.entityGuardian.getZ() + d3 * 2.0D;
                double d11 = lookcontroller.getWantedX();
                double d12 = lookcontroller.getWantedY();
                double d13 = lookcontroller.getWantedZ();
                if (!lookcontroller.isHasWanted()) {
                    d11 = d8;
                    d12 = d9;
                    d13 = d10;
                }

                this.entityGuardian.getLookControl().setLookAt(Mth.lerp(0.125D, d11, d8), Mth.lerp(0.125D, d12, d9), Mth.lerp(0.125D, d13, d10), 10.0F, 40.0F);
                this.entityGuardian.setMoving(true);
            } else {
                this.entityGuardian.setSpeed(0.0F);
                this.entityGuardian.setMoving(false);
            }
        }
    }

}

