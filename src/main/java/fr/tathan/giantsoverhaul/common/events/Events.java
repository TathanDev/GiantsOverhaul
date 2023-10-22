package fr.tathan.giantsoverhaul.common.events;

import fr.tathan.giantsoverhaul.GiantsOverhaul;
import fr.tathan.giantsoverhaul.common.entity.GiantDrownedEntity;
import fr.tathan.giantsoverhaul.common.registries.EntityRegistry;
import fr.tathan.giantsoverhaul.common.registries.TagRegistry;
import fr.tathan.giantsoverhaul.common.util.Methods;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.monster.Drowned;
import net.minecraft.world.entity.monster.Giant;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraftforge.event.entity.SpawnPlacementRegisterEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.MobSpawnEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = GiantsOverhaul.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class Events {

    @SubscribeEvent
    public static void spawnEntity(MobSpawnEvent.FinalizeSpawn event) {
        LivingEntity entity = event.getEntity();
        if (entity instanceof Giant giant) {
            giant.getPersistentData().putBoolean("HasSetRain", false);
            giant.getPersistentData().putBoolean("HasSummoned", false);
        }
        if (entity instanceof GiantDrownedEntity giant) {
            giant.setItemInHand(InteractionHand.MAIN_HAND, new ItemStack(Items.TRIDENT));
            giant.getPersistentData().putBoolean("HasSetRain", false);
            giant.getPersistentData().putBoolean("HasSummoned", false);
        }

    }

    @SubscribeEvent
    public static void livingDeathEvent(LivingDeathEvent event){
        LivingEntity entity = event.getEntity();
        EntityType type = entity.getType();
        Level level = entity.level();
        if (entity instanceof Giant giant) {
            LightningBolt lightningBolt = new LightningBolt(EntityType.LIGHTNING_BOLT, level);
            lightningBolt.moveTo(giant.getX(), giant.getY() + 1, giant.getZ());
            level.addFreshEntity(lightningBolt);
        }

        if(type.is(TagRegistry.GIANTS)) {
            Methods.setDay(level);
            GiantsOverhaul.LOGGER.debug("SUN is Here !");
        }
    }

    @SubscribeEvent
    public static void livingEntityTick(LivingEvent.LivingTickEvent event) {
        LivingEntity entity = event.getEntity();
        Level level = entity.level();
        if (level.isClientSide()) return;
        if (entity instanceof Giant giant) {
            Methods.giantBossFight(giant, level);
        }
        if (entity instanceof GiantDrownedEntity giant) {
            Methods.giantDrownedBossFight(giant, level);
        }
    }

    @SubscribeEvent
    public static void entitySpawnRestriction(SpawnPlacementRegisterEvent event) {
        event.register(EntityType.GIANT, SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                Animal::checkMobSpawnRules, SpawnPlacementRegisterEvent.Operation.REPLACE);

        event.register(EntityRegistry.GIANT_DROWNED.get(), SpawnPlacements.Type.IN_WATER, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                GiantDrownedEntity::checkDrownedSpawnRules, SpawnPlacementRegisterEvent.Operation.REPLACE);

    }
}
