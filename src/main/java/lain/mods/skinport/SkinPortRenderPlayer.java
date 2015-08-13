package lain.mods.skinport;

import lain.mods.skinport.api.ISkin;
import lain.mods.skinport.api.SkinProviderAPI;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

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
    protected ResourceLocation getEntityTexture(AbstractClientPlayer player)
    {
        ISkin skin = SkinProviderAPI.getSkin(player);
        if (!skin.isSkinReady())
            skin = SkinProviderAPI.getDefaultSkin(player);
        return skin.getSkinLocation();
    }

    @Override
    public void renderFirstPersonArm(EntityPlayer player)
    {
        // #blameMojang
        Minecraft.getMinecraft().getTextureManager().bindTexture(getEntityTexture(player));

        super.renderFirstPersonArm(player);

        SkinPortModelPlayer.copyModelAngles(modelPlayer.bipedRightArm, modelPlayer.bipedRightArmwear);
        modelPlayer.bipedRightArmwear.render(0.0625F);
    }

}
