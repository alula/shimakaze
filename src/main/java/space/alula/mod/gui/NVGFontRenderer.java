package space.alula.mod.gui;

import org.lwjgl.nanovg.NVGColor;

import java.nio.FloatBuffer;

import static org.lwjgl.nanovg.NanoVG.*;
import static space.alula.mod.gui.SDrawable.vg;

public class NVGFontRenderer implements FontRenderer {
    private FloatBuffer metricBuffer = FloatBuffer.allocate(4);
    private NVGColor color = NVGColor.create();

    @Override
    public void draw(String text, int x, int y) {
        draw(text, x, y, 16, 1, 1, 1, 1, "roboto");
    }

    public void draw(String text, int x, int y, float size) {
        draw(text, x, y, size, 1, 1, 1, 1, "roboto");
    }

    @Override
    public void draw(String text, int x, int y, float size, float r, float g, float b, float a, String font) {
        if (vg == 0) return;

        nvgSave(vg);

        color.r(r);
        color.g(g);
        color.b(b);
        color.a(a);

        nvgFontSize(vg, size / 2);
        nvgFontFace(vg, font);
        nvgTextAlign(vg, NVG_ALIGN_LEFT | NVG_ALIGN_TOP);
        nvgTextMetrics(vg, null, null, metricBuffer);
        nvgFillColor(vg, color);
        nvgText(vg, (float) x, (float) y, text);
        nvgRestore(vg);
    }

    @Override
    public FloatBuffer getMetrics() {
        return metricBuffer;
    }
}
