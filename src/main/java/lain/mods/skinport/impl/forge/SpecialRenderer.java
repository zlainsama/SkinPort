package lain.mods.skinport.impl.forge;

import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.renderer.entity.RenderManager;

public interface SpecialRenderer
{

    void onGetRenderer(RenderManager manager, AbstractClientPlayer player);

}
