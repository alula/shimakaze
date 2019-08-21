package space.alula.mixins.mixin;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiSlot;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.math.MathHelper;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(GuiSlot.class)
public abstract class MixinGuiSlot {
    @Shadow
    @Final
    protected Minecraft mc;

    @Shadow
    protected int width;
    @Shadow
    protected int height;
    @Shadow
    protected int top;
    @Shadow
    protected int bottom;
    @Shadow
    protected int right;
    @Shadow
    protected int left;
    @Shadow
    protected double amountScrolled;
    @Shadow
    protected boolean visible = true;
    @Shadow
    protected boolean hasListHeader;

    @Shadow
    protected abstract int getScrollBarX();

    @Shadow
    protected abstract void bindAmountScrolled();

    @Shadow
    protected abstract int getContentHeight();

    @Shadow
    protected abstract void drawListHeader(int p_drawListHeader_1_, int p_drawListHeader_2_, Tessellator p_drawListHeader_3_);

    @Shadow
    protected abstract void drawSelectionBox(int p_drawSelectionBox_1_, int p_drawSelectionBox_2_, int p_drawSelectionBox_3_, int p_drawSelectionBox_4_, float p_drawSelectionBox_5_);

    @Shadow
    protected abstract void renderDecorations(int p_renderDecorations_1_, int p_renderDecorations_2_);

    @Overwrite
    public void drawScreen(int p_drawScreen_1_, int p_drawScreen_2_, float p_drawScreen_3_) {
        GuiSlot self = (GuiSlot) (Object) this;

        if (this.visible) {
            //self.drawBackground();
            int lvt_4_1_ = this.getScrollBarX();
            int lvt_5_1_ = lvt_4_1_ + 6;
            this.bindAmountScrolled();
            GlStateManager.disableLighting();
            GlStateManager.disableFog();
            Tessellator lvt_6_1_ = Tessellator.getInstance();
            BufferBuilder lvt_7_1_ = lvt_6_1_.getBuffer();

            GL11.glEnable(GL11.GL_SCISSOR_TEST);
            double scaled = Minecraft.getInstance().mainWindow.getGuiScaleFactor();
            GL11.glScissor((int) (this.left * scaled), (int) ((this.height - this.bottom) * scaled),
                    (int) (this.width * scaled), (int) ((this.bottom - this.top) * scaled));

            int lvt_9_1_ = this.left + this.width / 2 - self.getListWidth() / 2 + 2;
            int lvt_10_1_ = this.top + 4 - (int) this.amountScrolled;
            if (this.hasListHeader) {
                this.drawListHeader(lvt_9_1_, lvt_10_1_, lvt_6_1_);
            }

            this.drawSelectionBox(lvt_9_1_, lvt_10_1_, p_drawScreen_1_, p_drawScreen_2_, p_drawScreen_3_);

            GL11.glDisable(GL11.GL_SCISSOR_TEST);
            GlStateManager.disableDepthTest();
            //this.overlayBackground(0, this.top, 255, 255);
            //this.overlayBackground(this.bottom, this.height, 255, 255);
            GlStateManager.enableBlend();
            GlStateManager.blendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ZERO, GlStateManager.DestFactor.ONE);
            GlStateManager.disableAlphaTest();
            GlStateManager.shadeModel(7425);
            GlStateManager.disableTexture2D();
            int lvt_12_1_ = self.getMaxScroll();
            if (lvt_12_1_ > 0) {
                int lvt_13_1_ = (int) ((float) ((this.bottom - this.top) * (this.bottom - this.top)) / (float) this.getContentHeight());
                lvt_13_1_ = MathHelper.clamp(lvt_13_1_, 32, this.bottom - this.top - 8);
                int lvt_14_1_ = (int) this.amountScrolled * (this.bottom - this.top - lvt_13_1_) / lvt_12_1_ + this.top;
                if (lvt_14_1_ < this.top) {
                    lvt_14_1_ = this.top;
                }

                lvt_7_1_.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
                lvt_7_1_.pos((double) lvt_4_1_, (double) this.bottom, 0.0D).tex(0.0D, 1.0D).color(0, 0, 0, 255).endVertex();
                lvt_7_1_.pos((double) lvt_5_1_, (double) this.bottom, 0.0D).tex(1.0D, 1.0D).color(0, 0, 0, 255).endVertex();
                lvt_7_1_.pos((double) lvt_5_1_, (double) this.top, 0.0D).tex(1.0D, 0.0D).color(0, 0, 0, 255).endVertex();
                lvt_7_1_.pos((double) lvt_4_1_, (double) this.top, 0.0D).tex(0.0D, 0.0D).color(0, 0, 0, 255).endVertex();
                lvt_6_1_.draw();
                lvt_7_1_.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
                lvt_7_1_.pos((double) lvt_4_1_, (double) (lvt_14_1_ + lvt_13_1_), 0.0D).tex(0.0D, 1.0D).color(128, 128, 128, 255).endVertex();
                lvt_7_1_.pos((double) lvt_5_1_, (double) (lvt_14_1_ + lvt_13_1_), 0.0D).tex(1.0D, 1.0D).color(128, 128, 128, 255).endVertex();
                lvt_7_1_.pos((double) lvt_5_1_, (double) lvt_14_1_, 0.0D).tex(1.0D, 0.0D).color(128, 128, 128, 255).endVertex();
                lvt_7_1_.pos((double) lvt_4_1_, (double) lvt_14_1_, 0.0D).tex(0.0D, 0.0D).color(128, 128, 128, 255).endVertex();
                lvt_6_1_.draw();
                lvt_7_1_.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
                lvt_7_1_.pos((double) lvt_4_1_, (double) (lvt_14_1_ + lvt_13_1_ - 1), 0.0D).tex(0.0D, 1.0D).color(192, 192, 192, 255).endVertex();
                lvt_7_1_.pos((double) (lvt_5_1_ - 1), (double) (lvt_14_1_ + lvt_13_1_ - 1), 0.0D).tex(1.0D, 1.0D).color(192, 192, 192, 255).endVertex();
                lvt_7_1_.pos((double) (lvt_5_1_ - 1), (double) lvt_14_1_, 0.0D).tex(1.0D, 0.0D).color(192, 192, 192, 255).endVertex();
                lvt_7_1_.pos((double) lvt_4_1_, (double) lvt_14_1_, 0.0D).tex(0.0D, 0.0D).color(192, 192, 192, 255).endVertex();
                lvt_6_1_.draw();
            }

            this.renderDecorations(p_drawScreen_1_, p_drawScreen_2_);

            GlStateManager.enableTexture2D();
            GlStateManager.shadeModel(7424);
            GlStateManager.enableAlphaTest();
            GlStateManager.disableBlend();
        }
    }
}
