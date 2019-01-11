package lain.mods.skinport.network.packet;

import java.util.UUID;
import io.netty.buffer.ByteBuf;
import lain.mods.skinport.SkinPort;
import lain.mods.skinport.network.NetworkPacket;
import net.minecraft.entity.player.EntityPlayerMP;

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
        SkinPort.network.sendTo(new PacketPut1(uuid, SkinPort.serverCache.getUnchecked(uuid)), player);
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
