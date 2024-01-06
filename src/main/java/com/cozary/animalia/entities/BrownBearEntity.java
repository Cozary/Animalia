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
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.RandomPositionGenerator;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.controller.MovementController;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.BeeEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.particles.ItemParticleData;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.pathfinding.Path;
import net.minecraft.pathfinding.PathNodeType;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.EnumSet;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.function.Predicate;

public class BrownBearEntity extends AnimalEntity implements IAngerable {
    private static final DataParameter<Boolean> IS_STANDING = EntityDataManager.defineId(BrownBearEntity.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Integer> EAT_COUNTER = EntityDataManager.defineId(BrownBearEntity.class, DataSerializers.INT);
    private static final DataParameter<Byte> BEAR_FLAGS = EntityDataManager.defineId(BrownBearEntity.class, DataSerializers.BYTE);
    private static final RangedInteger field_234217_by_ = TickRangeConverter.rangeOfSeconds(20, 39);

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

    public BrownBearEntity(EntityType<? extends BrownBearEntity> type, World worldIn) {
        super(type, worldIn);
        this.setPathfindingMalus(PathNodeType.STICKY_HONEY, 0.0F);
        this.moveControl = new MoveHelperController(this);
        if (!this.isBaby()) {
            this.setCanPickUpLoot(true);
        }
    }

    public static AttributeModifierMap.MutableAttribute setCustomAttributes() {
        return MobEntity.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 35.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.3D)
                .add(Attributes.FOLLOW_RANGE, 15.0D)
                .add(Attributes.ATTACK_DAMAGE, 5.0D);
    }

