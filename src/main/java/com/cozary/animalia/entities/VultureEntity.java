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
import com.cozary.animalia.init.ModEntityTypes;
import com.cozary.animalia.init.ModSound;
import com.cozary.animalia.util.ModClientEvents;
import com.cozary.animalia.util.VultureEvent;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.BodyRotationControl;
import net.minecraft.world.entity.ai.control.LookControl;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.Cat;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.monster.Skeleton;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.*;
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

public class VultureEntity extends FlyingMobEntity implements Enemy {

    private static final EntityDataAccessor<Integer> SIZE = SynchedEntityData.defineId(VultureEntity.class, EntityDataSerializers.INT);
    private Vec3 orbitOffset = Vec3.ZERO;
    private BlockPos orbitPosition = BlockPos.ZERO;
    private AttackPhase attackPhase = AttackPhase.CIRCLE;

    public VultureEntity(EntityType<? extends Animal> type, Level worldIn) {
        super(type, worldIn);
        this.moveControl = new MoveHelperController(this);
        this.lookControl = new LookHelperController(this);
    }

    public static AttributeSupplier.Builder setCustomAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 13.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.25D)
                .add(Attributes.ATTACK_DAMAGE, 2D)
                .add(Attributes.SPAWN_REINFORCEMENTS_CHANCE, 0.5D);
    }

    /**
     * Uses for Spawn
     */

    public static boolean canVultureSpawn(EntityType<? extends VultureEntity> entityType, LevelAccessor world, MobSpawnType spawnReason, BlockPos pos, Random random) {
        BlockPos blockpos = pos.below();
        return spawnReason == MobSpawnType.SPAWNER || world.getBlockState(blockpos).isValidSpawn(world, blockpos, entityType);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(0, new PickAttackGoal());
        this.goalSelector.addGoal(1, new SweepAttackGoal());
        this.goalSelector.addGoal(2, new OrbitPointGoal());
        this.targetSelector.addGoal(2, new AttackPlayerGoal());
        this.targetSelector.addGoal(3, (new HurtByTargetGoal(this)).setAlertOthers(VultureEntity.class));
        this.targetSelector.addGoal(1, new AttackZombieGoal());
        this.targetSelector.addGoal(0, new AttackSkeletonGoal());
    }

    @Override
    public boolean hurt(DamageSource source, float amount) {
        if (!super.hurt(source, amount)) {
            return false;
        } else if (!(this.level instanceof ServerLevel)) {
            return false;
        } else {
            ServerLevel serverworld = (ServerLevel) this.level;
            LivingEntity livingentity = this.getTarget();
            if (livingentity == null && source.getDirectEntity() instanceof LivingEntity) {
                livingentity = (LivingEntity) source.getDirectEntity();
            }

            int i = Mth.floor(this.getX());
            int j = Mth.floor(this.getY());
            int k = Mth.floor(this.getZ());

            VultureEvent.SummonAidEvent event = ModClientEvents.fireVultureSummonAid(this, level, i, j, k, livingentity, this.getAttribute(Attributes.SPAWN_REINFORCEMENTS_CHANCE).getValue());
            if (event.getResult() == net.minecraftforge.eventbus.api.Event.Result.DENY) return true;
            if (event.getResult() == net.minecraftforge.eventbus.api.Event.Result.ALLOW ||
                    livingentity != null && this.level.getDifficulty() == Difficulty.HARD && (double) this.random.nextFloat() < this.getAttribute(Attributes.SPAWN_REINFORCEMENTS_CHANCE).getValue() && this.level.getGameRules().getBoolean(GameRules.RULE_DOMOBSPAWNING) && this.level.getDifficulty() == Difficulty.NORMAL && (double) this.random.nextFloat() < ((this.getAttribute(Attributes.SPAWN_REINFORCEMENTS_CHANCE).getValue()) / 2)) {
                VultureEntity vultureentity = event.getCustomSummonedAidMod() != null && event.getResult() == net.minecraftforge.eventbus.api.Event.Result.ALLOW ? event.getCustomSummonedAidMod() : ModEntityTypes.VULTURE.get().create(this.level);

                for (int l = 0; l < 50; ++l) {
                    int i1 = i + Mth.nextInt(this.random, 7, 40) * Mth.nextInt(this.random, -1, 1);
                    int j1 = j + Mth.nextInt(this.random, 7, 40) * Mth.nextInt(this.random, -1, 1);
                    int k1 = k + Mth.nextInt(this.random, 7, 40) * Mth.nextInt(this.random, -1, 1);
                    BlockPos blockpos = new BlockPos(i1, j1, k1);
                    EntityType<?> entitytype = vultureentity.getType();
                    SpawnPlacements.Type entityspawnplacementregistry$placementtype = SpawnPlacements.getPlacementType(entitytype);
                    if (NaturalSpawner.isSpawnPositionOk(entityspawnplacementregistry$placementtype, this.level, blockpos, entitytype) && SpawnPlacements.checkSpawnRules(entitytype, serverworld, MobSpawnType.REINFORCEMENT, blockpos, this.level.random)) {
                        vultureentity.setPos(i1, j1, k1);
                        if (!this.level.hasNearbyAlivePlayer(i1, j1, k1, 7.0D) && this.level.isUnobstructed(vultureentity) && this.level.noCollision(vultureentity) && !this.level.containsAnyLiquid(vultureentity.getBoundingBox())) {
                            if (livingentity != null)
                                vultureentity.setTarget(livingentity);
                            vultureentity.finalizeSpawn(serverworld, this.level.getCurrentDifficultyAt(vultureentity.blockPosition()), MobSpawnType.REINFORCEMENT, null, null);
                            serverworld.addFreshEntityWithPassengers(vultureentity);
                            this.getAttribute(Attributes.SPAWN_REINFORCEMENTS_CHANCE).addPermanentModifier(new AttributeModifier("Zombie reinforcement caller charge", -0.05F, AttributeModifier.Operation.ADDITION));
                            vultureentity.getAttribute(Attributes.SPAWN_REINFORCEMENTS_CHANCE).addPermanentModifier(new AttributeModifier("Zombie reinforcement callee charge", -0.05F, AttributeModifier.Operation.ADDITION));
                            break;
                        }
                    }
                }
            }
            return true;
        }
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
        }
    */
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
        this.orbitPosition = this.blockPosition().above(20);
        //this.setPhantomSize(0);
        return super.finalizeSpawn(worldIn, difficultyIn, reason, spawnDataIn, dataTag);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        if (compound.contains("AX")) {
            this.orbitPosition = new BlockPos(compound.getInt("AX"), compound.getInt("AY"), compound.getInt("AZ"));
        }
        //this.setPhantomSize(compound.getInt("Size"));
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        this.entityData.define(SIZE, 0);
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

    public EntityDimensions getDimensions(Pose poseIn) {
        int i = 1;
        EntityDimensions entitysize = super.getDimensions(poseIn);
        float f = (entitysize.width + 0.2F * (float) i) / entitysize.width;
        return entitysize.scale(f);
    }

    @Override
    protected int getExperienceReward(Player player) {
        return 4 + this.level.random.nextInt(4);
    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        return ModSound.VULTURE_AMBIENT.get();
    }

    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        return ModSound.VULTURE_DEATH.get();
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return ModSound.VULTURE_DEATH.get();
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

    @OnlyIn(Dist.CLIENT)
    public Vec3 func_241205_ce_() {
        return new Vec3(0.0D, 0.6F * this.getEyeHeight(), this.getBbWidth() * 0.4F);
    }

    public boolean canBeLeashedTo(Player player) {
        return false;
    }

    enum AttackPhase {
        CIRCLE,
        SWOOP
    }

    class AttackPlayerGoal extends Goal {
        private final TargetingConditions field_220842_b = (TargetingConditions.DEFAULT).range(64.0D);
        private int tickDelay = 20;

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
                List<Player> list = VultureEntity.this.level.getNearbyPlayers(this.field_220842_b, VultureEntity.this, VultureEntity.this.getBoundingBox().inflate(16.0D, 64.0D, 16.0D));
                if (!list.isEmpty()) {
                    list.sort(Comparator.comparing(Entity::getEyeY).reversed());

                    for (Player playerentity : list) {
                        if (VultureEntity.this.canAttack(playerentity, TargetingConditions.DEFAULT)) {
                            VultureEntity.this.setTarget(playerentity);
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
            LivingEntity livingentity = VultureEntity.this.getTarget();
            return livingentity != null && VultureEntity.this.canAttack(livingentity, TargetingConditions.DEFAULT);
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
            VultureEntity.this.yHeadRot = VultureEntity.this.yBodyRot;
            VultureEntity.this.yBodyRot = VultureEntity.this.yRotO;
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
            return VultureEntity.this.orbitOffset.distanceToSqr(VultureEntity.this.getX(), VultureEntity.this.getY(), VultureEntity.this.getZ()) < 4.0D;
        }
    }

    class MoveHelperController extends MoveControl {
        private float speed = 0.8F;

        public MoveHelperController(Mob entityIn) {
            super(entityIn);
        }

        public void tick() {
            if (VultureEntity.this.horizontalCollision) {
                VultureEntity.this.yRotO += 180.0F;
                this.speed = 0.8F;
            }

            float f = (float) (VultureEntity.this.orbitOffset.x - VultureEntity.this.getX());
            float f1 = (float) (VultureEntity.this.orbitOffset.y - VultureEntity.this.getY());
            float f2 = (float) (VultureEntity.this.orbitOffset.z - VultureEntity.this.getZ());
            double d0 = Mth.sqrt(f * f + f2 * f2);
            if (Math.abs(d0) > (double) 1.0E-5F) {
                double d1 = 1.0D - (double) Mth.abs(f1 * 0.7F) / d0;
                f = (float) ((double) f * d1);
                f2 = (float) ((double) f2 * d1);
                d0 = Mth.sqrt(f * f + f2 * f2);
                double d2 = Mth.sqrt(f * f + f2 * f2 + f1 * f1);
                float f3 = VultureEntity.this.getYRot();
                float f4 = (float) Mth.atan2(f2, f);
                float f5 = Mth.wrapDegrees(VultureEntity.this.getYRot() + 90.0F);
                float f6 = Mth.wrapDegrees(f4 * (180F / (float) Math.PI));
                VultureEntity.this.setYRot(Mth.approachDegrees(f5, f6, 4.0F) - 90.0F);
                VultureEntity.this.yBodyRot = VultureEntity.this.getYRot();
                if (Mth.degreesDifferenceAbs(f3, VultureEntity.this.getYRot()) < 3.0F) {
                    this.speed = Mth.approach(this.speed, 1.8F, 0.005F * (1.8F / this.speed));
                } else {
                    this.speed = Mth.approach(this.speed, 0.2F, 0.025F);
                }

                float f7 = (float) (-(Mth.atan2(-f1, d0) * (double) (180F / (float) Math.PI)));
                VultureEntity.this.setXRot(f7);
                float f8 = VultureEntity.this.getYRot() + 90.0F;
                double d3 = (double) (this.speed * Mth.cos(f8 * ((float) Math.PI / 180F))) * Math.abs((double) f / d2);
                double d4 = (double) (this.speed * Mth.sin(f8 * ((float) Math.PI / 180F))) * Math.abs((double) f2 / d2);
                double d5 = (double) (this.speed * Mth.sin(f7 * ((float) Math.PI / 180F))) * Math.abs((double) f1 / d2);
                Vec3 vec3 = VultureEntity.this.getDeltaMovement();
                VultureEntity.this.setDeltaMovement(vec3.add((new Vec3(d3, d5, d4)).subtract(vec3).scale(0.2D)));
            }
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
            return VultureEntity.this.getTarget() == null || VultureEntity.this.attackPhase == AttackPhase.CIRCLE;
        }

        /**
         * Execute a one shot task or start executing a continuous task
         */
        public void start() {
            this.field_203151_d = 5.0F + VultureEntity.this.random.nextFloat() * 10.0F;
            this.field_203152_e = -4.0F + VultureEntity.this.random.nextFloat() * 9.0F;
            this.field_203153_f = VultureEntity.this.random.nextBoolean() ? 1.0F : -1.0F;
            this.func_203148_i();
        }

        /**
         * Keep ticking a continuous task that has already been started
         */
        public void tick() {
            if (VultureEntity.this.random.nextInt(350) == 0) {
                this.field_203152_e = -4.0F + VultureEntity.this.random.nextFloat() * 9.0F;
            }

            if (VultureEntity.this.random.nextInt(250) == 0) {
                ++this.field_203151_d;
                if (this.field_203151_d > 15.0F) {
                    this.field_203151_d = 5.0F;
                    this.field_203153_f = -this.field_203153_f;
                }
            }

            if (VultureEntity.this.random.nextInt(450) == 0) {
                this.field_203150_c = VultureEntity.this.random.nextFloat() * 2.0F * (float) Math.PI;
                this.func_203148_i();
            }

            if (this.func_203146_f()) {
                this.func_203148_i();
            }

            if (VultureEntity.this.orbitOffset.y < VultureEntity.this.getY() && !VultureEntity.this.level.isEmptyBlock(VultureEntity.this.blockPosition().below(1))) {
                this.field_203152_e = Math.max(1.0F, this.field_203152_e);
                this.func_203148_i();
            }

            if (VultureEntity.this.orbitOffset.y > VultureEntity.this.getY() && !VultureEntity.this.level.isEmptyBlock(VultureEntity.this.blockPosition().above(1))) {
                this.field_203152_e = Math.min(-1.0F, this.field_203152_e);
                this.func_203148_i();
            }

        }

        private void func_203148_i() {
            if (BlockPos.ZERO.equals(VultureEntity.this.orbitPosition)) {
                VultureEntity.this.orbitPosition = VultureEntity.this.blockPosition();
            }

            this.field_203150_c += this.field_203153_f * 15.0F * ((float) Math.PI / 180F);
            VultureEntity.this.orbitOffset = Vec3.atLowerCornerOf(VultureEntity.this.orbitPosition).add(this.field_203151_d * Mth.cos(this.field_203150_c), -4.0F + this.field_203152_e, this.field_203151_d * Mth.sin(this.field_203150_c));
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
            LivingEntity livingentity = VultureEntity.this.getTarget();
            return livingentity != null && VultureEntity.this.canAttack(VultureEntity.this.getTarget(), TargetingConditions.DEFAULT);
        }

        /**
         * Execute a one shot task or start executing a continuous task
         */
        public void start() {
            this.tickDelay = 10;
            VultureEntity.this.attackPhase = AttackPhase.CIRCLE;
            this.func_203143_f();
        }

        /**
         * Reset the task's internal state. Called when this task is interrupted by another one
         */
        public void stop() {
            VultureEntity.this.orbitPosition = VultureEntity.this.level.getHeightmapPos(Heightmap.Types.MOTION_BLOCKING, VultureEntity.this.orbitPosition).above(10 + VultureEntity.this.random.nextInt(20));
        }

        /**
         * Keep ticking a continuous task that has already been started
         */
        public void tick() {
            if (VultureEntity.this.attackPhase == AttackPhase.CIRCLE) {
                --this.tickDelay;
                if (this.tickDelay <= 0) {
                    VultureEntity.this.attackPhase = AttackPhase.SWOOP;
                    this.func_203143_f();
                    this.tickDelay = (8 + VultureEntity.this.random.nextInt(4)) * 20;
                    VultureEntity.this.playSound(ModSound.VULTURE_DEATH.get(), 10.0F, 0.95F + VultureEntity.this.random.nextFloat() * 0.1F);
                }
            }

        }

        private void func_203143_f() {
            VultureEntity.this.orbitPosition = VultureEntity.this.getTarget().blockPosition().above(20 + VultureEntity.this.random.nextInt(20));
            if (VultureEntity.this.orbitPosition.getY() < VultureEntity.this.level.getSeaLevel()) {
                VultureEntity.this.orbitPosition = new BlockPos(VultureEntity.this.orbitPosition.getX(), VultureEntity.this.level.getSeaLevel() + 1, VultureEntity.this.orbitPosition.getZ());
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
            return VultureEntity.this.getTarget() != null && VultureEntity.this.attackPhase == AttackPhase.SWOOP;
        }

        /**
         * Returns whether an in-progress EntityAIBase should continue executing
         */
        public boolean canContinueToUse() {
            LivingEntity livingentity = VultureEntity.this.getTarget();
            if (livingentity == null) {
                return false;
            } else if (!livingentity.isAlive()) {
                return false;
            } else if (!(livingentity instanceof Player) || !livingentity.isSpectator() && !((Player) livingentity).isCreative()) {
                if (!this.canUse()) {
                    return false;
                } else {
                    if (VultureEntity.this.tickCount % 20 == 0) {
                        List<Cat> list = VultureEntity.this.level.getEntitiesOfClass(Cat.class, VultureEntity.this.getBoundingBox().inflate(16.0D), EntitySelector.ENTITY_STILL_ALIVE);
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
            VultureEntity.this.setTarget(null);
            VultureEntity.this.attackPhase = AttackPhase.CIRCLE;
        }

        /**
         * Keep ticking a continuous task that has already been started
         */
        public void tick() {
            LivingEntity livingentity = VultureEntity.this.getTarget();
            VultureEntity.this.orbitOffset = new Vec3(livingentity.getX(), livingentity.getY(0.5D), livingentity.getZ());
            if (VultureEntity.this.getBoundingBox().inflate(0.2F).intersects(livingentity.getBoundingBox())) {
                VultureEntity.this.doHurtTarget(livingentity);
                VultureEntity.this.attackPhase = AttackPhase.CIRCLE;
                if (!VultureEntity.this.isSilent()) {
                    VultureEntity.this.level.levelEvent(1039, VultureEntity.this.blockPosition(), 0);
                }
            } else if (VultureEntity.this.horizontalCollision || VultureEntity.this.hurtTime > 0) {
                VultureEntity.this.attackPhase = AttackPhase.CIRCLE;
            }

        }
    }

    class AttackZombieGoal extends Goal {
        private final TargetingConditions field_220842_b = (TargetingConditions.DEFAULT).range(100.0D);
        private int tickDelay = 20;

        private AttackZombieGoal() {
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
                List<Zombie> list = VultureEntity.this.level.getNearbyEntities(Zombie.class, field_220842_b, VultureEntity.this, VultureEntity.this.getBoundingBox().inflate(16.0D, 64.0D, 16.0D));
                if (!list.isEmpty()) {
                    list.sort(Comparator.comparing(Entity::getEyeY).reversed());

                    for (Zombie playerentity : list) {
                        if (VultureEntity.this.canAttack(playerentity, TargetingConditions.DEFAULT)) {
                            VultureEntity.this.setTarget(playerentity);
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
            LivingEntity livingentity = VultureEntity.this.getTarget();
            return livingentity != null && VultureEntity.this.canAttack(livingentity, TargetingConditions.DEFAULT);
        }
    }

    class AttackSkeletonGoal extends Goal {
        private final TargetingConditions field_220842_b = (TargetingConditions.DEFAULT).range(100.0D);
        private int tickDelay = 20;

        private AttackSkeletonGoal() {
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
                List<Skeleton> list = VultureEntity.this.level.getNearbyEntities(Skeleton.class, field_220842_b, VultureEntity.this, VultureEntity.this.getBoundingBox().inflate(16.0D, 64.0D, 16.0D));
                if (!list.isEmpty()) {
                    list.sort(Comparator.comparing(Entity::getEyeY).reversed());

                    for (Skeleton playerentity : list) {
                        if (VultureEntity.this.canAttack(playerentity, TargetingConditions.DEFAULT)) {
                            VultureEntity.this.setTarget(playerentity);
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
            LivingEntity livingentity = VultureEntity.this.getTarget();
            return livingentity != null && VultureEntity.this.canAttack(livingentity, TargetingConditions.DEFAULT);
        }
    }

}

