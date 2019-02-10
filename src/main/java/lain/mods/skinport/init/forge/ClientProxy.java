package lain.mods.skinport.init.forge;

import java.lang.reflect.Field;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.WeakHashMap;
import java.util.function.Predicate;
import com.mojang.authlib.GameProfile;
import cpw.mods.fml.client.FMLClientHandler;
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
import lain.mods.skinport.impl.forge.compat.SkinPortRenderPlayer_RPA;
import lain.mods.skins.api.SkinProviderAPI;
import lain.mods.skins.api.interfaces.ISkin;
import lain.mods.skins.impl.PlayerProfile;
import lain.mods.skins.impl.forge.CustomSkinTexture;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiOptions;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.model.ModelSkeletonHead;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.texture.ITextureObject;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.client.event.RenderPlayerEvent;

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
            return modelHumanoidHead.setTextureSize(((CustomSkinTexture) texture).getWidth(), ((CustomSkinTexture) texture).getHeight());
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
        return renderers.getOrDefault(getSkinType(player), result);
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
        return getLocationCape(player, null) != null;
    }

    public static boolean hasSkin(AbstractClientPlayer player, boolean result)
    {
        return getLocationSkin(player, null) != null;
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
        if (Loader.isModLoaded("RenderPlayerAPI")) // Compatibility with RenderPlayerAPI
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

    private final Map<Class<?>, Optional<Field[]>> allSubModelFields = new HashMap<Class<?>, Optional<Field[]>>();;

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
            }
        }
    }

    @SubscribeEvent
    public void handleEvent(ClientDisconnectionFromServerEvent event)
    {
        SkinCustomization.Flags.clear(Side.CLIENT);
    }

    @SubscribeEvent
    public void handlePlayerRender_Post(RenderPlayerEvent.Post event)
    {
        ModelBiped model = event.renderer.modelBipedMain;
        setSubModelTextureSize_Main(model, 64, 64);
        setSubModelTextureSize_Cape(model, 64, 32);
    }

    @SubscribeEvent
    public void handlePlayerRender_Pre(RenderPlayerEvent.Pre event)
    {
        EntityPlayer p = event.entityPlayer;
        if (p instanceof AbstractClientPlayer)
        {
            AbstractClientPlayer player = (AbstractClientPlayer) p;
            ModelBiped model = event.renderer.modelBipedMain;
            ResourceLocation locSkin = getLocationSkin(player, null);
            ResourceLocation locCape = getLocationCape(player, null);
            if (locSkin != null)
            {
                ITextureObject texture = FMLClientHandler.instance().getClient().getTextureManager().getTexture(locSkin);
                if (texture instanceof CustomSkinTexture)
                    setSubModelTextureSize_Main(model, ((CustomSkinTexture) texture).getWidth(), ((CustomSkinTexture) texture).getHeight());
            }
            if (locCape != null)
            {
                ITextureObject texture = FMLClientHandler.instance().getClient().getTextureManager().getTexture(locCape);
                if (texture instanceof CustomSkinTexture)
                    setSubModelTextureSize_Cape(model, ((CustomSkinTexture) texture).getWidth(), ((CustomSkinTexture) texture).getHeight());
            }
        }
    }

    private void setSubModelTextureSize(ModelBase model, int width, int height, Predicate<ModelRenderer> filter)
    {
        if (allSubModelFields == null)
            return;

        Class<?> clazz = model.getClass();
        if (!allSubModelFields.containsKey(clazz))
        {
            try
            {
                List<Field> fields = new ArrayList<Field>();
                do
                {
                    for (Field f : clazz.getDeclaredFields())
                    {
                        try
                        {
                            if (ModelRenderer.class.isAssignableFrom(f.getType()))
                            {
                                f.setAccessible(true);
                                fields.add(f);
                            }
                        }
                        catch (Exception e)
                        {
                        }
                    }
                    clazz = clazz.getSuperclass();
                }
                while (clazz != null);
                if (fields.isEmpty())
                    allSubModelFields.put(clazz, Optional.empty());
                else
                {
                    Field[] found = new Field[fields.size()];
                    fields.toArray(found);
                    allSubModelFields.put(clazz, Optional.of(found));
                }
            }
            catch (Exception e)
            {
                allSubModelFields.put(clazz, Optional.empty());
            }
        }

        allSubModelFields.get(clazz).ifPresent(fields -> {
            for (Field f : fields)
            {
                Object o = null;
                try
                {
                    o = f.get(model);
                }
                catch (Exception e)
                {
                }
                if (o != null)
                {
                    ModelRenderer m = (ModelRenderer) o;
                    if (filter.test(m))
                        m.setTextureSize(width, height);
                }
            }
        });
    }

    private void setSubModelTextureSize_Cape(ModelBase model, int width, int height)
    {
        setSubModelTextureSize(model, width, height, m -> m.textureWidth == m.textureHeight * 2);
    }

    private void setSubModelTextureSize_Main(ModelBase model, int width, int height)
    {
        setSubModelTextureSize(model, width, height, m -> m.textureWidth == m.textureHeight);
    }

}
