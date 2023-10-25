package fr.tathan.giantsoverhaul;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.config.ModConfigEvent;

@Mod.EventBusSubscriber(modid = GiantsOverhaul.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class Config
{
    private static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();

    private static final ForgeConfigSpec.IntValue NUMBERS_OF_ZOMBIES_SUMMONED = BUILDER
            .comment("How much the giant should summon zombies")
            .defineInRange("zombiesSummoned", 5, 1,  Integer.MAX_VALUE);

    static final ForgeConfigSpec SPEC = BUILDER.build();

    public static int numberOfZombies;

    @SubscribeEvent
    static void onLoad(final ModConfigEvent event)
    {
        numberOfZombies = NUMBERS_OF_ZOMBIES_SUMMONED.get();

    }
}
