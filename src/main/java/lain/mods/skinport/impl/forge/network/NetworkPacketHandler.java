package lain.mods.skinport.impl.forge.network;

import cpw.mods.fml.common.network.NetworkRegistry;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import net.minecraft.network.NetHandlerPlayServer;

@Sharable
public class NetworkPacketHandler extends SimpleChannelInboundHandler<NetworkPacket>
{

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, NetworkPacket msg) throws Exception
    {
        if (ctx.channel().attr(NetworkRegistry.CHANNEL_SOURCE).get().isServer())
            msg.handlePacketServer(((NetHandlerPlayServer) ctx.channel().attr(NetworkRegistry.NET_HANDLER).get()).playerEntity);
        else
            msg.handlePacketClient();
    }

}
