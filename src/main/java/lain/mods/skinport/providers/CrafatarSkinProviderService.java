package lain.mods.skinport.providers;

import java.awt.image.BufferedImage;
import java.net.HttpURLConnection;
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
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;

public class CrafatarSkinProviderService
{

    public static ISkinProvider createSkinProvider()
    {
        return new ISkinProvider()
        {

            @Override
            public ISkin getSkin(AbstractClientPlayer player)
            {
                final SkinData data = new SkinData();
                data.profile = player.getGameProfile();
                pool.execute(new Runnable()
                {

                    @Override
                    public void run()
                    {
                        String url = String.format("https://crafatar.com/skins/%s", data.profile.getId());
                        boolean flag = false;
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
                                    flag = true;
                                    break;
                                }
                            }
                            catch (Exception e)
                            {
                            }
                        }
                        if (!flag)
                        {
                            url = String.format("https://crafatar.com/skins/%s", data.profile.getName());
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
                                        flag = true;
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

        };
    }

    private static final ExecutorService pool = new ThreadPoolExecutor(1, 4, 10, TimeUnit.MINUTES, new LinkedBlockingQueue<Runnable>());

}
