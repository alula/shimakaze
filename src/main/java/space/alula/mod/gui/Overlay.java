package space.alula.mod.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import space.alula.mixins.mixin.IMixinMinecraft;

public class Overlay extends SDrawable {
    @Override
    public void render() {
        EntityPlayerSP entity = Minecraft.getInstance().player;
        setColor(0.1f, 0.1f, 0.1f, 0.5f);
        drawRoundedRectMat(2, 2, 90, 47, 1);
        fontRenderer.draw("Shimakaze", 8, 5, 24);
        fontRenderer.draw("fps: " + IMixinMinecraft.getDebugFPS(), 8, 20);
        fontRenderer.draw("x: " + (int) entity.posX + " y: " + (int) entity.posY + " z: " + (int) entity.posZ, 8, 30);
        fontRenderer.draw("Press [RSHIFT] for menu", 8, 40, 12);
    }
}
