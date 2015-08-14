package lain.mods.skinport.providers;

import java.awt.image.BufferedImage;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import javax.imageio.ImageIO;
import lain.mods.skinport.LegacyConversion;
import lain.mods.skinport.SkinData;
import lain.mods.skinport.api.ISkin;
import lain.mods.skinport.api.ISkinProvider;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.cache.RemovalListener;
import com.google.common.cache.RemovalNotification;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.minecraft.MinecraftProfileTexture;

public class MojangSkinProviderService
{

    public static ISkinProvider createSkinProvider()
    {
        return new ISkinProvider()
        {

            @Override
            public ISkin getSkin(AbstractClientPlayer player)
            {
                return skinCache.getUnchecked(player.getGameProfile());
            }

        };
    }

    private static final ExecutorService pool = new ThreadPoolExecutor(1, 4, 10, TimeUnit.MINUTES, new LinkedBlockingQueue<Runnable>());
    private static final LoadingCache<GameProfile, SkinData> skinCache = CacheBuilder.newBuilder().expireAfterAccess(5, TimeUnit.MINUTES).removalListener(new RemovalListener<GameProfile, SkinData>()
    {

        @Override
        public void onRemoval(RemovalNotification<GameProfile, SkinData> notification)
        {
            SkinData data = notification.getValue();
            if (data != null)
                data.deleteTexture();
        }

    }).build(new CacheLoader<GameProfile, SkinData>()
    {

        @Override
        public SkinData load(GameProfile key) throws Exception
        {
            final SkinData data = new SkinData();
            data.profile = key;
            pool.execute(new Runnable()
            {

                @Override
                public void run()
                {
                    @SuppressWarnings("unchecked")
                    Map<MinecraftProfileTexture.Type, MinecraftProfileTexture> textures = Minecraft.getMinecraft().func_152342_ad().func_152788_a(data.profile);
                    if (textures.containsKey(MinecraftProfileTexture.Type.SKIN))
                    {
                        String url = textures.get(MinecraftProfileTexture.Type.SKIN).getUrl();
                        for (int n = 0; n < 5; n++)
                        {
                            try
                            {
                                if (n > 0)
                                    Thread.sleep(1000 * n);
                                HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection(Minecraft.getMinecraft().getProxy());
                                conn.connect();
                                if (conn.getResponseCode() / 100 == 2)
                                {
                                    BufferedImage image = ImageIO.read(conn.getInputStream());
                                    String type = SkinData.judgeSkinType(image);
                                    if ("legacy".equals(type))
                                        type = "default";
                                    image = new LegacyConversion().convert(image);
                                    data.put(image, type);
                                    break;
                                }
                            }
                            catch (Exception e)
                            {
                            }
                        }
                    }
                }

            });
            return data;
        }

    });

}
