package lain.mods.skinport.impl.forge.network.packet;

import java.util.UUID;
import cpw.mods.fml.relauncher.Side;
import io.netty.buffer.ByteBuf;
import lain.mods.skinport.impl.forge.SkinCustomization;
import lain.mods.skinport.impl.forge.network.NetworkPacket;
import lain.mods.skinport.init.forge.ForgeSkinPort;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;

public class PacketGet1 extends NetworkPacket
{

    UUID uuid;

    public PacketGet1()
    {
    }

    public PacketGet1(UUID uuid)
    {
        this.uuid = uuid;
    }

    @Override
    public void handlePacketClient()
    {
    }

    @Override
    public void handlePacketServer(EntityPlayerMP player)
    {
        Integer flags = SkinCustomization.Flags.get(Side.SERVER, uuid);
        if (flags == null)
        {
            SkinCustomization.Flags.put(Side.SERVER, uuid, flags = SkinCustomization.getDefaultFlags());
            for (Object obj : MinecraftServer.getServer().getConfigurationManager().playerEntityList)
            {
                EntityPlayerMP otherplayer = (EntityPlayerMP) obj;
                if (!otherplayer.getUniqueID().equals(uuid))
                    continue;
                ForgeSkinPort.network.sendTo(new PacketGet0(), otherplayer);
            }
        }
        ForgeSkinPort.network.sendTo(new PacketPut1(uuid, flags), player);
    }

    @Override
    public void readFromBuffer(ByteBuf buf)
    {
        uuid = new UUID(buf.readLong(), buf.readLong());
    }

    @Override
    public void writeToBuffer(ByteBuf buf)
    {
        buf.writeLong(uuid.getMostSignificantBits());
        buf.writeLong(uuid.getLeastSignificantBits());
    }

}
