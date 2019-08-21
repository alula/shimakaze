package space.alula.mod.gui;

import org.lwjgl.nanovg.NVGColor;
import org.lwjgl.nanovg.NVGPaint;

import static org.lwjgl.nanovg.NanoVG.*;
import static org.lwjgl.nanovg.NanoVGGL2.NVG_ANTIALIAS;
import static org.lwjgl.nanovg.NanoVGGL2.nvgCreate;

public abstract class SDrawable implements AutoCloseable {
    protected static FontRenderer fontRenderer = new MinecraftFontRenderer();
    private static long vg;
    protected NVGColor color = NVGColor.create();
    protected NVGColor colorA = NVGColor.create();
    protected NVGColor colorB = NVGColor.create();
    protected NVGPaint shadowPaint = NVGPaint.create();

    public static void glInit() {
        vg = nvgCreate(NVG_ANTIALIAS);
    }

    protected int width;
    protected int height;
    protected double scale;

    public void draw(int width, int height, double scale) {
        this.width = width;
        this.height = height;
        this.scale = scale;

        nvgBeginFrame(vg, width, height, (float) scale);
        render();
        nvgEndFrame(vg);
    }

    public abstract void render();

    protected void setColor(int rgba) {
        color.r(((rgba >> 16) & 0xff) / 255f);
        color.g(((rgba >> 8) & 0xff) / 255f);
        color.b((rgba & 0xff) / 255f);
        color.a(((rgba >> 24) & 0xff) / 255f);
    }

    protected void setColor(float r, float g, float b) {
        color.r(r);
        color.g(g);
        color.b(b);
        color.a(1);
    }

    protected void setColor(float r, float g, float b, float a) {
        color.r(r);
        color.g(g);
        color.b(b);
        color.a(a);
    }

    public void drawRoundedRect(int x, int y, int width, int height, float radius) {
        nvgSave(vg);
        nvgBeginPath(vg);
        nvgRoundedRect(vg, x, y, width, height, radius);
        nvgFillColor(vg, color);
        nvgFill(vg);
        nvgRestore(vg);
    }

    public void drawRoundedRectMat(int x, int y, int width, int height, int elevation) {
        nvgSave(vg);
        nvgBeginPath(vg);
        nvgRoundedRect(vg, x, y, width, height, 1);
        nvgFillColor(vg, color);
        nvgFill(vg);

        nvgBoxGradient(vg, x, y + 2f, width, height, 2, 10, rgba(0, 0, 0, 128, colorA), rgba(0, 0, 0, 0, colorB), shadowPaint);
        nvgBeginPath(vg);
        nvgRect(vg, x - 10f, y - 10f, width + 20f, height + 30f);
        nvgRoundedRect(vg, x, y, width, height, 1);
        nvgPathWinding(vg, NVG_HOLE);
        nvgFillPaint(vg, shadowPaint);
        nvgFill(vg);

        nvgRestore(vg);
    }

    private static NVGColor rgba(int r, int g, int b, int a, NVGColor color) {
        color.r(r / 255.0f);
        color.g(g / 255.0f);
        color.b(b / 255.0f);
        color.a(a / 255.0f);

        return color;
    }

    @Override
    public void close() {
        color.close();
        colorA.close();
        colorB.close();
    }
}