package space.alula.mod.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;

public class SDrawableScreenWrapper extends GuiScreen {
    private final SDrawable drawable;

    public SDrawableScreenWrapper(SDrawable drawable) {
        this.drawable = drawable;
    }

    @Override
    public void render(int mouseX, int mouseY, float delta) {
        int scaledWidth = Minecraft.getInstance().mainWindow.getScaledWidth();
        int scaledHeight = Minecraft.getInstance().mainWindow.getScaledHeight();

        GlStateManager.disableTexture2D();
        GlStateManager.enableBlend();
        GlStateManager.bindTexture(0);
        GlStateManager.pushMatrix();
        GlStateManager.ortho(0, scaledWidth, scaledHeight, 0, 0, 300);
        drawable.draw(scaledWidth, scaledHeight, Minecraft.getInstance().mainWindow.getGuiScaleFactor());
        GlStateManager.popMatrix();
    }

    @Override
    public boolean keyPressed(int charCode, int scanCode, int idk) {
        if (drawable instanceof GuiInputHandler && charCode > 0xff
                && ((GuiInputHandler) drawable).keyPressed((char) charCode)) {
            return true;
        }

        return super.keyPressed(charCode, scanCode, idk);
    }

    @Override
    public boolean charTyped(char charCode, int idk) {
        if (drawable instanceof GuiInputHandler && charCode < 0xff && ((GuiInputHandler) drawable).keyPressed(charCode)) {
            return true;
        }

        return super.charTyped(charCode, idk);
    }

    @Override
    public void onGuiClosed() {
        drawable.close();
    }
}
