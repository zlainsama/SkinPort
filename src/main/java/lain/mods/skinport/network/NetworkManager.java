package lain.mods.skinport.network;

import java.util.EnumMap;
import net.minecraft.entity.player.EntityPlayerMP;
import cpw.mods.fml.common.network.FMLEmbeddedChannel;
import cpw.mods.fml.common.network.FMLOutboundHandler;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint;
import cpw.mods.fml.relauncher.Side;

public class NetworkManager
{

    private final NetworkPacketCodec codec = new NetworkPacketCodec();
    private final NetworkPacketHandler handler = new NetworkPacketHandler();

    private EnumMap<Side, FMLEmbeddedChannel> channels;

    public NetworkManager(String channelName)
    {
        channels = NetworkRegistry.INSTANCE.newChannel(channelName, codec);
        for (FMLEmbeddedChannel channel : channels.values())
            channel.pipeline().addAfter(channel.findChannelHandlerNameForType(codec.getClass()), "NetworkPacketHandler", handler);
    }

    public void registerPacket(int discriminator, Class<? extends NetworkPacket> packetClass)
    {
        if (discriminator < 0 || discriminator > 255)
            throw new IllegalArgumentException("Invalid discriminator, valid range: 0-255.");
        codec.addDiscriminator(discriminator, packetClass);
    }

    public void sendTo(NetworkPacket packet, EntityPlayerMP player)
    {
        if (packet != null && player != null)
        {
            channels.get(Side.SERVER).attr(FMLOutboundHandler.FML_MESSAGETARGET).set(FMLOutboundHandler.OutboundTarget.PLAYER);
            channels.get(Side.SERVER).attr(FMLOutboundHandler.FML_MESSAGETARGETARGS).set(player);
            channels.get(Side.SERVER).writeAndFlush(packet);
        }
    }

    public void sendToAll(NetworkPacket packet)
    {
        if (packet != null)
        {
            channels.get(Side.SERVER).attr(FMLOutboundHandler.FML_MESSAGETARGET).set(FMLOutboundHandler.OutboundTarget.ALL);
            channels.get(Side.SERVER).writeAndFlush(packet);
        }
    }

    public void sendToAllAround(NetworkPacket packet, TargetPoint point)
    {
        if (packet != null && point != null)
        {
            channels.get(Side.SERVER).attr(FMLOutboundHandler.FML_MESSAGETARGET).set(FMLOutboundHandler.OutboundTarget.ALLAROUNDPOINT);
            channels.get(Side.SERVER).attr(FMLOutboundHandler.FML_MESSAGETARGETARGS).set(point);
            channels.get(Side.SERVER).writeAndFlush(packet);
        }
    }

    public void sendToDimension(NetworkPacket packet, int dimensionId)
    {
        if (packet != null)
        {
            channels.get(Side.SERVER).attr(FMLOutboundHandler.FML_MESSAGETARGET).set(FMLOutboundHandler.OutboundTarget.DIMENSION);
            channels.get(Side.SERVER).attr(FMLOutboundHandler.FML_MESSAGETARGETARGS).set(dimensionId);
            channels.get(Side.SERVER).writeAndFlush(packet);
        }
    }

    public void sendToServer(NetworkPacket packet)
    {
        if (packet != null)
        {
            channels.get(Side.CLIENT).attr(FMLOutboundHandler.FML_MESSAGETARGET).set(FMLOutboundHandler.OutboundTarget.TOSERVER);
            channels.get(Side.CLIENT).writeAndFlush(packet);
        }
    }

}
