package fr.tathan.giantsoverhaul.client.registries;

import fr.tathan.giantsoverhaul.GiantsOverhaul;
import fr.tathan.giantsoverhaul.client.renderer.entity.giant_drowned.GiantDrownedRenderer;
import fr.tathan.giantsoverhaul.client.renderer.entity.giant_husk.GiantHuskRenderer;
import fr.tathan.giantsoverhaul.common.registries.EntityRegistry;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = GiantsOverhaul.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class EntityRendererRegistry {
    @SubscribeEvent
    public static void register(EntityRenderersEvent.RegisterRenderers event) {
        /** MOBS */
        event.registerEntityRenderer(EntityRegistry.GIANT_DROWNED.get(), GiantDrownedRenderer::new);
        event.registerEntityRenderer(EntityRegistry.GIANT_HUSK.get(), GiantHuskRenderer::new);

    }
}
