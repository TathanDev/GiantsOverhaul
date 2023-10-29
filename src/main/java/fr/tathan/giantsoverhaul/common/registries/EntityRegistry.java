package fr.tathan.giantsoverhaul.common.registries;

import fr.tathan.giantsoverhaul.GiantsOverhaul;
import fr.tathan.giantsoverhaul.common.entity.GiantDrownedEntity;
import fr.tathan.giantsoverhaul.common.entity.GiantHuskEntity;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class EntityRegistry {

    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, GiantsOverhaul.MODID);

    public static final RegistryObject<EntityType<GiantDrownedEntity>> GIANT_DROWNED = ENTITIES.register("giant_drowned", () -> EntityType.Builder.of(GiantDrownedEntity::new, MobCategory.CREATURE)
            .sized(3.6F, 12.0F).clientTrackingRange(10)
            .build(new ResourceLocation(GiantsOverhaul.MODID, "giant_drowned")
                    .toString()));
    public static final RegistryObject<EntityType<GiantHuskEntity>> GIANT_HUSK = ENTITIES.register("giant_husk", () -> EntityType.Builder.of(GiantHuskEntity::new, MobCategory.CREATURE)
            .sized(3.6F, 12.0F).clientTrackingRange(10)
            .build(new ResourceLocation(GiantsOverhaul.MODID, "giant_husk")
                    .toString()));

}
