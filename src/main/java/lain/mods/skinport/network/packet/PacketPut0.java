package lain.mods.skinport.network.packet;

import java.util.UUID;
import io.netty.buffer.ByteBuf;
import lain.mods.skinport.PlayerUtils;
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
        UUID uuid = PlayerUtils.getPlayerID(player);
        SkinPort.serverCache.put(uuid, value);
        SkinPort.onPut0(uuid, value);
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
