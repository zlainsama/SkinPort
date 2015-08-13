package lain.mods.skinport;

import java.awt.image.BufferedImage;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import lain.mods.skinport.api.ISkin;
import lain.mods.skinport.api.ISkinProvider;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.renderer.texture.SimpleTexture;
import net.minecraft.client.renderer.texture.TextureUtil;
import net.minecraft.util.ResourceLocation;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.cache.RemovalListener;
import com.google.common.cache.RemovalNotification;

public class MojangSkinProviderService
{

    private static class SkinData extends SimpleTexture implements ISkin
    {

        final UUID uuid;

        String type = "default";
        BufferedImage image = null;
        boolean uploaded = false;

        public SkinData(UUID uuid)
        {
            super(new ResourceLocation("skinport", String.format("textures/entity/%s", uuid)));
            this.uuid = uuid;
        }

        @Override
        public int getGlTextureId()
        {
            if (!uploaded && image != null)
            {
                deleteGlTexture();

                TextureUtil.uploadTextureImage(super.getGlTextureId(), image);
                type = judgeSkinType(uuid, image);
                uploaded = true;
            }

            return super.getGlTextureId();
        }

        @Override
        public ResourceLocation getSkinLocation()
        {
            return textureLocation;
        }

        @Override
        public String getSkinType()
        {
            return type;
        }

        @Override
        public boolean isSkinReady()
        {
            return uploaded;
        }

        private void putImage(BufferedImage image)
        {
            this.image = image;
        }

    }

    private static final ExecutorService pool = new ThreadPoolExecutor(0, 2, 1, TimeUnit.MINUTES, new LinkedBlockingQueue<Runnable>());
    private static final LoadingCache<UUID, SkinData> skinCache = CacheBuilder.newBuilder().expireAfterAccess(5, TimeUnit.MINUTES).removalListener(new RemovalListener<UUID, SkinData>()
    {

        @Override
        public void onRemoval(RemovalNotification<UUID, SkinData> notification)
        {
            SkinData data = notification.getValue();
            if (data != null)
                data.deleteGlTexture();
        }

    }).build(new CacheLoader<UUID, SkinData>()
    {

        @Override
        public SkinData load(UUID key) throws Exception
        {
            final SkinData data = new SkinData(key);
            pool.execute(new Runnable()
            {

                @Override
                public void run()
                {
                    //TODO
                    data.putImage(null);
                }

            });
            return data;
        }

    });

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

    public static String judgeSkinType(UUID uuid, BufferedImage image)
    {
        // TODO
        return "default";
    }

}
