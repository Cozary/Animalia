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
import com.google.common.collect.Lists;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.util.TimeUtil;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.goal.target.ResetUniversalAngerTargetGoal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.Bee;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.level.pathfinder.Path;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.EnumSet;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.function.Predicate;

public class BrownBearEntity extends Animal implements NeutralMob {
    private static final EntityDataAccessor<Boolean> IS_STANDING = SynchedEntityData.defineId(BrownBearEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Integer> EAT_COUNTER = SynchedEntityData.defineId(BrownBearEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Byte> BEAR_FLAGS = SynchedEntityData.defineId(BrownBearEntity.class, EntityDataSerializers.BYTE);
    private static final UniformInt field_234217_by_ = TimeUtil.rangeOfSeconds(20, 39);

    private static final Predicate<ItemEntity> BEAR_ITEMS = (p_213575_0_) -> {
        Item item = p_213575_0_.getItem().getItem();
        return (item == Items.HONEYCOMB || item == Blocks.HONEYCOMB_BLOCK.asItem() || item == Blocks.HONEY_BLOCK.asItem() || item == Blocks.CAKE.asItem()) && p_213575_0_.isAlive() && !p_213575_0_.hasPickUpDelay();
    };
    private boolean gotHoney;
    private float clientSideStandAnimation0;
    private float clientSideStandAnimation;
    private int warningSoundTicks;
    private int field_234218_bz_;
    private UUID field_234216_bA_;
    private int remainingCooldownBeforeLocatingNewHive = 0;
    @Nullable
    private BlockPos hivePos = null;
    private FindBeehiveGoal findBeehiveGoal;

    public BrownBearEntity(EntityType<? extends BrownBearEntity> type, Level worldIn) {
        super(type, worldIn);
        this.setPathfindingMalus(BlockPathTypes.STICKY_HONEY, 0.0F);
        this.moveControl = new MoveHelperController(this);
        if (!this.isBaby()) {
            this.setCanPickUpLoot(true);
        }
    }

    public static AttributeSupplier.Builder setCustomAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 35.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.3D)
                .add(Attributes.FOLLOW_RANGE, 15.0D)
                .add(Attributes.ATTACK_DAMAGE, 5.0D);
    }

    /**
     * spawn
     */

