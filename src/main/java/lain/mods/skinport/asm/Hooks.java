package lain.mods.skinport.asm;

import java.util.List;
import lain.mods.skinport.SkinPort;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiOptions;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

public class Hooks
{

    public static ResourceLocation getLocationCape(AbstractClientPlayer player, ResourceLocation result)
    {
        return SkinPort.getLocationCape(player, result);
    }

    public static ResourceLocation getLocationSkin(AbstractClientPlayer player, ResourceLocation result)
    {
        return SkinPort.getLocationSkin(player, result);
    }

    public static void GuiOptions_postInitGui(GuiOptions gui, List<GuiButton> buttonList)
    {
        SkinPort.GuiOptions_postInitGui(gui, buttonList);
    }

    public static void GuiOptions_preActionPerformed(GuiOptions gui, GuiButton button)
    {
        SkinPort.GuiOptions_preActionPerformed(gui, button);
    }

    public static boolean hasCape(AbstractClientPlayer player, boolean result)
    {
        return SkinPort.hasCape(player, result);
    }

    public static Render RenderManager_getEntityRenderObject(RenderManager manager, Entity entity, Render value)
    {
        return SkinPort.RenderManager_getEntityRenderObject(manager, entity, value);
    }

    public static void RenderManager_postRenderManagerInit(RenderManager manager)
    {
        SkinPort.RenderManager_postRenderManagerInit(manager);
    }

}
