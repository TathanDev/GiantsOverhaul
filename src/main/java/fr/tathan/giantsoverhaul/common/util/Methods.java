package fr.tathan.giantsoverhaul.common.util;

import fr.tathan.giantsoverhaul.Config;
import fr.tathan.giantsoverhaul.common.entity.GiantDrownedEntity;
import fr.tathan.giantsoverhaul.common.entity.GiantHuskEntity;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.Difficulty;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffectUtil;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;
import java.util.Random;

public class Methods {

    public static Zombie summonZombies(Level level) {
        Zombie zombie = new Zombie(level);
        equipZombies(zombie, level);
        return zombie;
    }

    public static Husk summonHusks(Level level) {
        Husk zombie = new Husk(EntityType.HUSK, level);
        equipZombies(zombie, level);
        return zombie;
    }

    public static void equipZombies(Zombie zombie, Level level){
        Random random = new Random();
        boolean haveHelmet = random.nextBoolean();
        boolean haveSword = random.nextBoolean();

        if (level.getDifficulty() == Difficulty.NORMAL) {
            if (haveSword) zombie.setItemInHand(InteractionHand.MAIN_HAND, new ItemStack(Items.IRON_SWORD));
            if (haveHelmet) zombie.setItemSlot(EquipmentSlot.HEAD, new ItemStack(Items.IRON_HELMET));
        } else if (level.getDifficulty() == Difficulty.HARD) {
            if (haveSword) zombie.setItemInHand(InteractionHand.MAIN_HAND, new ItemStack(Items.DIAMOND_SWORD));
            if (haveHelmet) zombie.setItemSlot(EquipmentSlot.HEAD, new ItemStack(Items.DIAMOND_HELMET));
        } else {
            if (haveSword) zombie.setItemInHand(InteractionHand.MAIN_HAND, new ItemStack(Items.GOLDEN_SWORD));
            if (haveHelmet) zombie.setItemSlot(EquipmentSlot.HEAD, new ItemStack(Items.GOLDEN_HELMET));
        }
    }

    public static Drowned summonDrowned(Level level) {
        Drowned Drowned = new Drowned(EntityType.DROWNED, level);
        Random random = new Random();
        boolean haveHelmet = random.nextBoolean();

        if (level.getDifficulty() == Difficulty.NORMAL) {
            if (haveHelmet) Drowned.setItemSlot(EquipmentSlot.HEAD, new ItemStack(Items.IRON_HELMET));
        } else if (level.getDifficulty() == Difficulty.HARD) {
            if (haveHelmet) Drowned.setItemSlot(EquipmentSlot.HEAD, new ItemStack(Items.DIAMOND_HELMET));
        } else {
            if (haveHelmet) Drowned.setItemSlot(EquipmentSlot.HEAD, new ItemStack(Items.GOLDEN_HELMET));
        }
        Drowned.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(Items.TRIDENT));

