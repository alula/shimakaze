package space.alula.mixins.mixin;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import space.alula.mod.util.Util;

@Mixin(GuiButton.class)
public abstract class MixinGuiButton extends Gui {
    float fade = 0.0f;

    @Shadow
    private boolean hovered;
    @Shadow
    protected int width;
    @Shadow
    protected int height;

    @Overwrite
    public void render(int p_render_1_, int p_render_2_, float partialTicks) {
        GuiButton self = (GuiButton) (Object) this;
        if (!self.visible) return;
        this.hovered = p_render_1_ >= self.x && p_render_2_ >= self.y && p_render_1_ < self.x + this.width && p_render_2_ < self.y + this.height;
        int gay = Util.gay();

        float squared = fade * (2 - fade);
        int f = 0xa0 + (int) (squared * 0x40);
        int color = f << 16 | f << 8 | f;
        if (!self.enabled) {
            color = 0x666666;
        } else if (this.hovered) {
            if (fade < 1f) {
                fade = Math.min(fade + partialTicks * 0.1f, 1);
            }
        } else {
            if (fade > 0f) {
                fade = Math.max(fade - partialTicks * 0.1f, 0);
            }
        }

        int barWidth = (int) (this.width * (1 - squared)) / 2;
        drawCenteredString(Minecraft.getInstance().fontRenderer,
                self.displayString,
                self.x + this.width / 2,
                self.y + (this.height - 8) / 2,
                color);
        drawRect(self.x + barWidth,
                self.y + 19,
                self.x + this.width - barWidth,
                self.y + 20,
                (((int) (squared * 255)) << 24) | gay);
    }
}
