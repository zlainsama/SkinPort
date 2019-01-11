package lain.mods.skinport;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.entity.player.EntityPlayer;

@SideOnly(Side.CLIENT)
public class SkinPortRenderPlayer extends RenderPlayer
{

    public SkinPortModelPlayer modelPlayer;

    public SkinPortRenderPlayer(boolean smallArms)
    {
        super();

        mainModel = new SkinPortModelPlayer(0.0F, smallArms);
        modelBipedMain = (ModelBiped) mainModel;
        modelPlayer = (SkinPortModelPlayer) mainModel;
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

        int flags = SkinPort.clientCache.getUnchecked(PlayerUtils.getPlayerID(p_76986_1_));
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
    protected void renderEquippedItems(AbstractClientPlayer p_77029_1_, float p_77029_2_)
    {
        boolean smHeadwear = modelPlayer.bipedHeadwear.showModel;
        boolean smLeftLegwear = modelPlayer.bipedLeftLegwear.showModel;
        boolean smRightLegwear = modelPlayer.bipedRightLegwear.showModel;
        boolean smLeftArmwear = modelPlayer.bipedLeftArmwear.showModel;
        boolean smRightArmwear = modelPlayer.bipedRightArmwear.showModel;
        boolean smBodyWear = modelPlayer.bipedBodyWear.showModel;
        boolean smCloak = modelPlayer.bipedCloak.showModel;

        int flags = SkinPort.clientCache.getUnchecked(PlayerUtils.getPlayerID(p_77029_1_));
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

        int flags = SkinPort.clientCache.getUnchecked(PlayerUtils.getPlayerID(player));
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
