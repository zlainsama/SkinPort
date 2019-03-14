package lain.mods.skinport.init.forge;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.util.UUID;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import lain.mods.skinport.impl.forge.SkinCustomization;
import lain.mods.skinport.impl.forge.network.NetworkManager;
import lain.mods.skinport.impl.forge.network.packet.PacketGet0;
import lain.mods.skinport.impl.forge.network.packet.PacketGet1;
import lain.mods.skinport.impl.forge.network.packet.PacketPut0;
import lain.mods.skinport.impl.forge.network.packet.PacketPut1;
import lain.mods.skins.api.SkinProviderAPI;
import lain.mods.skins.api.interfaces.IPlayerProfile;
import lain.mods.skins.api.interfaces.ISkin;
import lain.mods.skins.api.interfaces.ISkinProvider;
import lain.mods.skins.impl.LegacyConversion;
import lain.mods.skins.impl.SkinData;
import lain.mods.skins.providers.CrafatarCapeProvider;
import lain.mods.skins.providers.CrafatarSkinProvider;
import lain.mods.skins.providers.CustomServerCapeProvider;
import lain.mods.skins.providers.CustomServerSkinProvider;
import lain.mods.skins.providers.MojangCapeProvider;
import lain.mods.skins.providers.MojangSkinProvider;
import lain.mods.skins.providers.UserManagedCapeProvider;
import lain.mods.skins.providers.UserManagedSkinProvider;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;

@Mod(modid = "skinport", useMetadata = true)
public class ForgeSkinPort
{

    private static class DefaultSkinProvider implements ISkinProvider
    {

        ISkin DefaultSteve;
        ISkin DefaultAlex;

        DefaultSkinProvider()
        {
            try
            {
                byte[] data;
                ((SkinData) (DefaultSteve = new SkinData())).put(data = IOUtils.toByteArray(DefaultSkinProvider.class.getResource("/DefaultSteve.png")), SkinData.judgeSkinType(data));
                ((SkinData) (DefaultAlex = new SkinData())).put(data = IOUtils.toByteArray(DefaultSkinProvider.class.getResource("/DefaultAlex.png")), SkinData.judgeSkinType(data));
            }
            catch (IOException e)
            {
                DefaultSteve = null;
                DefaultAlex = null;
            }
        }

        @Override
        public ISkin getSkin(IPlayerProfile profile)
        {
            UUID uuid;
            if ((uuid = profile.getPlayerID()) != null && (uuid.hashCode() & 0x1) == 1)
                return DefaultAlex;
            return DefaultSteve;
        }

    }

    @SidedProxy(clientSide = "lain.mods.skinport.init.forge.ClientProxy", serverSide = "lain.mods.skinport.init.forge.CommonProxy")
    public static CommonProxy proxy = new CommonProxy();
    public static NetworkManager network = new NetworkManager("skinport");

    public static void loadOptions()
    {
        try
        {
            for (String line : FileUtils.readLines(Paths.get(".", "options_skinport.txt").toFile(), StandardCharsets.UTF_8))
            {
                String[] as = line.split(":", 2);
                if (as.length != 2 || as[0].startsWith("#"))
                    continue;
                if ("clientFlags".equals(as[0]))
                    SkinCustomization.ClientFlags = Integer.parseInt(as[1]);
            }
        }
        catch (FileNotFoundException | NumberFormatException e)
        {
            saveOptions();
        }
        catch (IOException e)
        {
            System.err.println(String.format("Error loading options: %s", e.getMessage()));
        }
    }

    public static void saveOptions()
    {
        try
        {
            FileUtils.write(Paths.get(".", "options_skinport.txt").toFile(), String.format("clientFlags:%d", SkinCustomization.ClientFlags), StandardCharsets.UTF_8);
        }
        catch (IOException e)
        {
            System.err.println(String.format("Error saving options: %s", e.getMessage()));
        }
    }

    @Mod.EventHandler
    public void init(FMLPreInitializationEvent event)
    {
        if (event.getSide().isClient())
        {
            loadOptions();

            Configuration config = new Configuration(event.getSuggestedConfigurationFile());
            boolean useMojang = config.getBoolean("useMojang", "client", true, "");
            boolean useCrafatar = config.getBoolean("useCrafatar", "client", true, "");
            boolean useCustomServer = config.getBoolean("useCustomServer", "client", false, "");
            String hostCustomServer = config.getString("hostCustomServer", "client", "http://example.com", "/skins/(uuid|username) and /capes/(uuid|username) will be queried for respective resources");
            if (config.hasChanged())
                config.save();

            SkinProviderAPI.SKIN.clearProviders();
            SkinProviderAPI.SKIN.registerProvider(new UserManagedSkinProvider(Paths.get(".", "cachedImages")).withFilter(LegacyConversion.createFilter()));
            if (useCustomServer)
                SkinProviderAPI.SKIN.registerProvider(new CustomServerSkinProvider().setHost(hostCustomServer).withFilter(LegacyConversion.createFilter()));
            if (useMojang)
                SkinProviderAPI.SKIN.registerProvider(new MojangSkinProvider().withFilter(LegacyConversion.createFilter()));
            if (useCrafatar)
                SkinProviderAPI.SKIN.registerProvider(new CrafatarSkinProvider().withFilter(LegacyConversion.createFilter()));
            SkinProviderAPI.SKIN.registerProvider(new DefaultSkinProvider());

            SkinProviderAPI.CAPE.clearProviders();
            SkinProviderAPI.CAPE.registerProvider(new UserManagedCapeProvider(Paths.get(".", "cachedImages")));
            if (useCustomServer)
                SkinProviderAPI.CAPE.registerProvider(new CustomServerCapeProvider().setHost(hostCustomServer));
            if (useMojang)
                SkinProviderAPI.CAPE.registerProvider(new MojangCapeProvider());
            if (useCrafatar)
                SkinProviderAPI.CAPE.registerProvider(new CrafatarCapeProvider());
        }

        network.registerPacket(1, PacketGet0.class);
        network.registerPacket(2, PacketPut0.class);
        network.registerPacket(3, PacketGet1.class);
        network.registerPacket(4, PacketPut1.class);

        MinecraftForge.EVENT_BUS.register(proxy);
        FMLCommonHandler.instance().bus().register(proxy);
    }

}
