package fr.tathan.giantsoverhaul.client.registries;

import fr.tathan.giantsoverhaul.GiantsOverhaul;
import fr.tathan.giantsoverhaul.client.renderer.entity.GiantDrownedRenderer;
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
    }
}
