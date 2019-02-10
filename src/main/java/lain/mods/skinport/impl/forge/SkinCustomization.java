package lain.mods.skinport.impl.forge;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import cpw.mods.fml.relauncher.Side;
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

    public static class SidedOptionalTupleKeyMap<K, V>
    {

        Map<Side, Map<K, V>> map = new ConcurrentHashMap<>();

        public void clear(Side side)
        {
            getMap(side).clear();
        }

        public V get(Side side, K key)
        {
            return get(side, key, Optional.empty());
        }

        public V get(Side side, K key, Optional<K> key2)
        {
            Map<K, V> m = getMap(side);
            V v = m.get(key);
            if (v == null && key2.isPresent())
                v = m.get(key2.get());
            return v;
        }

        private Map<K, V> getMap(Side side)
        {
            if (!map.containsKey(side))
                map.putIfAbsent(side, new ConcurrentHashMap<>());
            return map.get(side);
        }

        public V put(Side side, K key, Optional<K> key2, V value)
        {
            Map<K, V> m = getMap(side);
            V v = m.put(key, value);
            if (key2.isPresent())
                m.put(key2.get(), value);
            return v;
        }

        public V put(Side side, K key, V value)
        {
            return put(side, key, Optional.empty(), value);
        }

        public V remove(Side side, K key)
        {
            return remove(side, key, Optional.empty());
        }

        public V remove(Side side, K key, Optional<K> key2)
        {
            Map<K, V> m = getMap(side);
            V v = m.remove(key);
            if (key2.isPresent())
                m.remove(key2.get());
            return v;
        }

    }

    public static final SidedOptionalTupleKeyMap<UUID, Integer> Flags = new SidedOptionalTupleKeyMap<>();
    public static int ClientFlags = getDefaultFlags();

    private static final int _defaultFlags = of(values());

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
            flags |= part._flag;
        return flags;
    }

    private final IChatComponent _displayName = new ChatComponentTranslation("options.modelPart." + name(), new Object[0]);
    private final int _flag = (int) Math.pow(2, ordinal());

    public IChatComponent getDisplayName()
    {
        return _displayName;
    }

    public int getFlag()
    {
        return _flag;
    }

}
