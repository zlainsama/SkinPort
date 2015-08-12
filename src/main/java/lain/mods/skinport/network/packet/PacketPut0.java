package lain.mods.skinport.network.packet;

import io.netty.buffer.ByteBuf;
import lain.mods.skinport.SkinPort;
import lain.mods.skinport.network.NetworkPacket;
import net.minecraft.entity.player.EntityPlayerMP;

public class PacketPut0 extends NetworkPacket
{

    int value;

    public PacketPut0()
    {
    }

    public PacketPut0(int value)
    {
        this.value = value;
    }

    @Override
    public void handlePacketClient()
    {
    }

    @Override
    public void handlePacketServer(EntityPlayerMP player)
    {
        SkinPort.serverCache.put(player.getUniqueID(), value);
        SkinPort.onPut0(player.getUniqueID(), value);
    }

    @Override
    public void readFromBuffer(ByteBuf buf)
    {
        value = buf.readInt();
    }

    @Override
    public void writeToBuffer(ByteBuf buf)
    {
        buf.writeInt(value);
    }

}
