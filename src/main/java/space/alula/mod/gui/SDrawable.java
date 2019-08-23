package space.alula.mod.gui;

import org.lwjgl.nanovg.NVGColor;
import org.lwjgl.nanovg.NVGPaint;
import space.alula.mod.util.Util;

import java.nio.ByteBuffer;

import static org.lwjgl.nanovg.NanoVG.*;
import static org.lwjgl.nanovg.NanoVGGL2.NVG_ANTIALIAS;
import static org.lwjgl.nanovg.NanoVGGL2.nvgCreate;

public abstract class SDrawable implements AutoCloseable {
    protected static FontRenderer fontRenderer = new NVGFontRenderer();
    static long vg;
    static volatile boolean inFrame = false;
    private static int roboto;
    private static int robotoBold;
    private static int inconsolata;
    private static ByteBuffer robotoTTF;
    private static ByteBuffer robotoBoldTTF;
    private static ByteBuffer inconsolataTTF;
    private static volatile NVGColor color = NVGColor.create();
    private static volatile NVGColor colorA = NVGColor.create();
    private static volatile NVGColor colorB = NVGColor.create();
    private static volatile NVGPaint shadowPaint = NVGPaint.create();

    private volatile boolean closed = false;

    public static void glInit() {
        try {
            vg = nvgCreate(NVG_ANTIALIAS);
            robotoTTF = Util.ioResourceToByteBuffer("assets/shimakaze/fonts/Roboto-Regular.ttf", 350 * 1024);
            roboto = nvgCreateFontMem(vg, "roboto", robotoTTF, 0);
            robotoBoldTTF = Util.ioResourceToByteBuffer("assets/shimakaze/fonts/Roboto-Bold.ttf", 350 * 1024);
            robotoBold = nvgCreateFontMem(vg, "roboto-bold", robotoBoldTTF, 0);
            inconsolataTTF = Util.ioResourceToByteBuffer("assets/shimakaze/fonts/Inconsolata-Regular.ttf", 350 * 1024);
            inconsolata = nvgCreateFontMem(vg, "inconsolata", inconsolataTTF, 0);

            if (roboto == -1) throw new IllegalStateException("roboto font loading failed!");
            if (robotoBold == -1) throw new IllegalStateException("roboto bold font loading failed!");
            if (inconsolata == -1) throw new IllegalStateException("inconsolata font loading failed!");
        } catch (Exception e) {
            throw new RuntimeException("GLInit failed", e);
        }
    }

    protected int width;
    protected int height;
    protected double scale;

    public void draw(int width, int height, double scale) {
        if (closed) return;

        this.width = width;
        this.height = height;
        this.scale = scale;

        nvgBeginFrame(vg, width, height, (float) scale);

        inFrame = true;
        render();
        inFrame = false;

        nvgEndFrame(vg);
    }

    public abstract void render();

    public void setColor(int rgba) {
        if (closed) return;

        color.r(((rgba >> 16) & 0xff) / 255f);
        color.g(((rgba >> 8) & 0xff) / 255f);
        color.b((rgba & 0xff) / 255f);
        color.a(((rgba >> 24) & 0xff) / 255f);
    }

    public void setColor(float r, float g, float b) {
        if (closed) return;

        color.r(r);
        color.g(g);
        color.b(b);
        color.a(1);
    }

    public void setColor(float r, float g, float b, float a) {
        if (closed) return;

        color.r(r);
        color.g(g);
        color.b(b);
        color.a(a);
    }

    public void drawRect(int x, int y, int width, int height) {
        if (closed) return;

        nvgSave(vg);
        nvgBeginPath(vg);
        nvgRect(vg, x, y, width, height);
        nvgFillColor(vg, color);
        nvgFill(vg);
        nvgRestore(vg);
    }

    public void drawRoundedRect(int x, int y, int width, int height, float radius) {
        if (closed) return;

        nvgSave(vg);
        nvgBeginPath(vg);
        nvgRoundedRect(vg, x, y, width, height, radius);
        nvgFillColor(vg, color);
        nvgFill(vg);
        nvgRestore(vg);
    }

    public void drawRoundedRectMat(int x, int y, int width, int height, int elevation) {
        if (closed) return;

        nvgBoxGradient(vg, x, y + (float) elevation, width, height, 2, elevation + 2f,
                rgba(0, 0, 0, 128, colorA),
                rgba(0, 0, 0, 0, colorB),
                shadowPaint);
        nvgBeginPath(vg);
        nvgRect(vg, x - 10f, y - 10f, width + 20f, height + 30f);
        nvgRoundedRect(vg, x, y, width, height, 1);
        nvgPathWinding(vg, NVG_HOLE);
        nvgFillPaint(vg, shadowPaint);
        nvgFill(vg);

        nvgSave(vg);
        nvgBeginPath(vg);
        nvgRoundedRect(vg, x, y, width, height, 1);
        nvgFillColor(vg, color);
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
        if (!closed) {
            closed = true;
        }
    }
}
