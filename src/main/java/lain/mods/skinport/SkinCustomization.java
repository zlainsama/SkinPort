package lain.mods.skinport;

public enum SkinCustomization
{

    cape,
    jacket,
    left_sleeve,
    right_sleeve,
    left_pants_leg,
    right_pants_leg,
    hat;

    public static boolean contains(int value, SkinCustomization... flags)
    {
        return (value & of(flags)) != 0;
    }

    public static int getDefaultValue()
    {
        return _defaultValue;
    }

    public static int of(SkinCustomization... flags)
    {
        int value = 0;
        for (SkinCustomization flag : flags)
            value += flag._value;
        return value;
    }

    private final int _value = (int) Math.pow(2, ordinal());
    private static final int _defaultValue = of(values());

}
