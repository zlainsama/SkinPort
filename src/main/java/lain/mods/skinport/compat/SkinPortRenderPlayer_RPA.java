package lain.mods.skinport.compat;

import lain.mods.skinport.SkinCustomization;
import lain.mods.skinport.SkinPort;
import lain.mods.skinport.api.ISkin;
import lain.mods.skinport.api.SkinProviderAPI;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;

public class SkinPortRenderPlayer_RPA extends RenderPlayer
{

    public SkinPortModelPlayer_RPA modelPlayer;

    public SkinPortRenderPlayer_RPA(boolean smallArms)
    {
        super();

        mainModel = new SkinPortModelPlayer_RPA(0.0F, smallArms);
        modelBipedMain = (ModelBiped) mainModel;
        modelPlayer = (SkinPortModelPlayer_RPA) mainModel;
    }

    @Override
    public void doRender(AbstractClientPlayer p_76986_1_, double p_76986_2_, double p_76986_4_, double p_76986_6_, float p_76986_8_, float p_76986_9_)
    {
        boolean smHeadwear = modelPlayer.bipedHeadwear.showModel;
        boolean smLeftLegwear = modelPlayer.bipedLeftLegwear.showModel;
        boolean smRightLegwear = modelPlayer.bipedRightLegwear.showModel;
        boolean smLeftArmwear = modelPlayer.bipedLeftArmwear.showModel;
        boolean smRightArmwear = modelPlayer.bipedRightArmwear.showModel;
        boolean smBodyWear = modelPlayer.bipedBodyWear.showModel;
        boolean smCloak = modelPlayer.bipedCloak.showModel;

        int flags = SkinPort.clientCache.getUnchecked(p_76986_1_.getUniqueID());
        if (modelPlayer.bipedHeadwear.showModel)
            modelPlayer.bipedHeadwear.showModel = SkinCustomization.contains(flags, SkinCustomization.hat);
        if (modelPlayer.bipedLeftLegwear.showModel)
            modelPlayer.bipedLeftLegwear.showModel = SkinCustomization.contains(flags, SkinCustomization.left_pants_leg);
        if (modelPlayer.bipedRightLegwear.showModel)
            modelPlayer.bipedRightLegwear.showModel = SkinCustomization.contains(flags, SkinCustomization.right_pants_leg);
        if (modelPlayer.bipedLeftArmwear.showModel)
            modelPlayer.bipedLeftArmwear.showModel = SkinCustomization.contains(flags, SkinCustomization.left_sleeve);
        if (modelPlayer.bipedRightArmwear.showModel)
            modelPlayer.bipedRightArmwear.showModel = SkinCustomization.contains(flags, SkinCustomization.right_sleeve);
        if (modelPlayer.bipedBodyWear.showModel)
            modelPlayer.bipedBodyWear.showModel = SkinCustomization.contains(flags, SkinCustomization.jacket);
        if (modelPlayer.bipedCloak.showModel)
            modelPlayer.bipedCloak.showModel = SkinCustomization.contains(flags, SkinCustomization.cape);

        super.doRender(p_76986_1_, p_76986_2_, p_76986_4_, p_76986_6_, p_76986_8_, p_76986_9_);

        modelPlayer.bipedHeadwear.showModel = smHeadwear;
        modelPlayer.bipedLeftLegwear.showModel = smLeftLegwear;
        modelPlayer.bipedRightLegwear.showModel = smRightLegwear;
        modelPlayer.bipedLeftArmwear.showModel = smLeftArmwear;
        modelPlayer.bipedRightArmwear.showModel = smRightArmwear;
        modelPlayer.bipedBodyWear.showModel = smBodyWear;
        modelPlayer.bipedCloak.showModel = smCloak;
    }

    @Override
    protected ResourceLocation getEntityTexture(AbstractClientPlayer player)
    {
        ISkin skin = SkinProviderAPI.getSkin(player);
        if (!skin.isSkinReady())
            skin = SkinProviderAPI.getDefaultSkin(player);
        return skin.getSkinLocation();
    }

