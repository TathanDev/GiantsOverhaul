package fr.tathan.giantsoverhaul.common.registries;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;

public class TagRegistry {
    public static void init() {}

    public static final TagKey<EntityType<?>> GIANTS = tag("giants");

    private static TagKey<EntityType<?>> tag(String name)
    {
        return TagKey.create(Registries.ENTITY_TYPE, new ResourceLocation("giants_overhaul", name));
    }
}
