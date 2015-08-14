package lain.mods.skinport;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
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
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiOptions;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.resources.I18n;
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
    public static void GuiOptions_postInitGui(GuiOptions gui, List<GuiButton> buttonList)
    {
        buttonList.add(new GuiButton(110, gui.width / 2 - 155, gui.height / 6 + 48 - 6, 150, 20, I18n.format("options.skinCustomisation", new Object[0])));
    }

    @SideOnly(Side.CLIENT)
    public static void GuiOptions_preActionPerformed(GuiOptions gui, GuiButton button)
    {
        if (!button.enabled)
            return;
        if (button.id == 110)
        {
            gui.mc.gameSettings.saveOptions();
            gui.mc.displayGuiScreen(new SkinPortGuiCustomizeSkin(gui));
        }
    }

    @SideOnly(Side.CLIENT)
    public static void loadOptions()
    {
        clientFlags = SkinCustomization.getDefaultFlags();
        try
        {
            for (String line : Files.readAllLines(new File(Minecraft.getMinecraft().mcDataDir, "options_skinport.txt").toPath()))
            {
                String[] astring = line.split(":");
                if ("clientFlags".equals(astring[0]))
                    clientFlags = Integer.parseInt(astring[1]);
            }
        }
        catch (FileNotFoundException e)
        {
            saveOptions();
        }
        catch (IOException e)
        {
            System.err.println(String.format("Error loading options: %s", e));
        }
    }

    public static void onPut0(UUID uuid, int value)
    {
        // EntityPlayerMP player = findPlayer(uuid); if (player != null) { for (EntityPlayer watcher : player.getServerForPlayer().getEntityTracker().getTrackingPlayers(player)) network.sendTo(new PacketPut1(uuid, value), (EntityPlayerMP) watcher); }
        network.sendToAll(new PacketPut1(uuid, value));
    }

    @SideOnly(Side.CLIENT)
    public static Render RenderManager_getEntityRenderObject(RenderManager manager, Entity entity, Render value)
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

    @SideOnly(Side.CLIENT)
    public static void RenderManager_postRenderManagerInit(RenderManager manager)
    {
        skinMap = Maps.newHashMap();
        skinMap.put("default", new SkinPortRenderPlayer(false));
        skinMap.put("slim", new SkinPortRenderPlayer(true));

        for (Render entry : skinMap.values())
            entry.setRenderManager(manager);
    }

    @SideOnly(Side.CLIENT)
    public static void saveOptions()
    {
        try
        {
            PrintWriter printwriter = new PrintWriter(new FileWriter(new File(Minecraft.getMinecraft().mcDataDir, "options_skinport.txt")));
            printwriter.println("clientFlags:" + clientFlags);
            printwriter.close();
        }
        catch (IOException e)
        {
            System.err.println(String.format("Error saving options: %s", e));
        }
    }

    @SideOnly(Side.CLIENT)
    public static void toggleModelPart(SkinCustomization part)
    {
        if (SkinCustomization.contains(clientFlags, part))
            clientFlags -= part.getFlag();
        else
            clientFlags += part.getFlag();
        saveOptions();
        Minecraft mc = Minecraft.getMinecraft();
        if (mc.thePlayer != null)
        {
            clientCache.put(mc.thePlayer.getUniqueID(), clientFlags);
            new PacketGet0().handlePacketClient();
        }
    }

    public static final NetworkManager network = new NetworkManager("skinport");

    public static LoadingCache<UUID, Integer> serverCache;
    @SideOnly(Side.CLIENT)
    public static LoadingCache<UUID, Integer> clientCache;
    @SideOnly(Side.CLIENT)
    public static Map<String, Render> skinMap;
    @SideOnly(Side.CLIENT)
    public static int clientFlags;

    @Mod.EventHandler
    public void init(FMLPreInitializationEvent event)
    {
        serverCache = CacheBuilder.newBuilder().expireAfterAccess(30, TimeUnit.MINUTES).build(new CacheLoader<UUID, Integer>()
        {

            @Override
            public Integer load(UUID key) throws Exception
            {
                EntityPlayerMP player = findPlayer(key);
                if (player != null)
                    network.sendTo(new PacketGet0(), player);
                return SkinCustomization.getDefaultFlags();
            }

        });

        if (event.getSide().isClient())
        {
            clientCache = CacheBuilder.newBuilder().expireAfterAccess(10, TimeUnit.MINUTES).build(new CacheLoader<UUID, Integer>()
            {

                @Override
                public Integer load(UUID key) throws Exception
                {
                    network.sendToServer(new PacketGet1(key));
                    return SkinCustomization.getDefaultFlags();
                }

            });
        }

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

            loadOptions();

            if (config.hasChanged())
                config.save();
        }
    }

}
