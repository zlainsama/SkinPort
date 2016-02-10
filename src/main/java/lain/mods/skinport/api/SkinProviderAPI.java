package lain.mods.skinport.api;

import java.util.List;
import java.util.concurrent.TimeUnit;
import lain.mods.skinport.PlayerUtils;
import lain.mods.skinport.SkinPort;
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

    public static ISkinProviderService createService()
    {
        return new ISkinProviderService()
        {

            private final List<ISkinProvider> providers = Lists.newArrayList();
            private final LoadingCache<AbstractClientPlayer, List<ISkin>> cache = CacheBuilder.newBuilder().expireAfterAccess(15, TimeUnit.SECONDS).removalListener(new RemovalListener<AbstractClientPlayer, List<ISkin>>()
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
                    for (ISkinProvider p : providers)
                    {
                        ISkin s = p.getSkin(key);
                        if (s != null)
                            list.add(s);
                    }
                    return list;
                }

            });

            @Override
            public void clear()
            {
                providers.clear();
                cache.invalidateAll();
            }

            @Override
            public ISkin getSkin(AbstractClientPlayer player)
            {
                List<ISkin> list = cache.getUnchecked(player);
                for (ISkin skin : list)
                {
                    if (skin.isSkinReady())
                        return skin;
                }
                return null;
            }

            @Override
            public void register(ISkinProvider provider)
            {
                if (provider == null || provider == this)
                    throw new UnsupportedOperationException();
                providers.add(provider);
            }

        };
    }

    public static ISkin getCape(AbstractClientPlayer player)
    {
        PlayerUtils.getPlayerID(player); // make sure offline info for this player is cached.
        if (SkinPort.capeService != null)
            return SkinPort.capeService.getSkin(player);
        return null;
    }

    public static ISkin getDefaultSkin(AbstractClientPlayer player)
    {
        if ((PlayerUtils.getPlayerID(player).hashCode() & 0x1) == 1)
            return SKIN_ALEX;
        return SKIN_STEVE;
    }

    public static ISkin getSkin(AbstractClientPlayer player)
    {
        PlayerUtils.getPlayerID(player); // make sure offline info for this player is cached.
        if (SkinPort.skinService != null)
            return SkinPort.skinService.getSkin(player);
        return null;
    }

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