    public static boolean canBrownBearSpawn(EntityType<? extends DirtyPigEntity> animal, LevelAccessor world, MobSpawnType reason, BlockPos pos, Random random) {
        return world.getBlockState(pos.below()).is(Blocks.GRASS_BLOCK) && world.getLightEmission(pos) > 8 && world.canSeeSky(pos);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(0, new MeleeAttackGoal());
        this.targetSelector.addGoal(2, new HurtByTargetGoal());
        this.targetSelector.addGoal(1, new AttackPlayerGoal());
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, Player.class, 10, true, false, this::isAngryAt));
        this.targetSelector.addGoal(4, new NearestAttackableTargetGoal<>(this, Bee.class, 10, true, true, null));
        this.targetSelector.addGoal(5, new ResetUniversalAngerTargetGoal<>(this, false));
        this.goalSelector.addGoal(7, new FloatGoal(this));
        this.goalSelector.addGoal(8, new PanicGoal());
        this.goalSelector.addGoal(9, new FollowParentGoal(this, 1.25D));
        this.goalSelector.addGoal(10, new BreedGoal(this, 1.0D));
        this.goalSelector.addGoal(11, new RandomStrollGoal(this, 1.0D));
        this.goalSelector.addGoal(12, new LookAtPlayerGoal(this, Player.class, 6.0F));
        this.goalSelector.addGoal(13, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(14, new TemptGoal(this, 1.0D, Ingredient.of(Items.HONEYCOMB), false));
        this.goalSelector.addGoal(15, new SitGoal());
        this.findBeehiveGoal = new FindBeehiveGoal();
        this.goalSelector.addGoal(16, this.findBeehiveGoal);
    }


    /**
     * Go TO EATING BEES
     */

    @Override
    public void aiStep() {
        super.aiStep();
        if (!this.level.isClientSide) {
            if (this.remainingCooldownBeforeLocatingNewHive > 0) {
                --this.remainingCooldownBeforeLocatingNewHive;
            }
        }
    }

    private boolean isWithinDistance(BlockPos pos, int distance) {
        return pos.closerThan(this.blockPosition(), distance);
    }

    private boolean isTooFar(BlockPos pos) {
        return !this.isWithinDistance(pos, 32);
    }

    @Override
    public boolean canTakeItem(ItemStack itemstackIn) {
        EquipmentSlot equipmentslottype = Mob.getEquipmentSlotForItem(itemstackIn);
        if (!this.getItemBySlot(equipmentslottype).isEmpty()) {
            return false;
        } else {
            return equipmentslottype == EquipmentSlot.MAINHAND && super.canTakeItem(itemstackIn);
        }
    }

    private void func_213546_et() {
        if (!this.func_213578_dZ() && this.func_213556_dX() && !this.getItemBySlot(EquipmentSlot.MAINHAND).isEmpty() && this.random.nextInt(80) == 1) {
            this.func_213534_t(true);
        } else if (this.getItemBySlot(EquipmentSlot.MAINHAND).isEmpty() || !this.func_213556_dX()) {
            this.func_213534_t(false);
        }


        /**
         *   eat
         */

        if (this.func_213578_dZ()) { // && CanEat
            this.func_213533_eu();
            if (!this.level.isClientSide && this.getEatCounter() > 80 && this.random.nextInt(20) == 1) {
                if (this.getEatCounter() > 100 && this.isBreedingItemOrCake(this.getItemBySlot(EquipmentSlot.MAINHAND))) {
                    if (!this.level.isClientSide) {
                        this.setItemSlot(EquipmentSlot.MAINHAND, ItemStack.EMPTY);
                    }
                    this.func_213553_r(false);
                }
                this.func_213534_t(false);
                return;
            }
            this.setEatCounter(this.getEatCounter() + 1);
        }
    }

    private void func_213533_eu() {
        if (this.getEatCounter() % 5 == 0) {
            this.playSound(SoundEvents.PANDA_EAT, 0.5F + 0.5F * (float) this.random.nextInt(2), (this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F);

            for (int i = 0; i < 6; ++i) {
                Vec3 vector3d = new Vec3(((double) this.random.nextFloat() - 0.5D) * 0.1D, Math.random() * 0.1D + 0.1D, ((double) this.random.nextFloat() - 0.5D) * 0.1D);
                vector3d = vector3d.xRot(-this.xRotO * ((float) Math.PI / 180F));
                vector3d = vector3d.yRot(-this.yRotO * ((float) Math.PI / 180F));
                double d0 = (double) (-this.random.nextFloat()) * 0.6D - 0.3D;
                Vec3 vector3d1 = new Vec3(((double) this.random.nextFloat() - 0.5D) * 0.8D, d0, 1.0D + ((double) this.random.nextFloat() - 0.5D) * 0.4D);
                vector3d1 = vector3d1.yRot(-this.yBodyRot * ((float) Math.PI / 180F));
                vector3d1 = vector3d1.add(this.getX(), this.getEyeY() + 1.0D, this.getZ());
                this.level.addParticle(new ItemParticleOption(ParticleTypes.ITEM, this.getItemBySlot(EquipmentSlot.MAINHAND)), vector3d1.x, vector3d1.y, vector3d1.z, vector3d.x, vector3d.y + 0.05D, vector3d.z);
            }
        }
    }

    @Override
    protected void pickUpItem(ItemEntity itemEntity) {
        if (this.getItemBySlot(EquipmentSlot.MAINHAND).isEmpty() && BEAR_ITEMS.test(itemEntity)) {
            this.onItemPickup(itemEntity);
            ItemStack itemstack = itemEntity.getItem();
            this.setItemSlot(EquipmentSlot.MAINHAND, itemstack);
            this.handDropChances[EquipmentSlot.MAINHAND.getIndex()] = 2.0F;
            this.take(itemEntity, itemstack.getCount());
            itemEntity.remove(RemovalReason.KILLED);
        }
    }

    @Override
    public InteractionResult mobInteract(Player player, InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);
        if (this.func_213567_dY()) {
            this.func_213542_s(false);
            return InteractionResult.sidedSuccess(this.level.isClientSide);
        } else if (this.isBreedingItem(itemstack)) {
            if (this.getTarget() != null) {
                this.gotHoney = true;
            }
            if (this.isBaby()) {
                this.usePlayerItem(player, hand, itemstack);
                this.ageUp((int) ((float) (-this.getAge() / 20) * 0.1F), true);
            } else if (!this.level.isClientSide && this.getAge() == 0 && this.canFallInLove()) {
                this.usePlayerItem(player, hand, itemstack);
                this.setInLove(player);
            } else {
                if (this.level.isClientSide || this.func_213556_dX() || this.isInWater()) {
                    return InteractionResult.PASS;
                }
                this.tryToSit();
                this.func_213534_t(true);
                ItemStack itemstack1 = this.getItemBySlot(EquipmentSlot.MAINHAND);
                if (!itemstack1.isEmpty() && !player.getAbilities().instabuild) {
                    this.spawnAtLocation(itemstack1);
                }

                this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(itemstack.getItem(), 1));
                this.usePlayerItem(player, hand, itemstack);
            }
            return InteractionResult.SUCCESS;
        } else {
            return InteractionResult.PASS;
        }
    }

    public boolean func_213578_dZ() {
        return this.entityData.get(EAT_COUNTER) > 0;
    }

    public void func_213534_t(boolean p_213534_1_) {
        this.entityData.set(EAT_COUNTER, p_213534_1_ ? 1 : 0);
    }

    private int getEatCounter() {
        return this.entityData.get(EAT_COUNTER);
    }

    private void setEatCounter(int p_213571_1_) {
        this.entityData.set(EAT_COUNTER, p_213571_1_);
    }

    private void tryToSit() {
        if (!this.isInWater()) {
            this.setZza(0.0F);
            this.getNavigation().stop();
            this.func_213553_r(true);
        }
    }

    public boolean canPerformAction() {
        return !this.func_213567_dY() && !this.func_213578_dZ() && !this.func_213564_eh() && !this.func_213556_dX() && !isStanding();
    }

    public boolean isStanding() {
        return this.entityData.get(IS_STANDING);
    }

    public void setStanding(boolean standing) {
        this.entityData.set(IS_STANDING, standing);
    }

    @OnlyIn(Dist.CLIENT)
    public float getStandingAnimationScale(float p_189795_1_) {
        return Mth.lerp(p_189795_1_, this.clientSideStandAnimation0, this.clientSideStandAnimation) / 6.0F;
    }

    /**
     * Flags
     */

    private void setPandaFlag(int flagId, boolean p_213587_2_) {
        byte b0 = this.entityData.get(BEAR_FLAGS);
        if (p_213587_2_) {
            this.entityData.set(BEAR_FLAGS, (byte) (b0 | flagId));
        } else {
            this.entityData.set(BEAR_FLAGS, (byte) (b0 & ~flagId));
        }
    }

    public void func_213553_r(boolean p_213553_1_) {
        this.setPandaFlag(8, p_213553_1_);
    }

    public boolean func_213567_dY() {
        return this.getPandaFlag(16);
    }

    public void func_213542_s(boolean p_213542_1_) {
        this.setPandaFlag(16, p_213542_1_);
    }

    public boolean func_213564_eh() {
        return this.getPandaFlag(4);
    }

    public boolean func_213556_dX() {
        return this.getPandaFlag(8);
    }

    private boolean getPandaFlag(int flagId) {
        return (this.entityData.get(BEAR_FLAGS) & flagId) != 0;
    }


    /**
     * Breed items
     */

    public boolean isBreedingItem(ItemStack stack) {
        return stack.getItem() == Items.HONEYCOMB;
    }

    private boolean isBreedingItemOrCake(ItemStack stack) {
        return this.isBreedingItem(stack) || stack.getItem() == Blocks.CAKE.asItem();
    }

    public boolean canBeLeashedTo(Player player) {
        return false;
    }

    /**
     * Anger
     */
    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        this.readPersistentAngerSaveData(this.level, compound);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
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
     * Sounds
     *
     * @return
     */

    @Override
    @Nullable
    protected SoundEvent getAmbientSound() {
        return ModSound.BROWN_BEAR_AMBIENT.get();
    }

    @Override
    @Nullable
    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return ModSound.BROWN_BEAR_HURT.get();
    }

    @Override
    @Nullable
    protected SoundEvent getDeathSound() {
        return ModSound.BROWN_BEAR_DEATH.get();
    }

    @Override
    @Nullable
    protected void playStepSound(BlockPos pos, BlockState blockIn) {
        this.playSound(SoundEvents.POLAR_BEAR_STEP, 0.15F, 0.8F);
    }

    /**
     * Data
     */

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(IS_STANDING, false);
        this.entityData.define(BEAR_FLAGS, (byte) 0);
        this.entityData.define(EAT_COUNTER, 0);
    }

    /**
     * Called to update the entity's position/logic.
     */

    @Override
    public void tick() {
        super.tick();

        if (this.level.isClientSide) {
            if (this.clientSideStandAnimation != this.clientSideStandAnimation0) {
                this.refreshDimensions();
            }

            this.clientSideStandAnimation0 = this.clientSideStandAnimation;
            if (this.isStanding()) {
                this.clientSideStandAnimation = Mth.clamp(this.clientSideStandAnimation + 1.0F, 0.0F, 6.0F);
            } else {
                this.clientSideStandAnimation = Mth.clamp(this.clientSideStandAnimation - 1.0F, 0.0F, 6.0F);
            }
        }
        if (this.getTarget() == null) {
            this.gotHoney = false;
        }
        if (this.warningSoundTicks > 0) {
            --this.warningSoundTicks;
        }

        if (!this.level.isClientSide) {
            this.updatePersistentAnger((ServerLevel) this.level, true);
        }
        this.func_213546_et();

    }

    /**
     * hitbox
     *
     * @param poseIn
     * @return
     */

    @Override
    public EntityDimensions getDimensions(Pose poseIn) {
        if (this.clientSideStandAnimation > 0.0F) {
            float f = this.clientSideStandAnimation / 6.0F;
            float f1 = 1.0F + f;
            return super.getDimensions(poseIn).scale(1.0F, f1);
        } else {
            return super.getDimensions(poseIn);
        }
    }

    /**
     *
     *
     * @param entityIn
     * @return
     */

    @Override
    public boolean doHurtTarget(Entity entityIn) {
        boolean flag = entityIn.hurt(DamageSource.mobAttack(this), (float) ((int) this.getAttributeValue(Attributes.ATTACK_DAMAGE)));
        if (flag) {
            this.doEnchantDamageEffects(this, entityIn);
        }
        return flag;
    }

    /**
     *
     * @return
     */

    @Override
    protected float getWaterSlowDown() {
        return 0.98F;
    }

    /**
     *
     * @param worldIn
     * @param difficultyIn
     * @param reason
     * @param spawnDataIn
     * @param dataTag
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
        return ModEntityTypes.BROWN_BEAR.get().create(this.level);
    }

    @Override
    protected int getExperienceReward(Player p_70693_1_) {
        return 3 + this.level.random.nextInt(4);
    }

    @Override
    public boolean canBreatheUnderwater() {
        return true;
    }

    /**
     */

    static class MoveHelperController extends MoveControl {
        private final BrownBearEntity panda;

        public MoveHelperController(BrownBearEntity pandaIn) {
            super(pandaIn);
            this.panda = pandaIn;
        }

        public void tick() {
            if (this.panda.canPerformAction()) {
                super.tick();
            }
        }
    }

    /**
     * Do or Not Do EATING BEES
     */
    abstract class PassiveGoal extends Goal {
        private PassiveGoal() {
        }

        public abstract boolean canBeeStart();

        public abstract boolean canBeeContinue();

        /**
         * Returns whether execution should begin. You can also read and cache any state necessary for execution in this
         * method as well.
         */
        public boolean canUse() {
            return this.canBeeStart() && !BrownBearEntity.this.isAngry();
        }

        /**
         * Returns whether an in-progress EntityAIBase should continue executing
         */
        public boolean canContinueToUse() {
            return this.canBeeContinue() && !BrownBearEntity.this.isAngry();
        }
    }

    public class FindBeehiveGoal extends PassiveGoal {

        private final List<BlockPos> possibleHives = Lists.newArrayList();
        private int ticks = BrownBearEntity.this.level.random.nextInt(10);
        @Nullable
        private Path path = null;
        private int field_234183_f_;

        FindBeehiveGoal() {
            this.setFlags(EnumSet.of(Goal.Flag.MOVE));
        }

        public boolean canBeeStart() {
            return BrownBearEntity.this.hivePos != null && !BrownBearEntity.this.hasRestriction() && !this.isCloseEnough(BrownBearEntity.this.hivePos) && BrownBearEntity.this.level.getBlockState(BrownBearEntity.this.hivePos).is(BlockTags.BEEHIVES);
        }

        public boolean canBeeContinue() {
            return this.canBeeStart();
        }

        /**
         * Execute a one shot task or start executing a continuous task
         */
        public void start() {
            this.ticks = 0;
            this.field_234183_f_ = 0;
            super.start();
        }

        /**
         * Reset the task's internal state. Called when this task is interrupted by another one
         */
        public void stop() {
            this.ticks = 0;
            this.field_234183_f_ = 0;
            BrownBearEntity.this.navigation.stop();
            BrownBearEntity.this.navigation.resetMaxVisitedNodesMultiplier();
        }

        /**
         * Keep ticking a continuous task that has already been started
         */
        public void tick() {

            if (BrownBearEntity.this.hivePos != null) {
                ++this.ticks;
                if (this.ticks > 600) {
                    this.makeChosenHivePossibleHive();
                } else if (!BrownBearEntity.this.navigation.isInProgress()) {
                    if (!BrownBearEntity.this.isWithinDistance(BrownBearEntity.this.hivePos, 16)) {
                        if (BrownBearEntity.this.isTooFar(BrownBearEntity.this.hivePos)) {
                            this.reset();
                        }
                    } else {
                        boolean flag = this.startMovingToFar(BrownBearEntity.this.hivePos);
                        if (!flag) {
                            this.makeChosenHivePossibleHive();
                        } else if (this.path != null && BrownBearEntity.this.navigation.getPath().sameAs(this.path)) {
                            ++this.field_234183_f_;
                            if (this.field_234183_f_ > 60) {
                                this.reset();
                                this.field_234183_f_ = 0;
                            }
                        } else {
                            this.path = BrownBearEntity.this.navigation.getPath();
                        }

                    }
                }
            }
        }

        private boolean startMovingToFar(BlockPos pos) {

            BrownBearEntity.this.navigation.setMaxVisitedNodesMultiplier(10.0F);
            BrownBearEntity.this.navigation.moveTo(pos.getX(), pos.getY(), pos.getZ(), 1.0D);
            return BrownBearEntity.this.navigation.getPath() != null && BrownBearEntity.this.navigation.getPath().canReach();
        }

        private boolean isPossibleHive(BlockPos pos) {
            return this.possibleHives.contains(pos);
        }

        private void addPossibleHives(BlockPos pos) {
            this.possibleHives.add(pos);

            while (this.possibleHives.size() > 3) {
                this.possibleHives.remove(0);
            }

        }

        private void clearPossibleHives() {
            this.possibleHives.clear();
        }

        private void makeChosenHivePossibleHive() {
            if (BrownBearEntity.this.hivePos != null) {
                this.addPossibleHives(BrownBearEntity.this.hivePos);
            }

            this.reset();
        }

        private void reset() {
            BrownBearEntity.this.hivePos = null;
            BrownBearEntity.this.remainingCooldownBeforeLocatingNewHive = 200;
        }

        private boolean isCloseEnough(BlockPos pos) {
            if (BrownBearEntity.this.isWithinDistance(pos, 2)) {
                return true;
            } else {
                Path path = BrownBearEntity.this.navigation.getPath();
                return path != null && path.getTarget().equals(pos) && path.canReach() && path.isDone();
            }
        }
    }

    /**
     * SitGoal
     */

    class SitGoal extends Goal {
        private int field_220832_b;

        public SitGoal() {
            this.setFlags(EnumSet.of(Goal.Flag.MOVE));
        }

        /**
         * Returns whether execution should begin. You can also read and cache any state necessary for execution in this
         * method as well.
         */

        @Override
        public boolean canUse() {
            if (this.field_220832_b <= BrownBearEntity.this.tickCount && !BrownBearEntity.this.isBaby() && !BrownBearEntity.this.isInWater() && BrownBearEntity.this.canPerformAction()) {
                List<ItemEntity> list = BrownBearEntity.this.level.getEntitiesOfClass(ItemEntity.class, BrownBearEntity.this.getBoundingBox().inflate(6.0D, 6.0D, 6.0D), BrownBearEntity.BEAR_ITEMS);
                return !list.isEmpty() || !BrownBearEntity.this.getItemBySlot(EquipmentSlot.MAINHAND).isEmpty();
            } else {
                return false;
            }
        }

        /**
         * Returns whether an in-progress EntityAIBase should continue executing
         */

        public boolean canContinueToUse() {
            if (!BrownBearEntity.this.isInWater() && (BrownBearEntity.this.random.nextInt(600) != 1)) {
                return BrownBearEntity.this.random.nextInt(2000) != 1;
            } else {
                return false;
            }
        }

        /**
         * Keep ticking a continuous task that has already been started
         */

        public void tick() {
            if (!BrownBearEntity.this.func_213556_dX() && !BrownBearEntity.this.getItemBySlot(EquipmentSlot.MAINHAND).isEmpty()) {
                BrownBearEntity.this.tryToSit();
            }

        }

        /**
         * Execute a one shot task or start executing a continuous task
         */

        public void start() {
            List<ItemEntity> list = BrownBearEntity.this.level.getEntitiesOfClass(ItemEntity.class, BrownBearEntity.this.getBoundingBox().inflate(8.0D, 8.0D, 8.0D), BrownBearEntity.BEAR_ITEMS);
            if (!list.isEmpty() && BrownBearEntity.this.getItemBySlot(EquipmentSlot.MAINHAND).isEmpty()) {
                BrownBearEntity.this.getNavigation().moveTo(list.get(0), 1.2F);
            } else if (!BrownBearEntity.this.getItemBySlot(EquipmentSlot.MAINHAND).isEmpty()) {
                BrownBearEntity.this.tryToSit();
            }

            this.field_220832_b = 0;
        }

        /**
         * Reset the task's internal state. Called when this task is interrupted by another one
         */

        public void stop() {
            ItemStack itemstack = BrownBearEntity.this.getItemBySlot(EquipmentSlot.MAINHAND);
            if (!itemstack.isEmpty()) {
                BrownBearEntity.this.spawnAtLocation(itemstack);
                BrownBearEntity.this.setItemSlot(EquipmentSlot.MAINHAND, ItemStack.EMPTY);
                int i = BrownBearEntity.this.random.nextInt(150) + 10;
                this.field_220832_b = BrownBearEntity.this.tickCount + i * 20;
            }

            BrownBearEntity.this.func_213553_r(false);
        }
    }

    /**
     */
    class AttackPlayerGoal extends NearestAttackableTargetGoal<Player> {
        public AttackPlayerGoal() {
            super(BrownBearEntity.this, Player.class, 20, true, true, null);
        }

        /**
         * Returns whether execution should begin. You can also read and cache any state necessary for execution in this
         * method as well.
         */

        @Override
        public boolean canUse() {
            if (BrownBearEntity.this.isBaby()) {
                return false;
            } else {
                if (super.canUse()) {
                    for (BrownBearEntity polarbearentity : BrownBearEntity.this.level.getEntitiesOfClass(BrownBearEntity.class, BrownBearEntity.this.getBoundingBox().inflate(8.0D, 4.0D, 8.0D))) {
                        if (polarbearentity.isBaby()) {
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

    /**
     * HurtBy Goal
     */

    class HurtByTargetGoal extends net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal {
        public HurtByTargetGoal() {
            super(BrownBearEntity.this);
        }

        /**
         * Execute a one shot task or start executing a continuous task
         */

        public void start() {
            super.start();
            if (BrownBearEntity.this.isBaby()) {
                this.alertOthers();
                this.stop();
            }

        }

        @Override
        protected void alertOther(Mob mobIn, LivingEntity targetIn) {
            if (mobIn instanceof BrownBearEntity && !mobIn.isBaby()) {
                super.alertOther(mobIn, targetIn);

            }

        }
    }

    /**
     * MeleeAttack GOAL
     */

    class MeleeAttackGoal extends net.minecraft.world.entity.ai.goal.MeleeAttackGoal {

        public MeleeAttackGoal() {
            super(BrownBearEntity.this, 1.25D, true);

        }

        protected void checkAndPerformAttack(LivingEntity enemy, double distToEnemySqr) {
            BrownBearEntity.this.setItemInHand(InteractionHand.MAIN_HAND, ItemStack.EMPTY);
            double d0 = this.getAttackReachSqr(enemy);
            if (distToEnemySqr <= d0 && this.isTimeToAttack()) {
                this.resetAttackCooldown();
                this.mob.doHurtTarget(enemy);
                BrownBearEntity.this.setStanding(false);
            } else if (distToEnemySqr <= d0 * 2.0D) {
                if (this.isTimeToAttack()) {
                    BrownBearEntity.this.setStanding(false);
                    this.resetAttackCooldown();
                }

                if (this.getTicksUntilNextAttack() <= 10) {
                    BrownBearEntity.this.setStanding(true);
                }
            } else {
                this.resetAttackCooldown();
                BrownBearEntity.this.setStanding(false);
            }

        }

        /**
         * Reset the task's internal state. Called when this task is interrupted by another one
         */

        public void stop() {
            BrownBearEntity.this.setStanding(false);
            super.stop();
        }

        @Override
        protected double getAttackReachSqr(LivingEntity attackTarget) {
            return 4.0F + attackTarget.getBbWidth();

        }
    }

    /**
     * Panic GOAL
     */

    class PanicGoal extends net.minecraft.world.entity.ai.goal.PanicGoal {
        public PanicGoal() {
            super(BrownBearEntity.this, 2.0D);
        }

        /**
         * Returns whether execution should begin. You can also read and cache any state necessary for execution in this
         * method as well.
         */

        public boolean canUse() {
            return (BrownBearEntity.this.isBaby() || BrownBearEntity.this.isOnFire()) && super.canUse();
        }
    }
}