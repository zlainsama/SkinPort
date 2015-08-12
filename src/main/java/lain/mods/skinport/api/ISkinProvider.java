package lain.mods.skinport.api;

import net.minecraft.client.entity.AbstractClientPlayer;

public interface ISkinProvider
{

    /**
     * @param player
     * @return You can only return null when this provider is registered as secondary providers.
     */
    ISkin getSkin(AbstractClientPlayer player);

}
