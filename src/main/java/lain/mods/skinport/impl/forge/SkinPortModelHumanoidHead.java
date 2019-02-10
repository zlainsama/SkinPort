package lain.mods.skinport.impl.forge;

import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.model.ModelSkeletonHead;
import net.minecraft.entity.Entity;

public class SkinPortModelHumanoidHead extends ModelSkeletonHead
{

    public ModelRenderer hat = new ModelRenderer(this, 32, 0);

    public SkinPortModelHumanoidHead()
    {
        super(0, 0, 64, 64);
        hat.addBox(-4.0F, -8.0F, -4.0F, 8, 8, 8, 0.25F);
        hat.setRotationPoint(0.0F, 0.0F, 0.0F);
    }

    @Override
    public void render(Entity p_render_1_, float p_render_2_, float p_render_3_, float p_render_4_, float p_render_5_, float p_render_6_, float p_render_7_)
    {
        super.render(p_render_1_, p_render_2_, p_render_3_, p_render_4_, p_render_5_, p_render_6_, p_render_7_);
        hat.render(p_render_7_);
    }

    @Override
    public void setRotationAngles(float p_setRotationAngles_1_, float p_setRotationAngles_2_, float p_setRotationAngles_3_, float p_setRotationAngles_4_, float p_setRotationAngles_5_, float p_setRotationAngles_6_, Entity p_setRotationAngles_7_)
    {
        super.setRotationAngles(p_setRotationAngles_1_, p_setRotationAngles_2_, p_setRotationAngles_3_, p_setRotationAngles_4_, p_setRotationAngles_5_, p_setRotationAngles_6_, p_setRotationAngles_7_);
        hat.rotateAngleY = skeletonHead.rotateAngleY;
        hat.rotateAngleX = skeletonHead.rotateAngleX;
    }

}
