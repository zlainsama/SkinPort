package lain.mods.skinport.api;

public interface ISkinProviderService extends ISkinProvider
{

    void clear();

    void register(ISkinProvider provider);

}
