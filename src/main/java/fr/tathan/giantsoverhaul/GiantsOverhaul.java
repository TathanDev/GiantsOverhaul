package fr.tathan.giantsoverhaul;

import com.mojang.logging.LogUtils;
import fr.tathan.giantsoverhaul.common.registries.EntityRegistry;
import fr.tathan.giantsoverhaul.common.registries.TagRegistry;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(GiantsOverhaul.MODID)
public class GiantsOverhaul {
    public static final String MODID = "giants_overhaul";
    public static final Logger LOGGER = LogUtils.getLogger();

    public GiantsOverhaul() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        EntityRegistry.ENTITIES.register(modEventBus);
        TagRegistry.init();
        modEventBus.addListener(this::commonSetup);
        MinecraftForge.EVENT_BUS.register(this);

        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        LOGGER.info("Giant Overhaul has been enabled (Common)");

    }

    @Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {

        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event)
        {
            LOGGER.info("Giant Overhaul has been enabled (Client)");
        }
    }
}
