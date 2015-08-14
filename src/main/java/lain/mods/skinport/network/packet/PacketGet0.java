package lain.mods.skinport.network.packet;

import io.netty.buffer.ByteBuf;
import lain.mods.skinport.SkinPort;
import lain.mods.skinport.network.NetworkPacket;
import net.minecraft.entity.player.EntityPlayerMP;

public class PacketGet0 extends NetworkPacket
{

    public PacketGet0()
    {
    }

    @Override
    public void handlePacketClient()
    {
        SkinPort.network.sendToServer(new PacketPut0(SkinPort.clientFlags));
    }

    @Override
    public void handlePacketServer(EntityPlayerMP player)
    {
    }

    @Override
    public void readFromBuffer(ByteBuf buf)
    {
    }

    @Override
    public void writeToBuffer(ByteBuf buf)
    {
    }

}
