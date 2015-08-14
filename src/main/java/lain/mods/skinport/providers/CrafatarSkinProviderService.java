package lain.mods.skinport.providers;

import java.awt.image.BufferedImage;
import java.net.URL;
import java.util.UUID;
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
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.cache.RemovalListener;
import com.google.common.cache.RemovalNotification;

public class CrafatarSkinProviderService
{

    public static ISkinProvider createSkinProvider()
    {
        return new ISkinProvider()
        {

            @Override
            public ISkin getSkin(AbstractClientPlayer player)
            {
                return skinCache.getUnchecked(player.getUniqueID());
            }

        };
    }

    private static final ExecutorService pool = new ThreadPoolExecutor(1, 4, 10, TimeUnit.MINUTES, new LinkedBlockingQueue<Runnable>());
    private static final LoadingCache<UUID, SkinData> skinCache = CacheBuilder.newBuilder().expireAfterAccess(5, TimeUnit.MINUTES).removalListener(new RemovalListener<UUID, SkinData>()
    {

        @Override
        public void onRemoval(RemovalNotification<UUID, SkinData> notification)
        {
            SkinData data = notification.getValue();
            if (data != null)
                data.deleteTexture();
        }

    }).build(new CacheLoader<UUID, SkinData>()
    {

        @Override
        public SkinData load(UUID key) throws Exception
        {
            final SkinData data = new SkinData();
            data.uuid = key;
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
                            BufferedImage image = ImageIO.read(new URL(String.format("https://crafatar.com/skins/%s", data.uuid.toString().replaceAll("-", ""))));
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
