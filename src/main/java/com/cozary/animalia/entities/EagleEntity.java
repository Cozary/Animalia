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
import net.minecraft.block.BlockState;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.controller.BodyController;
import net.minecraft.entity.ai.controller.LookController;
import net.minecraft.entity.ai.controller.MovementController;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.CatEntity;
import net.minecraft.entity.passive.ChickenEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityPredicates;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.Comparator;
import java.util.EnumSet;
import java.util.List;
import java.util.Random;


public class EagleEntity extends FlyingMobEntity implements IMob {

    private static final DataParameter<Integer> SIZE = EntityDataManager.defineId(EagleEntity.class, DataSerializers.INT);
    private Vector3d orbitOffset = Vector3d.ZERO;
    private BlockPos orbitPosition = BlockPos.ZERO;
    private AttackPhase attackPhase = AttackPhase.CIRCLE;

    public EagleEntity(EntityType<? extends AnimalEntity> type, World worldIn) {
        super(type, worldIn);
        this.xpReward = 5;
        this.moveControl = new MoveHelperController(this);
        this.lookControl = new LookHelperController(this);
    }

    public static AttributeModifierMap.MutableAttribute setCustomAttributes() {
        return MobEntity.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 10.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.25D)
                .add(Attributes.ATTACK_DAMAGE, 3D);
    }

    /**
     * Uses for Spawn
     */

    public static boolean canEagleSpawn(EntityType<? extends EagleEntity> typeIn, IWorld world, SpawnReason reason, BlockPos pos, Random random) {
        BlockPos blockpos = pos.below();
        return reason == SpawnReason.SPAWNER || world.getBlockState(blockpos).isValidSpawn(world, blockpos, typeIn);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(0, new PickAttackGoal());
        this.goalSelector.addGoal(1, new SweepAttackGoal());
        this.goalSelector.addGoal(2, new OrbitPointGoal());
        this.targetSelector.addGoal(1, new AttackPlayerGoal());
        this.targetSelector.addGoal(0, new AttackChickenGoal());
    }

    @Override
    protected BodyController createBodyControl() {
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

    public void onSyncedDataUpdated(DataParameter<?> key) {
        /*if (SIZE.equals(key)) {
            this.updatePhantomSize();
        }*/
        super.onSyncedDataUpdated(key);
    }

    @Override
    protected void customServerAiStep() {
        super.customServerAiStep();
    }

    public ILivingEntityData finalizeSpawn(IServerWorld worldIn, DifficultyInstance difficultyIn, SpawnReason reason, @Nullable ILivingEntityData spawnDataIn, @Nullable CompoundNBT dataTag) {
        this.orbitPosition = this.blockPosition().above(20);
        //this.setPhantomSize(0);
        return super.finalizeSpawn(worldIn, difficultyIn, reason, spawnDataIn, dataTag);
    }

    @Override
    public void readAdditionalSaveData(CompoundNBT compound) {
        super.readAdditionalSaveData(compound);
        if (compound.contains("AX")) {
            this.orbitPosition = new BlockPos(compound.getInt("AX"), compound.getInt("AY"), compound.getInt("AZ"));
        }

        //this.setPhantomSize(compound.getInt("Size"));
    }

    @Override
    public void addAdditionalSaveData(CompoundNBT compound) {
        super.addAdditionalSaveData(compound);
        compound.putInt("AX", this.orbitPosition.getX());
        compound.putInt("AY", this.orbitPosition.getY());
        compound.putInt("AZ", this.orbitPosition.getZ());
        //compound.putInt("Size", this.getPhantomSize());
    }


    @OnlyIn(Dist.CLIENT)
    public boolean shouldRenderAtSqrDistance(double distance) {
        return true;
    }

    public boolean canAttackType(EntityType<?> typeIn) {
        return true;
    }

    public EntitySize getDimensions(Pose poseIn) {
        int i = 1;
        EntitySize entitysize = super.getDimensions(poseIn);
        float f = (entitysize.width + 0.2F * (float) i) / entitysize.width;
        return entitysize.scale(f);
    }

    @Override
    protected int getExperienceReward(PlayerEntity player) {
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
    public AgeableEntity getBreedOffspring(ServerWorld p_241840_1_, AgeableEntity p_241840_2_) {
        return null;
    }

    public boolean canBeLeashedTo(PlayerEntity player) {
        return false;
    }

    enum AttackPhase {
        CIRCLE,
        SWOOP
    }

    class AttackPlayerGoal extends Goal {
        private final EntityPredicate field_220842_b = (new EntityPredicate()).range(64.0D);
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
                List<PlayerEntity> list = EagleEntity.this.level.getNearbyPlayers(this.field_220842_b, EagleEntity.this, EagleEntity.this.getBoundingBox().inflate(16.0D, 64.0D, 16.0D));
                if (!list.isEmpty()) {
                    list.sort(Comparator.comparing(Entity::getEyeY).reversed());

                    for (PlayerEntity playerentity : list) {
                        if (EagleEntity.this.canAttack(playerentity, EntityPredicate.DEFAULT)) {
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
            return livingentity != null && EagleEntity.this.canAttack(livingentity, EntityPredicate.DEFAULT);
        }
    }

    class AttackChickenGoal extends Goal {
        private final EntityPredicate field_220842_b = (new EntityPredicate()).range(100.0D);
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
                List<ChickenEntity> list = EagleEntity.this.level.getNearbyEntities(ChickenEntity.class, field_220842_b, EagleEntity.this, EagleEntity.this.getBoundingBox().inflate(16.0D, 64.0D, 16.0D));
                if (!list.isEmpty()) {
                    list.sort(Comparator.comparing(Entity::getEyeY).reversed());

                    for (ChickenEntity playerentity : list) {
                        if (EagleEntity.this.canAttack(playerentity, EntityPredicate.DEFAULT)) {
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
            return livingentity != null && EagleEntity.this.canAttack(livingentity, EntityPredicate.DEFAULT);
        }
    }

    class BodyHelperController extends BodyController {
        public BodyHelperController(MobEntity mob) {
            super(mob);
        }

        /**
         * Update the Head and Body rendenring angles
         */
        public void clientTick() {
            EagleEntity.this.yHeadRot = EagleEntity.this.yBodyRot;
            EagleEntity.this.yBodyRot = EagleEntity.this.yRot;
        }
    }

    class LookHelperController extends LookController {
        public LookHelperController(MobEntity entityIn) {
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
            this.setFlags(EnumSet.of(Flag.MOVE));
        }

        protected boolean func_203146_f() {
            return EagleEntity.this.orbitOffset.distanceToSqr(EagleEntity.this.getX(), EagleEntity.this.getY(), EagleEntity.this.getZ()) < 4.0D;
        }
    }

    class MoveHelperController extends MovementController {
        private float speedFactor = 2F;

        public MoveHelperController(MobEntity entityIn) {
            super(entityIn);
        }

        public void tick() {
            if (EagleEntity.this.horizontalCollision) {
                EagleEntity.this.yRot += 180.0F;
                this.speedFactor = 2F;
            }

            float f = (float) (EagleEntity.this.orbitOffset.x - EagleEntity.this.getX());
            float f1 = (float) (EagleEntity.this.orbitOffset.y - EagleEntity.this.getY());
            float f2 = (float) (EagleEntity.this.orbitOffset.z - EagleEntity.this.getZ());
            double d0 = MathHelper.sqrt(f * f + f2 * f2);
            double d1 = 1.0D - (double) MathHelper.abs(f1 * 0.7F) / d0;
            f = (float) ((double) f * d1);
            f2 = (float) ((double) f2 * d1);
            d0 = MathHelper.sqrt(f * f + f2 * f2);
            double d2 = MathHelper.sqrt(f * f + f2 * f2 + f1 * f1);
            float f3 = EagleEntity.this.yRot;
            float f4 = (float) MathHelper.atan2(f2, f);
            float f5 = MathHelper.wrapDegrees(EagleEntity.this.yRot + 90.0F);
            float f6 = MathHelper.wrapDegrees(f4 * (180F / (float) Math.PI));
            EagleEntity.this.yRot = MathHelper.approachDegrees(f5, f6, 4.0F) - 90.0F;
            EagleEntity.this.yBodyRot = EagleEntity.this.yRot;
            if (MathHelper.degreesDifferenceAbs(f3, EagleEntity.this.yRot) < 3.0F) {
                this.speedFactor = MathHelper.approach(this.speedFactor, 1.8F, 0.005F * (1.8F / this.speedFactor));
            } else {
                this.speedFactor = MathHelper.approach(this.speedFactor, 0.2F, 0.025F);
            }

            float f7 = (float) (-(MathHelper.atan2(-f1, d0) * (double) (180F / (float) Math.PI)));
            EagleEntity.this.xRot = f7;
            float f8 = EagleEntity.this.yRot + 90.0F;
            double d3 = (double) (this.speedFactor * MathHelper.cos(f8 * ((float) Math.PI / 180F))) * Math.abs((double) f / d2);
            double d4 = (double) (this.speedFactor * MathHelper.sin(f8 * ((float) Math.PI / 180F))) * Math.abs((double) f2 / d2);
            double d5 = (double) (this.speedFactor * MathHelper.sin(f7 * ((float) Math.PI / 180F))) * Math.abs((double) f1 / d2);
            Vector3d vector3d = EagleEntity.this.getDeltaMovement();
            EagleEntity.this.setDeltaMovement(vector3d.add((new Vector3d(d3, d5, d4)).subtract(vector3d).scale(0.2D)));
        }
    }

    class OrbitPointGoal extends MoveGoal {
        private float field_203150_c;
        private float field_203151_d;
        private float field_203152_e;
        private float field_203153_f;

        private OrbitPointGoal() {
        }

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

            if (EagleEntity.this.orbitOffset.y < EagleEntity.this.getY() && !EagleEntity.this.level.isEmptyBlock(EagleEntity.this.blockPosition().below(1))) {
                this.field_203152_e = Math.max(1.0F, this.field_203152_e);
                this.func_203148_i();
            }

            if (EagleEntity.this.orbitOffset.y > EagleEntity.this.getY() && !EagleEntity.this.level.isEmptyBlock(EagleEntity.this.blockPosition().above(1))) {
                this.field_203152_e = Math.min(-1.0F, this.field_203152_e);
                this.func_203148_i();
            }

        }

        private void func_203148_i() {
            if (BlockPos.ZERO.equals(EagleEntity.this.orbitPosition)) {
                EagleEntity.this.orbitPosition = EagleEntity.this.blockPosition();
            }

            this.field_203150_c += this.field_203153_f * 15.0F * ((float) Math.PI / 180F);
            EagleEntity.this.orbitOffset = Vector3d.atLowerCornerOf(EagleEntity.this.orbitPosition).add(this.field_203151_d * MathHelper.cos(this.field_203150_c), -4.0F + this.field_203152_e, this.field_203151_d * MathHelper.sin(this.field_203150_c));
        }
    }

    class PickAttackGoal extends Goal {
        private int tickDelay;

        private PickAttackGoal() {
        }

        /**
         * Returns whether execution should begin. You can also read and cache any state necessary for execution in this
         * method as well.
         */
        public boolean canUse() {
            LivingEntity livingentity = EagleEntity.this.getTarget();
            return livingentity != null && EagleEntity.this.canAttack(EagleEntity.this.getTarget(), EntityPredicate.DEFAULT);
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
            EagleEntity.this.orbitPosition = EagleEntity.this.level.getHeightmapPos(Heightmap.Type.MOTION_BLOCKING, EagleEntity.this.orbitPosition).above(10 + EagleEntity.this.random.nextInt(20));
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
            EagleEntity.this.orbitPosition = EagleEntity.this.getTarget().blockPosition().above(20 + EagleEntity.this.random.nextInt(20));
            if (EagleEntity.this.orbitPosition.getY() < EagleEntity.this.level.getSeaLevel()) {
                EagleEntity.this.orbitPosition = new BlockPos(EagleEntity.this.orbitPosition.getX(), EagleEntity.this.level.getSeaLevel() + 1, EagleEntity.this.orbitPosition.getZ());
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
            } else if (!(livingentity instanceof PlayerEntity) || !livingentity.isSpectator() && !((PlayerEntity) livingentity).isCreative()) {
                if (!this.canUse()) {
                    return false;
                } else {
                    if (EagleEntity.this.tickCount % 20 == 0) {
                        List<CatEntity> list = EagleEntity.this.level.getEntitiesOfClass(CatEntity.class, EagleEntity.this.getBoundingBox().inflate(16.0D), EntityPredicates.ENTITY_STILL_ALIVE);
                        if (!list.isEmpty()) {
                            for (CatEntity catentity : list) {
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
            EagleEntity.this.orbitOffset = new Vector3d(livingentity.getX(), livingentity.getY(0.5D), livingentity.getZ());
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

