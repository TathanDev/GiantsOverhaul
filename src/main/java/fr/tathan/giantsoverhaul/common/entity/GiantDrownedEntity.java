package fr.tathan.giantsoverhaul.common.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.BiomeTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Difficulty;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.animal.Turtle;
import net.minecraft.world.entity.monster.*;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ThrownTrident;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.biome.Biome;

public class GiantDrownedEntity extends Zombie implements RangedAttackMob {
    public GiantDrownedEntity(EntityType<? extends Zombie> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    @Override
    protected void registerGoals() {
        //livingEntity.goalSelector.addGoal(4, new Zombie.ZombieAttackTurtleEggGoal(this, 1.0D, 3));
        this.goalSelector.addGoal(8, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(8, new RandomLookAroundGoal(this));
        this.addBehaviourGoals();
    }

    protected void addBehaviourGoals() {

        //this.goalSelector.addGoal(2, new GiantAttackGoal(this, 0.7D, false));
        this.goalSelector.addGoal(7, new WaterAvoidingRandomStrollGoal(this, 1.0D));
        this.targetSelector.addGoal(1, (new HurtByTargetGoal(this)).setAlertOthers(ZombifiedPiglin.class));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, AbstractVillager.class, false));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, IronGolem.class, true));
        this.targetSelector.addGoal(5, new NearestAttackableTargetGoal<>(this, Turtle.class, 10, true, false, Turtle.BABY_ON_LAND_SELECTOR));
    }

    public static AttributeSupplier.Builder createAttributes() {
        return  (Monster.createMonsterAttributes()
                .add(Attributes.FOLLOW_RANGE, 35.0D)
                .add(Attributes.MOVEMENT_SPEED, (double)0.23F)
                .add(Attributes.ATTACK_KNOCKBACK, 1.5D)
                .add(Attributes.ATTACK_DAMAGE, 15.0D)
                .add(Attributes.ARMOR, 2.0D)
                .add(Attributes.SPAWN_REINFORCEMENTS_CHANCE)
                .add(Attributes.MAX_HEALTH, 100.0D));
    }

    @Override
    public void performRangedAttack(LivingEntity pTarget, float pVelocity) {
        ThrownTrident throwntrident = new ThrownTrident(this.level(), this, new ItemStack(Items.TRIDENT));
        double d0 = pTarget.getX() - this.getX();
        double d1 = pTarget.getY(0.3333333333333333D) - throwntrident.getY();
        double d2 = pTarget.getZ() - this.getZ();
        double d3 = Math.sqrt(d0 * d0 + d2 * d2);
        throwntrident.shoot(d0, d1 + d3 * (double)0.2F, d2, 1.6F, (float)(14 - this.level().getDifficulty().getId() * 4));
        this.playSound(SoundEvents.DROWNED_SHOOT, 1.0F, 1.0F / (this.getRandom().nextFloat() * 0.4F + 0.8F));
        this.level().addFreshEntity(throwntrident);
    }

    public static boolean checkDrownedSpawnRules(EntityType<GiantDrownedEntity> pDrowned, ServerLevelAccessor pServerLevel, MobSpawnType pMobSpawnType, BlockPos pPos, RandomSource pRandom) {
        if (!pServerLevel.getFluidState(pPos.below()).is(FluidTags.WATER)) {
            return false;
        } else {
            Holder<Biome> holder = pServerLevel.getBiome(pPos);
            boolean flag = pServerLevel.getDifficulty() != Difficulty.PEACEFUL && (pMobSpawnType == MobSpawnType.SPAWNER || pServerLevel.getFluidState(pPos).is(FluidTags.WATER));
            if (holder.is(BiomeTags.MORE_FREQUENT_DROWNED_SPAWNS)) {
                return pRandom.nextInt(15) == 0 && flag;
            } else {
                return pRandom.nextInt(40) == 0 && isDeepEnoughToSpawn(pServerLevel, pPos) && flag;
            }
        }
    }

    private static boolean isDeepEnoughToSpawn(LevelAccessor pLevel, BlockPos pPos) {
        return pPos.getY() < pLevel.getSeaLevel() - 5;
    }


}
