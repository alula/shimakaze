package space.alula.mod.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.util.InputMappings;
import org.lwjgl.glfw.GLFW;
import space.alula.mixins.mixin.IMixinMinecraft;

public class Overlay extends SDrawable {
    @Override
    public void render() {
        if (InputMappings.isKeyDown(GLFW.GLFW_KEY_RIGHT_SHIFT)) {
            Minecraft.getInstance().displayGuiScreen(new SDrawableScreenWrapper(new CommandScreen()));
        }

        EntityPlayerSP entity = Minecraft.getInstance().player;

        setColor(0x80212121);
        drawRoundedRectMat(2, 2, 90, 47, 1);

        fontRenderer.draw("Shimakaze", 8, 5, 24);
        fontRenderer.draw("fps: " + IMixinMinecraft.getDebugFPS(), 8, 20);
        fontRenderer.draw("x: " + (int) entity.posX + " y: " + (int) entity.posY + " z: " + (int) entity.posZ, 8, 30);
        fontRenderer.draw("Press [RSHIFT] for CLI", 8, 40, 12);
    }
}
