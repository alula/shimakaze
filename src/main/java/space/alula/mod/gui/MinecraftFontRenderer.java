package space.alula.mod.gui;

import net.minecraft.client.Minecraft;

public class MinecraftFontRenderer implements FontRenderer {
    @Override
    public void draw(String text, int x, int y) {
        Minecraft.getInstance().fontRenderer.drawStringWithShadow(text, x, y, 0xffffffff);
    }
}
