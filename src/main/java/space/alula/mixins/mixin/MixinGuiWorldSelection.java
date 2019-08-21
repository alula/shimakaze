package space.alula.mixins.mixin;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiWorldSelection;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GuiWorldSelection.class)
public class MixinGuiWorldSelection extends GuiScreen {
    @Inject(method = "render", at = @At("HEAD"))
    public void render(int p_render_1_, int p_render_2_, float p_render_3_, CallbackInfo ci) {
        drawDefaultBackground();
    }
}
