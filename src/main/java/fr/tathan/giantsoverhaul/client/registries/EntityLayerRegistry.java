package fr.tathan.giantsoverhaul.client.registries;

import fr.tathan.giantsoverhaul.GiantsOverhaul;
import fr.tathan.giantsoverhaul.client.renderer.entity.GiantDrownedModel;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = GiantsOverhaul.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class EntityLayerRegistry {
    @SubscribeEvent
    public static void register(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(GiantDrownedModel.LAYER_LOCATION, GiantDrownedModel::createBodyLayer);
    }
}
