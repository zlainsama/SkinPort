package lain.mods.skinport.impl.forge.network.packet;

import java.util.UUID;
import cpw.mods.fml.relauncher.Side;
import io.netty.buffer.ByteBuf;
import lain.mods.skinport.impl.forge.SkinCustomization;
import lain.mods.skinport.impl.forge.network.NetworkPacket;
import net.minecraft.entity.player.EntityPlayerMP;

public class PacketPut1 extends NetworkPacket
{

    UUID uuid;
    int value;

    public PacketPut1()
    {
    }

    public PacketPut1(UUID uuid, int value)
    {
        this.uuid = uuid;
        this.value = value;
    }

    @Override
    public void handlePacketClient()
    {
        SkinCustomization.Flags.put(Side.CLIENT, uuid, value);
    }

    @Override
    public void handlePacketServer(EntityPlayerMP player)
    {
    }

    @Override
    public void readFromBuffer(ByteBuf buf)
    {
        uuid = new UUID(buf.readLong(), buf.readLong());
        value = buf.readInt();
    }

    @Override
    public void writeToBuffer(ByteBuf buf)
    {
        buf.writeLong(uuid.getMostSignificantBits());
        buf.writeLong(uuid.getLeastSignificantBits());
        buf.writeInt(value);
    }

}
