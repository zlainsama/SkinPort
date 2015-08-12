package lain.mods.skinport;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import lain.mods.skinport.network.NetworkManager;
import lain.mods.skinport.network.packet.PacketGet0;
import lain.mods.skinport.network.packet.PacketGet1;
import lain.mods.skinport.network.packet.PacketPut0;
import lain.mods.skinport.network.packet.PacketPut1;
import net.minecraft.entity.player.EntityPlayerMP;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@Mod(modid = "skinport", useMetadata = true)
public class SkinPort
{

    @SuppressWarnings("unchecked")
    public static EntityPlayerMP findPlayer(UUID uuid)
    {
        try
        {
            for (EntityPlayerMP player : (List<EntityPlayerMP>) FMLCommonHandler.instance().getMinecraftServerInstance().getConfigurationManager().playerEntityList)
                if (uuid.equals(player.getUniqueID()))
                    return player;
        }
        catch (Exception e)
        {
        }
        return null;
    }

    public static void onPut0(UUID uuid, int value)
    {
        // EntityPlayerMP player = findPlayer(uuid); if (player != null) { for (EntityPlayer watcher : player.getServerForPlayer().getEntityTracker().getTrackingPlayers(player)) SkinPort.network.sendTo(new PacketPut1(uuid, value), (EntityPlayerMP) watcher); }
        SkinPort.network.sendToAll(new PacketPut1(uuid, value));
    }

    public static final NetworkManager network = new NetworkManager("skinport");

    public static final LoadingCache<UUID, Integer> serverCache = CacheBuilder.newBuilder().expireAfterAccess(30, TimeUnit.MINUTES).build(new CacheLoader<UUID, Integer>()
    {

        @Override
        public Integer load(UUID key) throws Exception
        {
            EntityPlayerMP player = findPlayer(key);
            if (player != null)
                SkinPort.network.sendTo(new PacketGet0(), player);
            return SkinCustomization.getDefaultValue();
        }

    });

    @SideOnly(Side.CLIENT)
    public static final LoadingCache<UUID, Integer> clientCache = CacheBuilder.newBuilder().expireAfterAccess(10, TimeUnit.MINUTES).build(new CacheLoader<UUID, Integer>()
    {

        @Override
        public Integer load(UUID key) throws Exception
        {
            SkinPort.network.sendToServer(new PacketGet1(key));
            return SkinCustomization.getDefaultValue();
        }

    });

    @SideOnly(Side.CLIENT)
    public static int clientValue = SkinCustomization.getDefaultValue();

    @Mod.EventHandler
    public void init(FMLPreInitializationEvent event)
    {
        network.registerPacket(1, PacketGet0.class);
        network.registerPacket(2, PacketPut0.class);
        network.registerPacket(3, PacketGet1.class);
        network.registerPacket(4, PacketPut1.class);
    }

}
