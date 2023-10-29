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

    private static final ForgeConfigSpec.IntValue EFFECT_RADIUS = BUILDER
            .comment("The radius of the effect when the Giant give an effects to entities")
            .defineInRange("effectRadius", 260, 1,  Integer.MAX_VALUE);
    private static final ForgeConfigSpec.IntValue FIRE_TIME = BUILDER
            .comment("How long the player should be on fire against the Giant Husk")
            .defineInRange("huskFireTime", 15, 1,  Integer.MAX_VALUE);

    static final ForgeConfigSpec SPEC = BUILDER.build();

    public static int numberOfZombies;
    public static int effectRadius;
    public static int huskFireTime;
    @SubscribeEvent
    static void onLoad(final ModConfigEvent event)
    {

        numberOfZombies = NUMBERS_OF_ZOMBIES_SUMMONED.get();
        effectRadius = EFFECT_RADIUS.get();
        huskFireTime = FIRE_TIME.get();
    }
}
