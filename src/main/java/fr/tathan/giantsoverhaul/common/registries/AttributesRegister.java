package fr.tathan.giantsoverhaul.common.registries;

import fr.tathan.giantsoverhaul.GiantsOverhaul;
import fr.tathan.giantsoverhaul.common.entity.GiantDrownedEntity;
import fr.tathan.giantsoverhaul.common.entity.GiantHuskEntity;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, modid = GiantsOverhaul.MODID)
public class AttributesRegister {

    @SubscribeEvent
    public static void newEntityAttributes(EntityAttributeCreationEvent event) {
        event.put(EntityRegistry.GIANT_DROWNED.get(), GiantDrownedEntity.createAttributes().build());
        event.put(EntityRegistry.GIANT_HUSK.get(), GiantHuskEntity.createAttributes().build());

    }

}


