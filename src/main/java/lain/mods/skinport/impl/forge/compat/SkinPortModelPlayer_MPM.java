package lain.mods.skinport.impl.forge.compat;

import cpw.mods.fml.relauncher.ReflectionHelper;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import lain.mods.skinport.impl.forge.SpecialModel;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import noppes.mpm.client.model.Model2DRenderer;
import noppes.mpm.client.model.ModelMPM;
import noppes.mpm.client.model.ModelPartInterface;
import noppes.mpm.client.model.ModelScaleRenderer;
import noppes.mpm.client.model.part.ModelClaws;
import noppes.mpm.client.model.part.ModelHeadwear;
import noppes.mpm.client.model.part.ModelLegs;

@SideOnly(Side.CLIENT)
public class SkinPortModelPlayer_MPM extends ModelMPM implements SpecialModel
{

    public static class ModelHeadwearFixed extends ModelHeadwear
    {

        public ModelHeadwearFixed(ModelBase base)
        {
            super(base);

            childModels.clear();

            Model2DRenderer right = new Model2DRenderer(base, 32.0F, 8.0F, 8, 8, 64.0F, 64.0F);
            right.setRotationPoint(-4.641F, 0.8F, 4.64F);
            right.setScale(0.58F);
            right.setThickness(0.65F);
            setRotation(right, 0.0F, 1.570796F, 0.0F);
            addChild(right);

            Model2DRenderer left = new Model2DRenderer(base, 48.0F, 8.0F, 8, 8, 64.0F, 64.0F);
            left.setRotationPoint(4.639F, 0.8F, -4.64F);
            left.setScale(0.58F);
            left.setThickness(0.65F);
            setRotation(left, 0.0F, -1.570796F, 0.0F);
            addChild(left);

            Model2DRenderer front = new Model2DRenderer(base, 40.0F, 8.0F, 8, 8, 64.0F, 64.0F);
            front.setRotationPoint(-4.64F, 0.801F, -4.641F);
            front.setScale(0.58F);
            front.setThickness(0.65F);
            setRotation(front, 0.0F, 0.0F, 0.0F);
            addChild(front);

            Model2DRenderer back = new Model2DRenderer(base, 56.0F, 8.0F, 8, 8, 64.0F, 64.0F);
            back.setRotationPoint(4.64F, 0.801F, 4.639F);
            back.setScale(0.58F);
            back.setThickness(0.65F);
            setRotation(back, 0.0F, 3.141593F, 0.0F);
            addChild(back);

            Model2DRenderer top = new Model2DRenderer(base, 40.0F, 0.0F, 8, 8, 64.0F, 64.0F);
            top.setRotationPoint(-4.64F, -8.5F, -4.64F);
            top.setScale(0.5799F);
            top.setThickness(0.65F);
            setRotation(top, -1.570796F, 0.0F, 0.0F);
            addChild(top);

            Model2DRenderer bottom = new Model2DRenderer(base, 48.0F, 0.0F, 8, 8, 64.0F, 64.0F);
            bottom.setRotationPoint(-4.64F, 0.0F, -4.64F);
            bottom.setScale(0.5799F);
            bottom.setThickness(0.65F);
            setRotation(bottom, -1.570796F, 0.0F, 0.0F);
            addChild(bottom);
        }

    }

    public ModelRenderer bipedLeftArmwear;
    public ModelRenderer bipedRightArmwear;
    public ModelRenderer bipedLeftLegwear;
    public ModelRenderer bipedRightLegwear;
    public ModelRenderer bipedBodyWear;
    public boolean smallArms;

    public SkinPortModelPlayer_MPM(float z, boolean smallArms)
    {
        super(z);

        this.smallArms = smallArms;

        bipedCloak = new ModelRenderer(this, 0, 0);
        bipedCloak.setTextureSize(64, 32);
        bipedCloak.addBox(-5.0F, 0.0F, -1.0F, 10, 16, 1, z);

        if (smallArms)
        {
            bipedLeftArm = new ModelScaleRenderer(this, 32, 48);
            bipedLeftArm.addBox(-1.0F, -2.0F, -2.0F, 3, 12, 4, z);
            bipedLeftArm.setRotationPoint(5.0F, 2.5F, 0.0F);

            bipedRightArm = new ModelScaleRenderer(this, 40, 16);
            bipedRightArm.addBox(-2.0F, -2.0F, -2.0F, 3, 12, 4, z);
            bipedRightArm.setRotationPoint(-5.0F, 2.5F, 0.0F);

            bipedLeftArmwear = new ModelRenderer(this, 48, 48);
            bipedLeftArmwear.addBox(-1.0F, -2.0F, -2.0F, 3, 12, 4, z + 0.25F);
            bipedLeftArm.addChild(bipedLeftArmwear);

            bipedRightArmwear = new ModelRenderer(this, 40, 32);
            bipedRightArmwear.addBox(-2.0F, -2.0F, -2.0F, 3, 12, 4, z + 0.25F);
            bipedRightArm.addChild(bipedRightArmwear);

            if (!isArmor)
            {
                ModelPartInterface clawsL = new ModelClaws(this, false);
                ReflectionHelper.setPrivateValue(ModelMPM.class, this, clawsL, "clawsL");
                bipedLeftArm.addChild(clawsL);

                ModelPartInterface clawsR = new ModelClaws(this, true);
                ReflectionHelper.setPrivateValue(ModelMPM.class, this, clawsR, "clawsR");
                bipedRightArm.addChild(clawsR);
            }
        }
        else
        {
            bipedLeftArm = new ModelScaleRenderer(this, 32, 48);
            bipedLeftArm.addBox(-1.0F, -2.0F, -2.0F, 4, 12, 4, z);
            bipedLeftArm.setRotationPoint(5.0F, 2.0F, 0.0F);

            bipedLeftArmwear = new ModelRenderer(this, 48, 48);
            bipedLeftArmwear.addBox(-1.0F, -2.0F, -2.0F, 4, 12, 4, z + 0.25F);
            bipedLeftArm.addChild(bipedLeftArmwear);

            bipedRightArmwear = new ModelRenderer(this, 40, 32);
            bipedRightArmwear.addBox(-3.0F, -2.0F, -2.0F, 4, 12, 4, z + 0.25F);
            bipedRightArm.addChild(bipedRightArmwear);

            if (!isArmor)
            {
                ModelPartInterface clawsL = new ModelClaws(this, false);
                ReflectionHelper.setPrivateValue(ModelMPM.class, this, clawsL, "clawsL");
                bipedLeftArm.addChild(clawsL);
            }
        }

        bipedLeftLeg = new ModelScaleRenderer(this, 16, 48);
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

        ModelScaleRenderer headwear = new ModelHeadwearFixed(this);
        ReflectionHelper.setPrivateValue(ModelMPM.class, this, headwear, "headwear");
        ModelLegs legs = new ModelLegs(this, (ModelScaleRenderer) bipedRightLeg, (ModelScaleRenderer) bipedLeftLeg);
        ReflectionHelper.setPrivateValue(ModelMPM.class, this, legs, "legs");
    }

    @Override
    public int initHeight()
    {
        return 64;
    }

    @Override
    public int initWidth()
    {
        return 64;
    }

    @Override
    public void render(Entity p_render_1_, float p_render_2_, float p_render_3_, float p_render_4_, float p_render_5_, float p_render_6_, float p_render_7_)
    {
        super.render(p_render_1_, p_render_2_, p_render_3_, p_render_4_, p_render_5_, p_render_6_, p_render_7_);

        if (smallArms)
            bipedRightArm.rotationPointX += 1.0F;
    }

}