    @Override
    protected void renderEquippedItems(AbstractClientPlayer p_77029_1_, float p_77029_2_)
    {
        boolean smHeadwear = modelPlayer.bipedHeadwear.showModel;
        boolean smLeftLegwear = modelPlayer.bipedLeftLegwear.showModel;
        boolean smRightLegwear = modelPlayer.bipedRightLegwear.showModel;
        boolean smLeftArmwear = modelPlayer.bipedLeftArmwear.showModel;
        boolean smRightArmwear = modelPlayer.bipedRightArmwear.showModel;
        boolean smBodyWear = modelPlayer.bipedBodyWear.showModel;
        boolean smCloak = modelPlayer.bipedCloak.showModel;

        int flags = SkinPort.clientCache.getUnchecked(p_77029_1_.getUniqueID());
        if (modelPlayer.bipedHeadwear.showModel)
            modelPlayer.bipedHeadwear.showModel = SkinCustomization.contains(flags, SkinCustomization.hat);
        if (modelPlayer.bipedLeftLegwear.showModel)
            modelPlayer.bipedLeftLegwear.showModel = SkinCustomization.contains(flags, SkinCustomization.left_pants_leg);
        if (modelPlayer.bipedRightLegwear.showModel)
            modelPlayer.bipedRightLegwear.showModel = SkinCustomization.contains(flags, SkinCustomization.right_pants_leg);
        if (modelPlayer.bipedLeftArmwear.showModel)
            modelPlayer.bipedLeftArmwear.showModel = SkinCustomization.contains(flags, SkinCustomization.left_sleeve);
        if (modelPlayer.bipedRightArmwear.showModel)
            modelPlayer.bipedRightArmwear.showModel = SkinCustomization.contains(flags, SkinCustomization.right_sleeve);
        if (modelPlayer.bipedBodyWear.showModel)
            modelPlayer.bipedBodyWear.showModel = SkinCustomization.contains(flags, SkinCustomization.jacket);
        if (modelPlayer.bipedCloak.showModel)
            modelPlayer.bipedCloak.showModel = SkinCustomization.contains(flags, SkinCustomization.cape);

        super.renderEquippedItems(p_77029_1_, p_77029_2_);

        modelPlayer.bipedHeadwear.showModel = smHeadwear;
        modelPlayer.bipedLeftLegwear.showModel = smLeftLegwear;
        modelPlayer.bipedRightLegwear.showModel = smRightLegwear;
        modelPlayer.bipedLeftArmwear.showModel = smLeftArmwear;
        modelPlayer.bipedRightArmwear.showModel = smRightArmwear;
        modelPlayer.bipedBodyWear.showModel = smBodyWear;
        modelPlayer.bipedCloak.showModel = smCloak;
    }

    @Override
    public void renderFirstPersonArm(EntityPlayer player)
    {
        boolean smHeadwear = modelPlayer.bipedHeadwear.showModel;
        boolean smLeftLegwear = modelPlayer.bipedLeftLegwear.showModel;
        boolean smRightLegwear = modelPlayer.bipedRightLegwear.showModel;
        boolean smLeftArmwear = modelPlayer.bipedLeftArmwear.showModel;
        boolean smRightArmwear = modelPlayer.bipedRightArmwear.showModel;
        boolean smBodyWear = modelPlayer.bipedBodyWear.showModel;
        boolean smCloak = modelPlayer.bipedCloak.showModel;

        int flags = SkinPort.clientCache.getUnchecked(player.getUniqueID());
        if (modelPlayer.bipedHeadwear.showModel)
            modelPlayer.bipedHeadwear.showModel = SkinCustomization.contains(flags, SkinCustomization.hat);
        if (modelPlayer.bipedLeftLegwear.showModel)
            modelPlayer.bipedLeftLegwear.showModel = SkinCustomization.contains(flags, SkinCustomization.left_pants_leg);
        if (modelPlayer.bipedRightLegwear.showModel)
            modelPlayer.bipedRightLegwear.showModel = SkinCustomization.contains(flags, SkinCustomization.right_pants_leg);
        if (modelPlayer.bipedLeftArmwear.showModel)
            modelPlayer.bipedLeftArmwear.showModel = SkinCustomization.contains(flags, SkinCustomization.left_sleeve);
        if (modelPlayer.bipedRightArmwear.showModel)
            modelPlayer.bipedRightArmwear.showModel = SkinCustomization.contains(flags, SkinCustomization.right_sleeve);
        if (modelPlayer.bipedBodyWear.showModel)
            modelPlayer.bipedBodyWear.showModel = SkinCustomization.contains(flags, SkinCustomization.jacket);
        if (modelPlayer.bipedCloak.showModel)
            modelPlayer.bipedCloak.showModel = SkinCustomization.contains(flags, SkinCustomization.cape);

        // #blameMojang
        Minecraft.getMinecraft().getTextureManager().bindTexture(getEntityTexture(player));

        modelPlayer.isRiding = modelPlayer.isSneak = false;
        super.renderFirstPersonArm(player);

        modelPlayer.bipedHeadwear.showModel = smHeadwear;
        modelPlayer.bipedLeftLegwear.showModel = smLeftLegwear;
        modelPlayer.bipedRightLegwear.showModel = smRightLegwear;
        modelPlayer.bipedLeftArmwear.showModel = smLeftArmwear;
        modelPlayer.bipedRightArmwear.showModel = smRightArmwear;
        modelPlayer.bipedBodyWear.showModel = smBodyWear;
        modelPlayer.bipedCloak.showModel = smCloak;
    }

}
