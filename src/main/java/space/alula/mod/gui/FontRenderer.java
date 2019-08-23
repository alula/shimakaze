package space.alula.mod.gui;

import java.nio.FloatBuffer;

public interface FontRenderer {
    void draw(String text, int x, int y);

    void draw(String text, int x, int y, float size);

    void draw(String text, int x, int y, float size, float r, float g, float b, float a, String font);

    FloatBuffer getMetrics();
}
