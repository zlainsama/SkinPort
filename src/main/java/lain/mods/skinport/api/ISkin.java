package lain.mods.skinport.api;

import net.minecraft.util.ResourceLocation;

public interface ISkin
{

    ResourceLocation getSkinLocation();

    /**
     * @return "default" for classical 4-pixel arms; "slim" for 3-pixel slim arms.
     */
    String getSkinType();

    /**
     * @return You should only return true when the texture is already uploaded to the TextureManager which means it's ready to use.
     */
    boolean isSkinReady();

}