    public static boolean canBrownBearSpawn(EntityType<? extends DirtyPigEntity> animal, IWorld world, SpawnReason reason, BlockPos pos, Random random) {
        return world.getBlockState(pos.below()).is(Blocks.GRASS_BLOCK) && world.getLightEmission(pos) > 8 && world.canSeeSky(pos);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(0, new MeleeAttackGoal());
        this.targetSelector.addGoal(2, new HurtByTargetGoal());
        this.targetSelector.addGoal(1, new AttackPlayerGoal());
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, PlayerEntity.class, 10, true, false, this::isAngryAt));
        this.targetSelector.addGoal(4, new NearestAttackableTargetGoal<>(this, BeeEntity.class, 10, true, true, null));
        this.targetSelector.addGoal(5, new ResetAngerGoal<>(this, false));
        this.goalSelector.addGoal(7, new SwimGoal(this));
        this.goalSelector.addGoal(8, new PanicGoal());
        this.goalSelector.addGoal(9, new FollowParentGoal(this, 1.25D));
        this.goalSelector.addGoal(10, new BreedGoal(this, 1.0D));
        this.goalSelector.addGoal(11, new RandomWalkingGoal(this, 1.0D));
        this.goalSelector.addGoal(12, new LookAtGoal(this, PlayerEntity.class, 6.0F));
        this.goalSelector.addGoal(13, new LookRandomlyGoal(this));
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

    private void startMovingTo(BlockPos pos) {
        Vector3d vector3d = Vector3d.atBottomCenterOf(pos);
        int i = 0;
        BlockPos blockpos = this.blockPosition();
        int j = (int) vector3d.y - blockpos.getY();
        if (j > 2) {
            i = 4;
        } else if (j < -2) {
            i = -4;
        }

        int k = 6;
        int l = 8;
        int i1 = blockpos.distManhattan(pos);
        if (i1 < 15) {
            k = i1 / 2;
            l = i1 / 2;
        }

        Vector3d vector3d1 = RandomPositionGenerator.getAirPosTowards(this, k, l, i, vector3d, (float) Math.PI / 10F);
        if (vector3d1 != null) {
            this.navigation.setMaxVisitedNodesMultiplier(0.5F);
            this.navigation.moveTo(vector3d1.x, vector3d1.y, vector3d1.z, 1.0D);
        }
    }

    @Override
    public boolean canTakeItem(ItemStack itemstackIn) {
        EquipmentSlotType equipmentslottype = MobEntity.getEquipmentSlotForItem(itemstackIn);
        if (!this.getItemBySlot(equipmentslottype).isEmpty()) {
            return false;
        } else {
            return equipmentslottype == EquipmentSlotType.MAINHAND && super.canTakeItem(itemstackIn);
        }
    }

    private void func_213546_et() {
        if (!this.func_213578_dZ() && this.func_213556_dX() && !this.getItemBySlot(EquipmentSlotType.MAINHAND).isEmpty() && this.random.nextInt(80) == 1) {
            this.func_213534_t(true);
        } else if (this.getItemBySlot(EquipmentSlotType.MAINHAND).isEmpty() || !this.func_213556_dX()) {
            this.func_213534_t(false);
        }


        /**
         *   start eating
         */

        if (this.func_213578_dZ()) { // && CanEat
            this.func_213533_eu();
            if (!this.level.isClientSide && this.getEatCounter() > 80 && this.random.nextInt(20) == 1) {
                if (this.getEatCounter() > 100 && this.isBreedingItemOrCake(this.getItemBySlot(EquipmentSlotType.MAINHAND))) {
                    if (!this.level.isClientSide) {
                        this.setItemSlot(EquipmentSlotType.MAINHAND, ItemStack.EMPTY);
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
                Vector3d vector3d = new Vector3d(((double) this.random.nextFloat() - 0.5D) * 0.1D, Math.random() * 0.1D + 0.1D, ((double) this.random.nextFloat() - 0.5D) * 0.1D);
                vector3d = vector3d.xRot(-this.xRot * ((float) Math.PI / 180F));
                vector3d = vector3d.yRot(-this.yRot * ((float) Math.PI / 180F));
                double d0 = (double) (-this.random.nextFloat()) * 0.6D - 0.3D;
                Vector3d vector3d1 = new Vector3d(((double) this.random.nextFloat() - 0.5D) * 0.8D, d0, 1.0D + ((double) this.random.nextFloat() - 0.5D) * 0.4D);
                vector3d1 = vector3d1.yRot(-this.yBodyRot * ((float) Math.PI / 180F));
                vector3d1 = vector3d1.add(this.getX(), this.getEyeY() + 1.0D, this.getZ());
                this.level.addParticle(new ItemParticleData(ParticleTypes.ITEM, this.getItemBySlot(EquipmentSlotType.MAINHAND)), vector3d1.x, vector3d1.y, vector3d1.z, vector3d.x, vector3d.y + 0.05D, vector3d.z);
            }
        }
    }

    @Override
    protected void pickUpItem(ItemEntity itemEntity) {
        if (this.getItemBySlot(EquipmentSlotType.MAINHAND).isEmpty() && BEAR_ITEMS.test(itemEntity)) {
            this.onItemPickup(itemEntity);
            ItemStack itemstack = itemEntity.getItem();
            this.setItemSlot(EquipmentSlotType.MAINHAND, itemstack);
            this.handDropChances[EquipmentSlotType.MAINHAND.getIndex()] = 2.0F;
            this.take(itemEntity, itemstack.getCount());
            itemEntity.remove();
        }
    }

    @Override
    public ActionResultType mobInteract(PlayerEntity player, Hand hand) {
        ItemStack itemstack = player.getItemInHand(hand);
        if (this.func_213567_dY()) {
            this.func_213542_s(false);
            return ActionResultType.sidedSuccess(this.level.isClientSide);
        } else if (this.isBreedingItem(itemstack)) {
            if (this.getTarget() != null) {
                this.gotHoney = true;
            }
            if (this.isBaby()) {
                this.usePlayerItem(player, itemstack);
                this.ageUp((int) ((float) (-this.getAge() / 20) * 0.1F), true);
            } else if (!this.level.isClientSide && this.getAge() == 0 && this.canFallInLove()) {
                this.usePlayerItem(player, itemstack);
                this.setInLove(player);
            } else {
                if (this.level.isClientSide || this.func_213556_dX() || this.isInWater()) {
                    return ActionResultType.PASS;
                }
                this.tryToSit();
                this.func_213534_t(true);
                ItemStack itemstack1 = this.getItemBySlot(EquipmentSlotType.MAINHAND);
                if (!itemstack1.isEmpty() && !player.abilities.instabuild) {
                    this.spawnAtLocation(itemstack1);
                }

                this.setItemSlot(EquipmentSlotType.MAINHAND, new ItemStack(itemstack.getItem(), 1));
                this.usePlayerItem(player, itemstack);
            }
            return ActionResultType.SUCCESS;
        } else {
            return ActionResultType.PASS;
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

    /**
     * negate eating
     */

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
        return MathHelper.lerp(p_189795_1_, this.clientSideStandAnimation0, this.clientSideStandAnimation) / 6.0F;
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

    public boolean canBeLeashedTo(PlayerEntity player) {
        return false;
    }

    /**
     * Anger
     */
    @Override
    public void readAdditionalSaveData(CompoundNBT compound) {
        super.readAdditionalSaveData(compound);
        this.readPersistentAngerSaveData((ServerWorld) this.level, compound);
    }

    @Override
    public void addAdditionalSaveData(CompoundNBT compound) {
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
        this.setRemainingPersistentAngerTime(field_234217_by_.randomValue(this.random));

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
                this.clientSideStandAnimation = MathHelper.clamp(this.clientSideStandAnimation + 1.0F, 0.0F, 6.0F);
            } else {
                this.clientSideStandAnimation = MathHelper.clamp(this.clientSideStandAnimation - 1.0F, 0.0F, 6.0F);
            }
        }
        if (this.getTarget() == null) {
            this.gotHoney = false;
        }
        if (this.warningSoundTicks > 0) {
            --this.warningSoundTicks;
        }

        if (!this.level.isClientSide) {
            this.updatePersistentAnger((ServerWorld) this.level, true);
        }
        this.func_213546_et();

    }

    /**
     * plus hitbox
     *
     * @param poseIn
     * @return
     */

    @Override
    public EntitySize getDimensions(Pose poseIn) {
        if (this.clientSideStandAnimation > 0.0F) {
            float f = this.clientSideStandAnimation / 6.0F;
            float f1 = 1.0F + f;
            return super.getDimensions(poseIn).scale(1.0F, f1);
        } else {
            return super.getDimensions(poseIn);
        }
    }

    /**
     * attack
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
     * water slow
     *
     * @return
     */

    @Override
    protected float getWaterSlowDown() {
        return 0.98F;
    }

    /**
     * spawn world
     *
     * @param worldIn
     * @param difficultyIn
     * @param reason
     * @param spawnDataIn
     * @param dataTag
     * @return
     */

    @Override
    public ILivingEntityData finalizeSpawn(IServerWorld worldIn, DifficultyInstance difficultyIn, SpawnReason reason, @Nullable ILivingEntityData spawnDataIn, @Nullable CompoundNBT dataTag) {
        if (spawnDataIn == null) {
            spawnDataIn = new AgeableData(0.1F);
        }
        return super.finalizeSpawn(worldIn, difficultyIn, reason, spawnDataIn, dataTag);
    }

    @Override
    protected int getExperienceReward(PlayerEntity p_70693_1_) {
        return 3 + this.level.random.nextInt(4);
    }

    @Override
    public boolean canBreatheUnderwater() {
        return true;
    }

    @Nullable
    @Override
    public AgeableEntity getBreedOffspring(ServerWorld p_241840_1_, AgeableEntity p_241840_2_) {
        return ModEntityTypes.BROWN_BEAR.get().create(this.level);
    }


    /**
     * animations
     */

    static class MoveHelperController extends MovementController {
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
            this.setFlags(EnumSet.of(Flag.MOVE));
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
                        } else {
                            BrownBearEntity.this.startMovingTo(BrownBearEntity.this.hivePos);
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
            this.setFlags(EnumSet.of(Flag.MOVE));
        }

        /**
         * Returns whether execution should begin. You can also read and cache any state necessary for execution in this
         * method as well.
         */

        @Override
        public boolean canUse() {
            if (this.field_220832_b <= BrownBearEntity.this.tickCount && !BrownBearEntity.this.isBaby() && !BrownBearEntity.this.isInWater() && BrownBearEntity.this.canPerformAction()) {
                List<ItemEntity> list = BrownBearEntity.this.level.getEntitiesOfClass(ItemEntity.class, BrownBearEntity.this.getBoundingBox().inflate(6.0D, 6.0D, 6.0D), BrownBearEntity.BEAR_ITEMS);
                return !list.isEmpty() || !BrownBearEntity.this.getItemBySlot(EquipmentSlotType.MAINHAND).isEmpty();
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
            if (!BrownBearEntity.this.func_213556_dX() && !BrownBearEntity.this.getItemBySlot(EquipmentSlotType.MAINHAND).isEmpty()) {
                BrownBearEntity.this.tryToSit();
            }

        }

        /**
         * Execute a one shot task or start executing a continuous task
         */

        public void start() {
            List<ItemEntity> list = BrownBearEntity.this.level.getEntitiesOfClass(ItemEntity.class, BrownBearEntity.this.getBoundingBox().inflate(8.0D, 8.0D, 8.0D), BrownBearEntity.BEAR_ITEMS);
            if (!list.isEmpty() && BrownBearEntity.this.getItemBySlot(EquipmentSlotType.MAINHAND).isEmpty()) {
                BrownBearEntity.this.getNavigation().moveTo(list.get(0), 1.2F);
            } else if (!BrownBearEntity.this.getItemBySlot(EquipmentSlotType.MAINHAND).isEmpty()) {
                BrownBearEntity.this.tryToSit();
            }

            this.field_220832_b = 0;
        }

        /**
         * Reset the task's internal state. Called when this task is interrupted by another one
         */

        public void stop() {
            ItemStack itemstack = BrownBearEntity.this.getItemBySlot(EquipmentSlotType.MAINHAND);
            if (!itemstack.isEmpty()) {
                BrownBearEntity.this.spawnAtLocation(itemstack);
                BrownBearEntity.this.setItemSlot(EquipmentSlotType.MAINHAND, ItemStack.EMPTY);
                int i = BrownBearEntity.this.random.nextInt(150) + 10;
                this.field_220832_b = BrownBearEntity.this.tickCount + i * 20;
            }

            BrownBearEntity.this.func_213553_r(false);
        }
    }

    /**
     * attack player GOAL
     */
    class AttackPlayerGoal extends NearestAttackableTargetGoal<PlayerEntity> {
        public AttackPlayerGoal() {
            super(BrownBearEntity.this, PlayerEntity.class, 20, true, true, null);
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

    class HurtByTargetGoal extends net.minecraft.entity.ai.goal.HurtByTargetGoal {
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
        protected void alertOther(MobEntity mobIn, LivingEntity targetIn) {
            if (mobIn instanceof BrownBearEntity && !mobIn.isBaby()) {
                super.alertOther(mobIn, targetIn);

            }

        }
    }

    /**
     * MeleeAttack GOAL
     */

    class MeleeAttackGoal extends net.minecraft.entity.ai.goal.MeleeAttackGoal {

        public MeleeAttackGoal() {
            super(BrownBearEntity.this, 1.25D, true);

        }

        protected void checkAndPerformAttack(LivingEntity enemy, double distToEnemySqr) {
            BrownBearEntity.this.setItemInHand(Hand.MAIN_HAND, ItemStack.EMPTY);
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

    class PanicGoal extends net.minecraft.entity.ai.goal.PanicGoal {
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