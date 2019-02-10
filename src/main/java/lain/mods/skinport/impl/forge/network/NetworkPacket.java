package lain.mods.skinport.impl.forge.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayerMP;

public abstract class NetworkPacket
{

    public abstract void handlePacketClient();

    public abstract void handlePacketServer(EntityPlayerMP player);

    public abstract void readFromBuffer(ByteBuf buf);

    public abstract void writeToBuffer(ByteBuf buf);

}
