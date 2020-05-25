package lain.mods.skinport.init.forge;

import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.WeakHashMap;
import com.mojang.authlib.GameProfile;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import cpw.mods.fml.common.network.FMLNetworkEvent.ClientDisconnectionFromServerEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import lain.mods.skinport.impl.forge.SkinCustomization;
import lain.mods.skinport.impl.forge.SkinPortGuiCustomizeSkin;
import lain.mods.skinport.impl.forge.SkinPortModelHumanoidHead;
import lain.mods.skinport.impl.forge.SkinPortRenderPlayer;
import lain.mods.skinport.impl.forge.SpecialModel;
import lain.mods.skinport.impl.forge.SpecialRenderer;
import lain.mods.skinport.impl.forge.compat.SkinPortRenderPlayer_MPM;
import lain.mods.skinport.impl.forge.compat.SkinPortRenderPlayer_RPA;
import lain.mods.skins.api.SkinProviderAPI;
import lain.mods.skins.api.interfaces.ISkin;
import lain.mods.skins.impl.PlayerProfile;
import lain.mods.skins.impl.forge.CustomSkinTexture;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiOptions;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelSkeletonHead;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.texture.ITextureObject;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.client.renderer.tileentity.TileEntitySkullRenderer;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntitySkull;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

@SideOnly(Side.CLIENT)
public class ClientProxy extends CommonProxy
{

    private static final Map<String, Render> renderers = new HashMap<>();
    private static final Map<ByteBuffer, CustomSkinTexture> textures = new WeakHashMap<>();
    private static final SkinPortModelHumanoidHead modelHumanoidHead = new SkinPortModelHumanoidHead();

    public static ResourceLocation bindTexture(GameProfile profile, ResourceLocation result)
    {
        if (profile != null)
        {
            ISkin skin = SkinProviderAPI.SKIN.getSkin(PlayerProfile.wrapGameProfile(profile));
            if (skin != null && skin.isDataReady())
                return ClientProxy.getOrCreateTexture(skin.getData(), skin).getLocation();
        }
        return null;
    }

    public static ResourceLocation generateRandomLocation()
    {
        return new ResourceLocation("skinport", String.format("textures/generated/%s", UUID.randomUUID().toString()));
    }

    public static ModelSkeletonHead getHumanoidHead(ResourceLocation location, ModelSkeletonHead result)
    {
        ITextureObject texture = FMLClientHandler.instance().getClient().getTextureManager().getTexture(location);
        if (texture instanceof CustomSkinTexture)
            return modelHumanoidHead;
        return null;
    }

    public static ResourceLocation getLocationCape(AbstractClientPlayer player, ResourceLocation result)
    {
        ISkin skin = SkinProviderAPI.CAPE.getSkin(PlayerProfile.wrapGameProfile(player.getGameProfile()));
        if (skin != null && skin.isDataReady())
            return ClientProxy.getOrCreateTexture(skin.getData(), skin).getLocation();
        return null;
    }

    public static ResourceLocation getLocationSkin(AbstractClientPlayer player, ResourceLocation result)
    {
        ISkin skin = SkinProviderAPI.SKIN.getSkin(PlayerProfile.wrapGameProfile(player.getGameProfile()));
        if (skin != null && skin.isDataReady())
            return ClientProxy.getOrCreateTexture(skin.getData(), skin).getLocation();
        return null;
    }

    public static CustomSkinTexture getOrCreateTexture(ByteBuffer data, ISkin skin)
    {
        if (!textures.containsKey(data))
        {
            CustomSkinTexture texture = new CustomSkinTexture(generateRandomLocation(), data);
            FMLClientHandler.instance().getClient().getTextureManager().loadTexture(texture.getLocation(), texture);
            textures.put(data, texture);

            if (skin != null)
            {
                skin.setRemovalListener(s -> {
                    if (data == s.getData())
                    {
                        // addScheduledTask
                        FMLClientHandler.instance().getClient().func_152344_a(() -> {
                            FMLClientHandler.instance().getClient().getTextureManager().deleteTexture(texture.getLocation());
                            textures.remove(data);
                        });
                    }
                });
            }
        }
        return textures.get(data);
    }

