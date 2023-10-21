package fr.tathan.giantsoverhaul.events;

import fr.tathan.giantsoverhaul.Config;
import fr.tathan.giantsoverhaul.GiantsOverhaul;
import fr.tathan.giantsoverhaul.mixin.GiantMixin;
import fr.tathan.giantsoverhaul.util.Methods;
import net.minecraft.client.model.GiantZombieModel;
import net.minecraft.client.model.ZombieModel;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.Component;
import net.minecraft.server.commands.SummonCommand;
import net.minecraft.server.commands.WeatherCommand;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.monster.Giant;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraftforge.event.entity.SpawnPlacementRegisterEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.MobSpawnEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.List;

@Mod.EventBusSubscriber(modid = GiantsOverhaul.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class Events {

    @SubscribeEvent
    public static void spawnEntity(MobSpawnEvent.FinalizeSpawn event) {
        LivingEntity entity = event.getEntity();
        if (entity instanceof Giant giant) {
            giant.getPersistentData().putBoolean("HasSetRain", false);
            giant.getPersistentData().putBoolean("HasSummoned", false);
        }

    }

    @SubscribeEvent
    public static void livingDeathEvent(LivingDeathEvent event){
        LivingEntity entity = event.getEntity();
        Level level = entity.level();
        if (entity instanceof Giant giant) {
            LightningBolt lightningBolt = new LightningBolt(EntityType.LIGHTNING_BOLT, level);
            lightningBolt.moveTo(giant.getX(), giant.getY() + 1, giant.getZ());
            level.addFreshEntity(lightningBolt);
        }
    }

    @SubscribeEvent
    public static void livingEntityTick(LivingEvent.LivingTickEvent event) {
        LivingEntity entity = event.getEntity();
        Level level = entity.level();
        if (level.isClientSide()) return;
        if (entity instanceof Giant giant) {
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
    }

    @SubscribeEvent
    public static void entitySpawnRestriction(SpawnPlacementRegisterEvent event) {
        event.register(EntityType.GIANT, SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                Animal::checkMobSpawnRules, SpawnPlacementRegisterEvent.Operation.REPLACE);
    }
}
