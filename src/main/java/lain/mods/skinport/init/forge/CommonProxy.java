package lain.mods.skinport.init.forge;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent.PlayerLoggedOutEvent;
import cpw.mods.fml.relauncher.Side;
import lain.mods.skinport.impl.forge.SkinCustomization;
import lain.mods.skinport.impl.forge.network.packet.PacketGet0;
import net.minecraft.entity.player.EntityPlayerMP;

public class CommonProxy
{

    @SubscribeEvent
    public void handleEvent(PlayerLoggedInEvent event)
    {
        if (event.player instanceof EntityPlayerMP)
            ForgeSkinPort.network.sendTo(new PacketGet0(), (EntityPlayerMP) event.player);
    }

    @SubscribeEvent
    public void handleEvent(PlayerLoggedOutEvent event)
    {
        if (event.player instanceof EntityPlayerMP)
            SkinCustomization.Flags.remove(Side.SERVER, event.player.getUniqueID());
    }

}
