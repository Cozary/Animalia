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

import com.cozary.animalia.init.ModItems;
import com.cozary.animalia.init.ModSound;
import com.cozary.animalia.potions.RegistryObjects;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.PanicGoal;
import net.minecraft.world.entity.animal.WaterAnimal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.Random;

public class JellyfishEntity extends WaterAnimal {

    private static final EntityDataAccessor<Boolean> FROM_BUCKET = SynchedEntityData.defineId(JellyfishEntity.class, EntityDataSerializers.BOOLEAN);
    public float squidPitch;
    public float prevSquidPitch;
    public float squidYaw;
    public float prevSquidYaw;
    public float squidRotation;
    public float prevSquidRotation;
    public float tentacleAngle;
    public float lastTentacleAngle;
    private float randomMotionSpeed;
    private float rotationVelocity;
    private float rotateSpeed;
    private float randomMotionVecX;
    private float randomMotionVecY;
    private float randomMotionVecZ;

    public JellyfishEntity(EntityType<? extends JellyfishEntity> type, Level worldIn) {
        super(type, worldIn);
        this.random.setSeed(this.getId());
        this.rotationVelocity = 1.0F / (this.random.nextFloat() + 1.0F) * 0.2F;
    }

    public static AttributeSupplier.Builder setCustomAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 3.0D);
    }

    /**
     * Uses for Spawn
     */

    public static boolean canJellyfishSpawn(EntityType<? extends JellyfishEntity> entityType, LevelAccessor worldIn, MobSpawnType spawnReason, BlockPos pos, Random random) {
        return pos.getY() > 5 && pos.getY() < worldIn.getSeaLevel();
    }

    public static double horizontalMag(Vec3 vec) {
        return vec.x * vec.x + vec.z * vec.z;
    }

    @Override
    public void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(0, new MoveRandomGoal(this));
        this.goalSelector.addGoal(1, new PanicGoal(this, 1.25D));
        this.goalSelector.addGoal(2, new FleeGoal());
        this.goalSelector.addGoal(3, new AvoidEntityGoal<>(this, Player.class, 8.0F, 1.6D, 1.4D, EntitySelector.NO_SPECTATORS::test));
    }

    /**
     * FISHBUCKET
     *
     * @return
     */


    @Override
    public boolean requiresCustomPersistence() {
        return super.requiresCustomPersistence() || this.isFromBucket();
    }

    @Nullable
    @Override
    public boolean removeWhenFarAway(double distanceToClosestPlayer) {
        return !this.isFromBucket() && !this.hasCustomName();
    }

    public boolean isFromBucket() {
        return this.entityData.get(FROM_BUCKET);
    }

    public void setFromBucket(boolean p_203706_1_) {
        this.entityData.set(FROM_BUCKET, p_203706_1_);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(FROM_BUCKET, false);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        this.setFromBucket(compound.getBoolean("FromBucket"));
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putBoolean("FromBucket", this.isFromBucket());
    }

    @Override
    public InteractionResult mobInteract(Player p_230254_1_, InteractionHand p_230254_2_) {
        ItemStack itemstack = p_230254_1_.getItemInHand(p_230254_2_);
        if (itemstack.getItem() == Items.WATER_BUCKET && this.isAlive()) {
            this.playSound(SoundEvents.BUCKET_FILL_FISH, 1.0F, 1.0F);
            itemstack.shrink(1);
            ItemStack itemstack1 = this.getFishBucket();
            this.setBucketData(itemstack1);
            if (!this.level.isClientSide) {
                CriteriaTriggers.FILLED_BUCKET.trigger((ServerPlayer) p_230254_1_, itemstack1);
            }
            if (itemstack.isEmpty()) {
                p_230254_1_.setItemInHand(p_230254_2_, itemstack1);
            } else if (!p_230254_1_.getInventory().add(itemstack1)) {
                p_230254_1_.drop(itemstack1, false);
            }
            this.remove(RemovalReason.KILLED);
            return InteractionResult.sidedSuccess(this.level.isClientSide);
        } else {
            return super.mobInteract(p_230254_1_, p_230254_2_);
        }
    }

    public void setBucketData(ItemStack bucket) {
        if (this.hasCustomName()) {
            bucket.setHoverName(this.getCustomName());
        }

    }

    protected ItemStack getFishBucket() {
        return new ItemStack(ModItems.JELLYFISH_BUCKET.get());
    }

    @Override
    protected float getStandingEyeHeight(Pose poseIn, EntityDimensions sizeIn) {
        return sizeIn.height * 0.5F;
    }

    @Override
    protected int getExperienceReward(Player player) {
        return 1 + this.level.random.nextInt(4);
    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        return ModSound.JELLYFISH_AMBIENT.get();
    }

    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        return ModSound.JELLYFISH_DEATH.get();
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return ModSound.JELLYFISH_HURT.get();
    }

    @Nullable
    @Override
    protected float getSoundVolume() {
        return 0.4F;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void handleEntityEvent(byte id) {
        if (id == 19) {
            this.squidRotation = 0.0F;
        } else {
            super.handleEntityEvent(id);
        }

    }

    public void setMovementVector(float randomMotionVecXIn, float randomMotionVecYIn, float randomMotionVecZIn) {
        this.randomMotionVecX = randomMotionVecXIn;
        this.randomMotionVecY = randomMotionVecYIn;
        this.randomMotionVecZ = randomMotionVecZIn;
    }

    public boolean hasMovementVector() {
        return this.randomMotionVecX != 0.0F || this.randomMotionVecY != 0.0F || this.randomMotionVecZ != 0.0F;
    }

    @Override
    public void travel(Vec3 travelVector) {
        this.move(MoverType.SELF, this.getDeltaMovement());
    }

    @Override
    public boolean canBeLeashed(Player player) {
        return true;
    }

    @Override
    public boolean hurt(DamageSource source, float amount) {
        if (source.getEntity() instanceof LivingEntity) {
            LivingEntity livingentity = (LivingEntity) source.getEntity();
            if (!source.isExplosion()) {
                livingentity.addEffect(new MobEffectInstance(MobEffects.POISON, 60, 1));
                livingentity.addEffect(new MobEffectInstance(RegistryObjects.PARALIZE_EFFECT, 10, 1));
            }
        }
        return super.hurt(source, amount);
    }

    @Override
    public void aiStep() {
        super.aiStep();
        this.prevSquidPitch = this.squidPitch;
        this.prevSquidYaw = this.squidYaw;
        this.prevSquidRotation = this.squidRotation;
        this.lastTentacleAngle = this.tentacleAngle;
        this.squidRotation += this.rotationVelocity;

        if ((double) this.squidRotation > (Math.PI * 2D)) {
            if (this.level.isClientSide) {
                this.squidRotation = ((float) Math.PI * 2F);
            } else {
                this.squidRotation = (float) ((double) this.squidRotation - (Math.PI * 2D));
                if (this.random.nextInt(10) == 0) {
                    this.rotationVelocity = 1.0F / (this.random.nextFloat() + 1.0F) * 0.2F;
                }

                this.level.broadcastEntityEvent(this, (byte) 19);
            }
        }
        if (this.isInWaterOrBubble()) {
            if (this.squidRotation < (float) Math.PI) {
                float f = this.squidRotation / (float) Math.PI;
                this.tentacleAngle = Mth.sin(f * f * (float) Math.PI) * (float) Math.PI * 0.25F;
                if ((double) f > 0.75D) {
                    this.randomMotionSpeed = 1.0F;
                    this.rotateSpeed = 1.0F;
                } else {
                    this.rotateSpeed *= 0.8F;
                }
            } else {
                this.tentacleAngle = 0.0F;
                this.randomMotionSpeed *= 0.9F;
                this.rotateSpeed *= 0.99F;
            }

            if (!this.level.isClientSide) {
                this.setDeltaMovement(this.randomMotionVecX * this.randomMotionSpeed, this.randomMotionVecY * this.randomMotionSpeed, this.randomMotionVecZ * this.randomMotionSpeed);
            }

            Vec3 vector3d = this.getDeltaMovement();
            float f1 = Mth.sqrt((float) horizontalMag(vector3d));
            this.yBodyRot += (-((float) Mth.atan2(vector3d.x, vector3d.z)) * (180F / (float) Math.PI) - this.yBodyRot) * 0.1F;
            this.yRotO = this.yBodyRot;
            this.squidYaw = (float) ((double) this.squidYaw + Math.PI * (double) this.rotateSpeed * 1.5D);
            this.squidPitch += (-((float) Mth.atan2(f1, vector3d.y)) * (180F / (float) Math.PI) - this.squidPitch) * 0.1F;
        } else {
            this.tentacleAngle = Mth.abs(Mth.sin(this.squidRotation)) * (float) Math.PI * 0.25F;
            if (!this.level.isClientSide) {
                double d0 = this.getDeltaMovement().y;
                if (this.hasEffect(MobEffects.LEVITATION)) {
                    d0 = 0.05D * (double) (this.getEffect(MobEffects.LEVITATION).getAmplifier() + 1);
                } else if (!this.isNoGravity()) {
                    d0 -= 0.08D;
                }

                this.setDeltaMovement(0.0D, d0 * (double) 0.98F, 0.0D);
            }

            this.squidPitch = (float) ((double) this.squidPitch + (double) (-90.0F - this.squidPitch) * 0.02D);
        }
    }

    class FleeGoal extends Goal {
        private int tickCounter;

        private FleeGoal() {
        }

        /**
         * Returns whether execution should begin. You can also read and cache any state necessary for execution in this
         * method as well.
         */

        public boolean canUse() {
            LivingEntity livingentity = JellyfishEntity.this.getTarget();
            if (JellyfishEntity.this.isInWater() && livingentity != null) {
                return JellyfishEntity.this.distanceToSqr(livingentity) < 100.0D;
            } else {
                return false;
            }
        }

        /**
         * Execute a one shot task or start executing a continuous task
         */

        public void start() {
            this.tickCounter = 0;
        }

        /**
         * Keep ticking a continuous task that has already been started
         */

        public void tick() {
            ++this.tickCounter;
            LivingEntity livingentity = JellyfishEntity.this.getTarget();
            if (livingentity != null) {
                Vec3 vector3d = new Vec3(JellyfishEntity.this.getX() - livingentity.getX(), JellyfishEntity.this.getY() - livingentity.getY(), JellyfishEntity.this.getZ() - livingentity.getZ());
                BlockState blockstate = JellyfishEntity.this.level.getBlockState(new BlockPos(JellyfishEntity.this.getX() + vector3d.x, JellyfishEntity.this.getY() + vector3d.y, JellyfishEntity.this.getZ() + vector3d.z));
                FluidState fluidstate = JellyfishEntity.this.level.getFluidState(new BlockPos(JellyfishEntity.this.getX() + vector3d.x, JellyfishEntity.this.getY() + vector3d.y, JellyfishEntity.this.getZ() + vector3d.z));
                if (fluidstate.is(FluidTags.WATER) || blockstate.isAir()) {
                    double d0 = vector3d.length();
                    if (d0 > 0.0D) {
                        vector3d.normalize();
                        float f = 3.0F;
                        if (d0 > 5.0D) {
                            f = (float) ((double) f - (d0 - 5.0D) / 5.0D);
                        }

                        if (f > 0.0F) {
                            vector3d = vector3d.scale(f);
                        }
                    }

                    if (blockstate.isAir()) {
                        vector3d = vector3d.subtract(0.0D, vector3d.y, 0.0D);
                    }

                    JellyfishEntity.this.setMovementVector((float) vector3d.x / 20.0F, (float) vector3d.y / 20.0F, (float) vector3d.z / 20.0F);
                }

                if (this.tickCounter % 10 == 5) {
                    JellyfishEntity.this.level.addParticle(ParticleTypes.BUBBLE, JellyfishEntity.this.getX(), JellyfishEntity.this.getY(), JellyfishEntity.this.getZ(), 0.0D, 0.0D, 0.0D);
                }

            }
        }
    }

    class MoveRandomGoal extends Goal {
        private final JellyfishEntity squid;

        public MoveRandomGoal(JellyfishEntity p_i48823_2_) {
            this.squid = p_i48823_2_;
        }

        /**
         * Returns whether execution should begin. You can also read and cache any state necessary for execution in this
         * method as well.
         */
        public boolean canUse() {
            return true;
        }

        /**
         * Keep ticking a continuous task that has already been started
         */
        public void tick() {
            int i = this.squid.getNoActionTime();
            if (i > 100) {
                this.squid.setMovementVector(0.0F, 0.0F, 0.0F);
            } else if (this.squid.getRandom().nextInt(50) == 0 || !this.squid.isInWater() || !this.squid.hasMovementVector()) {
                float f = this.squid.getRandom().nextFloat() * ((float) Math.PI * 2F);
                float f1 = Mth.cos(f) * 0.2F;
                float f2 = -0.1F + this.squid.getRandom().nextFloat() * 0.2F;
                float f3 = Mth.sin(f) * 0.2F;
                this.squid.setMovementVector(f1, f2, f3);
            }

        }
    }

}

