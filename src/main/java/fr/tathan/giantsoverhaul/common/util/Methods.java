package fr.tathan.giantsoverhaul.common.util;

import fr.tathan.giantsoverhaul.Config;
import fr.tathan.giantsoverhaul.common.entity.GiantDrownedEntity;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.Difficulty;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.monster.Drowned;
import net.minecraft.world.entity.monster.Giant;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;

import java.util.Random;

public class Methods {

    public static Zombie summonZombies(Level level) {
        Zombie zombie = new Zombie(level);
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
        return zombie;
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
    }
}
