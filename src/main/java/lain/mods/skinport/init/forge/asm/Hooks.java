package lain.mods.skinport.init.forge.asm;

import java.util.List;
import com.mojang.authlib.GameProfile;
import lain.mods.skinport.init.forge.ClientProxy;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiOptions;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelSkeletonHead;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

public class Hooks
{

    public static ResourceLocation getLocationCape(AbstractClientPlayer player, ResourceLocation result)
    {
        ResourceLocation loc = ClientProxy.getLocationCape(player, result);
        if (loc != null)
            return loc;
        return result;
    }

    public static ResourceLocation getLocationSkin(AbstractClientPlayer player, ResourceLocation result)
    {
        ResourceLocation loc = ClientProxy.getLocationSkin(player, result);
        if (loc != null)
            return loc;
        return result;
    }

    public static void GuiOptions_postInitGui(GuiOptions gui, List<GuiButton> buttonList)
    {
        ClientProxy.setupButton(gui, buttonList);
    }

    public static void GuiOptions_preActionPerformed(GuiOptions gui, GuiButton button)
    {
        ClientProxy.onButtonAction(gui, button);
    }

    public static ResourceLocation GuiPlayerTabOverlay_bindTexture(GameProfile profile, ResourceLocation result)
    {
        ResourceLocation loc = ClientProxy.bindTexture(profile, result);
        if (loc != null)
            return loc;
        return result;
    }

    public static boolean hasCape(AbstractClientPlayer player, boolean result)
    {
        return ClientProxy.hasCape(player, result);
    }

    public static boolean hasSkin(AbstractClientPlayer player, boolean result)
    {
        return ClientProxy.hasSkin(player, result);
    }

    public static int ModelBiped_initHeight(ModelBiped model, int textureHeight)
    {
        return ClientProxy.initHeight(model, textureHeight);
    }

    public static int ModelBiped_initWidth(ModelBiped model, int textureWidth)
    {
        return ClientProxy.initWidth(model, textureWidth);
    }

    public static Render RenderManager_getEntityRenderObject(RenderManager manager, Entity entity, Render result)
    {
        if (entity instanceof EntityPlayerSP || entity instanceof EntityClientPlayerMP || entity instanceof EntityOtherPlayerMP)
        {
            Render renderer = ClientProxy.getPlayerRenderer(manager, (AbstractClientPlayer) entity, result);
            if (renderer != null)
                return renderer;
        }
        return result;
    }

    public static ModelSkeletonHead TileEntitySkullRenderer_bindHumanoidHead(ResourceLocation location, ModelSkeletonHead result)
    {
        ModelSkeletonHead model = ClientProxy.getHumanoidHead(location, result);
        if (model != null)
            return model;
        return result;
    }

    public static ResourceLocation TileEntitySkullRenderer_bindTexture(GameProfile profile, ResourceLocation result)
    {
        ResourceLocation loc = ClientProxy.bindTexture(profile, result);
        if (loc != null)
            return loc;
        return result;
    }

}
