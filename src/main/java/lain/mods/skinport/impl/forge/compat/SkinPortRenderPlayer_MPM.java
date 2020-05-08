package lain.mods.skinport.impl.forge.compat;

import java.util.Optional;
import java.util.UUID;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import lain.mods.skinport.impl.forge.SkinCustomization;
import lain.mods.skinport.impl.forge.SpecialRenderer;
import lain.mods.skinport.impl.forge.network.packet.PacketGet1;
import lain.mods.skinport.init.forge.ForgeSkinPort;
import lain.mods.skins.impl.PlayerProfile;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.player.EntityPlayer;
import noppes.mpm.client.RenderEvent;
import noppes.mpm.client.RenderMPM;

@SideOnly(Side.CLIENT)
public class SkinPortRenderPlayer_MPM extends RenderMPM implements SpecialRenderer
{

    public SkinPortModelPlayer_MPM modelPlayer;

    public SkinPortRenderPlayer_MPM(RenderManager manager, boolean smallArms)
    {
        super();

        setRenderManager(manager);
        mainModel = new SkinPortModelPlayer_MPM(0.0F, smallArms);
        modelBipedMain = (ModelBiped) mainModel;
        modelPlayer = (SkinPortModelPlayer_MPM) mainModel;

        modelArmor = new SkinPortModelPlayer_MPM(0.5F, smallArms);
        modelArmorChestplate = new SkinPortModelPlayer_MPM(1.0F, smallArms);
    }

    @Override
    public void doRender(AbstractClientPlayer p_76986_1_, double p_76986_2_, double p_76986_4_, double p_76986_6_, float p_76986_8_, float p_76986_9_)
    {
        boolean smHeadwear = modelPlayer.bipedHeadwear.showModel;
        boolean smLeftLegwear = modelPlayer.bipedLeftLegwear.showModel;
        boolean smRightLegwear = modelPlayer.bipedRightLegwear.showModel;
        boolean smLeftArmwear = modelPlayer.bipedLeftArmwear.showModel;
        boolean smRightArmwear = modelPlayer.bipedRightArmwear.showModel;
        boolean smBodyWear = modelPlayer.bipedBodyWear.showModel;
        boolean smCloak = modelPlayer.bipedCloak.showModel;

        int flags = getFlags(p_76986_1_);
        if (modelPlayer.bipedHeadwear.showModel)
            modelPlayer.bipedHeadwear.showModel = SkinCustomization.contains(flags, SkinCustomization.hat);
        if (modelPlayer.bipedLeftLegwear.showModel)
            modelPlayer.bipedLeftLegwear.showModel = SkinCustomization.contains(flags, SkinCustomization.left_pants_leg);
        if (modelPlayer.bipedRightLegwear.showModel)
            modelPlayer.bipedRightLegwear.showModel = SkinCustomization.contains(flags, SkinCustomization.right_pants_leg);
        if (modelPlayer.bipedLeftArmwear.showModel)
            modelPlayer.bipedLeftArmwear.showModel = SkinCustomization.contains(flags, SkinCustomization.left_sleeve);
        if (modelPlayer.bipedRightArmwear.showModel)
            modelPlayer.bipedRightArmwear.showModel = SkinCustomization.contains(flags, SkinCustomization.right_sleeve);
        if (modelPlayer.bipedBodyWear.showModel)
            modelPlayer.bipedBodyWear.showModel = SkinCustomization.contains(flags, SkinCustomization.jacket);
        if (modelPlayer.bipedCloak.showModel)
            modelPlayer.bipedCloak.showModel = SkinCustomization.contains(flags, SkinCustomization.cape);

        super.doRender(p_76986_1_, p_76986_2_, p_76986_4_, p_76986_6_, p_76986_8_, p_76986_9_);

        modelPlayer.bipedHeadwear.showModel = smHeadwear;
        modelPlayer.bipedLeftLegwear.showModel = smLeftLegwear;
        modelPlayer.bipedRightLegwear.showModel = smRightLegwear;
        modelPlayer.bipedLeftArmwear.showModel = smLeftArmwear;
        modelPlayer.bipedRightArmwear.showModel = smRightArmwear;
        modelPlayer.bipedBodyWear.showModel = smBodyWear;
        modelPlayer.bipedCloak.showModel = smCloak;
    }

    private int getFlags(EntityPlayer player)
    {
        if (player == Minecraft.getMinecraft().thePlayer)
            return SkinCustomization.ClientFlags;
        UUID uuid = player.getUniqueID();
        Optional<UUID> uuid2 = Optional.ofNullable(PlayerProfile.wrapGameProfile(player.getGameProfile()).getPlayerID());
        Integer flags = SkinCustomization.Flags.get(Side.CLIENT, uuid, uuid2);
        if (flags == null)
        {
            SkinCustomization.Flags.put(Side.CLIENT, uuid, uuid2, flags = SkinCustomization.getDefaultFlags());
            ForgeSkinPort.network.sendToServer(new PacketGet1(uuid));
        }
        return flags;
    }

    @Override
    public void onGetRenderer(RenderManager manager, AbstractClientPlayer player)
    {
        RenderEvent.renderer = this;
    }

