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
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.goal.target.ResetUniversalAngerTargetGoal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.BlockPathTypes;

import javax.annotation.Nullable;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.UUID;

public class HippopotamusEntity extends Animal implements NeutralMob {

    public static final Ingredient TEMPTATION_ITEMS = Ingredient.of(Items.MELON_SLICE);
    private static final UniformInt field_234217_by_ = TimeUtil.rangeOfSeconds(20, 39);
    public static EntityDataAccessor<Boolean> AGRESSIVE = SynchedEntityData.defineId(HippopotamusEntity.class, EntityDataSerializers.BOOLEAN);
    public int timeUntilNextSand = 0;
    boolean done = false;
    private int field_234284_bA_;
    private UUID field_234285_bB_;

    public HippopotamusEntity(EntityType<? extends Animal> type, Level worldIn) {
        super(type, worldIn);
        entityData.define(AGRESSIVE, false);
        this.setPathfindingMalus(BlockPathTypes.WATER, 0.0F);
        this.setPathfindingMalus(BlockPathTypes.WATER_BORDER, 16.0F);
    }

    public static AttributeSupplier.Builder setCustomAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 60.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.3D)
                .add(Attributes.FOLLOW_RANGE, 10.0D)
                .add(Attributes.ATTACK_DAMAGE, 2.0D);
    }

    /**
     * Uses for Spawn
     */

    public static boolean canHippopotamusSpawn(EntityType<? extends HippopotamusEntity> entityType, LevelAccessor world, MobSpawnType spawnReason, BlockPos pos, Random random) {
        return world.getBlockState(pos.below()).is(Blocks.SAND) && world.getLightEmission(pos) > 8 && world.canSeeSky(pos);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(5, new FloatGoal(this));
        this.goalSelector.addGoal(1, new RandomStrollGoal(this, 1.0D));
        this.goalSelector.addGoal(2, new TemptGoal(this, 1.0D, TEMPTATION_ITEMS, false));
        this.goalSelector.addGoal(4, new LookAtPlayerGoal(this, Player.class, 20.0F));
        this.goalSelector.addGoal(0, new MeleeHipo());
        this.targetSelector.addGoal(2, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(1, new ResetUniversalAngerTargetGoal<>(this, false));
        this.targetSelector.addGoal(0, new AttackPlayerGoal());

    }

    public void setAttack(boolean attack) {
        this.entityData.set(AGRESSIVE, attack);
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


    @Nullable
    @Override
    public AgeableMob getBreedOffspring(ServerLevel p_146743_, AgeableMob p_146744_) {
        return null;
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        this.addPersistentAngerSaveData(compound);
        compound.putInt("EggLayTime", this.timeUntilNextSand);
    }

    public boolean canBreatheUnderwater() {
        return true;
    }

    protected float getWaterSlowDown() {
        return 1F;
    }

    public boolean isBreedingItem(ItemStack stack) {
        return TEMPTATION_ITEMS.test(stack);
    }

    private boolean isAgressiveL() {
        return this.targetSelector.getRunningGoals().anyMatch(goal -> goal.getGoal().getClass() == NearestAttackableTargetGoal.class);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag p_28243_) {
        super.readAdditionalSaveData(p_28243_);
        if (p_28243_.contains("EggLayTime")) {
            this.timeUntilNextSand = p_28243_.getInt("EggLayTime");
        }

    }

    @Override
    public void tick() {
        super.tick();
        if (!level.isClientSide) {
            if (isAgressiveL() && !entityData.get(AGRESSIVE)) {
                entityData.set(AGRESSIVE, true);

            } else if (!isAgressiveL() && entityData.get(AGRESSIVE)) {
                entityData.set(AGRESSIVE, false);
            }
        }
        if (!this.level.isClientSide && !this.isBaby() && --this.timeUntilNextSand <= 0 && !done) {
            this.timeUntilNextSand = this.random.nextInt(3600) + 3600;
            done = true;
        }
    }


    @Override
    public InteractionResult mobInteract(Player player, InteractionHand handItem) {
        ItemStack itemstack = player.getItemInHand(handItem);
        if (itemstack.getItem() == Items.WOODEN_SHOVEL || itemstack.getItem() == Items.DIAMOND_SHOVEL || itemstack.getItem() == Items.GOLDEN_SHOVEL || itemstack.getItem() == Items.IRON_SHOVEL || itemstack.getItem() == Items.NETHERITE_SHOVEL || itemstack.getItem() == Items.STONE_SHOVEL && --this.timeUntilNextSand <= 0) {
            if (!this.level.isClientSide && done) {
                int i = 1 + this.random.nextInt(32);
                for (int j = 0; j < i; ++j) {
                    itemstack.hurtAndBreak(1, player, (p_213613_1_) -> {
                        p_213613_1_.broadcastBreakEvent(handItem);
                    });
                    this.spawnAtLocation(Items.SAND, 1);
                    done = false;
                }
                return InteractionResult.SUCCESS;
            } else {
                this.timeUntilNextSand = this.random.nextInt(200) + 200;
                return InteractionResult.CONSUME;
            }
        } else {
            return super.mobInteract(player, handItem);
        }
    }

    @Override
    protected int getExperienceReward(Player player) {
        return 2 + this.level.random.nextInt(4);
    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        return ModSound.HIPPOPOTAMUS_AMBIENT.get();
    }

    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        return ModSound.HIPPOPOTAMUS_DEATH.get();
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return ModSound.HIPPOPOTAMUS_HURT.get();
    }

    @Nullable
    @Override
    protected void playStepSound(BlockPos pos, BlockState blockIn) {
        this.playSound(SoundEvents.PIG_STEP, 0.15F, 0.5F);
    }


    public boolean canBeLeashedTo(Player player) {
        return false;
    }


    class MeleeHipo extends net.minecraft.world.entity.ai.goal.MeleeAttackGoal {

        public MeleeHipo() {
            super(HippopotamusEntity.this, 2D, true);

        }

        @Override
        protected void checkAndPerformAttack(LivingEntity enemy, double distToEnemySqr) {
            HippopotamusEntity.this.setItemInHand(InteractionHand.MAIN_HAND, ItemStack.EMPTY);
            double d0 = this.getAttackReachSqr(enemy);
            if (distToEnemySqr <= d0) {
                this.resetAttackCooldown();
                this.mob.doHurtTarget(enemy);
                HippopotamusEntity.this.setAttack(false);
            } else if (distToEnemySqr <= d0 * 2.0D) {
                if (this.isTimeToAttack()) {
                    HippopotamusEntity.this.setAttack(false);
                    this.resetAttackCooldown();
                }

                if (this.getTicksUntilNextAttack() <= 5) {
                    HippopotamusEntity.this.setAttack(true);
                }
            } else {
                this.resetAttackCooldown();
                HippopotamusEntity.this.setAttack(false);
            }

        }

        /**
         * Reset the task's internal state. Called when this task is interrupted by another one
         */
        public void stop() {
            HippopotamusEntity.this.setAttack(false);
            super.stop();
        }

        @Override
        protected double getAttackReachSqr(LivingEntity attackTarget) {
            return 4.0F + attackTarget.getBbWidth();
        }
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
                List<Player> list = HippopotamusEntity.this.level.getNearbyPlayers(this.field_220842_b, HippopotamusEntity.this, HippopotamusEntity.this.getBoundingBox().inflate(5.0D, 5.0D, 5.0D));
                if (!list.isEmpty()) {
                    list.sort(Comparator.comparing(Entity::getEyeY).reversed());

                    for (Player playerentity : list) {
                        if (HippopotamusEntity.this.canAttack(playerentity, TargetingConditions.DEFAULT) && !HippopotamusEntity.this.isBaby()) {
                            HippopotamusEntity.this.setTarget(playerentity);
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
            LivingEntity livingentity = HippopotamusEntity.this.getTarget();
            return livingentity != null && HippopotamusEntity.this.canAttack(livingentity, TargetingConditions.DEFAULT);
        }
    }

    /*private static final DataParameter<String> HIPO_TYPE = EntityDataManager.createKey(MooshroomEntity.class, DataSerializers.STRING);
    private UUID lightningUUID;

    public void func_241841_a(ServerWorld p_241841_1_, LightningBoltEntity p_241841_2_) {
        UUID uuid = p_241841_2_.getUniqueID();
        if (!uuid.equals(this.lightningUUID)) {
            this.setHipoType(this.getHipoType() == HipopotamusEntity.Type.RED ? BROWN : HipopotamusEntity.Type.RED);
            this.lightningUUID = uuid;
            this.playSound(SoundEvents.ENTITY_MOOSHROOM_CONVERT, 2.0F, 1.0F);
        }
    }
    @Override
    protected void registerData() {
        super.registerData();
        this.dataManager.register(HIPO_TYPE, HipopotamusEntity.Type.RED.name);
    }

    private void setHipoType(HipopotamusEntity.Type typeIn) {
        this.dataManager.set(HIPO_TYPE, typeIn.name);
    }

    public HipopotamusEntity.Type getHipoType() {
        return HipopotamusEntity.Type.getTypeByName(this.dataManager.get(HIPO_TYPE));
    }

    public HipopotamusEntity func_241840_a(ServerWorld p_241840_1_, AgeableEntity p_241840_2_) {
        HipopotamusEntity hipomentity = ModEntityTypes.HIPOPOTAMUS.get().create(p_241840_1_);
        hipomentity.setHipoType(this.func_213445_a((HipopotamusEntity)p_241840_2_));
        return hipomentity;
    }
    private HipopotamusEntity.Type func_213445_a(HipopotamusEntity p_213445_1_) {
        HipopotamusEntity.Type hipoentity$type = this.getHipoType();
        HipopotamusEntity.Type hipoentity$type1 = p_213445_1_.getHipoType();
        HipopotamusEntity.Type hipoentity$type2;
        if (hipoentity$type == hipoentity$type1 && this.rand.nextInt(1024) == 0) {
            hipoentity$type2 = hipoentity$type == BROWN ? HipopotamusEntity.Type.RED : BROWN;
        } else {
            hipoentity$type2 = this.rand.nextBoolean() ? hipoentity$type : hipoentity$type1;
        }

        return hipoentity$type2;
    }
    public static enum Type {
        RED("red", Blocks.RED_MUSHROOM.getDefaultState()),
        BROWN("brown", Blocks.BROWN_MUSHROOM.getDefaultState());

        private final String name;
        private final BlockState renderState;

        private Type(String nameIn, BlockState renderStateIn) {
            this.name = nameIn;
            this.renderState = renderStateIn;
        }

        /**
         * A block state that is rendered on the back of the mooshroom.
         */ /*
        @OnlyIn(Dist.CLIENT)
        public BlockState getRenderState() {
            return this.renderState;
        }

        private static HipopotamusEntity.Type getTypeByName(String nameIn) {
            for(HipopotamusEntity.Type hipoentity$type : values()) {
                if (hipoentity$type.name.equals(nameIn)) {
                    return hipoentity$type;
                }
            }

            return RED;
        }
    }
*/
}