        return Drowned;
    }

    public static void setThunder(Level level) {
        if(level.isClientSide) {
            level.setRainLevel(1.0F);
        } else {
            ServerLevel serverlevel = level.getServer().getLevel(Level.OVERWORLD);
            serverlevel.setWeatherParameters(0, ServerLevel.THUNDER_DURATION.getMaxValue(), true, true);
        }
    }

    public static void setDay(Level level) {
        if(level.isClientSide) {
            level.setRainLevel(0);
        } else {
            ServerLevel serverlevel = level.getServer().getLevel(Level.OVERWORLD);
            serverlevel.setWeatherParameters(0, ServerLevel.RAIN_DELAY.getMaxValue(), false, false);
        }
    }

    public static void giantBossFight(Giant giant, Level level){
        if(giant.getHealth() < 50.0D && !giant.getPersistentData().getBoolean("HasSummoned")) {
            giant.getPersistentData().putBoolean("HasSummoned", true);

            for(int i = 0; i <= Config.numberOfZombies; i++) {
                Entity zombie = Methods.summonZombies(level);
                zombie.moveTo(giant.getX(), giant.getY(), giant.getZ());
                level.addFreshEntity(zombie);
            }
        }
        if(giant.getHealth() < 70.0D && !giant.getPersistentData().getBoolean("HasSetRain")) {
            Methods.setThunder(level);
            giant.getPersistentData().putBoolean("HasSetRain", true);
        }

        if (giant.getHealth() < 25.0D && !giant.getPersistentData().getBoolean("HasGiveEffect")){
            applyEffectInRadius((ServerLevel) giant.level(), giant.position(), giant, Config.effectRadius, MobEffects.MOVEMENT_SLOWDOWN);
        }
    }

    public static void giantDrownedBossFight(GiantDrownedEntity giant, Level level){
        if(giant.getHealth() < 50.0D && !giant.getPersistentData().getBoolean("HasSummoned")) {
            giant.getPersistentData().putBoolean("HasSummoned", true);

            for(int i = 0; i <= Config.numberOfZombies; i++) {
                Entity zombie = Methods.summonDrowned(level);
                zombie.moveTo(giant.getX(), giant.getY(), giant.getZ());
                level.addFreshEntity(zombie);
            }
        }
        if(giant.getHealth() < 70.0D && !giant.getPersistentData().getBoolean("HasSetRain")) {
            Methods.setThunder(level);
            giant.getPersistentData().putBoolean("HasSetRain", true);
        }

        if (giant.getHealth() < 25.0D && !giant.getPersistentData().getBoolean("HasGiveEffect")){
            applyEffectInRadius((ServerLevel) giant.level(), giant.position(), giant, Config.effectRadius, MobEffects.DIG_SLOWDOWN);
        }
    }

    public static void giantHuskBossFight(GiantHuskEntity giant, Level level){
        if(giant.getHealth() < 50.0D && !giant.getPersistentData().getBoolean("HasSummoned")) {
            giant.getPersistentData().putBoolean("HasSummoned", true);

            for(int i = 0; i <= Config.numberOfZombies; i++) {
                Entity zombie = Methods.summonHusks(level);
                zombie.moveTo(giant.getX(), giant.getY(), giant.getZ());
                level.addFreshEntity(zombie);
            }
        }
        if(giant.getHealth() < 70.0D && !giant.getPersistentData().getBoolean("HasSetRain")) {
            Methods.setThunder(level);
            giant.getPersistentData().putBoolean("HasSetRain", true);
        }

        if (giant.getHealth() < 25.0D && !giant.getPersistentData().getBoolean("HasGiveEffect")){
            applyEffectInRadius((ServerLevel) giant.level(), giant.position(), giant, Config.effectRadius, MobEffects.WEAKNESS);
        }
    }

    public static AttributeSupplier.Builder giantsAttributes(){
        return (Monster.createMonsterAttributes()
                .add(Attributes.FOLLOW_RANGE, 60.0D)
                .add(Attributes.MOVEMENT_SPEED, (double)0.30F)
                .add(Attributes.ATTACK_KNOCKBACK, 1.5D)
                .add(Attributes.ATTACK_DAMAGE, 15.0D)
                .add(Attributes.ARMOR, 4.0D)
                .add(Attributes.SPAWN_REINFORCEMENTS_CHANCE)
                .add(Attributes.MAX_HEALTH, 100.0D));
    }

    public static void addTagsToGiants(Entity entity){
        if (entity instanceof Giant giant) {
            giant.getPersistentData().putBoolean("HasSetRain", false);
            giant.getPersistentData().putBoolean("HasSummoned", false);
            giant.getPersistentData().putBoolean("HasGiveEffect", false);
        }
        if (entity instanceof GiantDrownedEntity giant) {
            giant.setItemInHand(InteractionHand.MAIN_HAND, new ItemStack(Items.TRIDENT));
            giant.getPersistentData().putBoolean("HasSetRain", false);
            giant.getPersistentData().putBoolean("HasSummoned", false);
            giant.getPersistentData().putBoolean("HasGiveEffect", false);

        }
    }

    public static void applyEffectInRadius(ServerLevel pLevel, Vec3 pPos, @Nullable Entity pSource, int pRadius, MobEffect effect) {
        MobEffectInstance mobeffectinstance = new MobEffectInstance(effect, 260, 0, false, false);
        MobEffectUtil.addEffectToPlayersAround(pLevel, pSource, pPos, (double)pRadius, mobeffectinstance, 200);
    }

}