    @Override
    protected void renderEquippedItems(AbstractClientPlayer p_77029_1_, float p_77029_2_)
    {
        boolean smHeadwear = modelPlayer.bipedHeadwear.showModel;
        boolean smLeftLegwear = modelPlayer.bipedLeftLegwear.showModel;
        boolean smRightLegwear = modelPlayer.bipedRightLegwear.showModel;
        boolean smLeftArmwear = modelPlayer.bipedLeftArmwear.showModel;
        boolean smRightArmwear = modelPlayer.bipedRightArmwear.showModel;
        boolean smBodyWear = modelPlayer.bipedBodyWear.showModel;
        boolean smCloak = modelPlayer.bipedCloak.showModel;

        int flags = getFlags(p_77029_1_);
        if (modelPlayer.bipedHeadwear.showModel)
            modelPlayer.bipedHeadwear.showModel = SkinCustomization.contains(flags, SkinCustomization.hat);
        if (modelPlayer.bipedLeftLegwear.showModel)
            modelPlayer.bipedLeftLegwear.showModel = SkinCustomization.contains(flags, SkinCustomization.left_pants_leg);
        if (modelPlayer.bipedRightLegwear.showModel)
            modelPlayer.bipedRightLegwear.showModel = SkinCustomization.contains(flags, SkinCustomization.right_pants_leg);
        if (modelPlayer.bipedLeftArmwear.showModel)
            modelPlayer.bipedLeftArmwear.showModel = SkinCustomization.contains(flags, SkinCustomization.left_sleeve);
        if (modelPlayer.bipedRightArmwear.showModel)
            modelPlayer.bipedRightArmwear.showModel = SkinCustomization.contains(flags, SkinCustomization.right_sleeve);
        if (modelPlayer.bipedBodyWear.showModel)
            modelPlayer.bipedBodyWear.showModel = SkinCustomization.contains(flags, SkinCustomization.jacket);
        if (modelPlayer.bipedCloak.showModel)
            modelPlayer.bipedCloak.showModel = SkinCustomization.contains(flags, SkinCustomization.cape);

        super.renderEquippedItems(p_77029_1_, p_77029_2_);

        modelPlayer.bipedHeadwear.showModel = smHeadwear;
        modelPlayer.bipedLeftLegwear.showModel = smLeftLegwear;
        modelPlayer.bipedRightLegwear.showModel = smRightLegwear;
        modelPlayer.bipedLeftArmwear.showModel = smLeftArmwear;
        modelPlayer.bipedRightArmwear.showModel = smRightArmwear;
        modelPlayer.bipedBodyWear.showModel = smBodyWear;
        modelPlayer.bipedCloak.showModel = smCloak;
    }

    @Override
    public void renderFirstPersonArm(EntityPlayer player)
    {
        boolean smHeadwear = modelPlayer.bipedHeadwear.showModel;
        boolean smLeftLegwear = modelPlayer.bipedLeftLegwear.showModel;
        boolean smRightLegwear = modelPlayer.bipedRightLegwear.showModel;
        boolean smLeftArmwear = modelPlayer.bipedLeftArmwear.showModel;
        boolean smRightArmwear = modelPlayer.bipedRightArmwear.showModel;
        boolean smBodyWear = modelPlayer.bipedBodyWear.showModel;
        boolean smCloak = modelPlayer.bipedCloak.showModel;

        int flags = getFlags(player);
        if (modelPlayer.bipedHeadwear.showModel)
            modelPlayer.bipedHeadwear.showModel = SkinCustomization.contains(flags, SkinCustomization.hat);
        if (modelPlayer.bipedLeftLegwear.showModel)
            modelPlayer.bipedLeftLegwear.showModel = SkinCustomization.contains(flags, SkinCustomization.left_pants_leg);
        if (modelPlayer.bipedRightLegwear.showModel)
            modelPlayer.bipedRightLegwear.showModel = SkinCustomization.contains(flags, SkinCustomization.right_pants_leg);
        if (modelPlayer.bipedLeftArmwear.showModel)
            modelPlayer.bipedLeftArmwear.showModel = SkinCustomization.contains(flags, SkinCustomization.left_sleeve);
        if (modelPlayer.bipedRightArmwear.showModel)
            modelPlayer.bipedRightArmwear.showModel = SkinCustomization.contains(flags, SkinCustomization.right_sleeve);
        if (modelPlayer.bipedBodyWear.showModel)
            modelPlayer.bipedBodyWear.showModel = SkinCustomization.contains(flags, SkinCustomization.jacket);
        if (modelPlayer.bipedCloak.showModel)
            modelPlayer.bipedCloak.showModel = SkinCustomization.contains(flags, SkinCustomization.cape);

        modelPlayer.isRiding = modelPlayer.isSneak = false;
        super.renderFirstPersonArm(player);

        modelPlayer.bipedHeadwear.showModel = smHeadwear;
        modelPlayer.bipedLeftLegwear.showModel = smLeftLegwear;
        modelPlayer.bipedRightLegwear.showModel = smRightLegwear;
        modelPlayer.bipedLeftArmwear.showModel = smLeftArmwear;
        modelPlayer.bipedRightArmwear.showModel = smRightArmwear;
        modelPlayer.bipedBodyWear.showModel = smBodyWear;
        modelPlayer.bipedCloak.showModel = smCloak;
    }

}
