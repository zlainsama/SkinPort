package lain.mods.skinport;

import api.player.model.ModelPlayerAPI;
import api.player.model.ModelPlayerBase;

public class SkinPortModelPlayerBase extends ModelPlayerBase
{

    public SkinPortModelPlayerBase(ModelPlayerAPI modelPlayerAPI)
    {
        super(modelPlayerAPI);
        // TODO Auto-generated constructor stub
    }

    @Override
    public void beforeRender(net.minecraft.entity.Entity paramEntity, float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6)
    {
        System.out.println("beforeRender");
    }

}
