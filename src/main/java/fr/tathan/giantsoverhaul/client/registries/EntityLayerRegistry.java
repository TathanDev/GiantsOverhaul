package fr.tathan.giantsoverhaul.client.registries;

import fr.tathan.giantsoverhaul.GiantsOverhaul;
import fr.tathan.giantsoverhaul.client.renderer.entity.giant_drowned.GiantDrownedModel;
import fr.tathan.giantsoverhaul.client.renderer.entity.giant_husk.GiantHuskModel;
import fr.tathan.giantsoverhaul.common.entity.GiantHuskEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = GiantsOverhaul.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class EntityLayerRegistry {
    @SubscribeEvent
    public static void register(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(GiantDrownedModel.LAYER_LOCATION, GiantDrownedModel::createBodyLayer);
        event.registerLayerDefinition(GiantHuskModel.LAYER_LOCATION, GiantHuskModel::createBodyLayer);

    }
}
