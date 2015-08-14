package lain.mods.skinport;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import lain.mods.skinport.api.ISkin;
import lain.mods.skinport.api.SkinProviderAPI;
import lain.mods.skinport.network.NetworkManager;
import lain.mods.skinport.network.packet.PacketGet0;
import lain.mods.skinport.network.packet.PacketGet1;
import lain.mods.skinport.network.packet.PacketPut0;
import lain.mods.skinport.network.packet.PacketPut1;
import lain.mods.skinport.providers.CrafatarSkinProviderService;
import lain.mods.skinport.providers.MojangSkinProviderService;
import lain.mods.skinport.providers.UserManagedLocalSkinProviderService;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.common.config.Configuration;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.collect.Maps;
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

    @SideOnly(Side.CLIENT)
    public static Render getEntityRenderObject(RenderManager manager, Entity entity, Render value)
    {
        if (entity instanceof AbstractClientPlayer)
        {
            AbstractClientPlayer player = (AbstractClientPlayer) entity;
            ISkin skin = SkinProviderAPI.getSkin(player);
            if (!skin.isSkinReady())
                skin = SkinProviderAPI.getDefaultSkin(player);
            Render render = skinMap.get(skin.getSkinType());
            if (render != null)
                return render;
        }
        return value;
    }

    public static void onPut0(UUID uuid, int value)
    {
        // EntityPlayerMP player = findPlayer(uuid); if (player != null) { for (EntityPlayer watcher : player.getServerForPlayer().getEntityTracker().getTrackingPlayers(player)) SkinPort.network.sendTo(new PacketPut1(uuid, value), (EntityPlayerMP) watcher); }
        SkinPort.network.sendToAll(new PacketPut1(uuid, value));
    }

    @SideOnly(Side.CLIENT)
    public static void postRenderManagerInit(RenderManager manager)
    {
        skinMap.put("default", new SkinPortRenderPlayer(false));
        skinMap.put("slim", new SkinPortRenderPlayer(true));

        for (Render entry : skinMap.values())
            entry.setRenderManager(manager);
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

    @SideOnly(Side.CLIENT)
    public static final Map<String, Render> skinMap = Maps.newHashMap();

    @Mod.EventHandler
    public void init(FMLPreInitializationEvent event)
    {
        network.registerPacket(1, PacketGet0.class);
        network.registerPacket(2, PacketPut0.class);
        network.registerPacket(3, PacketGet1.class);
        network.registerPacket(4, PacketPut1.class);

        if (event.getSide().isClient())
        {
            Configuration config = new Configuration(event.getSuggestedConfigurationFile());
            SkinProviderAPI.register(MojangSkinProviderService.createSkinProvider(), true);
            SkinProviderAPI.register(UserManagedLocalSkinProviderService.createSkinProvider(), false);
            if (config.getBoolean("useCrafatar", Configuration.CATEGORY_GENERAL, true, "add Crafatar as secondary skin provider?"))
                SkinProviderAPI.register(CrafatarSkinProviderService.createSkinProvider(), false);
        }
    }

}
