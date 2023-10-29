package fr.tathan.giantsoverhaul.client.renderer.entity.giant_drowned;

import com.mojang.blaze3d.vertex.PoseStack;
import fr.tathan.giantsoverhaul.GiantsOverhaul;
import fr.tathan.giantsoverhaul.common.entity.GiantDrownedEntity;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class GiantDrownedRenderer extends MobRenderer<GiantDrownedEntity, EntityModel<GiantDrownedEntity>> {
    public static final ResourceLocation TEXTURE = new ResourceLocation(GiantsOverhaul.MODID, "textures/entity/giant_drowned.png");

    public GiantDrownedRenderer(EntityRendererProvider.Context renderManagerIn) {
        super(renderManagerIn, new GiantDrownedModel<>(renderManagerIn.bakeLayer(GiantDrownedModel.LAYER_LOCATION)), 0.5f);
    }

    @Override
    public ResourceLocation getTextureLocation(GiantDrownedEntity entity) {
        return TEXTURE;
    }

    @Override
    protected void scale(GiantDrownedEntity pLivingEntity, PoseStack pPoseStack, float pPartialTickTime) {
        pPoseStack.scale(6.0f, 6.0f, 6.0f);
    }
}
