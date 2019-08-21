package space.alula.mixins.mixin;

import net.minecraft.client.Minecraft;
import net.minecraft.client.MouseHelper;
import net.minecraft.client.gui.GuiScreen;
import org.lwjgl.BufferUtils;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import space.alula.mod.util.Util;

import java.nio.FloatBuffer;

import static net.minecraft.client.gui.Gui.drawRect;

@Mixin(GuiScreen.class)
public abstract class MixinGuiScreen {
    private static final FloatBuffer starLayer1;
    private static final FloatBuffer starLayer2;
    private static final FloatBuffer starLayer3;

    static {
        starLayer1 = BufferUtils.createFloatBuffer(200);
        starLayer2 = BufferUtils.createFloatBuffer(200);
        starLayer3 = BufferUtils.createFloatBuffer(50);
        for (int i = 0; i < 100; i++) {
            starLayer1.put((float) Math.random());
            starLayer1.put((float) Math.random());
            starLayer2.put((float) Math.random());
            starLayer2.put((float) Math.random());
            if (i % 4 == 0) {
                starLayer3.put((float) Math.random());
                starLayer3.put((float) Math.random());
            }
        }
    }

    @Inject(method = "render", at = @At("RETURN"))
    public void postRender(int p_drawScreen_1_, int p_drawScreen_2_, float partialTicks, CallbackInfo ci) {
        Util.updateCycle(partialTicks);
    }

    @Overwrite
    public void drawBackground(int p_drawBackground_1_) {
        int scaledWidth = Minecraft.getInstance().mainWindow.getScaledWidth();
        int scaledHeight = Minecraft.getInstance().mainWindow.getScaledHeight();

        drawRect(0, 0, scaledWidth, scaledHeight, 0xff000000);
        MouseHelper helper = Minecraft.getInstance().mouseHelper;
        int mouX = (int) helper.getMouseX();
        int mouY = (int) helper.getMouseY();
        int parX = mouX - scaledWidth / 2;
        int parY = mouY - scaledWidth / 2;
        int x;
        int y;
        for (int n = 0; n < 100; n++) { // one noose please
            x = (int) (scaledWidth * starLayer1.get(n * 2)) - parX / 20;
            y = (int) (scaledHeight * starLayer1.get((n * 2) + 1)) - parY / 20;
            drawRect(x, y, x + 1, y + 1, 0xff888888);

            x = (int) (scaledWidth * starLayer2.get(n * 2)) - parX / 30;
            y = (int) (scaledHeight * starLayer2.get((n * 2) + 1)) - parY / 30;
            drawRect(x, y, x + 1, y + 1, 0xffaaaaaa);

            if (n < 25) {
                x = (int) (scaledWidth * starLayer3.get(n * 2)) - parX / 50;
                y = (int) (scaledHeight * starLayer3.get((n * 2) + 1)) - parY / 50;
                drawRect(x, y, x + 2, y + 2, 0xffcccccc);
            }
        }
    }
}
