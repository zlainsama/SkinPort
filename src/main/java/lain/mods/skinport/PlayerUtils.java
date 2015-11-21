package lain.mods.skinport;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.common.MinecraftForge;
import com.google.common.base.Charsets;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent.PlayerLoggedOutEvent;
import cpw.mods.fml.common.network.FMLNetworkEvent.ClientDisconnectionFromServerEvent;

public class PlayerUtils
{

    public static class EventListener
    {

        @SubscribeEvent
        public void handleEvent(ClientDisconnectionFromServerEvent event)
        {
            clearCache();
        }

        @SubscribeEvent
        public void handleEvent(PlayerLoggedInEvent event)
        {
            getPlayerID(event.player);
        }

        @SubscribeEvent
        public void handleEvent(PlayerLoggedOutEvent event)
        {
            UUID uuid = getPlayerID(event.player);
            offlineIDs.remove(getOfflineID(uuid));
            offlineIDMap.remove(uuid);
        }

    }

    public static void clearCache()
    {
        offlineIDs.clear();
        offlineIDMap.clear();
    }

    @SuppressWarnings("unchecked")
    public static <T extends EntityPlayer> T findPlayer(UUID uuid)
    {
        try
        {
            List<EntityPlayerMP> playerList = (List<EntityPlayerMP>) FMLCommonHandler.instance().getMinecraftServerInstance().getConfigurationManager().playerEntityList;
            for (EntityPlayerMP player : playerList)
                if (uuid.equals(getPlayerID(player)))
                    return (T) player;
            if (!isOfflineID(uuid))
            {
                uuid = getOfflineID(uuid);
                for (EntityPlayerMP player : playerList)
                    if (uuid.equals(getPlayerID(player)))
                        return (T) player;
            }
        }
        catch (Exception e)
        {
        }
        return (T) null;
    }

    public static UUID getOfflineID(UUID uuid)
    {
        UUID result = uuid;
        if (!isOfflineID(uuid) && offlineIDMap.containsKey(uuid))
            result = offlineIDMap.get(uuid);
        return result;
    }

    public static UUID getPlayerID(EntityPlayer player)
    {
        UUID result = player.getUniqueID();

        if (result != null && !offlineIDMap.containsKey(result))
        {
            UUID offlineID = UUID.nameUUIDFromBytes(("OfflinePlayer:" + player.getGameProfile().getName()).getBytes(Charsets.UTF_8));
            offlineIDs.add(offlineID);
            offlineIDMap.put(result, offlineID);
        }

        return result;
    }

    public static boolean isOfflineID(UUID uuid)
    {
        return offlineIDs.contains(uuid);
    }

    public static void registerListener()
    {
        EventListener listener = new EventListener();
        MinecraftForge.EVENT_BUS.register(listener);
        FMLCommonHandler.instance().bus().register(listener);
    }

    static Set<UUID> offlineIDs = Sets.newHashSet();
    static Map<UUID, UUID> offlineIDMap = Maps.newHashMap();

}
