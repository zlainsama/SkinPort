package lain.mods.skinport.compat;

import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import api.player.model.ModelPlayer;

public class SkinPortModelPlayer_RPA extends ModelPlayer
{

    public ModelRenderer bipedLeftArmwear;
    public ModelRenderer bipedRightArmwear;
    public ModelRenderer bipedLeftLegwear;
    public ModelRenderer bipedRightLegwear;
    public ModelRenderer bipedBodyWear;
    public boolean smallArms;

    public SkinPortModelPlayer_RPA(float z, boolean smallArms)
    {
        super(z, 0.0F, 64, 64);

        this.smallArms = smallArms;

        bipedCloak = new ModelRenderer(this, 0, 0);
        bipedCloak.setTextureSize(64, 32);
        bipedCloak.addBox(-5.0F, 0.0F, -1.0F, 10, 16, 1, z);

        if (smallArms)
        {
            bipedLeftArm = new ModelRenderer(this, 32, 48);
            bipedLeftArm.addBox(-1.0F, -2.0F, -2.0F, 3, 12, 4, z);
            bipedLeftArm.setRotationPoint(5.0F, 2.5F, 0.0F);

            bipedRightArm = new ModelRenderer(this, 40, 16);
            bipedRightArm.addBox(-2.0F, -2.0F, -2.0F, 3, 12, 4, z);
            bipedRightArm.setRotationPoint(-5.0F, 2.5F, 0.0F);

            bipedLeftArmwear = new ModelRenderer(this, 48, 48);
            bipedLeftArmwear.addBox(-1.0F, -2.0F, -2.0F, 3, 12, 4, z + 0.25F);
            bipedLeftArm.addChild(bipedLeftArmwear);

            bipedRightArmwear = new ModelRenderer(this, 40, 32);
            bipedRightArmwear.addBox(-2.0F, -2.0F, -2.0F, 3, 12, 4, z + 0.25F);
            bipedRightArm.addChild(bipedRightArmwear);
        }
        else
        {
            bipedLeftArm = new ModelRenderer(this, 32, 48);
            bipedLeftArm.addBox(-1.0F, -2.0F, -2.0F, 4, 12, 4, z);
            bipedLeftArm.setRotationPoint(5.0F, 2.0F, 0.0F);

            bipedLeftArmwear = new ModelRenderer(this, 48, 48);
            bipedLeftArmwear.addBox(-1.0F, -2.0F, -2.0F, 4, 12, 4, z + 0.25F);
            bipedLeftArm.addChild(bipedLeftArmwear);

            bipedRightArmwear = new ModelRenderer(this, 40, 32);
            bipedRightArmwear.addBox(-3.0F, -2.0F, -2.0F, 4, 12, 4, z + 0.25F);
            bipedRightArm.addChild(bipedRightArmwear);
        }

        bipedLeftLeg = new ModelRenderer(this, 16, 48);
        bipedLeftLeg.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, z);
        bipedLeftLeg.setRotationPoint(1.9F, 12.0F, 0.0F);

        bipedLeftLegwear = new ModelRenderer(this, 0, 48);
        bipedLeftLegwear.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, z + 0.25F);
        bipedLeftLeg.addChild(bipedLeftLegwear);

        bipedRightLegwear = new ModelRenderer(this, 0, 32);
        bipedRightLegwear.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, z + 0.25F);
        bipedRightLeg.addChild(bipedRightLegwear);

        bipedBodyWear = new ModelRenderer(this, 16, 32);
        bipedBodyWear.addBox(-4.0F, 0.0F, -2.0F, 8, 12, 4, z + 0.25F);
        bipedBody.addChild(bipedBodyWear);
    }

    @Override
    public void render(Entity p_render_1_, float p_render_2_, float p_render_3_, float p_render_4_, float p_render_5_, float p_render_6_, float p_render_7_)
    {
        super.render(p_render_1_, p_render_2_, p_render_3_, p_render_4_, p_render_5_, p_render_6_, p_render_7_);

        if (smallArms)
            bipedRightArm.rotationPointX += 1.0F;
    }

}
