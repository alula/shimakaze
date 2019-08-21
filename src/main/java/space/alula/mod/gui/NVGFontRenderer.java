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
        draw(text, x, y, 16);
    }

    @Override
    public void draw(String text, int x, int y, float size) {
        if (vg == 0) return;

        nvgSave(vg);

        color.r(1);
        color.g(1);
        color.b(1);
        color.a(1);

        nvgFontSize(vg, size / 2);
        nvgFontFace(vg, "roboto");
        nvgTextAlign(vg, NVG_ALIGN_LEFT | NVG_ALIGN_TOP);
        //nvgTextMetrics(vg, null, null, metricBuffer);
        nvgFillColor(vg, color);
        nvgText(vg, (float) x, (float) y, text);
        nvgRestore(vg);
    }
}
