package lain.mods.skinport;

import lain.mods.skinport.api.ISkin;
import lain.mods.skinport.api.SkinProviderAPI;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;
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
        Minecraft.getMinecraft().getTextureManager().bindTexture(getEntityTexture(player));

        GL11.glColor3f(1.0F, 1.0F, 1.0F);
        modelPlayer.onGround = 0.0F;
        modelPlayer.isSneak = false;
        modelPlayer.setRotationAngles(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F, player);
        modelPlayer.bipedRightArm.render(0.0625F);
        modelPlayer.bipedRightArmwear.render(0.0625F);
    }
}
