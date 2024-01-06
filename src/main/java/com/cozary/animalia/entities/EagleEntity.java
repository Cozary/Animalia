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

import com.cozary.animalia.entities.ModTypes.FlyingMobEntity;
import com.cozary.animalia.init.ModSound;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.BodyRotationControl;
import net.minecraft.world.entity.ai.control.LookControl;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.Cat;
import net.minecraft.world.entity.animal.Chicken;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.Comparator;
import java.util.EnumSet;
import java.util.List;
import java.util.Random;

public class EagleEntity extends FlyingMobEntity implements Enemy {

    Vec3 moveTargetPoint = Vec3.ZERO;
    BlockPos anchorPoint = BlockPos.ZERO;
    AttackPhase attackPhase = AttackPhase.CIRCLE;

    public EagleEntity(EntityType<? extends Animal> type, Level worldIn) {
        super(type, worldIn);
        this.xpReward = 5;
        this.moveControl = new MoveHelperController(this);
        this.lookControl = new LookHelperController(this);
    }

    public static AttributeSupplier.Builder setCustomAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 10.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.25D)
                .add(Attributes.ATTACK_DAMAGE, 3D);
    }

    /**
     * Uses for Spawn
     */

    public static boolean canEagleSpawn(EntityType<? extends EagleEntity> typeIn, LevelAccessor world, MobSpawnType reason, BlockPos pos, Random random) {
        BlockPos blockpos = pos.below();
        return reason == MobSpawnType.SPAWNER || world.getBlockState(blockpos).isValidSpawn(world, blockpos, typeIn);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(0, new PhantomAttackStrategyGoal());
        this.goalSelector.addGoal(1, new SweepAttackGoal());
        this.goalSelector.addGoal(2, new PhantomCircleAroundAnchorGoal());
        this.targetSelector.addGoal(1, new PhantomAttackPlayerTargetGoal());
        this.targetSelector.addGoal(0, new AttackChickenGoal());
    }

    @Override
    protected BodyRotationControl createBodyControl() {
        return new BodyHelperController(this);
    }

