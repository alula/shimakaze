package space.alula.mixins.mixin;

import net.minecraft.client.MainWindow;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import space.alula.mod.gui.SDrawableScreenWrapper;
import space.alula.mod.main.Shimakaze;

@Mixin(MainWindow.class)
public class MixinMainWindow {
    @Shadow
    private int scaledWidth;
    @Shadow
    private int scaledHeight;
    @Shadow
    private double guiScaleFactor;

    @Inject(method = "update", at = @At("HEAD"))
    public void update(boolean wait, CallbackInfo ci) {
        if (wait) {
            Minecraft minecraft = Minecraft.getInstance();
            if (minecraft.world != null && minecraft.currentScreen == null) {
                GlStateManager.disableTexture2D();
                GlStateManager.enableBlend();
                GlStateManager.bindTexture(0);
                GlStateManager.pushMatrix();
                GlStateManager.ortho(0, scaledWidth, scaledHeight, 0, 0, 300);
                Shimakaze.getInstance().getOverlay().draw(scaledWidth, scaledHeight, guiScaleFactor);
                GlStateManager.popMatrix();
            }
        }
    }
}
