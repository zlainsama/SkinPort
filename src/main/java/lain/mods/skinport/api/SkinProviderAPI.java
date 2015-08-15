package lain.mods.skinport.api;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.util.ResourceLocation;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.cache.RemovalListener;
import com.google.common.cache.RemovalNotification;
import com.google.common.collect.Lists;

public class SkinProviderAPI
{

    public static void clearRegistry()
    {
        primaryProvider = null;
        secondaryProviders.clear();
    }

    public static ISkin getDefaultSkin(AbstractClientPlayer player)
    {
        if ((player.getUniqueID().hashCode() & 0x1) == 1)
            return SKIN_ALEX;
        return SKIN_STEVE;
    }

    public static ISkinProvider getPrimaryProvider()
    {
        return primaryProvider;
    }

    public static List<ISkinProvider> getSecondaryProviders()
    {
        return Collections.unmodifiableList(secondaryProviders);
    }

    public static ISkin getSkin(AbstractClientPlayer player)
    {
        ISkin result = primarySkinCache.getUnchecked(player);
        if (result.isSkinReady())
            return result;
        for (ISkin s : secondarySkinCache.getUnchecked(player))
        {
            if (s.isSkinReady())
            {
                result = s;
                break;
            }
        }
        return result;
    }

    public static void register(ISkinProvider provider, boolean primary)
    {
        if (primary)
            primaryProvider = provider;
        else
            secondaryProviders.add(provider);
    }

    private static ISkinProvider primaryProvider;
    private static List<ISkinProvider> secondaryProviders = Lists.newArrayList();

    private static final LoadingCache<AbstractClientPlayer, ISkin> primarySkinCache = CacheBuilder.newBuilder().expireAfterAccess(15, TimeUnit.SECONDS).removalListener(new RemovalListener<AbstractClientPlayer, ISkin>()
    {

        @Override
        public void onRemoval(RemovalNotification<AbstractClientPlayer, ISkin> notification)
        {
            ISkin skin = notification.getValue();
            if (skin != null)
                skin.onRemoval();
        }

    }).build(new CacheLoader<AbstractClientPlayer, ISkin>()
    {

        @Override
        public ISkin load(AbstractClientPlayer key) throws Exception
        {
            return primaryProvider.getSkin(key);
        }

    });
    private static final LoadingCache<AbstractClientPlayer, List<ISkin>> secondarySkinCache = CacheBuilder.newBuilder().expireAfterAccess(15, TimeUnit.SECONDS).removalListener(new RemovalListener<AbstractClientPlayer, List<ISkin>>()
    {

        @Override
        public void onRemoval(RemovalNotification<AbstractClientPlayer, List<ISkin>> notification)
        {
            List<ISkin> list = notification.getValue();
            if (list != null)
            {
                for (ISkin skin : list)
                    skin.onRemoval();
            }
        }

    }).build(new CacheLoader<AbstractClientPlayer, List<ISkin>>()
    {

        @Override
        public List<ISkin> load(AbstractClientPlayer key) throws Exception
        {
            List<ISkin> list = Lists.newArrayList();
            for (ISkinProvider p : secondaryProviders)
            {
                ISkin s = p.getSkin(key);
                if (s != null)
                    list.add(s);
            }
            return list;
        }

    });

    public static final ISkin SKIN_STEVE = new ISkin()
    {

        ResourceLocation texture = new ResourceLocation("skinport", "textures/entity/steve.png");

        @Override
        public ResourceLocation getSkinLocation()
        {
            return texture;
        }

        @Override
        public String getSkinType()
        {
            return "default";
        }

        @Override
        public boolean isSkinReady()
        {
            return true;
        }

        @Override
        public void onRemoval()
        {
        }

    };
    public static final ISkin SKIN_ALEX = new ISkin()
    {

        ResourceLocation texture = new ResourceLocation("skinport", "textures/entity/alex.png");

        @Override
        public ResourceLocation getSkinLocation()
        {
            return texture;
        }

        @Override
        public String getSkinType()
        {
            return "slim";
        }

        @Override
        public boolean isSkinReady()
        {
            return true;
        }

        @Override
        public void onRemoval()
        {
        }

    };

}
