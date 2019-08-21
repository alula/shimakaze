package space.alula.mod.util;

public class Util {
    private static int cycle;

    public static void updateCycle(float delta) {
        cycle += (int) (delta * 100);
    }

    public static int gay() {
        return gay(cycle);
    }

    public static int gay(int cycle) {
        double pi3 = 2 * Math.PI / 3;
        double freq = 0.001;
        return (((int) (Math.sin(freq * cycle) * 127 + 128) & 0xff) << 16)
                | (((int) (Math.sin(freq * cycle + pi3) * 127 + 128) & 0xff) << 8)
                | ((int) (Math.sin(freq * cycle + pi3 + pi3) * 127 + 128) & 0xff);
    }
}
