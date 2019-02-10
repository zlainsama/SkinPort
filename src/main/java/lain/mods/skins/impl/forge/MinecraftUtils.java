package lain.mods.skins.impl.forge;

import java.net.Proxy;
import com.mojang.authlib.minecraft.MinecraftSessionService;
import cpw.mods.fml.client.FMLClientHandler;

public class MinecraftUtils
{

    public static Proxy getProxy()
    {
        return FMLClientHandler.instance().getClient().getProxy();
    }

    public static MinecraftSessionService getSessionService()
    {
        return FMLClientHandler.instance().getClient().func_152347_ac(); // getSessionService()
    }

}
