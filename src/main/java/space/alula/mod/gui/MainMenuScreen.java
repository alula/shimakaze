package space.alula.mod.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiMultiplayer;
import net.minecraft.client.gui.GuiOptions;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiWorldSelection;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;
import space.alula.mod.util.Util;

public class MainMenuScreen extends GuiScreen {
    private static final ResourceLocation SHIMAKAZE = new ResourceLocation("shimakaze", "textures/donotdmcameowo.png");

    @Override
    protected void initGui() {
        int scaledWidth = Minecraft.getInstance().mainWindow.getScaledWidth();

        addButton(new SButton(1, (scaledWidth - 200) / 2, 90,
                "i have no internet or friends",
                () -> Minecraft.getInstance().displayGuiScreen(new GuiWorldSelection(this))));
        addButton(new SButton(2, (scaledWidth - 200) / 2, 115,
                "i just want to play reeeeee",
                () -> Minecraft.getInstance().displayGuiScreen(new GuiMultiplayer(this))));
        addButton(new SButton(3, (scaledWidth - 200) / 2, 140,
                "where's that damn switch?",
                () -> Minecraft.getInstance().displayGuiScreen(new GuiOptions(this, Minecraft.getInstance().gameSettings))));
        addButton(new SButton(4, (scaledWidth - 200) / 2, 165,
                "fuck it",
                () -> Minecraft.getInstance().shutdown()));
    }

    @Override
    public void render(int mouX, int mouY, float delta) {
        int scaledWidth = Minecraft.getInstance().mainWindow.getScaledWidth();
        int scaledHeight = Minecraft.getInstance().mainWindow.getScaledHeight();

        drawDefaultBackground();
        Minecraft.getInstance().getTextureManager().bindTexture(SHIMAKAZE);
        GlStateManager.color4f(1, 1, 1, 1);
        GlStateManager.enableBlend();
        drawTexturedModalRect(scaledWidth - (335 / 2f) - 5, scaledHeight - (366 / 2f) - 5, 0, 0, 335, 366);
        drawGradientRect((scaledWidth - 220) / 2, 0, (scaledWidth + 220) / 2, scaledHeight, 0x80000000, 0x80111111);

        GlStateManager.scaled(4, 4, 1);
        String text = "Shimakaze";
        drawCenteredString(Minecraft.getInstance().fontRenderer, text, scaledWidth / 8, 8, Util.cycleColor());
        GlStateManager.scaled(0.25, 0.25, 1);

        String copyrightShit = "made by Alula, Minecraft is © Mojang AB *blah blah blah*.";
        drawString(Minecraft.getInstance().fontRenderer, copyrightShit, 2, scaledHeight - 10, 0xff999999);

        super.render(mouX, mouY, delta);
    }
}
