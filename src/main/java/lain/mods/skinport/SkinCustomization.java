package lain.mods.skinport;

import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.IChatComponent;

public enum SkinCustomization
{

    cape,
    jacket,
    left_sleeve,
    right_sleeve,
    left_pants_leg,
    right_pants_leg,
    hat;

    public static boolean contains(int flags, SkinCustomization... parts)
    {
        return (flags & of(parts)) != 0;
    }

    public static int getDefaultFlags()
    {
        return _defaultFlags;
    }

    public static int of(SkinCustomization... parts)
    {
        int flags = 0;
        for (SkinCustomization part : parts)
            flags += part._flag;
        return flags;
    }

    private final IChatComponent _displayName = new ChatComponentTranslation("options.modelPart." + name(), new Object[0]);
    private final int _flag = (int) Math.pow(2, ordinal());
    private static final int _defaultFlags = of(values());

    public IChatComponent getDisplayName()
    {
        return _displayName;
    }

    public int getFlag()
    {
        return _flag;
    }

}
