package space.alula.mod.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiMultiplayer;
import net.minecraft.client.gui.GuiOptions;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiWorldSelection;
import net.minecraft.client.renderer.GlStateManager;
import space.alula.mod.util.Util;

public class MainMenuScreen extends GuiScreen {
    @Override
    protected void initGui() {
        int scaledWidth = Minecraft.getInstance().mainWindow.getScaledWidth();

        addButton(new SButton(1, (scaledWidth - 200) / 2, 90,
                "i have no internet or friends",
                () -> Minecraft.getInstance().displayGuiScreen(new GuiWorldSelection(this))));
        addButton(new SButton(2, (scaledWidth - 200) / 2, 115,
                "cheating on public servers is bad tbh",
                () -> Minecraft.getInstance().displayGuiScreen(new GuiMultiplayer(this))));
        addButton(new SButton(3, (scaledWidth - 200) / 2, 140,
                "switches and other shit",
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
        drawGradientRect((scaledWidth - 220) / 2, 0, (scaledWidth + 220) / 2, scaledHeight, 0x80000000, 0x80111111);

        GlStateManager.scaled(4, 4, 1);
        String text = "Shimakaze";
        drawCenteredString(Minecraft.getInstance().fontRenderer, text, scaledWidth / 8, 8, Util.gay());
        GlStateManager.scaled(0.25, 0.25, 1);

        String copyrightShit = "Minecraft is © Mojang AB blah blah blah, Shimakaze is made by Alula, skidding the code is forbidden.";
        drawString(Minecraft.getInstance().fontRenderer, copyrightShit, 2, scaledHeight - 10, 0xff999999);

        super.render(mouX, mouY, delta);
    }
}
