package lain.mods.skinport;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class SkinPortGuiCustomizeSkin extends GuiScreen
{

    class ButtonPart extends GuiButton
    {

        private final SkinCustomization part;

        public ButtonPart(int arg0, int arg1, int arg2, int arg3, int arg4, SkinCustomization arg5)
        {
            super(arg0, arg1, arg2, arg3, arg4, printButtonText(arg5));
            part = arg5;
        }

    }

    private final GuiScreen parent;
    private String title;

    public SkinPortGuiCustomizeSkin(GuiScreen gui)
    {
        parent = gui;
    }

    @Override
    protected void actionPerformed(GuiButton button)
    {
        if (!button.enabled)
            return;

        if (button.id == 200)
        {
            mc.displayGuiScreen(parent);
        }
        else if (button instanceof ButtonPart)
        {
            ButtonPart a = (ButtonPart) button;
            SkinPort.toggleModelPart(a.part);
            button.displayString = printButtonText(a.part);
        }
    }

    @Override
    public void drawScreen(int arg0, int arg1, float arg2)
    {
        drawDefaultBackground();
        drawCenteredString(fontRendererObj, title, width / 2, 20, 16777215);

        super.drawScreen(arg0, arg1, arg2);
    }

    @Override
    public void initGui()
    {
        title = I18n.format("options.skinCustomisation.title", new Object[0]);
        int i = 0;
        for (SkinCustomization part : SkinCustomization.values())
        {
            buttonList.add(new ButtonPart(i, width / 2 - 155 + i % 2 * 160, height / 6 + 24 * (i >> 1), 150, 20, part));
            i++;
        }
        if (i % 2 == 1)
            i++;
        buttonList.add(new GuiButton(200, width / 2 - 100, height / 6 + 24 * (i >> 1), I18n.format("gui.done", new Object[0])));
    }

    private String printButtonText(SkinCustomization part)
    {
        return part.getDisplayName().getFormattedText() + ": " + (SkinCustomization.contains(SkinPort.clientFlags, part) ? I18n.format("options.on", new Object[0]) : I18n.format("options.off", new Object[0]));
    }

}
