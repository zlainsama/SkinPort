package lain.mods.skinport.init.forge.asm;

import java.util.Map;
import cpw.mods.fml.relauncher.IFMLLoadingPlugin;

@IFMLLoadingPlugin.Name("skinport")
@IFMLLoadingPlugin.MCVersion("")
@IFMLLoadingPlugin.TransformerExclusions("lain.mods.skinport.init.forge.asm.")
public class Plugin implements IFMLLoadingPlugin
{

    public static boolean runtimeDeobfuscationEnabled = false;
    public static boolean isDevelopmentEnvironment = false;

    @Override
    public String getAccessTransformerClass()
    {
        return null;
    }

    @Override
    public String[] getASMTransformerClass()
    {
        return new String[] { "lain.mods.skinport.init.forge.asm.ASMTransformer" };
    }

    @Override
    public String getModContainerClass()
    {
        return null;
    }

    @Override
    public String getSetupClass()
    {
        return "lain.mods.skinport.init.forge.asm.Setup";
    }

    @Override
    public void injectData(Map<String, Object> data)
    {
        runtimeDeobfuscationEnabled = (Boolean) data.get("runtimeDeobfuscationEnabled");
        isDevelopmentEnvironment = (getClass().getResource("/binpatches.pack.lzma") == null);
    }

}