/*
    private void updatePhantomSize() {
        this.refreshDimensions();
        this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(6 + this.getPhantomSize());
    }

    public int getPhantomSize() {
        return this.entityData.get(SIZE);
    }

    public void setPhantomSize(int sizeIn) {
        this.entityData.set(SIZE, MathHelper.clamp(sizeIn, 0, 64));
    }*/

    public void onSyncedDataUpdated(EntityDataAccessor<?> key) {
        /*if (SIZE.equals(key)) {
            this.updatePhantomSize();
        }*/
        super.onSyncedDataUpdated(key);
    }

    @Override
    protected void customServerAiStep() {
        super.customServerAiStep();
    }

    public SpawnGroupData finalizeSpawn(ServerLevelAccessor worldIn, DifficultyInstance difficultyIn, MobSpawnType reason, @Nullable SpawnGroupData spawnDataIn, @Nullable CompoundTag dataTag) {
        this.anchorPoint = this.blockPosition().above(5);
        //this.setPhantomSize(0);
        return super.finalizeSpawn(worldIn, difficultyIn, reason, spawnDataIn, dataTag);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag p_33132_) {
        super.readAdditionalSaveData(p_33132_);
        if (p_33132_.contains("AX")) {
            this.anchorPoint = new BlockPos(p_33132_.getInt("AX"), p_33132_.getInt("AY"), p_33132_.getInt("AZ"));
        }

        //this.setPhantomSize(compound.getInt("Size"));
    }

    @Override
    public void addAdditionalSaveData(CompoundTag p_33141_) {
        super.addAdditionalSaveData(p_33141_);
        p_33141_.putInt("AX", this.anchorPoint.getX());
        p_33141_.putInt("AY", this.anchorPoint.getY());
        p_33141_.putInt("AZ", this.anchorPoint.getZ());
        //compound.putInt("Size", this.getPhantomSize());
    }


    @OnlyIn(Dist.CLIENT)
    public boolean shouldRenderAtSqrDistance(double distance) {
        return true;
    }

    public boolean canAttackType(EntityType<?> typeIn) {
        return true;
    }

    public EntityDimensions getDimensions(Pose poseIn) {
        int i = 1;
        EntityDimensions entitysize = super.getDimensions(poseIn);
        float f = (entitysize.width + 0.2F * (float) i) / entitysize.width;
        return entitysize.scale(f);
    }

    @Override
    protected int getExperienceReward(Player player) {
        return 3 + this.level.random.nextInt(4);
    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        return ModSound.EAGLE_AMBIENT.get();
    }

    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        return ModSound.EAGLE_DEATH.get();
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return ModSound.EAGLE_HURT.get();
    }

    @Nullable
    @Override
    protected void playStepSound(BlockPos pos, BlockState blockIn) {
        this.playSound(SoundEvents.PIG_STEP, 0.15F, 1.0F);
    }

    /**
     * Kids
     *
     * @return
     */

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(ServerLevel p_241840_1_, AgeableMob p_241840_2_) {
        return null;
    }

    public boolean canBeLeashedTo(Player player) {
        return false;
    }

    enum AttackPhase {
        CIRCLE,
        SWOOP
    }

    class PhantomAttackPlayerTargetGoal extends Goal {
        private final TargetingConditions field_220842_b = (TargetingConditions.DEFAULT).range(64.0D);
        private int tickDelay = 60;

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
                List<Player> list = EagleEntity.this.level.getNearbyPlayers(this.field_220842_b, EagleEntity.this, EagleEntity.this.getBoundingBox().inflate(16.0D, 64.0D, 16.0D));
                if (!list.isEmpty()) {
                    list.sort(Comparator.comparing(Entity::getEyeY).reversed());

                    for (Player playerentity : list) {
                        if (EagleEntity.this.canAttack(playerentity, TargetingConditions.DEFAULT)) {
                            EagleEntity.this.setTarget(playerentity);
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
            LivingEntity livingentity = EagleEntity.this.getTarget();
            return livingentity != null && EagleEntity.this.canAttack(livingentity, TargetingConditions.DEFAULT);
        }
    }

    class AttackChickenGoal extends Goal {
        private final TargetingConditions field_220842_b = (TargetingConditions.DEFAULT).range(100.0D);
        private int tickDelay = 20;

        private AttackChickenGoal() {
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
                List<Chicken> list = EagleEntity.this.level.getNearbyEntities(Chicken.class, field_220842_b, EagleEntity.this, EagleEntity.this.getBoundingBox().inflate(16.0D, 64.0D, 16.0D));
                if (!list.isEmpty()) {
                    list.sort(Comparator.comparing(Entity::getEyeY).reversed());

                    for (Chicken playerentity : list) {
                        if (EagleEntity.this.canAttack(playerentity, TargetingConditions.DEFAULT)) {
                            EagleEntity.this.setTarget(playerentity);
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
            LivingEntity livingentity = EagleEntity.this.getTarget();
            return livingentity != null && EagleEntity.this.canAttack(livingentity, TargetingConditions.DEFAULT);
        }
    }

    class BodyHelperController extends BodyRotationControl {
        public BodyHelperController(Mob mob) {
            super(mob);
        }

        /**
         * Update the Head and Body rendenring angles
         */
        public void clientTick() {
            EagleEntity.this.yHeadRot = EagleEntity.this.yBodyRot;
            EagleEntity.this.yBodyRot = EagleEntity.this.yRotO;
        }
    }

    class LookHelperController extends LookControl {
        public LookHelperController(Mob entityIn) {
            super(entityIn);
        }

        /**
         * Updates look
         */
        public void tick() {
        }
    }

    abstract class MoveGoal extends Goal {
        public MoveGoal() {
            this.setFlags(EnumSet.of(Goal.Flag.MOVE));
        }

        protected boolean func_203146_f() {
            return EagleEntity.this.moveTargetPoint.distanceToSqr(EagleEntity.this.getX(), EagleEntity.this.getY(), EagleEntity.this.getZ()) < 4.0D;
        }
    }

    class MoveHelperController extends MoveControl {
        private float speed = 2F;

        public MoveHelperController(Mob entityIn) {
            super(entityIn);
        }

        public void tick() {
            if (EagleEntity.this.horizontalCollision) {
                EagleEntity.this.setYRot(EagleEntity.this.getYRot() + 180.0F);
                this.speed = 2F;
            }

            float f = (float) (EagleEntity.this.moveTargetPoint.x - EagleEntity.this.getX());
            float f1 = (float) (EagleEntity.this.moveTargetPoint.y - EagleEntity.this.getY());
            float f2 = (float) (EagleEntity.this.moveTargetPoint.z - EagleEntity.this.getZ());
            double d0 = Mth.sqrt(f * f + f2 * f2);
            if (Math.abs(d0) > (double) 1.0E-5F) {
                double d1 = 1.0D - (double) Mth.abs(f1 * 0.7F) / d0;
                f = (float) ((double) f * d1);
                f2 = (float) ((double) f2 * d1);
                d0 = Mth.sqrt(f * f + f2 * f2);
                double d2 = Mth.sqrt(f * f + f2 * f2 + f1 * f1);
                float f3 = EagleEntity.this.getYRot();
                float f4 = (float) Mth.atan2(f2, f);
                float f5 = Mth.wrapDegrees(EagleEntity.this.getYRot() + 90.0F);
                float f6 = Mth.wrapDegrees(f4 * (180F / (float) Math.PI));
                EagleEntity.this.setYRot(Mth.approachDegrees(f5, f6, 4.0F) - 90.0F);
                EagleEntity.this.yBodyRot = EagleEntity.this.getYRot();
                if (Mth.degreesDifferenceAbs(f3, EagleEntity.this.getYRot()) < 3.0F) {
                    this.speed = Mth.approach(this.speed, 1.8F, 0.005F * (1.8F / this.speed));
                } else {
                    this.speed = Mth.approach(this.speed, 0.2F, 0.025F);
                }

                float f7 = (float) (-(Mth.atan2(-f1, d0) * (double) (180F / (float) Math.PI)));
                EagleEntity.this.setXRot(f7);
                float f8 = EagleEntity.this.getYRot() + 90.0F;
                double d3 = (double) (this.speed * Mth.cos(f8 * ((float) Math.PI / 180F))) * Math.abs((double) f / d2);
                double d4 = (double) (this.speed * Mth.sin(f8 * ((float) Math.PI / 180F))) * Math.abs((double) f2 / d2);
                double d5 = (double) (this.speed * Mth.sin(f7 * ((float) Math.PI / 180F))) * Math.abs((double) f1 / d2);
                Vec3 vec3 = EagleEntity.this.getDeltaMovement();
                EagleEntity.this.setDeltaMovement(vec3.add((new Vec3(d3, d5, d4)).subtract(vec3).scale(0.2D)));

            }
        }
    }

    class PhantomCircleAroundAnchorGoal extends MoveGoal {
        private float field_203150_c;
        private float field_203151_d;
        private float field_203152_e;
        private float field_203153_f;


        /**
         * Returns whether execution should begin. You can also read and cache any state necessary for execution in this
         * method as well.
         */
        public boolean canUse() {
            return EagleEntity.this.getTarget() == null || EagleEntity.this.attackPhase == AttackPhase.CIRCLE;
        }

        /**
         * Execute a one shot task or start executing a continuous task
         */
        public void start() {
            this.field_203151_d = 5.0F + EagleEntity.this.random.nextFloat() * 10.0F;
            this.field_203152_e = -4.0F + EagleEntity.this.random.nextFloat() * 9.0F;
            this.field_203153_f = EagleEntity.this.random.nextBoolean() ? 1.0F : -1.0F;
            this.func_203148_i();
        }

        /**
         * Keep ticking a continuous task that has already been started
         */
        public void tick() {
            if (EagleEntity.this.random.nextInt(350) == 0) {
                this.field_203152_e = -4.0F + EagleEntity.this.random.nextFloat() * 9.0F;
            }

            if (EagleEntity.this.random.nextInt(250) == 0) {
                ++this.field_203151_d;
                if (this.field_203151_d > 15.0F) {
                    this.field_203151_d = 5.0F;
                    this.field_203153_f = -this.field_203153_f;
                }
            }

            if (EagleEntity.this.random.nextInt(450) == 0) {
                this.field_203150_c = EagleEntity.this.random.nextFloat() * 2.0F * (float) Math.PI;
                this.func_203148_i();
            }

            if (this.func_203146_f()) {
                this.func_203148_i();
            }

            if (EagleEntity.this.moveTargetPoint.y < EagleEntity.this.getY() && !EagleEntity.this.level.isEmptyBlock(EagleEntity.this.blockPosition().below(1))) {
                this.field_203152_e = Math.max(1.0F, this.field_203152_e);
                this.func_203148_i();
            }

            if (EagleEntity.this.moveTargetPoint.y > EagleEntity.this.getY() && !EagleEntity.this.level.isEmptyBlock(EagleEntity.this.blockPosition().above(1))) {
                this.field_203152_e = Math.min(-1.0F, this.field_203152_e);
                this.func_203148_i();
            }

        }

        private void func_203148_i() {
            if (BlockPos.ZERO.equals(EagleEntity.this.anchorPoint)) {
                EagleEntity.this.anchorPoint = EagleEntity.this.blockPosition();
            }

            this.field_203150_c += this.field_203153_f * 15.0F * ((float) Math.PI / 180F);
            EagleEntity.this.moveTargetPoint = Vec3.atLowerCornerOf(EagleEntity.this.anchorPoint).add(this.field_203151_d * Mth.cos(this.field_203150_c), -4.0F + this.field_203152_e, this.field_203151_d * Mth.sin(this.field_203150_c));
        }
    }

    class PhantomAttackStrategyGoal extends Goal {
        private int tickDelay;


        /**
         * Returns whether execution should begin. You can also read and cache any state necessary for execution in this
         * method as well.
         */
        public boolean canUse() {
            LivingEntity livingentity = EagleEntity.this.getTarget();
            return livingentity != null && EagleEntity.this.canAttack(EagleEntity.this.getTarget(), TargetingConditions.DEFAULT);
        }

        /**
         * Execute a one shot task or start executing a continuous task
         */
        public void start() {
            this.tickDelay = 10;
            EagleEntity.this.attackPhase = AttackPhase.CIRCLE;
            this.func_203143_f();
        }

        /**
         * Reset the task's internal state. Called when this task is interrupted by another one
         */
        public void stop() {
            EagleEntity.this.anchorPoint = EagleEntity.this.level.getHeightmapPos(Heightmap.Types.MOTION_BLOCKING, EagleEntity.this.anchorPoint).above(10 + EagleEntity.this.random.nextInt(20));
        }

        /**
         * Keep ticking a continuous task that has already been started
         */
        public void tick() {
            if (EagleEntity.this.attackPhase == AttackPhase.CIRCLE) {
                --this.tickDelay;
                if (this.tickDelay <= 0) {
                    EagleEntity.this.attackPhase = AttackPhase.SWOOP;
                    this.func_203143_f();
                    this.tickDelay = (8 + EagleEntity.this.random.nextInt(4)) * 20;
                    EagleEntity.this.playSound(ModSound.EAGLE_HURT.get(), 10.0F, 0.95F + EagleEntity.this.random.nextFloat() * 0.1F);
                }
            }

        }

        private void func_203143_f() {
            EagleEntity.this.anchorPoint = EagleEntity.this.getTarget().blockPosition().above(20 + EagleEntity.this.random.nextInt(20));
            if (EagleEntity.this.anchorPoint.getY() < EagleEntity.this.level.getSeaLevel()) {
                EagleEntity.this.anchorPoint = new BlockPos(EagleEntity.this.anchorPoint.getX(), EagleEntity.this.level.getSeaLevel() + 1, EagleEntity.this.anchorPoint.getZ());
            }

        }
    }

    class SweepAttackGoal extends MoveGoal {
        private SweepAttackGoal() {
        }

        /**
         * Returns whether execution should begin. You can also read and cache any state necessary for execution in this
         * method as well.
         */
        public boolean canUse() {
            return EagleEntity.this.getTarget() != null && EagleEntity.this.attackPhase == AttackPhase.SWOOP;
        }

        /**
         * Returns whether an in-progress EntityAIBase should continue executing
         */
        public boolean canContinueToUse() {
            LivingEntity livingentity = EagleEntity.this.getTarget();
            if (livingentity == null) {
                return false;
            } else if (!livingentity.isAlive()) {
                return false;
            } else if (!(livingentity instanceof Player) || !livingentity.isSpectator() && !((Player) livingentity).isCreative()) {
                if (!this.canUse()) {
                    return false;
                } else {
                    if (EagleEntity.this.tickCount % 20 == 0) {
                        List<Cat> list = EagleEntity.this.level.getEntitiesOfClass(Cat.class, EagleEntity.this.getBoundingBox().inflate(16.0D), EntitySelector.ENTITY_STILL_ALIVE);
                        if (!list.isEmpty()) {
                            for (Cat catentity : list) {
                                catentity.hiss();
                            }

                            return false;
                        }
                    }

                    return true;
                }
            } else {
                return false;
            }
        }

        /**
         * Execute a one shot task or start executing a continuous task
         */
        public void start() {
        }

        /**
         * Reset the task's internal state. Called when this task is interrupted by another one
         */
        public void stop() {
            EagleEntity.this.setTarget(null);
            EagleEntity.this.attackPhase = AttackPhase.CIRCLE;
        }

        /**
         * Keep ticking a continuous task that has already been started
         */
        public void tick() {
            LivingEntity livingentity = EagleEntity.this.getTarget();
            EagleEntity.this.moveTargetPoint = new Vec3(livingentity.getX(), livingentity.getY(0.5D), livingentity.getZ());
            if (EagleEntity.this.getBoundingBox().inflate(0.2F).intersects(livingentity.getBoundingBox())) {
                EagleEntity.this.doHurtTarget(livingentity);
                EagleEntity.this.attackPhase = AttackPhase.CIRCLE;
                if (!EagleEntity.this.isSilent()) {
                    EagleEntity.this.level.levelEvent(1039, EagleEntity.this.blockPosition(), 0);
                }
            } else if (EagleEntity.this.horizontalCollision || EagleEntity.this.hurtTime > 0) {
                EagleEntity.this.attackPhase = AttackPhase.CIRCLE;
            }

        }
    }
}

