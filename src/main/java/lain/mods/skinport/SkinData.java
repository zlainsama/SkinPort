package lain.mods.skinport;

import java.awt.image.BufferedImage;
import java.util.UUID;
import lain.mods.skinport.api.ISkin;
import net.minecraft.client.renderer.texture.SimpleTexture;
import net.minecraft.client.renderer.texture.TextureUtil;
import net.minecraft.util.ResourceLocation;

public class SkinData extends SimpleTexture implements ISkin
{

    public static String judgeSkinType(BufferedImage image)
    {
        if (image.getHeight() == 32)
            return "legacy";
        if (((image.getRGB(55, 20) & 0xFF000000) >>> 24) == 0)
            return "slim";
        return "default";
    }

    public final UUID uuid;

    private String type;
    private boolean uploaded;

    public SkinData()
    {
        this(UUID.randomUUID());
    }

    public SkinData(UUID uuid)
    {
        this(uuid, new ResourceLocation("skinport", String.format("textures/entity/%s", uuid)));
    }

    public SkinData(UUID uuid, ResourceLocation location)
    {
        super(location);
        this.uuid = uuid;
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

    public void put(BufferedImage image)
    {
        put(image, null);
    }

    public void put(BufferedImage image, String type)
    {
        if (image != null && type == null)
            type = judgeSkinType(image);

        uploaded = false;
        deleteGlTexture();

        this.type = type;

        if (image != null)
        {
            TextureUtil.uploadTextureImage(getGlTextureId(), image);
            uploaded = true;
        }
    }

}
