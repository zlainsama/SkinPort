package lain.mods.skinport.providers;

import java.awt.image.BufferedImage;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import javax.imageio.ImageIO;
import lain.mods.skinport.LegacyConversion;
import lain.mods.skinport.SkinData;
import lain.mods.skinport.api.ISkin;
import lain.mods.skinport.api.ISkinProvider;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.util.StringUtils;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.cache.RemovalListener;
import com.google.common.cache.RemovalNotification;

public class MojangSkinProviderService
{

    public static ISkinProvider createSkinProvider()
    {
        return new ISkinProvider()
        {

            @Override
            public ISkin getSkin(AbstractClientPlayer player)
            {
                return skinCache.getUnchecked(StringUtils.stripControlCodes(player.getCommandSenderName()));
            }

        };
    }

    private static final ExecutorService pool = new ThreadPoolExecutor(1, 4, 10, TimeUnit.MINUTES, new LinkedBlockingQueue<Runnable>());
    private static final LoadingCache<String, SkinData> skinCache = CacheBuilder.newBuilder().expireAfterAccess(5, TimeUnit.MINUTES).removalListener(new RemovalListener<String, SkinData>()
    {

        @Override
        public void onRemoval(RemovalNotification<String, SkinData> notification)
        {
            SkinData data = notification.getValue();
            if (data != null)
                data.deleteTexture();
        }

    }).build(new CacheLoader<String, SkinData>()
    {

        @Override
        public SkinData load(String key) throws Exception
        {
            final SkinData data = new SkinData();
            data.username = key;
            pool.execute(new Runnable()
            {

                @Override
                public void run()
                {
                    for (int n = 0; n < 5; n++)
                    {
                        try
                        {
                            if (n > 0)
                                Thread.sleep(1000 * n);
                            BufferedImage image = ImageIO.read(new URL(String.format("http://skins.minecraft.net/MinecraftSkins/%s.png", data.username)));
                            data.put(new LegacyConversion().convert(image), SkinData.judgeSkinType(image));
                            break;
                        }
                        catch (Exception e)
                        {
                        }
                    }
                }

            });
            return data;
        }

    });

}