    public static Render getPlayerRenderer(RenderManager manager, AbstractClientPlayer player, Render result)
    {
        if (renderers.isEmpty())
            setupRenderers(manager);
        result = renderers.getOrDefault(getSkinType(player), result);
        if (result instanceof SpecialRenderer)
            ((SpecialRenderer) result).onGetRenderer(manager, player);
        return result;
    }

    public static String getSkinType(AbstractClientPlayer player)
    {
        ResourceLocation location = getLocationSkin(player, null);
        if (location != null)
        {
            ISkin skin = SkinProviderAPI.SKIN.getSkin(PlayerProfile.wrapGameProfile(player.getGameProfile()));
            if (skin != null && skin.isDataReady())
                return skin.getSkinType();
        }
        return "default";
    }

    public static boolean hasCape(AbstractClientPlayer player, boolean result)
    {
        return player.getLocationCape() != null;
    }

    public static boolean hasSkin(AbstractClientPlayer player, boolean result)
    {
        return player.getLocationSkin() != null;
    }

    public static int initHeight(ModelBiped model, int textureHeight)
    {
        if (model instanceof SpecialModel)
            return ((SpecialModel) model).initHeight();
        return textureHeight;
    }

    public static int initWidth(ModelBiped model, int textureWidth)
    {
        if (model instanceof SpecialModel)
            return ((SpecialModel) model).initWidth();
        return textureWidth;
    }

    @SideOnly(Side.CLIENT)
    public static void onButtonAction(GuiOptions gui, GuiButton button)
    {
        if (!button.enabled || button.id != 110)
            return;
        gui.mc.gameSettings.saveOptions();
        gui.mc.displayGuiScreen(new SkinPortGuiCustomizeSkin(gui));
    }

    public static void setupButton(GuiOptions gui, List<GuiButton> buttonList)
    {
        buttonList.add(new GuiButton(110, gui.width / 2 - 155, gui.height / 6 + 48 - 6, 150, 20, I18n.format("options.skinCustomisation")));
    }

    public static void setupRenderers(RenderManager manager)
    {
        if (Loader.isModLoaded("moreplayermodels")) // Compatibility with MorePlayerModels
        {
            renderers.put("default", new SkinPortRenderPlayer_MPM(manager, false));
            renderers.put("slim", new SkinPortRenderPlayer_MPM(manager, true));
        }
        else if (Loader.isModLoaded("RenderPlayerAPI")) // Compatibility with RenderPlayerAPI
        {
            renderers.put("default", new SkinPortRenderPlayer_RPA(manager, false));
            renderers.put("slim", new SkinPortRenderPlayer_RPA(manager, true));
        }
        else
        {
            renderers.put("default", new SkinPortRenderPlayer(manager, false));
            renderers.put("slim", new SkinPortRenderPlayer(manager, true));
        }
    }

    @SubscribeEvent
    public void handleClientTicks(TickEvent.ClientTickEvent event)
    {
        if (event.phase == TickEvent.Phase.START)
        {
            World world = Minecraft.getMinecraft().theWorld;
            if (world != null)
            {
                for (Object obj : world.playerEntities)
                {
                    EntityPlayer player = (EntityPlayer) obj;
                    SkinProviderAPI.SKIN.getSkin(PlayerProfile.wrapGameProfile(player.getGameProfile()));
                    SkinProviderAPI.CAPE.getSkin(PlayerProfile.wrapGameProfile(player.getGameProfile()));
                }
                if (TileEntityRendererDispatcher.instance.getSpecialRendererByClass(TileEntitySkull.class).getClass() != TileEntitySkullRenderer.class) // Aggressively restore vanilla TileEntitySkullRenderer
                    ClientRegistry.bindTileEntitySpecialRenderer(TileEntitySkull.class, new TileEntitySkullRenderer());
            }
        }
    }

    @SubscribeEvent
    public void handleEvent(ClientDisconnectionFromServerEvent event)
    {
        SkinCustomization.Flags.clear(Side.CLIENT);
    }

}
