package fr.tathan.giantsoverhaul.util;

import fr.tathan.giantsoverhaul.GiantsOverhaul;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.Difficulty;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
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

    public static void setThunder(Level level) {
        if(level.isClientSide) {
            level.setRainLevel(1.0F);
        } else {
            ServerLevel serverlevel = level.getServer().getLevel(Level.OVERWORLD);
            serverlevel.setWeatherParameters(0, ServerLevel.THUNDER_DURATION.getMaxValue(), true, true);
        }
    }
}
