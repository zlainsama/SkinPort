package lain.mods.skinport.impl.forge.network.packet;

import java.util.UUID;
import cpw.mods.fml.relauncher.Side;
import io.netty.buffer.ByteBuf;
import lain.mods.skinport.impl.forge.SkinCustomization;
import lain.mods.skinport.impl.forge.network.NetworkPacket;
import lain.mods.skinport.init.forge.ForgeSkinPort;
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
        UUID uuid = player.getUniqueID();
        SkinCustomization.Flags.put(Side.SERVER, uuid, value);
        ForgeSkinPort.network.sendToAll(new PacketPut1(uuid, value));
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
