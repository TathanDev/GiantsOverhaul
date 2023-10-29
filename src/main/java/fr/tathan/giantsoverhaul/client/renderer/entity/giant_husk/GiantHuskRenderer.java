package fr.tathan.giantsoverhaul.client.renderer.entity.giant_husk;

import com.mojang.blaze3d.vertex.PoseStack;
import fr.tathan.giantsoverhaul.GiantsOverhaul;
import fr.tathan.giantsoverhaul.common.entity.GiantDrownedEntity;
import fr.tathan.giantsoverhaul.common.entity.GiantHuskEntity;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.HuskRenderer;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.monster.Husk;

public class GiantHuskRenderer extends MobRenderer<GiantHuskEntity, EntityModel<GiantHuskEntity>> {
    public static final ResourceLocation TEXTURE = new ResourceLocation("textures/entity/zombie/husk.png");

    public GiantHuskRenderer(EntityRendererProvider.Context renderManagerIn) {
        super(renderManagerIn, new GiantHuskModel<>(renderManagerIn.bakeLayer(GiantHuskModel.LAYER_LOCATION)), 0.5f);
    }

    @Override
    public ResourceLocation getTextureLocation(GiantHuskEntity entity) {
        return TEXTURE;
    }

    @Override
    protected void scale(GiantHuskEntity pLivingEntity, PoseStack pPoseStack, float pPartialTickTime) {
        pPoseStack.scale(6.0f, 6.0f, 6.0f);
    }

}
